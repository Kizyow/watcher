package fr.kizyow.watcher.commands;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public abstract class Command {

    private final String command;
    private final String description;
    private final Permission[] permissions;

    public Command(String command, String description){
        this(command, description, Permission.EMPTY_PERMISSIONS);

    }

    public Command(String command, String description, Permission... permissions){
        this.command = command;
        this.description = description;
        this.permissions = permissions;

    }

    public abstract void execute(GuildMessageReceivedEvent event, String[] args);

    public String getCommand(){
        return command;

    }

    public String getDescription(){
        return description;

    }

    public Permission[] getPermissions(){
        return permissions;

    }

}
