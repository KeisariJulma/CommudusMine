// ESP.java
package com.exz.commudus;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.resources.ResourceLocation;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;

public class ESP {
    public static void renderBlockHighlights(PoseStack poseStack, MultiBufferSource buffer, List<String> entries) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null || mc.player == null) return;

        double camX = mc.gameRenderer.getMainCamera().getPosition().x;
        double camY = mc.gameRenderer.getMainCamera().getPosition().y;
        double camZ = mc.gameRenderer.getMainCamera().getPosition().z;

        BlockPos playerPos = mc.player.blockPosition();
        int radius = 10;

        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    BlockPos pos = playerPos.offset(x, y, z);
                    BlockState state = mc.level.getBlockState(pos);
                    Block block = state.getBlock();
                    ResourceLocation id = BuiltInRegistries.BLOCK.getKey(block);

                    if (id != null && entries.contains(id.toString())) {
                        double x1 = pos.getX() - camX;
                        double y1 = pos.getY() - camY;
                        double z1 = pos.getZ() - camZ;

                        double x2 = x1 + 1;
                        double y2 = y1 + 1;
                        double z2 = z1 + 1;

                        LevelRenderer.renderLineBox(
                                poseStack,
                                buffer.getBuffer(RenderType.lines()),
                                x1, y1, z1,
                                x2, y2, z2,
                                1.0F, 0.0F, 0.0F, 0.5F
                        );
                    }
                }
            }
        }
    }
}