package de.vatinmc.cursoranchor.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
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
    private static Pair<Double, Double> lastPos = new Pair<>(-1.0,-1.0);
    @Unique
    private boolean firstFrame = true;
    public GenericContainerScreenMixin(GenericContainerScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void render(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo info){
        if(firstFrame){
            if(!(lastPos.getA() == -1.0 && lastPos.getB() == -1.0)){
                Window mcWindow = MinecraftClient.getInstance().getWindow();
                GLFW.glfwSetCursorPos(mcWindow.getHandle(), lastPos.getA(), lastPos.getB());
            }
            firstFrame = false;
        }
    }
    @Override
    public void close() {
        Mouse mouse = MinecraftClient.getInstance().mouse;
        lastPos = new Pair<>(mouse.getX(), mouse.getY());

        super.close();
    }
}
