package com.example;


import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

import net.minecraft.client.MinecraftClient;


public class ExampleModClient implements ClientModInitializer {

	private static ThreatTracker threatTracker;


	@Override
	public void onInitializeClient() {

		BiomeRegistryListener.init();

		


		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		ModSounds.registerSounds();
		
		threatTracker = new ThreatTracker();

		ClientTickEvents.END_CLIENT_TICK.register(threatTracker);

		KeyInputHandler.register();
	}

	public static void stop() {
		threatTracker.stopRegion(MinecraftClient.getInstance());
	}

	public static ThreatTracker getThreatTracker() {
		return threatTracker;
	}

}