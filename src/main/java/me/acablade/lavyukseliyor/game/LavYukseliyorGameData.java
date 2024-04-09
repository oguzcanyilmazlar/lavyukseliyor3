package me.acablade.lavyukseliyor.game;

import lombok.Getter;
import lombok.Setter;
import me.acablade.bladeapi.IGame;
import me.acablade.bladeapi.objects.BukkitActor;
import me.acablade.bladeapi.objects.GameData;
import me.acablade.bladeapi.objects.IActor;
import me.acablade.bladeapi.objects.IGameData;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
public class LavYukseliyorGameData implements IGameData, ConfigurationSerializable {

    public static LavYukseliyorGameData ZERO = new LavYukseliyorGameData(
            new Location(Bukkit.getWorld("world"), 0,0,0),
            new Location(Bukkit.getWorld("world"), -100,-64,-100),
            new Location(Bukkit.getWorld("world"), 100,256,100)
    );

    private Location spawn;
    private Location min;
    private Location max;
    private int currentLavaLevel;
    private int pvpLevel = 120;

    public LavYukseliyorGameData(Map<String, Object> map){
        this.max = (Location)map.get("max");
        this.min = (Location)map.get("min");
        this.spawn = (Location)map.get("spawn");
        this.pvpLevel = (int)map.get("pvpLevel");
    }

    public LavYukseliyorGameData(Location spawn, Location min, Location max){
        this(spawn, min, max, 120);
    }

    public LavYukseliyorGameData(Location spawn, Location min, Location max, int pvpLevel){
        this.max = max;
        this.min = min;
        this.spawn = spawn;
        this.pvpLevel = pvpLevel;
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("spawn", spawn);
        map.put("min", min);
        map.put("max", max);
        map.put("pvpLevel", pvpLevel);
        return map;
    }

    private HashSet<BukkitActor> actors = new HashSet<>();


    @Override
    public <T extends IActor> Set<T> getActors() {
        return (Set<T>) actors;
    }

    @Override
    public void addActor(IActor iActor) {
        actors.add((BukkitActor) iActor);
    }

    @Override
    public void removeActor(IActor iActor) {
        actors.remove((BukkitActor) iActor);
    }

    @Override
    public boolean contains(IActor iActor) {
        return actors.contains((BukkitActor) iActor);
    }
}