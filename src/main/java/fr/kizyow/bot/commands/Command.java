package fr.kizyow.bot.commands;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.ChannelType;

public abstract class Command {

    private final String name;
    private final String[] aliases;
    private final String description;
    private final ChannelType channelType;
    private final Permission[] permissions;

    public Command(String name, String[] aliases, String description, ChannelType channelType, Permission[] permissions) {
        this.name = name;
        this.aliases = aliases;
        this.description = description;
        this.channelType = channelType;
        this.permissions = permissions;
    }

    public Command(String name, String description, ChannelType channelType, Permission[] permissions){
        this(name, new String[0], description, channelType, permissions);
    }

    public Command(String name, String[] aliases, String description, ChannelType channelType){
        this(name, aliases, description, channelType, Permission.EMPTY_PERMISSIONS);
    }

    public Command(String name, String description, ChannelType channelType){
        this(name, new String[0], description, channelType, Permission.EMPTY_PERMISSIONS);
    }

    public String getName() {
        return name;
    }

    public String[] getAliases() {
        return aliases;
    }

    public String getDescription() {
        return description;
    }

    public ChannelType getChannelType() {
        return channelType;
    }

    public Permission[] getPermissions() {
        return permissions;
    }

}
