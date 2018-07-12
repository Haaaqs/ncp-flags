
package me.ihaq.ncpflags.hook;

import fr.neatmonster.nocheatplus.checks.CheckType;
import fr.neatmonster.nocheatplus.checks.access.IViolationInfo;
import fr.neatmonster.nocheatplus.hooks.NCPHook;
import fr.neatmonster.nocheatplus.hooks.NCPHookManager;
import me.ihaq.ncpflags.NCPFlags;
import me.ihaq.ncpflags.util.Loader;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import org.bukkit.entity.Player;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class NCPCheckHook implements NCPHook, Loader {

    private TextChannel textChannel;
    private static List<Message> messageList = new ArrayList<>();

    @Override
    public void onEnable() {
        NCPHookManager.addHook(CheckType.ALL, this);
        textChannel = NCPFlags.getJda().getTextChannelById(NCPFlags.Config.CHANNEL_ID);
    }

    @Override
    public void onDisable() {
        NCPHookManager.removeHook(this);
    }

    public String getHookName() {
        return "NCPFlags";
    }

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

        embedBuilder.addField("Player", player.getName(), true);
        embedBuilder.addField("Check", checkType.getName(), true);
        embedBuilder.addField("Violation Level", "" + Math.round(iViolationInfo.getTotalVl()), true);

        textChannel.sendMessage(embedBuilder.build()).queue(message -> {
            if (NCPFlags.Config.KICK_AND_WARN) {
                message.addReaction("❗").queue();
                message.addReaction("❌").queue();
                messageList.add(message);
            }
        });

        return false;
    }

    public static Message getMessageById(String id) {
        return messageList.stream().filter(message -> message.getId().equals(id)).findFirst().orElse(null);
    }
}