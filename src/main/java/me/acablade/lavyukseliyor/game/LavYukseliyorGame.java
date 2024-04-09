package me.acablade.lavyukseliyor.game;

import lombok.Getter;
import me.acablade.bladeapi.AbstractGame;
import me.acablade.bladeapi.events.PlayerJoinGameEvent;
import me.acablade.bladeapi.objects.BukkitActor;
import me.acablade.lavyukseliyor.game.phases.EndPhase;
import me.acablade.lavyukseliyor.game.phases.GamePhase;
import me.acablade.lavyukseliyor.game.phases.LobbyPhase;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public class LavYukseliyorGame extends AbstractGame {

    @Getter
    private final UUID id;
    private final LavYukseliyorGameData gameData;

    public LavYukseliyorGame(JavaPlugin plugin, LavYukseliyorGameData gameData) {
        super("lavyukseliyor", plugin);
        this.gameData = gameData;
        this.id = UUID.randomUUID();
        addPhase(new LobbyPhase(this));
        addPhase(new GamePhase(this));
        addPhase(new EndPhase(this));
    }


    @Override
    public LavYukseliyorGameData getGameData() {
        return gameData;
    }


    public void makeSpectator(Player player){
        BukkitActor.of(player).setSpectator(null, true);
        player.setGameMode(GameMode.ADVENTURE);
        player.getInventory().clear();
        player.setAllowFlight(true);
        player.setFlying(true);
    }

    public void makePlayer(Player player){
        getGameData().addActor(BukkitActor.of(player));
        PlayerJoinGameEvent event = new PlayerJoinGameEvent(player, this);
        Bukkit.getPluginManager().callEvent(event);
        BukkitActor.of(player).setSpectator(null, false);
        player.setGameMode(GameMode.SURVIVAL);
        player.getInventory().clear();
        player.setAllowFlight(false);
        player.setFlying(false);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        LavYukseliyorGame that = (LavYukseliyorGame) object;

        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
