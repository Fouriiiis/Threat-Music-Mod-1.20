package com.example.mixin.client;

import net.minecraft.client.sound.OggAudioStream;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.nio.ByteBuffer;

@Mixin(OggAudioStream.class)
public class OggMixin {

    @Shadow
    private ByteBuffer buffer;

    public int getCurrentBufferPosition() {
        return buffer.position();
    }
}
