package com.crrgames.mods.evanmod.characters;

import com.crrgames.mods.evanmod.util.ModStrings;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityOwnable;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

//The class for the fusion itself. we want to extend iron golem so we get alot of stuff for free, and only focus on
// determining our wool golem exists
public abstract class EntityFusion extends EntityIronGolem implements EntityOwnable {

    private EntityFusionStats _stats;

    private String creator;

    public EntityFusion(World world, EntityFusionStats stats) {
        super(world);
        this._stats = stats;
        this.setHealth(_stats.getMaxHealth());
    }

    public ResourceLocation getTexture()
    {
        return new ResourceLocation(ModStrings.ModID + ":textures/mob/" + _stats.getName() + ".png");
    }

    @Override
    public void dropFewItems(boolean recentlyHit, int lootingLevel) {
        //drop only the items we want, not the iron golem items
        for (ItemStack is : _stats.getDroppedItems()) {
            entityDropItem(is.copy(), 0F);
        }
    }

    public String getOwnerName(){
        return this.creator;
    }

    public Entity getOwner(){
        return this.worldObj.getPlayerEntityByName(this.getOwnerName());
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbt){
        super.writeEntityToNBT(nbt);
        nbt.setString("creator", this.creator == null ? "$null$" : this.creator);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt){
        super.readEntityFromNBT(nbt);
        String cr=nbt.getString("creator");
        this.creator = cr.equals("$null$") ? null : cr;
    }

    public static EntityFusion findAndSpawnAt(World world, int x, int y, int z)
    {
        int lowerBody = world.getBlockId(x, y - 2, z);
        int upperBody = world.getBlockId(x, y - 1, z);
        int leftArmX = world.getBlockId(x - 1, y - 1, z);
        int rightArmX = world.getBlockId(x + 1, y - 1, z);
        int leftArmZ = world.getBlockId(x, y - 1, z - 1);
        int rightArmZ = world.getBlockId(x, y - 1, z + 1);

        //It's not a character if any of the blocks are not wool
        //LowerBody and upperBody and left arm(either on x or z axis) and right arm (either on x or z axis) is wool
        if (lowerBody == Block.cloth.blockID
                && upperBody == Block.cloth.blockID
                && (leftArmX == Block.cloth.blockID || leftArmZ == Block.cloth.blockID)
                && (rightArmX == Block.cloth.blockID || rightArmZ == Block.cloth.blockID)
                ) {
            //all of the blocks around it are wool, so we need to get thier colors and see if we have a character for that
            int lowerBodyColor = world.getBlockMetadata(x, y - 2, z);
            int upperBodyColor = world.getBlockMetadata(x, y - 1, z);
            int leftArmColor = 99;
            int rightArmColor = 99;
            boolean isXAxis = true;

            if (leftArmX == Block.cloth.blockID && rightArmX == Block.cloth.blockID) {
                leftArmColor = world.getBlockMetadata(x - 1, y - 1, z);
                rightArmColor = world.getBlockMetadata(x + 1, y - 1, z);
            }

            if (leftArmZ == Block.cloth.blockID && rightArmZ == Block.cloth.blockID) {
                leftArmColor = world.getBlockMetadata(x, y - 1, z - 1);
                rightArmColor = world.getBlockMetadata(x, y - 1, z + 1);
                isXAxis = false;
            }

            EntityFusion character = detectCharacter(world, lowerBodyColor, upperBodyColor, leftArmColor, rightArmColor);
            if (character != null)
            {
                //we have a character for those wool colors, spawn it
                removeBlocks(world, x, y, z, isXAxis);
                character.setPlayerCreated(true);
                character.setLocationAndAngles((double) x + 0.5D, (double) y - 1.95D, (double) z + 0.5D, 0.0F, 0.0F);
                world.spawnEntityInWorld(character);
                notifyBlocks(world, x, y, z, isXAxis);
                return character;
            }
        }
        return null;
    }

    private static void removeBlocks(World world, int x, int y, int z, boolean isXAxis) {
        world.setBlock(x, y, z, 0, 0, 2);     //remove the head
        world.setBlock(x, y - 1, z, 0, 0, 2); //remove upper body
        world.setBlock(x, y - 2, z, 0, 0, 2); //remove lower body
        if (isXAxis) {
            world.setBlock(x - 1, y - 1, z, 0, 0, 2); //remove both arms
            world.setBlock(x + 1, y - 1, z, 0, 0, 2);
        } else {
            world.setBlock(x, y - 1, z - 1, 0, 0, 2); //remove both arms
            world.setBlock(x, y - 1, z + 1, 0, 0, 2);
        }
    }

    private static void notifyBlocks(World world, int x, int y, int z, boolean isXAxis) {
        world.notifyBlockChange(x, y, z, 0);
        world.notifyBlockChange(x, y - 1, z, 0);
        world.notifyBlockChange(x, y - 2, z, 0);
        if (isXAxis) {
            world.notifyBlockChange(x - 1, y - 1, z, 0);
            world.notifyBlockChange(x + 1, y - 1, z, 0);
        } else {
            world.notifyBlockChange(x, y - 1, z - 1, 0);
            world.notifyBlockChange(x, y - 1, z + 1, 0);
        }
    }

    private static EntityFusion detectCharacter(World world, int lowerBody, int upperBody, int leftArm, int rightArm) {
        EntityFusionStats stats = EntityFusionRegistry.checkRecipe(lowerBody, upperBody, leftArm, rightArm);
        if (stats != null) {
            try {
                return EntityFusion.class.getConstructor(World.class, EntityFusionStats.class)
                        .newInstance(world, stats);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}