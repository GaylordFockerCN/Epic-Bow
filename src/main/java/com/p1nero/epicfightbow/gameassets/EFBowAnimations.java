package com.p1nero.epicfightbow.gameassets;

import com.p1nero.epicfightbow.EpicFightBowMod;
import com.p1nero.epicfightbow.animations.ScanAttackAnimation;
import com.p1nero.epicfightbow.mob_effect.EFBowEffects;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.property.AnimationEvent;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.MovementAnimation;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.damagesource.StunType;

@Mod.EventBusSubscriber(modid = EpicFightBowMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EFBowAnimations {
    public static AnimationManager.AnimationAccessor<MovementAnimation> BOW_RUN;
    public static AnimationManager.AnimationAccessor<ScanAttackAnimation> BOW_AUTO1;
    public static AnimationManager.AnimationAccessor<ScanAttackAnimation> BOW_AUTO2;
    public static AnimationManager.AnimationAccessor<ScanAttackAnimation> BOW_AUTO3;
    public static AnimationManager.AnimationAccessor<AttackAnimation> BOW_DASH_ATTACK;
    public static AnimationManager.AnimationAccessor<ScanAttackAnimation> BOW_JUMP_ATTACK;
    public static AnimationManager.AnimationAccessor<AttackAnimation> ELBOW_2;
    public static AnimationManager.AnimationAccessor<AttackAnimation> ELBOW_3;

    @SubscribeEvent
    public static void efb$registerAnimations(AnimationManager.AnimationRegistryEvent event) {
        event.newBuilder(EpicFightBowMod.MOD_ID, EFBowAnimations::buildBowAnimations);
    }

    private static void buildBowAnimations(AnimationManager.AnimationBuilder builder) {
        BOW_RUN = builder.nextAccessor("biped/bow_run", accessor -> new MovementAnimation(true, accessor, Armatures.BIPED)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, ((dynamicAnimation, livingEntityPatch, v, v1, v2) -> BOW_RUN.get().getPlaySpeed(livingEntityPatch, dynamicAnimation) * 2.0F)));
        BOW_AUTO1 = builder.nextAccessor("biped/bow_auto1", accessor ->
                new ScanAttackAnimation(0.15F, 0, 0.15F, 65 / 60F, 65 / 60F,
                    InteractionHand.MAIN_HAND, EFBowColliders.BOW_SCAN, Armatures.BIPED.get().rootJoint, accessor, Armatures.BIPED)
                        .addEvents(setFullBowUseTime(20 / 60F), shootIn(30 / 60F),
                                setFullBowUseTime(50 / 60F), shootIn(60 / 60F)));
        BOW_AUTO2 = builder.nextAccessor("biped/bow_auto2", accessor ->
                new ScanAttackAnimation(0.15F, 0, 0.15F, 65 / 60F, 65 / 60F,
                        InteractionHand.MAIN_HAND, EFBowColliders.BOW_SCAN, Armatures.BIPED.get().rootJoint, accessor, Armatures.BIPED)
                        .addEvents(setFullBowUseTime(20 / 60F), shootIn(30 / 60F),
                                setFullBowUseTime(50 / 60F), shootIn(60 / 60F)));
        BOW_AUTO3 = builder.nextAccessor("biped/bow_auto3", accessor ->
                new ScanAttackAnimation(0.15F, 0, 0.15F, 100 / 60F, 120 / 60F,
                        InteractionHand.MAIN_HAND, EFBowColliders.BOW_SCAN, Armatures.BIPED.get().rootJoint, accessor, Armatures.BIPED)
                        .addEvents(setFullBowUseTime(50 / 60F), shootIn(80/60F),
                                setFullBowUseTime(85 / 60F), shootIn(90/60F),
                                setFullBowUseTime(95 / 60F), shootIn(100/60F)));
        BOW_DASH_ATTACK = builder.nextAccessor("biped/bow_dash_attack", accessor ->
                new AttackAnimation(0.15F, 0, 0, 40 / 60F, 60 / 60F,
                    EFBowColliders.BOW_DASH, Armatures.BIPED.get().rootJoint, accessor, Armatures.BIPED)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.0f))
                        .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true));
        BOW_JUMP_ATTACK = builder.nextAccessor("biped/bow_jump_attack", accessor ->
                new ScanAttackAnimation(0.15F, 0, 0.15F, 20 / 60F, 80 / 60F,
                        InteractionHand.MAIN_HAND, EFBowColliders.BOW_SCAN, Armatures.BIPED.get().rootJoint, accessor, Armatures.BIPED)
                        .addEvents(setFullBowUseTime(10 / 60F), shootIn(15 / 60F),
                                setFullBowUseTime(16 / 60F), shootIn(20 / 60F)));

        ELBOW_2 = builder.nextAccessor("biped/elbow_2", accessor ->
                new AttackAnimation(0.15F, 20 / 60F, 20 / 60F, 40 / 60F, 50 / 60F,
                        EFBowColliders.BOW_ELBOW, Armatures.BIPED.get().rootJoint, accessor, Armatures.BIPED)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.FALL)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.2F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(8.0F))
                        .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true));

        ELBOW_3 = builder.nextAccessor("biped/elbow_3", accessor ->
                new AttackAnimation(0.15F, 20 / 60F, 20 / 60F, 40 / 60F, 50 / 60F,
                        EFBowColliders.BOW_ELBOW, Armatures.BIPED.get().handR, accessor, Armatures.BIPED)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(8.0F)));

    }

    public static AnimationEvent.InTimeEvent<?> setFullBowUseTime(float time) {
        return AnimationEvent.InTimeEvent.create(time, (livingEntityPatch, assetAccessor, animationParameters) -> {
            livingEntityPatch.getOriginal().getMainHandItem().getOrCreateTag().putBoolean("is_full", true);
        }, AnimationEvent.Side.CLIENT);
    }
    /**
     * modify from {@link BowItem#releaseUsing(ItemStack, Level, LivingEntity, int)}
     */
    public static AnimationEvent.InTimeEvent<?> shootIn(float time) {
        return AnimationEvent.InTimeEvent.create(time, ((livingEntityPatch, assetAccessor, animationParameters) -> {
            int loopTime = livingEntityPatch.getOriginal().hasEffect(EFBowEffects.DOUBLE_ARROW.get()) ? 2 : 1;
            for(int i = 0; i < loopTime; i++) {
                shootOnce(livingEntityPatch, 3.0F + i * 0.3F);
            }
            livingEntityPatch.getOriginal().stopUsingItem();
        }), AnimationEvent.Side.BOTH);
    }

    private static void shootOnce(LivingEntityPatch<?> livingEntityPatch) {
        shootOnce(livingEntityPatch, 3.0F);
    }
    private static void shootOnce(LivingEntityPatch<?> livingEntityPatch, float speed) {
        LivingEntity living = livingEntityPatch.getOriginal();
        ItemStack itemStack = living.getMainHandItem();
        itemStack.getOrCreateTag().putBoolean("is_full", false);
        Item item = itemStack.getItem();
        Level level = living.level();
        int leftTime = 0;
        if (livingEntityPatch.getOriginal() instanceof ServerPlayer player && item instanceof BowItem bowItem) {
            boolean flag = player.getAbilities().instabuild || EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, itemStack) > 0;
            ItemStack itemstack = player.getProjectile(itemStack);

            int i = item.getUseDuration(itemStack) - leftTime;
            i = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(itemStack, level, player, i, !itemstack.isEmpty() || flag);
            if (i < 0) return;

            if (!itemstack.isEmpty() || flag) {
                if (itemstack.isEmpty()) {
                    itemstack = new ItemStack(Items.ARROW);
                }

//                    float f = getPowerForTime(i);
                float f = 1.0F;
                boolean flag1 = player.getAbilities().instabuild || (itemstack.getItem() instanceof ArrowItem && ((ArrowItem) itemstack.getItem()).isInfinite(itemstack, itemStack, player));
                if (!level.isClientSide) {
                    ArrowItem arrowitem = (ArrowItem)(itemstack.getItem() instanceof ArrowItem ? itemstack.getItem() : Items.ARROW);
                    AbstractArrow abstractarrow = arrowitem.createArrow(level, itemstack, player);
                    abstractarrow = bowItem.customArrow(abstractarrow);
                    abstractarrow.setPos(getJointWorldPos(livingEntityPatch, Armatures.BIPED.get().handL));
                    LivingEntity target = ScanAttackAnimation.getTarget(livingEntityPatch);
                    if(target == null) {
                        abstractarrow.shootFromRotation(player, living.getXRot(), livingEntityPatch.getYRot(), 0.0F, f * 3.0F, 1.0F);
                    } else {
                        Vec3 targetPos = target.getEyePosition();
                        Vec3 vec3 = targetPos.subtract(abstractarrow.position()).normalize().scale(speed * f);
                        abstractarrow.setDeltaMovement(vec3);
                        double d0 = vec3.horizontalDistance();
                        abstractarrow.setYRot((float)(Mth.atan2(vec3.x, vec3.z) * (double)(180F / (float)Math.PI)));
                        abstractarrow.setXRot((float)(Mth.atan2(vec3.y, d0) * (double)(180F / (float)Math.PI)));
                        abstractarrow.yRotO = abstractarrow.getYRot();
                        abstractarrow.xRotO = abstractarrow.getXRot();
                    }
                    if (f == 1.0F) {
                        abstractarrow.setCritArrow(true);
                    }

                    int j = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, itemStack);
                    if (j > 0) {
                        abstractarrow.setBaseDamage(abstractarrow.getBaseDamage() + (double)j * 0.5D + 0.5D);
                    }

                    int k = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PUNCH_ARROWS, itemStack);
                    if (k > 0) {
                        abstractarrow.setKnockback(k);
                    }

                    if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FLAMING_ARROWS, itemStack) > 0) {
                        abstractarrow.setSecondsOnFire(100);
                    }

                    itemStack.hurtAndBreak(1, player, (p_289501_) -> {
                        p_289501_.broadcastBreakEvent(player.getUsedItemHand());
                    });
                    if (flag1 || player.getAbilities().instabuild && (itemstack.is(Items.SPECTRAL_ARROW) || itemstack.is(Items.TIPPED_ARROW))) {
                        abstractarrow.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                    }

                    level.addFreshEntity(abstractarrow);
                }

                level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + f * 0.5F);
                if (!flag1 && !player.getAbilities().instabuild) {
                    itemstack.shrink(1);
                    if (itemstack.isEmpty()) {
                        player.getInventory().removeItem(itemstack);
                    }
                }

                player.awardStat(Stats.ITEM_USED.get(bowItem));
            }
        }
    }

    public static Vec3 getJointWorldPos(LivingEntityPatch<?> entityPatch, Joint joint) {
        LivingEntity entity = entityPatch.getOriginal();
        OpenMatrix4f transformMatrix = entityPatch.getArmature().getBoundTransformFor(entityPatch.getAnimator().getPose(0.1f), joint);
        OpenMatrix4f rotation = new OpenMatrix4f().rotate(-(float) Math.toRadians(entity.yBodyRotO + 180.0F), new Vec3f(0.0F, 1.0F, 0.0F));
        OpenMatrix4f.mul(rotation, transformMatrix, transformMatrix);
        return new Vec3(transformMatrix.m30 + (float) entity.getX(), transformMatrix.m31 + (float) entity.getY(), transformMatrix.m32 + (float) entity.getZ());
    }

}
