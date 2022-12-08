package fr.kizyow.bot.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.GenericMessageEvent;

import java.awt.*;

public class CommandHelper {
    CommandHelper(){}

    public static void checkUserMentionedInArg(GenericMessageEvent event, String[] args){
        if (CommandsArgsHelper.argsSetCorrectly(args, 1)) {
            MessageEmbed embed = new EmbedBuilder()
                    .setDescription("❌ Vous devez mentionner un utilisateur ou son identifiant Discord")
                    .setColor(Color.red)
                    .build();

            event.getChannel().sendMessageEmbeds(embed).queue();
        }
    }
}
