package me.ihaq.ncpflags.event;

import me.ihaq.ncpflags.NCPFlags;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageReaction;
import net.dv8tion.jda.core.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.bukkit.Bukkit;

import static me.ihaq.ncpflags.NCPFlags.Config.*;

public class MessageReactionEvent extends ListenerAdapter {

    private NCPFlags plugin;

    public MessageReactionEvent(NCPFlags plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event) {

        if (!kickAndWarn) {
            return;
        }

        // if its our bot reacting we ignore it
        if (event.getMember().getUser().getId().equals(event.getJDA().getSelfUser().getId())) {
            return;
        }

        MessageReaction.ReactionEmote reactionEmote = event.getReactionEmote();
        Message message = plugin.getMessageById(event.getMessageId());

        if (message == null) {
            return;
        }

        // getting the player name since its the very first field
        String playerName = message.getEmbeds().get(0).getFields().get(0).getValue();

        if (reactionEmote.getName().equals("❗")) { // warn
            Bukkit.getPlayer(playerName).sendRawMessage(prefix + " " + warnMessage);
        } else if (reactionEmote.getName().equals("❌")) { // kick
            Bukkit.getScheduler().runTask(plugin, () -> Bukkit.getPlayer(playerName).kickPlayer(prefix + " " + kickMessage));
        }
    }

}
