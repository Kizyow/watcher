package fr.kizyow.watcher.commands.commons;

import fr.kizyow.watcher.commands.Command;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class SpecialCharacterCommand extends Command {

    public SpecialCharacterCommand(){
        super("specialchar", "Afficher les caractères spéciaux pour kernioz");

    }

    @Override
    public void execute(GuildMessageReceivedEvent event, String[] args){
        event.getChannel().sendMessage("< > = ! / \\ - _ § & + - # ~ * <@446337116197355520>").queue();

    }

}
