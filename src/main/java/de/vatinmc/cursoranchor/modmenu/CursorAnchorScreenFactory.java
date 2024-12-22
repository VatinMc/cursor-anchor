package de.vatinmc.cursoranchor.modmenu;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import de.vatinmc.cursoranchor.gui.CursorAnchorConfigScreen;
import net.minecraft.client.gui.screen.Screen;

public class CursorAnchorScreenFactory implements ConfigScreenFactory<CursorAnchorConfigScreen> {

    @Override
    public CursorAnchorConfigScreen create(Screen screen) {
        return new CursorAnchorConfigScreen(screen);
    }
}