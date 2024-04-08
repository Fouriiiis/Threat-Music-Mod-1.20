package dev.fouriiiis.threatmusicmod.mixin.client;

import net.minecraft.client.gui.hud.BossBarHud;
import net.minecraft.client.gui.hud.ClientBossBar;


import java.util.Map;
import java.util.UUID;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BossBarHud.class)
public interface GetBossBarsMixin {
    //get bossbars
    @Accessor("bossBars")
    Map<UUID, ClientBossBar> getBossBars();
}