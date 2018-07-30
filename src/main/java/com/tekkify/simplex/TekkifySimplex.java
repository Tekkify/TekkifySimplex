package com.tekkify.simplex;

import com.google.common.collect.Lists;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistryModifiable;

import java.util.ArrayList;

@Mod(modid = "tekkifysimplex", dependencies = "after:binniecore;")
public class TekkifySimplex {

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        ArrayList<String> compartmentRecipeIds = Lists.newArrayList(
                "compartment", "compartment_copper", "compartment_bronze", "compartment_iron", "compartment_gold", "compartment_diamond");

        compartmentRecipeIds.stream()
                .map(r -> new ResourceLocation("binniecore", r))
                .forEach(TekkifySimplex::removeRecipes);
    }

    public static void removeRecipes(ResourceLocation... rls) {
        IForgeRegistryModifiable<IRecipe> recipes = (IForgeRegistryModifiable<IRecipe>) ForgeRegistries.RECIPES;

        for (ResourceLocation rl : rls) {
            recipes.remove(rl);
        }
    }
}
