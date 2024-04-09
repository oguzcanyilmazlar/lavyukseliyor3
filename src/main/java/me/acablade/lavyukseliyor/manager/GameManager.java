package me.acablade.lavyukseliyor.manager;

import lombok.RequiredArgsConstructor;
import me.acablade.bladeapi.IGame;
import me.acablade.bladeapi.objects.NoDuplicateArrayList;
import me.acablade.lavyukseliyor.game.LavYukseliyorGame;
import me.acablade.lavyukseliyor.game.LavYukseliyorGameData;
import me.acablade.lavyukseliyor.objects.ConfigurationFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
public class GameManager {

    private final JavaPlugin plugin;

    private List<LavYukseliyorGame> games = new NoDuplicateArrayList<>(Arrays.asList());

    public void addGame(LavYukseliyorGame game){
        games.add(game);
    }

    public void removeGame(LavYukseliyorGame game){
        games.remove(game);
    }

    public LavYukseliyorGame getGame(int index){
        return games.get(index);
    }

    public boolean containsGame(LavYukseliyorGame game){
        return games.contains(game);
    }

    public LavYukseliyorGame createGame(LavYukseliyorGameData data){
        LavYukseliyorGame lavYukseliyorGame = new LavYukseliyorGame(plugin, data);
        this.addGame(lavYukseliyorGame);
        return lavYukseliyorGame;
    }

}
