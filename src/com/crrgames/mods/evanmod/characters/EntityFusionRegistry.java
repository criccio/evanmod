package com.crrgames.mods.evanmod.characters;

import com.crrgames.mods.evanmod.EvanMod;
import com.crrgames.mods.evanmod.characters.fusions.EntityFusionDexter;
import cpw.mods.fml.common.registry.EntityRegistry;

import java.util.HashMap;
import java.util.Map;

public class EntityFusionRegistry {

    private static int fusionID = 1;

    private static Map<String, Class<? extends EntityFusion>> allCharacters = new HashMap<String, Class<? extends EntityFusion>>();

    private static String computeHashKey(int lowerBody, int upperBody, int leftArm, int rightArm) {
        return String.format("%d-%d-%d-%d", lowerBody, upperBody, leftArm, rightArm);
    }

    public static void registerFusion(String name, int lowerBody, int rightArm, int leftArm, int upperBody, Class clazz) {
        try {
            allCharacters.put(computeHashKey(lowerBody, upperBody, leftArm, rightArm), clazz);
            EntityRegistry.registerModEntity(clazz, name,
                    fusionID++, EvanMod.instance, 40, 1, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static Class<? extends EntityFusion> checkRecipe(int lowerBody, int upperBody, int leftArm, int rightArm) {
        return allCharacters.get(computeHashKey(lowerBody, upperBody, leftArm, rightArm));
    }
}
