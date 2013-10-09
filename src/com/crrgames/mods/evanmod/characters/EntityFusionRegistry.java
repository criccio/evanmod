package com.crrgames.mods.evanmod.characters;

import com.crrgames.mods.evanmod.EvanMod;
import cpw.mods.fml.common.registry.EntityRegistry;

import java.util.HashMap;
import java.util.Map;

public class EntityFusionRegistry {

    private static int fusionID = 1;

    private static Map<String, EntityFusionStats> allCharacters = new HashMap<String, EntityFusionStats>();

    private static String computeHashKey(int lowerBody, int upperBody, int leftArm, int rightArm) {
        return String.format("%d-%d-%d-%d", lowerBody, upperBody, leftArm, rightArm);
    }

    public static void registerFusion(String name, int lowerBody, int rightArm, int leftArm, int upperBody) {
        try {
            allCharacters.put(computeHashKey(lowerBody, upperBody, leftArm, rightArm), new EntityFusionStats(name));
            EntityRegistry.registerModEntity(EntityFusion.class, name,
                    fusionID++, EvanMod.instance, 40, 1, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static EntityFusionStats checkRecipe(int lowerBody, int upperBody, int leftArm, int rightArm) {
        return allCharacters.get(computeHashKey(lowerBody, upperBody, leftArm, rightArm));
    }
}
