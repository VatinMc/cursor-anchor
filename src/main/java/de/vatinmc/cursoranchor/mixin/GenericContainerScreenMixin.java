package de.vatinmc.cursoranchor.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.ScreenHandlerProvider;
import net.minecraft.client.util.Window;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import oshi.util.tuples.Pair;

@Mixin(GenericContainerScreen.class)
public abstract class GenericContainerScreenMixin extends HandledScreen<GenericContainerScreenHandler> implements ScreenHandlerProvider<GenericContainerScreenHandler> {
    @Unique
    private static Pair<Integer, Integer> lastPos = new Pair<>(0,0);
    @Unique
    private boolean firstFrame = true;
    public GenericContainerScreenMixin(GenericContainerScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void render(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo info){
        if(firstFrame){
            if(!(lastPos.getA() == 0 && lastPos.getB() == 0)){
                Window testWindow = MinecraftClient.getInstance().getWindow();
                double scale = testWindow.getScaleFactor();
                double relativeX = lastPos.getA() * scale;
                double relativeY = lastPos.getB() * scale;
                GLFW.glfwSetCursorPos(testWindow.getHandle(), relativeX, relativeY);
            }
            firstFrame = false;
        }
        lastPos = new Pair<>(mouseX, mouseY);
    }
}
