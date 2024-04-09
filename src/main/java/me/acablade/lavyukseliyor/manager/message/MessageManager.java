package me.acablade.lavyukseliyor.manager.message;

import lombok.Data;
import me.acablade.lavyukseliyor.objects.ConfigurationFile;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MessageManager {

    private static MessageManager instance;
    private static final MiniMessage miniMessage = MiniMessage.miniMessage();

    /**
     * use the built-in DI
     */
    @Deprecated
    public static MessageManager inst() {
        return instance;
    }

    private JavaPlugin plugin;

    private final ConfigurationFile messageConfig;
    public MessageManager(JavaPlugin plugin){
        this(plugin, new ConfigurationFile(plugin, "messages"));
    }

    public MessageManager(JavaPlugin plugin, ConfigurationFile file){
        this.messageConfig = file;
        this.plugin = plugin;
        instance = this;

        try (InputStream in = plugin.getClass().getClassLoader().getResourceAsStream("messages.yml")) {
            if(in==null) return;
            InputStreamReader reader = new InputStreamReader(in);
            YamlConfiguration yamlConfiguration = new YamlConfiguration();
            yamlConfiguration.load(reader);

            for(String key: yamlConfiguration.getKeys(true)){
                if(yamlConfiguration.isConfigurationSection(key)) continue;
                if(this.messageConfig.getConfiguration().contains(key)) continue;
                this.messageConfig.getConfiguration().set(key, yamlConfiguration.get(key));
            }
            this.messageConfig.save();

        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

    }


    public void sendMessage(String key, CommandSender player, Replaceable... replaceables){
        for(MessageEntry msg : getMessage(player instanceof Player ? (Player) player : null, key, replaceables)){
            msg.send(player);
        }
    }

    public void reload(){
        messageConfig.reload();
    }

    public List<MessageEntry> getMessage(Player player, String key, Replaceable... replaceables){
        YamlConfiguration config = messageConfig.getConfiguration();

        TagResolver.Single[] placeholders = Arrays.stream(replaceables).map(s -> Placeholder.component(s.toReplace, Component.text(s.replace))).collect(Collectors.toList()).toArray(new TagResolver.Single[]{});

        List<MessageEntry> msg = new ArrayList<>();

        if(!config.contains(key)){
            msg.add(MessageEntry.of("§cMessage with key \""+key+"\" not found, contact admins."));
            return msg;
        }

        if(config.isList(key)){
            List<String> msgs = config.getStringList(key);
            List<MessageEntry> msgsEdited = msgs.stream()
                    .map(s -> parseLine(player, key, s, placeholders, replaceables))
                    .collect(Collectors.toList());
            msg.addAll(msgsEdited);
        }else{
            msg.add(parseLine(player, key, config.getString(key), placeholders, replaceables));
        }

        return msg;
    }

    private static Method PAPI_PLACEHOLDER = null;

    static {
        try {
            PAPI_PLACEHOLDER = Class.forName("me.clip.placeholderapi.PlaceholderAPI").getDeclaredMethod("setBracketPlaceholders", Player.class, String.class);
        } catch (NoSuchMethodException | ClassNotFoundException ignored) {}
    }

    private MessageEntry parseLine(Player player, String key, String msgS,TagResolver.Single[] placeholders, Replaceable... replaceables){
        if(msgS.startsWith("{play")) return SoundEntry.parse(msgS);

        boolean legacy = msgS.contains("&");
        for(Replaceable replaceable: replaceables){
            msgS = msgS.replaceAll("%" + replaceable.toReplace + "%", replaceable.replace);
        }
        if(legacy) {
            plugin.getLogger().info(String.format("Message named \"%s\" ís using legacy placeholders, please use MiniMessage type formatting instead.", key));
        }

        if(player != null && PAPI_PLACEHOLDER != null){
            try {
                msgS = (String) PAPI_PLACEHOLDER.invoke(null, player, msgS);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        msgS = ChatColor.translateAlternateColorCodes('&', msgS);

        MessageEntry entry;

        if(msgS.contains("§")){
            entry = MessageEntry.of(msgS);
        }else{
            entry = MessageEntry.of(miniMessage.deserialize(msgS, placeholders));
        }

        return entry;
    }


    public interface MessageEntry{

        @Deprecated
        public static StringEntry of(String s){
            return new StringEntry(s);
        }

        public static ComponentEntry of(Component s){
            return new ComponentEntry(s);
        }

        public static SoundEntry of(String name, float volume, float pitch){
            return new SoundEntry(name,volume,pitch);
        }

        public void send(CommandSender player);

    }

    @Data
    @Deprecated
    public static class StringEntry implements MessageEntry {

        private final String val;

        @Override
        public void send(CommandSender player) {
            player.sendMessage(val);
        }

        public String toString(){
            return val;
        }

    }

    @Data
    public static class ComponentEntry implements MessageEntry {

        private final Component val;

        @Override
        public void send(CommandSender player) {
            player.sendMessage(val);
        }

        public String toString(){
            return LegacyComponentSerializer.legacySection().serialize(val);
        }

    }

    @Data
    public static class SoundEntry implements MessageEntry {

        private final String val;
        private final float volume;
        private final float pitch;

        @Override
        public void send(CommandSender player) {
            if(!(player instanceof Player)) return;
            Player p = (Player) player;
            p.playSound(p.getLocation(), val, volume, pitch);
        }

        public static SoundEntry parse(String s){
            String b = s.substring("{play:".length(), s.length()-1);
            String[] xd = b.split(":");
            String soundName = xd[0];
            float volume = Float.parseFloat(xd[1]);
            float pitch = Float.parseFloat(xd[2]);

            return new SoundEntry(soundName, volume, pitch);
        }

    }

    @Data
    public static class Replaceable {

        private final String toReplace;
        private final String replace;

    }

}
