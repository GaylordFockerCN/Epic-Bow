package com.p1nero.epicfightbow.events;

import com.p1nero.epicfightbow.EpicFightBowMod;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

@Mod.EventBusSubscriber(modid = EpicFightBowMod.MOD_ID)
public class ForgeEvents {

    @SubscribeEvent
    public static void startUsing(LivingEntityUseItemEvent.Start event) {
        LivingEntityPatch<?> livingEntityPatch = EpicFightCapabilities.getEntityPatch(event.getEntity(), LivingEntityPatch.class);
        if (livingEntityPatch != null && livingEntityPatch.getEntityState().inaction()) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onArrowImpact(ProjectileImpactEvent event) {
        HitResult rayTraceResult = event.getRayTraceResult();
        if (rayTraceResult instanceof EntityHitResult result) {
            Projectile projectile = event.getProjectile();
            if (result.getEntity() instanceof LivingEntity livingEntity) {
                if (projectile instanceof AbstractArrow) {
                    livingEntity.invulnerableTime = 0;
                }
            }
        }
    }
}
