package fr.kizyow.watcher.commands.defaults.mod;

import fr.kizyow.watcher.commands.Command;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class CheckCommand extends Command {

    public CheckCommand(){
        super("checker", "Checker", Permission.MANAGE_CHANNEL);

    }

    @Override
    public void execute(GuildMessageReceivedEvent event, String[] args){
        TextChannel textChannel = event.getChannel();
        Member member = event.getMember();
        textChannel.sendMessage("C'est bien un modérateur").queue();
    }

}
