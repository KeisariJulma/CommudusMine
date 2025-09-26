package com.exz.commudus;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.GuiGraphics;

public class CommudusMenuScreen extends Screen {
    private EditBox textBox;
    static final List<String> entries = new ArrayList<>(); // Static for persistence

    protected CommudusMenuScreen() {
        super(Component.literal("Commudus Menu"));
    }

    @Override
    protected void init() {
        int boxWidth = 150;
        int boxHeight = 20;
        int x = (this.width - boxWidth) / 2;
        int y = this.height / 2 + 20;
        textBox = new EditBox(this.font, x, y, boxWidth, boxHeight, Component.literal("Type here"));
        textBox.setMaxLength(50);
        textBox.setValue("");
        this.addRenderableWidget(textBox);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (textBox.isFocused() && keyCode == 257) { // 257 is GLFW_KEY_ENTER
            String value = textBox.getValue().trim();
            if (!value.isEmpty()) {
                entries.add(value);
                textBox.setValue("");
            }
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public void render(GuiGraphics gui, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(gui, mouseX, mouseY, partialTick);
        super.render(gui, mouseX, mouseY, partialTick);
        gui.drawCenteredString(this.font, "Hello from Commudus Menu!", this.width / 2, this.height / 2, 0xFFFFFF);
        textBox.render(gui, mouseX, mouseY, partialTick);

        int entryY = textBox.getY() + textBox.getHeight() + 5;
        for (int i = 0; i < entries.size(); i++) {
            gui.drawString(this.font, entries.get(i), textBox.getX(), entryY + i * 15, 0xAAAAAA, false);
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
