package fr.kizyow.watcher.commands.commons;

import fr.kizyow.watcher.commands.Command;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class CreditCommand extends Command {

    public CreditCommand(){
        super("credits", "Afficher les crédits");

    }

    @Override
    public void execute(GuildMessageReceivedEvent event, String[] args){
        TextChannel textChannel = event.getChannel();
        textChannel.sendMessage("Watcher (Discord bot) created by **Kizyow#6666** on July 17 2019! All rights reserved to the owner!").queue();
        textChannel.sendMessage("If you have technical issue, please contact the owner on Discord.").queue();

    }

}
