package me.acablade.templateplugin;

import me.acablade.templateplugin.manager.gui.GUIListener;
import me.acablade.templateplugin.manager.gui.GUIManager;
import me.acablade.templateplugin.manager.LogManager;
import me.acablade.templateplugin.manager.UpdateManager;
import me.acablade.templateplugin.manager.message.MessageManager;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import revxrsal.commands.bukkit.BukkitCommandHandler;

public final class TemplatePlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        String ver = getPluginMeta().getVersion();
        boolean debug = StringUtils.containsIgnoreCase(ver, "DEBUG");
        LogManager logManager = LogManager.builder().debug(debug).prefix("[TEMPLATEPLUGIN]").build();
        logManager.debug("Starting up as debug");
        GUIManager guiManager = new GUIManager();
        Bukkit.getPluginManager().registerEvents(new GUIListener(guiManager), this);
        MessageManager messageManager = new MessageManager(this);
        UpdateManager updateManager = new UpdateManager("update-url", logManager);

        boolean check = updateManager.check();

        if(check){
            getLogger().info("NEW VERSION AVAILABLE");
            getLogger().info("DOWNLOAD FROM GITHUB");
        }


        BukkitCommandHandler handler = BukkitCommandHandler.create(this);
        handler.registerDependency(JavaPlugin.class, this);
        handler.registerDependency(MessageManager.class, messageManager);
        handler.registerDependency(LogManager.class, logManager);
        handler.registerDependency(GUIManager.class, guiManager);
        handler.registerBrigadier();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
