package de.vatinmc.cursoranchor.gui;

import de.vatinmc.cursoranchor.config.CursorAnchorConfig;
import de.vatinmc.cursoranchor.util.HandledScreenType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.*;

import static org.lwjgl.glfw.GLFW.*;

public class CursorAnchorConfigScreen extends Screen {
    private final Screen parent;
    private final List<HandledScreenType> typeList = new LinkedList<>();

    public CursorAnchorConfigScreen(Screen parent) {
        super(Text.literal("Cursor Anchor Config"));
        this.parent = parent;
    }
    public CursorAnchorConfigScreen() {
        super(Text.literal("Cursor Anchor Config"));
        this.parent = null;
    }

    @Override
    protected void init() {
        super.init();
        typeList.clear();
        typeList.addAll(Arrays.asList(HandledScreenType.values()));
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);

        renderScreenTypes(context,mouseX,mouseY,delta);
    }

    private void renderScreenTypes(DrawContext context, int mouseX, int mouseY, float delta){
        int row = 0;
        for(HandledScreenType screenType : typeList){
            Row.render(context, row, screenType, new int[]{ mouseX, mouseY});
            row++;
        }
    }

    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta) {
        super.renderBackground(context, mouseX, mouseY, delta);
        int x1 = 5;
        int y1 = 5;
        int x2 = width - 5;
        int y2 = height - 5;
        int border = 1;

        context.fill(x1, y1, x2,y2,0xff000000);//black
        context.fill(x1 + border, y1 + border, x2 - border,y2 - border,0xffffffff);//white

        net.minecraft.util.Identifier texture = Identifier.of("minecraft", "textures/block/deepslate.png");
        context.drawTexture(texture, x1 +border,y1+border,0,0,x2-5-border*2,y2-5-border*2, 16, 16);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if(keyCode == GLFW_KEY_UP)
                scrollTypeList(true);
        if(keyCode == GLFW_KEY_DOWN)
            scrollTypeList(false);
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        click(mouseX, mouseY);

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        if(verticalAmount > 0.0)
                scrollTypeList(true);
            if(verticalAmount < 0.0)
                scrollTypeList(false);
        return super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
    }

    @Override
    public void close() {
        client.setScreen(parent);
    }

    private void click(double mouseX, double mouseY){
        int[] mousePos = new int[]{ (int) mouseX, (int) mouseY };
        int row = Row.getClickedRow(mousePos, width, height);
        if(row == -1) return;

        HandledScreenType screenType = typeList.get(row);
        boolean currentValue = CursorAnchorConfig.options.get(screenType);
        CursorAnchorConfig.options.replace(typeList.get(row), !currentValue);
    }

    private void scrollTypeList(boolean up){
        if(up){
            HandledScreenType type = typeList.removeLast();
            typeList.addFirst(type);
        } else {
            HandledScreenType type = typeList.removeFirst();
            typeList.add(type);
        }
    }

    private static class Row {
        public static int left = 16;
        public static int top = 16;
        public static int spacing = 5;
        public static int height = 16;
        public static int leftText = left + height + spacing;
        public static int paddingRowBg = 2;

        public static int colorText = 0xffffffff;
        public static int colorTextHover = 0xffffd700;
        public static int colorBg = 0x99000000;
        public static int colorBgHover = 0xbb000000;

        public Row(){}

        public static void render(DrawContext context, int row, HandledScreenType screenType, int[] mousePos){
            if(screenType.equals(HandledScreenType.ERROR)) return;
            if(isOffScreen(row, context.getScaledWindowHeight())) return;
            int top = getTop(row);
            int topText = top + 4;
            int colorText = Row.colorText;
            int colorRowBg = Row.colorBg;

            int[] areaBg = getArea(row, context.getScaledWindowWidth());
            if(hovered(mousePos, areaBg)){
                colorText = Row.colorTextHover;
                colorRowBg = Row.colorBgHover;
            }

            ItemStack itemStack = HandledScreenType.getItemStack(screenType);

            Text title = Text.translatable(itemStack.getTranslationKey());
            if(screenType.equals(HandledScreenType.HORSE))
                title = Text.translatable(EntityType.HORSE.getTranslationKey());
            else if(screenType.equals(HandledScreenType.MERCHANT))
                title = Text.translatable(EntityType.VILLAGER.getTranslationKey());

            context.fill(areaBg[0], areaBg[1], areaBg[2], areaBg[3], colorRowBg);
            context.drawItem(itemStack, left, top);
            boolean on = CursorAnchorConfig.options.getOrDefault(screenType, true);

            TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
            int leftStatus = leftText + textRenderer.getWidth(title) + spacing;
            if(!on){
                ItemStack errorStack = new ItemStack(Items.BARRIER, 1);
                context.drawItem(errorStack, leftStatus, top);
            } else {
                Identifier textureCheck = Identifier.ofVanilla("textures/gui/sprites/icon/checkmark.png");
                context.drawTexture(textureCheck, leftStatus, top + 4, 0,0,8,8,8,8);

            }
            context.drawTextWithShadow(textRenderer, title, leftText, topText, colorText);
        }

        public static int getTop(int row){
            return top + row * (height + spacing);
        }

        public static boolean isOffScreen(int row, int screenHeight){
            return (getTop(row) + height + spacing > screenHeight - 6);
        }

        public static int[] getArea(int row, int screenWidth){
            return new int[]{ left - paddingRowBg, getTop(row) - paddingRowBg, screenWidth - left + paddingRowBg, getTop(row) + height + paddingRowBg };
        }

        public static int getClickedRow(int[] mousePos, int screenWidth, int screenHeight){
            int row = 0;
            boolean clickedOnRow = false;
            while (!isOffScreen(row, screenHeight)){
                if(hovered(mousePos, getArea(row, screenWidth))) {
                    clickedOnRow = true;
                    break;
                }
                row++;
            }
            if(clickedOnRow)
                return row;
            else
                return -1;
        }

        public static boolean hovered(int[] mouse, int[] area){
            boolean hovered = false;
            if(mouse.length == 2 && area.length == 4)
                if(mouse[0] > area[0] && mouse[0] < area[2])
                    if(mouse[1] > area[1] && mouse[1] < area[3])
                        hovered = true;

            return hovered;
        }
    }
}