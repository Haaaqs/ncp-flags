package me.ihaq.ncpflags;

import me.ihaq.configmanager.ConfigManager;
import me.ihaq.configmanager.data.ConfigValue;
import me.ihaq.ncpflags.event.MessageReactionEvent;
import me.ihaq.ncpflags.hook.NCPCheckHook;
import me.ihaq.ncpflags.util.Loader;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import org.bukkit.plugin.java.JavaPlugin;

import javax.security.auth.login.LoginException;
import java.util.Arrays;

public class NCPFlags extends JavaPlugin {

    private static JavaPlugin plugin;
    private static JDA jda;
    private ConfigManager configManager;
    private Loader[] loaders = new Loader[]{new NCPCheckHook()};

    @Override
    public void onEnable() {
        plugin = this;
        configManager = new ConfigManager(this).register(new Config()).load();

        try {
            jda = new JDABuilder(AccountType.BOT)
                    .setToken(Config.TOKEN)
                    .addEventListener(new MessageReactionEvent())
                    .buildBlocking();
        } catch (LoginException | InterruptedException e) {
            e.printStackTrace();

            // disabling the plugin and the bot
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        Arrays.stream(loaders).forEach(Loader::onEnable);
    }

    @Override
    public void onDisable() {
        Arrays.stream(loaders).forEach(Loader::onDisable);
        configManager.save();
        if (jda != null) {
            jda.shutdown();
        }
    }

    public static JDA getJda() {
        return jda;
    }

    public static JavaPlugin getPlugin() {
        return plugin;
    }

    public static class Config {
        @ConfigValue("token")
        public static String TOKEN;

        @ConfigValue("kick_and_warn")
        public static boolean KICK_AND_WARN = true;

        @ConfigValue("channel_id")
        public static String CHANNEL_ID;

        @ConfigValue("warn_message")
        public static String WARN_MESSAGE;

        @ConfigValue("kick_message")
        public static String KICK_MESSAGE;
    }

}