package com.p1nero.epicfightbow;

import com.p1nero.epicfightbow.item.EFBowItems;
import com.p1nero.epicfightbow.mob_effect.EFBowEffects;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import yesman.epicfight.world.item.EpicFightCreativeTabs;

@Mod(EpicFightBowMod.MOD_ID)
public class EpicFightBowMod {

    public static final String MOD_ID = "p1nero_bow";

    public EpicFightBowMod(FMLJavaModLoadingContext context) {
        EFBowItems.ITEMS.register(context.getModEventBus());
        EFBowEffects.MOB_EFFECTS.register(context.getModEventBus());
        context.getModEventBus().addListener(this::addCreativeTab);
    }

    private void addCreativeTab(BuildCreativeModeTabContentsEvent event){
        if (event.getTab() == EpicFightCreativeTabs.ITEMS.get()){
            event.accept(EFBowItems.MORTIS);
        }
    }

}
