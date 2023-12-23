package com.example;


import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

import net.minecraft.client.MinecraftClient;

import net.minecraft.client.sound.AmbientSoundLoops.MusicLoop;
import net.minecraft.client.sound.BiomeEffectSoundPlayer;





public class ExampleModClient implements ClientModInitializer {

	private static ThreatTracker threatTracker;

	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		ModSounds.registerSounds();
		
		threatTracker = new ThreatTracker();

		ClientTickEvents.END_CLIENT_TICK.register(threatTracker);
	}

	public static void stop() {
		threatTracker.stopRegion(MinecraftClient.getInstance());
	}

}