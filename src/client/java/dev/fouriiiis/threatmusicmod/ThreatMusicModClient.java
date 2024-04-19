package dev.fouriiiis.threatmusicmod;


import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.client.MinecraftClient;


public class ThreatMusicModClient implements ClientModInitializer {

	private static ThreatTracker threatTracker;

	@Override
	public void onInitializeClient() {

		//BiomeRegistryListener.init();

		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		ModSounds.registerSounds();
		
		threatTracker = new ThreatTracker();

		ClientTickEvents.START_CLIENT_TICK.register(threatTracker);

		KeyInputHandler.register();

		ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
            // This code runs after the player joins a server/world.
            System.out.println("Player has joined the world/server!");
            // Your custom logic here
			BiomeRegistryChecker.checkWorldBiomes();
			System.out.println("Stopping music");
        	ThreatMusicModClient.stop();
        	ThreatTracker.clearTrackedThreats();
        	//set threat level to 0
        	ThreatTracker.currentThreat = 0;
        });
	}

	public static void stop() {
		//threatTracker.clearTrackedThreats();
		threatTracker.stopRegion(MinecraftClient.getInstance());
	}

	public static ThreatTracker getThreatTracker() {
		return threatTracker;
	}
}