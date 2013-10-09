package com.crrgames.mods.evanmod.client;

import com.crrgames.mods.evanmod.CommonProxy;
import com.crrgames.mods.evanmod.characters.EntityFusion;
import com.crrgames.mods.evanmod.characters.RenderEntityFusion;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.monster.EntityIronGolem;

public class ClientProxy extends CommonProxy {
    private static RenderEntityFusion fusionRenderer;

    @Override
    public void registerRenderers() {
        RenderManager.instance.entityRenderMap.remove(EntityIronGolem.class);
        fusionRenderer = new RenderEntityFusion();
        RenderManager.instance.entityRenderMap.put(EntityFusion.class,
                fusionRenderer);
        fusionRenderer.setRenderManager(RenderManager.instance);
    }
}
