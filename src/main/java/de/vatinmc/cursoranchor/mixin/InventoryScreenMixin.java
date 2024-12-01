package de.vatinmc.cursoranchor.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.screen.recipebook.RecipeBookProvider;
import net.minecraft.client.util.Window;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import oshi.util.tuples.Pair;

@Mixin(InventoryScreen.class)
public abstract class InventoryScreenMixin implements RecipeBookProvider{
    @Unique
    private static Pair<Integer, Integer> lastPos = new Pair<>(-1,-1);

    @Inject(method = "init", at = @At("HEAD"))
    protected void init(CallbackInfo ci){
        if(lastPos.getA() > 0 && lastPos.getB() > 0){
            Window mcWindow = MinecraftClient.getInstance().getWindow();
            double scale = mcWindow.getScaleFactor();
            double relX = lastPos.getA() * scale;
            double relY = lastPos.getB() * scale;
            GLFW.glfwSetCursorPos(mcWindow.getHandle(), relX, relY);
        }
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void render(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo info){
        lastPos = new Pair<>(mouseX, mouseY);
    }
}
