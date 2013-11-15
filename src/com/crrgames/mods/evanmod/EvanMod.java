package com.crrgames.mods.evanmod;

import com.crrgames.mods.evanmod.blocks.NanoBlock;
import com.crrgames.mods.evanmod.characters.EntityFusionRegistry;
import com.crrgames.mods.evanmod.characters.fusions.EntityFusionDexter;
import com.crrgames.mods.evanmod.util.ModStrings;
import com.crrgames.mods.evanmod.util.WoolColor;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler; // used in 1.6.2
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = ModStrings.ModID, name = ModStrings.ModName, version = ModStrings.ModVersion)
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class EvanMod {
    private static NanoBlock nanoBlock;

    // The instance of your mod that Forge uses.
    @Instance(ModStrings.ModID)
    public static EvanMod instance;

    // Says where the client and server 'proxy' code is loaded.
    @SidedProxy(clientSide = ModStrings.ModClientProxy, serverSide = ModStrings.ModCommonProxy)
    public static CommonProxy proxy;

    @EventHandler // used in 1.6.2
    public void preInit(FMLPreInitializationEvent event) {
        nanoBlock = new NanoBlock(500, Material.ground);
        registerFusions();
    }

    @EventHandler // used in 1.6.2
    public void load(FMLInitializationEvent event) {
        LanguageRegistry.addName(nanoBlock, "Nano Block");
        MinecraftForge.setBlockHarvestLevel(nanoBlock, "shovel", 0);
        GameRegistry.registerBlock(nanoBlock, ModStrings.NanoBlockIconName);
        proxy.registerRenderers();
    }

    @EventHandler // used in 1.6.2
    public void postInit(FMLPostInitializationEvent event) {
        // Stub Method
    }

    private void registerFusions()
    {
        //name, lowerBody, rightArm, leftArm, upperBody
        EntityFusionRegistry.registerFusion("dexter", WoolColor.White, WoolColor.White, WoolColor.White, WoolColor.Black, EntityFusionDexter.class);
        //TODO: add remaining fusions
    }
}