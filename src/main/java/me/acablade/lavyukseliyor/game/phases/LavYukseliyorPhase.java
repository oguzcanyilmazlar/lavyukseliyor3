package me.acablade.lavyukseliyor.game.phases;

import me.acablade.bladeapi.AbstractPhase;
import me.acablade.bladeapi.IGame;
import me.acablade.lavyukseliyor.game.LavYukseliyorGame;

import java.time.Duration;

public abstract class LavYukseliyorPhase extends AbstractPhase {
    public LavYukseliyorPhase(IGame game) {
        super(game);
    }

    @Override
    public LavYukseliyorGame getGame() {
        return (LavYukseliyorGame) super.getGame();
    }
}
