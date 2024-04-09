package me.acablade.lavyukseliyor.game.phases;

import me.acablade.bladeapi.IGame;
import me.acablade.lavyukseliyor.game.phases.features.*;
import me.acablade.lavyukseliyor.objects.LavaPlaceRunnable;
import org.bukkit.Bukkit;

import java.time.Duration;

public class GamePhase extends LavYukseliyorPhase{

    private int taskId;

    public GamePhase(IGame game) {
        super(game);
        addFeature(new AutoRespawnFeature(this, getGame().getPlugin()));
        addFeature(new PvPLimitFeature(this, getGame().getPlugin()));
        addFeature(new SpawnFeature(this, getGame().getPlugin()));
        addFeature(new RemovePlayerOnLeaveFeature(this, getGame().getPlugin()));
        addFeature(new SpectatorOnJoinFeature(this, getGame().getPlugin()));
    }

    @Override
    public void onEnable() {
        taskId = Bukkit.getScheduler().runTaskTimer(getGame().getPlugin(), new LavaPlaceRunnable(getGame(), 1), 0L, 20L).getTaskId();
    }

    @Override
    public void onDisable() {
        Bukkit.getScheduler().cancelTask(taskId);
    }

    @Override
    public void onTick() {
        int currentLavaLevel = getGame().getGameData().getCurrentLavaLevel();
        int pvpLevel = getGame().getGameData().getPvpLevel();
        if(currentLavaLevel >= pvpLevel && !getFeature(PvPLimitFeature.class).isPvp()){
            getFeature(PvPLimitFeature.class).setPvp(true);
            getGame().announce("pvp acildi");
        }
    }

    @Override
    public Duration duration() {
        return Duration.ofMinutes(20);
    }
}
