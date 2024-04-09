package me.acablade.lavyukseliyor.game.phases.features;

import me.acablade.bladeapi.AbstractPhase;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class AutoRespawnFeature extends LavYukseliyorFeature{
    public AutoRespawnFeature(AbstractPhase abstractPhase, JavaPlugin plugin) {
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

    @EventHandler
    public void onDeath(PlayerDeathEvent event){
        Location loc = event.getPlayer().getLocation();
        Bukkit.getScheduler().runTaskLater(getPlugin(), () -> {
            event.getPlayer().spigot().respawn();
            event.getPlayer().teleport(loc);
        }, 1L);
        getAbstractPhase().getGame().makeSpectator(event.getPlayer());
    }
}
