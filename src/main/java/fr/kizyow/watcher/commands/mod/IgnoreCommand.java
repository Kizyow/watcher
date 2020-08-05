package fr.kizyow.watcher.commands.mod;

import fr.kizyow.watcher.commands.Command;
import fr.kizyow.watcher.commands.CommandCategory;
import fr.kizyow.watcher.loggers.LoggerManager;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class IgnoreCommand extends Command {

    private final LoggerManager loggerManager;

    public IgnoreCommand(LoggerManager loggerManager){
        super("ignore", "Le bot ne notifie plus les messages dans un canal demandé", Permission.VIEW_AUDIT_LOGS);
        this.loggerManager = loggerManager;

    }

    @Override
    public void execute(GuildMessageReceivedEvent event, String[] args){

        TextChannel textChannel = event.getChannel();

        if(loggerManager.isIgnored(textChannel.getIdLong())){
            loggerManager.verbChannel(textChannel.getIdLong());
            textChannel.sendMessage("This channel is now visible for me! Hellfire :fire:").queue();
            return;

        }

        loggerManager.ignoreChannel(textChannel.getIdLong());
        textChannel.sendMessage("This channel is now invisible for me! Be careful :smiling_imp:").queue();

    }

}
