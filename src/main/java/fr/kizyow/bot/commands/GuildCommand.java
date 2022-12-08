package fr.kizyow.bot.commands;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.channel.ChannelType;

public abstract class GuildCommand extends Command {

    public GuildCommand(String name, String[] aliases, String description, Permission[] permissions) {
        super(name, aliases, description, ChannelType.TEXT, permissions);
    }

    public GuildCommand(String name, String description, Permission[] permissions){
        this(name, new String[0], description, permissions);
    }

    public GuildCommand(String name, String[] aliases, String description) {
        this(name, aliases, description, Permission.EMPTY_PERMISSIONS);
    }

    public GuildCommand(String name, String description) {
        this(name, new String[0], description, Permission.EMPTY_PERMISSIONS);
    }

}
