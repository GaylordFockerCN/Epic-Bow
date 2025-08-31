package com.p1nero.epicfightbow.events;

import com.p1nero.epicfightbow.EpicFightBowMod;
import com.p1nero.epicfightbow.item.EFBowItems;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.renderer.item.ItemPropertyFunction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

@Mod.EventBusSubscriber(modid = EpicFightBowMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        ItemPropertyFunction pull = (itemStack, clientLevel, livingEntity, p_174638_) -> {
            if (livingEntity == null) {
                return 0.0F;
            } else {
                LivingEntityPatch<?> livingEntityPatch = EpicFightCapabilities.getEntityPatch(livingEntity, LivingEntityPatch.class);
                if(livingEntityPatch != null && itemStack.hasTag() && itemStack.getOrCreateTag().getBoolean("is_full")) {
                    return 0.9F;
                }
                return livingEntity.getUseItem() != itemStack ? 0.0F : (float)(itemStack.getUseDuration() - livingEntity.getUseItemRemainingTicks()) / 20.0F;
            }
        };
        ItemPropertyFunction pulling = (itemStack, clientLevel, livingEntity, p_174633_) -> {
            LivingEntityPatch<?> livingEntityPatch = EpicFightCapabilities.getEntityPatch(livingEntity, LivingEntityPatch.class);
            if(livingEntityPatch != null && itemStack.hasTag() && itemStack.getOrCreateTag().getBoolean("is_full")) {
                return 1.0F;
            }
            return livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack ? 1.0F : 0.0F;
        };
        ItemProperties.register(EFBowItems.MORTIS.get(), ResourceLocation.fromNamespaceAndPath(EpicFightBowMod.MOD_ID, "pull"), pull);
        ItemProperties.register(EFBowItems.MORTIS.get(), ResourceLocation.fromNamespaceAndPath(EpicFightBowMod.MOD_ID, "pulling"), pulling);
        ItemProperties.register(Items.BOW, ResourceLocation.fromNamespaceAndPath(EpicFightBowMod.MOD_ID, "pull"), pull);
        ItemProperties.register(Items.BOW, ResourceLocation.fromNamespaceAndPath(EpicFightBowMod.MOD_ID, "pulling"), pulling);

    }
}
