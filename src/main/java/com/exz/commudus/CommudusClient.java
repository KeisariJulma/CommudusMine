package com.exz.commudus;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import org.lwjgl.glfw.GLFW;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;

@Mod(value = Commudus.MODID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = Commudus.MODID, value = Dist.CLIENT)
public class CommudusClient {

    private static KeyMapping openMenuKey;

    @SubscribeEvent
    public static void onRegisterKeys(RegisterKeyMappingsEvent event) {
        openMenuKey = new KeyMapping(
                "key.commudus.open_menu",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_SHIFT, // Right Shift
                "key.categories.commudus"
        );
        event.register(openMenuKey);
    }

    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        // Only trigger on key press
        if (event.getAction() != GLFW.GLFW_PRESS) return;

        if (openMenuKey.matches(event.getKey(), event.getScanCode())) {
            System.out.println("[Commudus] Right Shift pressed. Opening CommudusMenuScreen.");
            Minecraft.getInstance().setScreen(new CommudusMenuScreen());
        }
    }
    // Java
// Java
    @SubscribeEvent
    public static void onRenderWorldLast(RenderLevelStageEvent event) {
        ESP.renderBlockHighlights(
                event.getPoseStack(),
                Minecraft.getInstance().renderBuffers().bufferSource(),
                CommudusMenuScreen.entries
        );
    }

}
