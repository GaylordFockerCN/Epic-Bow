package com.p1nero.epicfightbow.mob_effect;

import com.p1nero.epicfightbow.EpicFightBowMod;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EFBowEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, EpicFightBowMod.MOD_ID);
    public static final RegistryObject<MobEffect> DOUBLE_ARROW = MOB_EFFECTS.register("double_arrow",() -> new DoubleArrowEffect(0X6c6a5c));

}
