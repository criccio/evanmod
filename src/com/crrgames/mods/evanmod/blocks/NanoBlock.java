package com.crrgames.mods.evanmod.blocks;

import com.crrgames.mods.evanmod.characters.EntityFusion;
import com.crrgames.mods.evanmod.util.ModStrings;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.world.World;

public class NanoBlock extends Block {
    public NanoBlock(int id, Material material) {
        super(id, material);
        setHardness(0.5F);
        setStepSound(Block.soundGravelFootstep);
        setCreativeTab(CreativeTabs.tabBlock);
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister) {
        this.blockIcon = par1IconRegister.registerIcon(ModStrings.NanoBlockIconName);
    }

    public void onBlockAdded(World world, int x, int y, int z) {
        super.onBlockAdded(world, x, y, z);
        EntityFusion.findAndSpawnAt(world, x, y, z);
    }
}
