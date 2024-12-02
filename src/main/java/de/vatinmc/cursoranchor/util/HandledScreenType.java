package de.vatinmc.cursoranchor.util;

import net.minecraft.screen.*;

public enum HandledScreenType {
    BEACON,
    BREWINGSTAND,
    CARTOGRAPHYTABLE,
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
}
