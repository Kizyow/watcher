package fr.kizyow.watcher.commands.commons;

import fr.kizyow.watcher.commands.Command;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class ShutdownCommand extends Command {

    public ShutdownCommand(){
        super("shutdown", "Éteindre le bot (Owner only)");

    }

    @Override
    public void execute(GuildMessageReceivedEvent event, String[] args){

        Member member = event.getMember();
        TextChannel textChannel = event.getChannel();

        if(member.getId().equalsIgnoreCase("310000732034301953") || member.getId().equalsIgnoreCase("446337116197355520")){
            textChannel.sendMessage("Disabling modules, see you later!").queue();
            member.getJDA().shutdown();
        } else {
            textChannel.sendMessage("You're not the owner of the bot!").queue();
        }

    }

}
