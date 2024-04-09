package me.acablade.lavyukseliyor.manager.message;


import lombok.Data;
import org.bukkit.command.CommandSender;

@Data
public class Message {

    private final String key;

    public void send(CommandSender sender, MessageManager.Replaceable... replaceables) {
        MessageManager.inst().sendMessage(key, sender, replaceables);
    }

}