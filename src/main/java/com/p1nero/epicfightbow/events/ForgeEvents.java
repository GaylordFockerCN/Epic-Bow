package com.p1nero.epicfightbow.events;

import com.p1nero.epicfightbow.EpicFightBowMod;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

@Mod.EventBusSubscriber(modid = EpicFightBowMod.MOD_ID)
public class ForgeEvents {

    @SubscribeEvent
    public static void startUsing(LivingEntityUseItemEvent.Start event){
        LivingEntityPatch<?> livingEntityPatch = EpicFightCapabilities.getEntityPatch(event.getEntity(), LivingEntityPatch.class);
        if(livingEntityPatch != null && livingEntityPatch.getEntityState().inaction()) {
            event.setCanceled(true);
        }
    }
}
