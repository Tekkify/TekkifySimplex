package com.tekkify.simplex;

import com.google.common.collect.Lists;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistryModifiable;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

@Mod(modid = "tekkifysimplex", dependencies = "after:binniecore;")
public class TekkifySimplex {

    private static Logger logger;

    private List<String> blockedMessages = Lists.newArrayList(
            // not actually used ..
            // ended up not needing it for now
    );

    public TekkifySimplex() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
    }

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

    @SubscribeEvent
    public void onChatReceived(ClientChatReceivedEvent event) {
        if (blockedMessages.size() == 0) {
            return;
        }

        String plainMessage = event.getMessage().getUnformattedText().replace("\u00A7", "&");

        // Blocks each message once (designed to remove messages that appear when you first load a world)

        if (blockedMessages.contains(plainMessage)) {
            blockedMessages.remove(plainMessage);
            event.setCanceled(true);
        }
    }
}
