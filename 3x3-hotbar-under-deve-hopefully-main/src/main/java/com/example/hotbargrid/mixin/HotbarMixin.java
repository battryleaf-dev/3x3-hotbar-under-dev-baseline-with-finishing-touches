package com.example.hotbargrid.mixin;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class HotbarMixin {

    @Inject(method = "renderHotbar", at = @At("HEAD"), cancellable = true)
    private void hideOnlyHotbar(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        //  Cancel ONLY the vanilla hotbar rendering
        //  Offhand & other HUD elements are rendered elsewhere, so they stay
        ci.cancel();
    }
}