package me.acablade.lavyukseliyor.commands;


import me.acablade.lavyukseliyor.game.LavYukseliyorGame;
import me.acablade.lavyukseliyor.game.LavYukseliyorGameData;
import me.acablade.lavyukseliyor.manager.GameManager;
import me.acablade.lavyukseliyor.manager.message.MessageManager;
import me.acablade.lavyukseliyor.manager.message.Messages;
import org.bukkit.entity.Player;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Dependency;
import revxrsal.commands.annotation.Subcommand;
import revxrsal.commands.bukkit.annotation.CommandPermission;

@Command({"lavyukseliyor", "ly"})
public class LavYukseliyorCommand {

    @Dependency private GameManager gameManager;

    @Subcommand("start")
    @CommandPermission("lavyukseliyor.start")
    public void start(Player player){
        Messages.CREATED.send(player);
        LavYukseliyorGame game = gameManager.createGame(LavYukseliyorGameData.ZERO);
        game.enable(0, 1);
    }

    @Subcommand("join")
    @CommandPermission("lavyukseliyor.join")
    public void join(Player player){
        gameManager.getGame(0).makePlayer(player);
    }

}
