package com.crrgames.mods.evanmod.characters;

import net.minecraft.client.renderer.entity.RenderIronGolem;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderEntityFusion extends RenderIronGolem
{
    protected ResourceLocation getEntityTexture(Entity entity) {
        return ((EntityFusion) entity).getTexture();
    }
}
