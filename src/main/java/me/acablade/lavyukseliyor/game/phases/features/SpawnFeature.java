package me.acablade.lavyukseliyor.game.phases.features;

import me.acablade.bladeapi.AbstractPhase;
import me.acablade.bladeapi.events.PlayerJoinGameEvent;
import me.acablade.bladeapi.objects.BukkitActor;
import me.acablade.lavyukseliyor.manager.message.MessageManager;
import me.acablade.lavyukseliyor.manager.message.Messages;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.java.JavaPlugin;

public class SpawnFeature extends LavYukseliyorFeature{

    public SpawnFeature(AbstractPhase abstractPhase, JavaPlugin plugin) {
        super(abstractPhase, plugin);
    }

    @Override
    public void onEnable() {
        getAbstractPhase().getGame().getAllPlayers()
                .map(actor -> (BukkitActor) actor)
                .forEach(actor -> actor.teleport(getAbstractPhase().getGame().getGameData().getSpawn()));
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
        event.getPlayer().teleport(getAbstractPhase().getGame().getGameData().getSpawn());
        getAbstractPhase().getGame().getAllPlayers()
                        .map(actor -> (BukkitActor) actor)
                        .forEach(actor -> Messages.JOIN.send(actor.getPlayer(), new MessageManager.Replaceable("player", event.getPlayer().getName())));
    }

}
