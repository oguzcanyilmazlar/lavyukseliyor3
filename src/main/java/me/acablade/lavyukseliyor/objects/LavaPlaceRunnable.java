package me.acablade.lavyukseliyor.objects;

import me.acablade.bladeapi.AbstractGame;
import me.acablade.bladeapi.objects.BukkitActor;
import me.acablade.bladeapi.objects.IActor;
import me.acablade.lavyukseliyor.LavYukseliyorPlugin;
import me.acablade.lavyukseliyor.game.LavYukseliyorGame;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.Objects;

/**
 * @author Acablade/oz
 */
public class LavaPlaceRunnable implements Runnable{

    private final LavYukseliyorPlugin plugin;
    private final LavYukseliyorGame game;
    private final int yMultiplier;

    public LavaPlaceRunnable(LavYukseliyorGame game, int yMultiplier){
        this.plugin = (LavYukseliyorPlugin) game.getPlugin();
        this.game = game;
        this.yMultiplier = yMultiplier;
    }

    @Override
    public void run() {

        Location min = game.getGameData().getMin();
        Location max = game.getGameData().getMax();

        int currentLevel = game.getGameData().getCurrentLavaLevel();

        for (int y = 0; y < yMultiplier; y++) {
            for (int x = min.getBlockX()-1; x <= max.getBlockX(); x++) {
                plugin.getWorkloadThread().add(new PlaceableBlock(min.getWorld().getUID(),x,currentLevel,min.getBlockZ()-1, Material.BARRIER));
                plugin.getWorkloadThread().add(new PlaceableBlock(min.getWorld().getUID(),x,currentLevel,max.getBlockZ(), Material.BARRIER));
            }
            for (int z = min.getBlockZ()-1; z <= max.getBlockZ(); z++) {
                plugin.getWorkloadThread().add(new PlaceableBlock(min.getWorld().getUID(),min.getBlockX()-1,currentLevel,z, Material.BARRIER));
                plugin.getWorkloadThread().add(new PlaceableBlock(min.getWorld().getUID(),max.getBlockX(),currentLevel,z, Material.BARRIER));
            }
            for (int x = min.getBlockX(); x < max.getBlockX(); x++) {
                for (int z = min.getBlockZ(); z < max.getBlockZ(); z++) {
                    plugin.getWorkloadThread().add(new PlaceableBlock(min.getWorld().getUID(),x,currentLevel,z, Material.LAVA));
                }
            }
            game.getGameData().setCurrentLavaLevel(currentLevel+1);
        }

        game.getAllPlayers()
                .map(actor -> (BukkitActor) actor)
                .filter(actor -> actor.isSpectator(null))
                .filter(actor -> actor.getPlayer().getLocation().getY() < currentLevel - 10)
                .forEach(actor -> actor.getPlayer().damage(Integer.MAX_VALUE));

    }
}