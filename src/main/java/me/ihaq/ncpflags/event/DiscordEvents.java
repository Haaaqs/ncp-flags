package me.ihaq.ncpflags.event;

import me.ihaq.ncpflags.NCPFlags;
import me.ihaq.ncpflags.hook.NCPCheckHook;
import net.dv8tion.jda.core.entities.MessageReaction;
import net.dv8tion.jda.core.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.bukkit.Bukkit;

public class DiscordEvents extends ListenerAdapter {

    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event) {

        // if its our bot reacting we ignore it
        if (event.getMember().getUser().getId().equals(NCPFlags.getJda().getSelfUser().getId())) {
            return;
        }

        MessageReaction.ReactionEmote reactionEmote = event.getReactionEmote();
        String playerName = NCPCheckHook.getMessageById(event.getMessageId()).getEmbeds().get(0).getFields().get(0).getValue();

        if (reactionEmote.getName().equals("❗")) { // warn
            Bukkit.getPlayer(playerName).sendRawMessage(NCPFlags.Config.WARN_MESSAGE);
        } else if (reactionEmote.getName().equals("❌")) { // kick
            Bukkit.getScheduler().runTask(NCPFlags.getPlugin(), () -> Bukkit.getPlayer(playerName).kickPlayer(NCPFlags.Config.KICK_MESSAGE));
        }
    }

}