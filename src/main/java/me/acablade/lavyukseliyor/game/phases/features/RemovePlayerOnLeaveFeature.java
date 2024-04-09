package me.acablade.lavyukseliyor.game.phases.features;

import me.acablade.bladeapi.AbstractPhase;
import me.acablade.bladeapi.events.PlayerLeaveGameEvent;
import me.acablade.bladeapi.objects.BukkitActor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class RemovePlayerOnLeaveFeature extends LavYukseliyorFeature{
    public RemovePlayerOnLeaveFeature(AbstractPhase abstractPhase, JavaPlugin plugin) {
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
    public void onQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        BukkitActor actor = BukkitActor.of(player);
        if(!getAbstractPhase().getGame().getGameData().contains(actor)) return;
        getAbstractPhase().getGame().getGameData().removeActor(actor);
        PlayerLeaveGameEvent leaveGameEvent = new PlayerLeaveGameEvent(player, getAbstractPhase().getGame());
        Bukkit.getPluginManager().callEvent(leaveGameEvent);
    }
}
