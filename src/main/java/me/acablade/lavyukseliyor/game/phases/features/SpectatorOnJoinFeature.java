package me.acablade.lavyukseliyor.game.phases.features;

import me.acablade.bladeapi.AbstractPhase;
import me.acablade.bladeapi.events.PlayerJoinGameEvent;
import me.acablade.bladeapi.objects.BukkitActor;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.java.JavaPlugin;

public class SpectatorOnJoinFeature extends LavYukseliyorFeature{
    public SpectatorOnJoinFeature(AbstractPhase abstractPhase, JavaPlugin plugin) {
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
    public void onJoin(PlayerJoinGameEvent event){
        if(!event.getGame().equals(getAbstractPhase().getGame())) return;
        getAbstractPhase().getGame().makeSpectator(event.getPlayer());
    }



}
