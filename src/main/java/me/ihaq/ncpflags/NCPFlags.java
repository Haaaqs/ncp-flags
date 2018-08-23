package me.ihaq.ncpflags;

import me.ihaq.configmanager.ConfigManager;
import me.ihaq.configmanager.data.ConfigValue;
import me.ihaq.ncpflags.event.MessageReactionEvent;
import me.ihaq.ncpflags.hook.NCPCheckHook;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Message;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.List;

import static me.ihaq.ncpflags.NCPFlags.Config.token;
import static org.bukkit.ChatColor.AQUA;
import static org.bukkit.ChatColor.RESET;

public class NCPFlags extends JavaPlugin {

    @Nullable
    private JDA jda;

    @Nullable
    private NCPCheckHook ncpCheckHook;

    private List<Message> messageList;

    @Override
    public void onEnable() {
        new ConfigManager(this)
                .register(new Config())
                .load();

        try {
            jda = new JDABuilder(AccountType.BOT)
                    .setToken(token)
                    .addEventListener(new MessageReactionEvent(this))
                    .buildBlocking();
        } catch (LoginException | InterruptedException e) {
            e.printStackTrace();

            // disabling the plugin and the bot
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        ncpCheckHook = new NCPCheckHook(this, jda);
        messageList = new ArrayList<>();
    }

    @Override
    public void onDisable() {
        if (ncpCheckHook != null) {
            ncpCheckHook.disable();
        }
        if (jda != null) {
            jda.shutdown();
        }
    }

    @Nullable
    public Message getMessageById(String id) {
        return messageList.stream()
                .filter(message -> message.getId().equals(id))
                .findFirst().orElse(null);
    }

    public List<Message> getMessageList() {
        return messageList;
    }

    public static class Config {

        @ConfigValue("prefix")
        public static String prefix = "[" + AQUA + "NCPFlags" + RESET + "]";

        @ConfigValue("token")
        public static String token = "";

        @ConfigValue("kick_and_warn")
        public static boolean kickAndWarn = true;

        @ConfigValue("channel_id")
        public static String channelId = "";

        @ConfigValue("messages.warn")
        public static String warnMessage = "You have been warned.";

        @ConfigValue("messages.kick")
        public static String kickMessage = "You have been kicked.";
    }

}