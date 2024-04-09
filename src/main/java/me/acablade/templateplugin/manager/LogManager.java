package me.acablade.templateplugin.manager;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

@RequiredArgsConstructor
public class LogManager {


    private static final MiniMessage miniMessage = MiniMessage.miniMessage();

    private CommandSender console = Bukkit.getConsoleSender();
    private String prefix = "[LOG]";

    @Getter
    @Setter
    private boolean debug = true;

    @Builder
    public LogManager(CommandSender console, boolean debug, String prefix){
        this.console = console == null ? Bukkit.getConsoleSender() : console;
        this.debug = debug;
        this.prefix = prefix;
    }

    public void send(String color, String msg){
        color = "<" + color + ">";
        TextComponent component = LegacyComponentSerializer.legacyAmpersand().deserialize(msg);
        console.sendMessage(miniMessage.deserialize(color + prefix + " ").append(component));
    }

    public void fine(String msg){
        this.send("green", "[fine] " + msg);
    }

    public void debug(String msg){
        if(!debug) return;
        this.send("blue", "[debug] " + msg);
    }

    public void warn(String msg){
        this.send("yellow", "[warn] " + msg);
    }

    public void err(String msg){
        this.send("red", "[err] " + msg);
    }



}
