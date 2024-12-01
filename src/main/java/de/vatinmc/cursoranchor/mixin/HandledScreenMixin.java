package de.vatinmc.cursoranchor.mixin;

import de.vatinmc.cursoranchor.util.HandledScreenType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.ScreenHandlerProvider;
import net.minecraft.client.util.Window;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import oshi.util.tuples.Pair;

import java.util.HashMap;

@Mixin(HandledScreen.class)
public abstract class HandledScreenMixin<T extends ScreenHandler> extends Screen implements ScreenHandlerProvider<T> {
    @Shadow @Final protected T handler;
    @Unique
    private static final HashMap<HandledScreenType, Pair<Double,Double>> cursorPosScreens = new HashMap<>();

    protected HandledScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "init", at = @At("HEAD"))
    protected void init(CallbackInfo ci){
        HandledScreenType type = HandledScreenType.getType(handler);
        if(!type.equals(HandledScreenType.ERROR)){
            Window mcWindow = MinecraftClient.getInstance().getWindow();
            Pair<Double, Double> lastPos = cursorPosScreens.getOrDefault(type, new Pair<>(-0.1,-0.1));
            if((lastPos.getA() > 0 && lastPos.getB() > 0)){
                GLFW.glfwSetCursorPos(mcWindow.getHandle(), lastPos.getA(), lastPos.getB());
            } else {
                cursorPosScreens.put(type, lastPos);
            }
        }
    }

    @Inject(method = "close", at = @At("HEAD"))
    public void close(CallbackInfo ci){
        Mouse mouse = MinecraftClient.getInstance().mouse;
        HandledScreenType type = HandledScreenType.getType(handler);
        if(!type.equals(HandledScreenType.ERROR))
            cursorPosScreens.replace(type, new Pair<>(mouse.getX(), mouse.getY()));
    }
}
