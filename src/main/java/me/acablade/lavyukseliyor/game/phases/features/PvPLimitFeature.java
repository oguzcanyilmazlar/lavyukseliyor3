package me.acablade.lavyukseliyor.game.phases.features;

import lombok.Getter;
import lombok.Setter;
import me.acablade.bladeapi.AbstractPhase;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.java.JavaPlugin;

@Setter
@Getter
public class PvPLimitFeature extends LavYukseliyorFeature{

    private boolean pvp = false;

    public PvPLimitFeature(AbstractPhase abstractPhase, JavaPlugin plugin) {
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
    public void onDamage(EntityDamageEvent event){
        event.setCancelled(!pvp && event.getEntity() instanceof Player);
    }
}
