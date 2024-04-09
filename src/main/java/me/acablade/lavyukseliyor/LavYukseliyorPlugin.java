package me.acablade.lavyukseliyor;

import lombok.Getter;
import me.acablade.lavyukseliyor.commands.LavYukseliyorCommand;
import me.acablade.lavyukseliyor.game.LavYukseliyorGameData;
import me.acablade.lavyukseliyor.manager.GameManager;
import me.acablade.lavyukseliyor.manager.gui.GUIListener;
import me.acablade.lavyukseliyor.manager.gui.GUIManager;
import me.acablade.lavyukseliyor.manager.LogManager;
import me.acablade.lavyukseliyor.manager.message.MessageManager;
import me.acablade.lavyukseliyor.objects.WorkloadThread;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;
import revxrsal.commands.bukkit.BukkitCommandHandler;

public final class LavYukseliyorPlugin extends JavaPlugin {

    @Getter
    private WorkloadThread workloadThread;


    @Override
    public void onEnable() {
        // Plugin startup logic
        String ver = getPluginMeta().getVersion();
        boolean debug = StringUtils.containsIgnoreCase(ver, "DEBUG");
        LogManager logManager = new LogManager(Bukkit.getConsoleSender(), debug, "[LAVYUKSELIYOR]");
        logManager.debug("Starting up as debug");
        GUIManager guiManager = new GUIManager();
        Bukkit.getPluginManager().registerEvents(new GUIListener(guiManager), this);
        MessageManager messageManager = new MessageManager(this);
        GameManager gameManager = new GameManager(this);


        BukkitCommandHandler handler = BukkitCommandHandler.create(this);
        handler.registerDependency(JavaPlugin.class, this);
        handler.registerDependency(MessageManager.class, messageManager);
        handler.registerDependency(LogManager.class, logManager);
        handler.registerDependency(GUIManager.class, guiManager);
        handler.registerDependency(GameManager.class, gameManager);
        handler.registerBrigadier();
        handler.register(new LavYukseliyorCommand());

        ConfigurationSerialization.registerClass(LavYukseliyorGameData.class);


        workloadThread = new WorkloadThread();
        Bukkit.getScheduler().runTaskTimer(this, workloadThread,0,1);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
