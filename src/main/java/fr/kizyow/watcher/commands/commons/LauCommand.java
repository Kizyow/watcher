package fr.kizyow.watcher.commands.commons;

import fr.kizyow.watcher.commands.Command;
import fr.kizyow.watcher.commands.CommandCategory;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class LauCommand extends Command {

    public LauCommand(){
        super("lau", "Meilleur commande ever");
    }

    @Override
    public void execute(GuildMessageReceivedEvent event, String[] args) {
        TextChannel textChannel = event.getChannel();

        textChannel.sendMessage("Faut vraiment se poser la question ? C'est la femme à jo").queue();
        textChannel.sendMessage(":heart: ").queue();
    }
}
