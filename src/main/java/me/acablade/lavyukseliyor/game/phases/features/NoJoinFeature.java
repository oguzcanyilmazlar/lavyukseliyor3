package me.acablade.lavyukseliyor.game.phases.features;

import me.acablade.bladeapi.AbstractPhase;
import me.acablade.bladeapi.events.PlayerJoinGameEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.java.JavaPlugin;

public class NoJoinFeature extends LavYukseliyorFeature{
    public NoJoinFeature(AbstractPhase abstractPhase, JavaPlugin plugin) {
        super(abstractPhase, plugin);
    }

    @Override
    public void onEnable() {
    }

    @Override
    public void onDisable() {
    }

    @Override
    public void onTick() {
    }
}
