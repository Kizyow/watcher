package fr.kizyow.bot.commands;

import net.dv8tion.jda.api.entities.ChannelType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CommandManager {

    private final List<Command> commandList;
    private final Logger logger = LoggerFactory.getLogger(CommandManager.class);

    public CommandManager() {
        this.commandList = new ArrayList<>();
    }

    /**
     * Register a new command
     *
     * @param command The new command to register
     */
    public void registerCommand(Command command) {
        if (command == null || commandList.contains(command)) return;
        logger.info("Registering '" + command.getName() + "' command");
        commandList.add(command);
    }

    /**
     * Remove a command
     *
     * @param command The command to remove
     */
    public void removeCommand(Command command) {
        if (command == null || !commandList.contains(command)) return;
        logger.info("Removing '" + command.getName() + "' command");
        commandList.remove(command);
    }

    /**
     * Get a guild command (name or aliases)
     *
     * @param command The name or alias of the command
     * @return An optional of GuildCommand
     */
    public Optional<Command> getGuildCommand(String command) {
        Optional<Command> optionalCommandName = this.getGuildCommandByName(command);
        Optional<Command> optionalCommandAlias = this.getGuildCommandByAlias(command);
        return optionalCommandName.or(() -> optionalCommandAlias);
    }

    /**
     * Get a guild commans by his name
     *
     * @param name The name of the command
     * @return An optional of GuildCommand
     */
    public Optional<Command> getGuildCommandByName(String name) {
        return commandList.stream().filter(command -> command.getName().equalsIgnoreCase(name) && command.getChannelType() == ChannelType.TEXT).findAny();
    }

    /**
     * Get a guild command by his alias
     *
     * @param alias The alias of the command
     * @return The first GuildCommand if multiples alias is existing
     */
    public Optional<Command> getGuildCommandByAlias(String alias) {
        return commandList.stream().filter(command -> {
            List<String> aliases = List.of(command.getAliases());
            return aliases.stream().anyMatch(commandAlias -> commandAlias.equalsIgnoreCase(alias)) && command.getChannelType() == ChannelType.TEXT;
        }).findFirst();
    }

    /**
     * Get a private command (name or aliases)
     *
     * @param command The name or alias of the command
     * @return An optional of PrivateCommand
     */
    public Optional<Command> getPrivateCommand(String command) {
        Optional<Command> optionalCommandName = this.getPrivateCommandByName(command);
        Optional<Command> optionalCommandAlias = this.getPrivateCommandByAlias(command);
        return optionalCommandName.or(() -> optionalCommandAlias);
    }

    /**
     * Get a private commans by his name
     *
     * @param name The name of the command
     * @return An optional of PrivateCommand
     */
    public Optional<Command> getPrivateCommandByName(String name) {
        return commandList.stream().filter(command -> command.getName().equalsIgnoreCase(name) && command.getChannelType() == ChannelType.PRIVATE).findAny();
    }

    /**
     * Get a private command by his alias
     *
     * @param alias The alias of the command
     * @return The first PrivateCommand if multiples alias is existing
     */
    public Optional<Command> getPrivateCommandByAlias(String alias) {
        return commandList.stream().filter(command -> {
            List<String> aliases = List.of(command.getAliases());
            return aliases.stream().anyMatch(commandAlias -> commandAlias.equalsIgnoreCase(alias)) && command.getChannelType() == ChannelType.PRIVATE;
        }).findFirst();
    }

    /**
     * Get the list of all commands
     *
     * @return A List of Command
     */
    public List<Command> getCommandList() {
        return commandList;
    }
}
