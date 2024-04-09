package me.acablade.lavyukseliyor.game.phases;

import me.acablade.bladeapi.IGame;
import me.acablade.lavyukseliyor.game.phases.features.NoJoinFeature;
import me.acablade.lavyukseliyor.game.phases.features.NoPvPFeature;
import me.acablade.lavyukseliyor.game.phases.features.RemovePlayerOnLeaveFeature;

import java.time.Duration;

public class EndPhase extends LavYukseliyorPhase{

    public EndPhase(IGame game) {
        super(game);
        addFeature(new NoPvPFeature(this, getGame().getPlugin()));
        addFeature(new RemovePlayerOnLeaveFeature(this, getGame().getPlugin()));
        addFeature(new NoJoinFeature(this, getGame().getPlugin()));
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

    @Override
    public Duration duration() {
        return null;
    }
}
