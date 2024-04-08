package dev.fouriiiis.threatmusicmod.mixin.client;

import dev.fouriiiis.threatmusicmod.ThreatSettingsScreen;
import net.minecraft.client.gui.screen.option.SoundOptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.MinecraftClient;

@Mixin(SoundOptionsScreen.class)
public abstract class MixinSoundOptionsScreen extends Screen{

    protected MixinSoundOptionsScreen(Text title) {
        super(title);
    }

    @Inject(method = "init", at = @At("TAIL"))
    private void init(CallbackInfo ci) {
        // Add a button to the screen
        ButtonWidget button = ButtonWidget.builder(Text.of("Threat Music"), buttonWidget -> {
            // When the button is clicked, clear the current contents and open the tutorial screen
            MinecraftClient.getInstance().setScreen(new ThreatSettingsScreen(this));
        })
                //position in the bottom right corner of the screen
                .size(100, 20)
                .position(this.width - 110, this.height - 27)
                .build();
        
        // Add the button to the screen
        this.addDrawableChild(button);
    }
}
