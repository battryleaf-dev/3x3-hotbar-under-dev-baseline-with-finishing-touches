package com.example.hotbargrid.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.entity.player.PlayerInventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mouse.class)
public class HotbarInputMixin {

    @Inject(method = "onMouseScroll", at = @At("HEAD"), cancellable = true)
    private void onScroll(long window, double horizontal, double vertical, CallbackInfo ci) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;
        if (client.currentScreen != null) return;

        PlayerInventory inv = client.player.getInventory();
        int slot = inv.selectedSlot;

        if (vertical > 0) {
            slot = (slot + 8) % 9;
        } else if (vertical < 0) {
            slot = (slot + 1) % 9;
        } else {
            return;
        }

        inv.selectedSlot = slot;
        ci.cancel();
    }
}