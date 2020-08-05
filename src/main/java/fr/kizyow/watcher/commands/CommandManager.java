package fr.kizyow.watcher.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CommandManager {

    public final String COMMAND_PREFIX = "!";
    private final List<Command> commandList = new ArrayList<>();

    public void registerCommand(Command command){
        commandList.add(command);
    }

    public void removeCommand(Command command){
        commandList.remove(command);
    }

    public Optional<Command> getCommand(String name){
        return commandList.stream().filter(command -> command.getCommand().equalsIgnoreCase(name)).findAny();
    }

    public List<Command> getCommandList(){
        return commandList;
    }

}
