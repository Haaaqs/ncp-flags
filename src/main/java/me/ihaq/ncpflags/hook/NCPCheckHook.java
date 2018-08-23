package me.ihaq.ncpflags.hook;

import fr.neatmonster.nocheatplus.checks.CheckType;
import fr.neatmonster.nocheatplus.checks.access.IViolationInfo;
import fr.neatmonster.nocheatplus.hooks.NCPHook;
import fr.neatmonster.nocheatplus.hooks.NCPHookManager;
import me.ihaq.ncpflags.NCPFlags;
import me.ihaq.ncpflags.util.Loader;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.TextChannel;
import org.bukkit.entity.Player;

import java.awt.*;
import java.time.Instant;

import static me.ihaq.ncpflags.NCPFlags.Config.channelId;
import static me.ihaq.ncpflags.NCPFlags.Config.kickAndWarn;

public class NCPCheckHook implements NCPHook, Loader {

    private TextChannel textChannel;
    private NCPFlags plugin;

    public NCPCheckHook(NCPFlags plugin, JDA jda) {
        this.plugin = plugin;

        NCPHookManager.addHook(CheckType.ALL, this);
        textChannel = jda.getTextChannelById(channelId);
    }

    @Override
    public void disable() {
        NCPHookManager.removeHook(this);
    }

    @Override
    public String getHookName() {
        return "NCPFlags";
    }

    @Override
    public String getHookVersion() {
        return "1.0";
    }

    @Override
    public boolean onCheckFailure(CheckType checkType, Player player, IViolationInfo iViolationInfo) {
        if (textChannel == null) {
            return false;
        }

        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(new Color(25, 153, 228).getRGB());
        embedBuilder.setTimestamp(Instant.now());

        embedBuilder.addField("Player", player.getName(), true);
        embedBuilder.addField("Check", checkType.getName(), true);
        embedBuilder.addField("Violation Level", "" + Math.round(iViolationInfo.getTotalVl()), true);

        textChannel.sendMessage(embedBuilder.build()).queue(message -> {
            if (kickAndWarn) {
                message.addReaction("❗").queue();
                message.addReaction("❌").queue();
                plugin.getMessageList().add(message);
            }
        });

        return false;
    }


}