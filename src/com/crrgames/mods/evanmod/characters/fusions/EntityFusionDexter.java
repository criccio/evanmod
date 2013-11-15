package com.crrgames.mods.evanmod.characters.fusions;

import com.crrgames.mods.evanmod.characters.EntityFusion;
import com.crrgames.mods.evanmod.characters.EntityFusionStats;
import net.minecraft.world.World;

public class EntityFusionDexter  extends EntityFusion
{
    private static final EntityFusionStats _stats = new EntityFusionStats("dexter");

    public EntityFusionDexter(World world) {
        super(world);
    }

    @Override
    public EntityFusionStats getStats() {
        return _stats;
    }
}
