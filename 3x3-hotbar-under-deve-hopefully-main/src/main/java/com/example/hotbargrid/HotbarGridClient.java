package com.example.hotbargrid;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;

public class HotbarGridClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        HudRenderCallback.EVENT.register((drawContext, tickCounter) -> {
            MinecraftClient client = MinecraftClient.getInstance();
            ClientPlayerEntity player = client.player;
            if (player == null) return;

            renderGrid(drawContext, client, player);
        });
    }

    private static void renderGrid(DrawContext context, MinecraftClient client, ClientPlayerEntity player) {
        PlayerInventory inv = player.getInventory();

        int screenWidth = client.getWindow().getScaledWidth();
        int screenHeight = client.getWindow().getScaledHeight();

        int slotSize = Math.max(16, screenHeight / 22);
        int gap = Math.max(2, screenHeight / 180);

        int gridWidth  = (slotSize * 3) + (gap * 2);
        int gridHeight = (slotSize * 3) + (gap * 2);

        int barsLeftEdge = (screenWidth / 2) - 91;
        int margin = Math.max(4, screenHeight / 60);

        int startX = barsLeftEdge - gridWidth - margin;

        int bottomPadding = Math.max(4, screenHeight / 60);
        int startY = screenHeight - gridHeight - bottomPadding;

        HotbarGridState.GRID_BOTTOM_Y = startY + gridHeight;

        for (int i = 0; i < 9; i++) {
            int col = i % 3;
            int row = i / 3;
            int x = startX + col * (slotSize + gap);
            int y = startY + row * (slotSize + gap);

            // Selected slot highlight
            if (i == inv.selectedSlot) {
                context.fill(x - 2, y - 2, x + slotSize + 2, y + slotSize + 2, 0xFFFFD700);
            }

            // Background
            context.fill(x, y, x + slotSize, y + slotSize, 0x88000000);

            // Item rendering
            ItemStack stack = inv.getStack(i);
            context.drawItem(stack, x + (slotSize - 16) / 2, y + (slotSize - 16) / 2);

            //  Removed count rendering (causing crash)
        }
    }
}