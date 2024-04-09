package me.acablade.lavyukseliyor.game.phases.features;

import me.acablade.bladeapi.AbstractPhase;
import me.acablade.bladeapi.features.AbstractFeature;
import me.acablade.lavyukseliyor.game.phases.LavYukseliyorPhase;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class LavYukseliyorFeature extends AbstractFeature {
    public LavYukseliyorFeature(AbstractPhase abstractPhase, JavaPlugin plugin) {
        super(abstractPhase, plugin);
    }


    @Override
    public LavYukseliyorPhase getAbstractPhase() {
        return (LavYukseliyorPhase) super.getAbstractPhase();
    }
}
