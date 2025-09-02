package com.p1nero.epicfightbow.item;

import com.p1nero.epicfightbow.EpicFightBowMod;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EFBowItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, EpicFightBowMod.MOD_ID);
    public static final RegistryObject<Item> MORTIS = ITEMS.register("mortis", () -> new MortisBowItem(new Item.Properties().rarity(Rarity.EPIC).fireResistant().stacksTo(1)));
    public static final RegistryObject<Item> OBLIVIONIS = ITEMS.register("oblivionis", () -> new BowItem(new Item.Properties().rarity(Rarity.EPIC).fireResistant().stacksTo(1)));
}
