package me.acablade.lavyukseliyor.game.phases;

import me.acablade.bladeapi.IGame;
import me.acablade.lavyukseliyor.game.phases.features.NoPvPFeature;
import me.acablade.lavyukseliyor.game.phases.features.RemovePlayerOnLeaveFeature;
import me.acablade.lavyukseliyor.game.phases.features.SpawnFeature;

import java.time.Duration;

public class LobbyPhase extends LavYukseliyorPhase{
    public LobbyPhase(IGame game) {
        super(game);
        addFeature(new NoPvPFeature(this, getGame().getPlugin()));
        addFeature(new RemovePlayerOnLeaveFeature(this, getGame().getPlugin()));
        addFeature(new SpawnFeature(this, getGame().getPlugin()));
    }

    @Override
    public void onEnable() {
        getGame().announce("start lobby");
    }

    @Override
    public void onDisable() {
        getGame().announce("stop lobby");
    }

    @Override
    public void onTick() {

    }

    @Override
    public Duration duration() {
        return Duration.ofSeconds(5);
    }
}
