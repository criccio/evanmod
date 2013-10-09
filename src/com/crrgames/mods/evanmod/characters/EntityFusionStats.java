package com.crrgames.mods.evanmod.characters;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class EntityFusionStats {
    private int maxHealth;
    private ItemStack[] droppedItems;
    private String name;

    public EntityFusionStats(String name) {
        this.name = name;
        this.maxHealth = 110;
        this.setDroppedItems(new ItemStack(Block.cloth, 5));
    }

    public String getName() {
        return name;
    }

    public ItemStack[] getDroppedItems() {
        return droppedItems;
    }

    public void setDroppedItems(ItemStack... droppedItems) {
        this.droppedItems = droppedItems;
    }

    public int getMaxHealth() {
        return maxHealth;
    }
}
