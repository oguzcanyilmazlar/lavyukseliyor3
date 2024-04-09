package me.acablade.lavyukseliyor.game.phases.features;

import me.acablade.bladeapi.AbstractPhase;
import me.acablade.bladeapi.objects.BukkitActor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class NoPvPFeature extends LavYukseliyorFeature{

    public NoPvPFeature(AbstractPhase abstractPhase, JavaPlugin plugin) {
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
    public void onHit(EntityDamageByEntityEvent event){
        if(!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();
        if(getAbstractPhase().getGame().getGameData().contains(BukkitActor.of(player)))
            event.setCancelled(true);
    }
}
