package de.vatinmc.cursoranchor.util;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.*;

public enum HandledScreenType {
    BEACON,
    BREWINGSTAND,
    CARTOGRAPHYTABLE,
    CRAFTER,
    CRAFTING,
    ENCHANTMENT,
    FORGING,
    GENERIC3X3CONTAINER,
    GENERICCONTAINER,
    GRINDSTONE,
    HOPPER,
    HORSE,
    LOOM,
    MERCHANT,
    SHULKERBOX,
    STONECUTTER,
    ERROR;
    public static HandledScreenType getType(ScreenHandler handler){
        if(handler instanceof BeaconScreenHandler)
            return BEACON;
        if(handler instanceof BrewingStandScreenHandler)
            return BREWINGSTAND;
        if(handler instanceof CartographyTableScreenHandler)
            return CARTOGRAPHYTABLE;
        if(handler instanceof CrafterScreenHandler)
            return CRAFTER;
        if(handler instanceof CraftingScreenHandler)
            return CRAFTING;
        if(handler instanceof EnchantmentScreenHandler)
            return ENCHANTMENT;
        if(handler instanceof ForgingScreenHandler)
            return FORGING;
        if(handler instanceof Generic3x3ContainerScreenHandler)
            return GENERIC3X3CONTAINER;
        if(handler instanceof GenericContainerScreenHandler)
            return GENERICCONTAINER;
        if(handler instanceof GrindstoneScreenHandler)
            return GRINDSTONE;
        if(handler instanceof HopperScreenHandler)
            return HOPPER;
        if(handler instanceof HorseScreenHandler)
            return HORSE;
        if(handler instanceof LoomScreenHandler)
            return LOOM;
        if(handler instanceof MerchantScreenHandler)
            return MERCHANT;
        if(handler instanceof ShulkerBoxScreenHandler)
            return SHULKERBOX;
        if(handler instanceof StonecutterScreenHandler)
            return STONECUTTER;

        return ERROR;
    }

    public static ItemStack getItemStack(HandledScreenType screenType){
        Item item = Items.BEDROCK;
        switch (screenType){
            case BEACON -> item = Items.BEACON;
            case BREWINGSTAND -> item = Items.BREWING_STAND;
            case CARTOGRAPHYTABLE -> item = Items.CARTOGRAPHY_TABLE;
            case CRAFTER -> item = Items.CRAFTER;
            case CRAFTING -> item = Items.CRAFTING_TABLE;
            case ENCHANTMENT -> item = Items.ENCHANTING_TABLE;
            case FORGING -> item = Items.ANVIL;//+ Smithingtable
            case GENERIC3X3CONTAINER -> item = Items.DROPPER;//+ Dispencer
            case GENERICCONTAINER -> item = Items.CHEST;//+ Redstonechest, Barrel, Enderchest
            case GRINDSTONE ->  item = Items.GRINDSTONE;
            case HOPPER -> item = Items.HOPPER;
            case HORSE -> item = Items.GOLDEN_HORSE_ARMOR;
            case LOOM -> item = Items.LOOM;
            case MERCHANT -> item = Items.EMERALD;
            case SHULKERBOX -> item = Items.SHULKER_BOX;
            case STONECUTTER -> item = Items.STONECUTTER;
        }

        return new ItemStack(item, 1);
    }
}
