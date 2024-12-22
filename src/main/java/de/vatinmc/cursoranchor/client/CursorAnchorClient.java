package de.vatinmc.cursoranchor.client;

import de.vatinmc.cursoranchor.config.CursorAnchorConfig;
import de.vatinmc.cursoranchor.gui.CursorAnchorConfigScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.minecraft.client.MinecraftClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CursorAnchorClient implements ClientModInitializer {
    public static final String MOD_ID = "cursor-anchor";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitializeClient() {
        CursorAnchorConfig.loadConfig();
        registerCommand();
        onClose();
    }

    private void onClose(){
        ClientLifecycleEvents.CLIENT_STOPPING.register((client) -> CursorAnchorConfig.saveConfig());
    }

    private void registerCommand(){
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) ->
                dispatcher.register(ClientCommandManager.literal("caconfig").executes(context -> {
                    MinecraftClient client = context.getSource().getClient();
                    client.send(() -> client.setScreen(new CursorAnchorConfigScreen()));
                    return 1;
                })));
    }
}
