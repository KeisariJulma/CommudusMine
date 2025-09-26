package com.exz.commudus;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.GuiGraphics;

public class CommudusMenuScreen extends Screen {
    private EditBox textBox;
    private EditBox espRangeBox;
    static final List<String> entries = new ArrayList<>(); // Static for persistence
    static int espRange = 50; // Default ESP range

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

        // ESP Range EditBox
        int espY = y + boxHeight + 10;
        espRangeBox = new EditBox(this.font, x, espY, boxWidth, boxHeight, Component.literal("ESP Range"));
        espRangeBox.setMaxLength(4);
        espRangeBox.setValue(String.valueOf(espRange));
        this.addRenderableWidget(espRangeBox);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (textBox.isFocused() && keyCode == 257) { // Enter for textBox
            String value = textBox.getValue().trim();
            if (!value.isEmpty()) {
                entries.add(value);
                textBox.setValue("");
            }
            return true;
        }
        if (espRangeBox.isFocused() && keyCode == 257) { // Enter for espRangeBox
            String value = espRangeBox.getValue().trim();
            try {
                int range = Integer.parseInt(value);
                if (range > 0) {
                    espRange = range;
                }
            } catch (NumberFormatException ignored) {}
            espRangeBox.setValue(String.valueOf(espRange));
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
        espRangeBox.render(gui, mouseX, mouseY, partialTick);

        int entryY = textBox.getY() + textBox.getHeight() + espRangeBox.getHeight() + 15;
        for (int i = 0; i < entries.size(); i++) {
            gui.drawString(this.font, entries.get(i), textBox.getX(), entryY + i * 15, 0xAAAAAA, false);
        }

        // Show current ESP range
        gui.drawString(this.font, "ESP Range: " + espRange, espRangeBox.getX(), espRangeBox.getY() - 15, 0x00FF00, false);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}