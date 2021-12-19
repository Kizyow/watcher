package fr.kizyow.bot.commands;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;

public abstract class PrivateCommand extends Command {

    public PrivateCommand(String name, String[] aliases, String description) {
        super(name, aliases, description, ChannelType.PRIVATE, Permission.EMPTY_PERMISSIONS);
    }

    public PrivateCommand(String name, String description) {
        this(name, new String[0], description);
    }

    public abstract void execute(PrivateMessageReceivedEvent event, String[] args);

}
