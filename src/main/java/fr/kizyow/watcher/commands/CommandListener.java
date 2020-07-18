package fr.kizyow.watcher.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.Arrays;
import java.util.Optional;

public class CommandListener extends ListenerAdapter {

    private final CommandManager commandManager;

    public CommandListener(CommandManager commandManager){
        this.commandManager = commandManager;

    }

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event){

        Message message = event.getMessage();
        String raw = message.getContentRaw();
        User user = event.getAuthor();

        if(user.isBot()){
            return;

        }

        if(!raw.startsWith(commandManager.COMMAND_PREFIX)){
            return;

        }

        String commandMessage = raw.substring(1);
        String[] commandSplit = commandMessage.split(" ");
        String commandName = commandSplit[0];

        Optional<Command> optionalCommand = commandManager.getCommand(commandName);
        if(optionalCommand.isPresent()){
            Command command = optionalCommand.get();
            Member member = event.getMember();

            if(command.getPermissions() == null || Arrays.equals(command.getPermissions(), Permission.EMPTY_PERMISSIONS)){
                String[] args = Arrays.copyOfRange(commandSplit, 1, commandSplit.length);
                command.execute(event, args);
                return;

            }

            if(member.hasPermission(command.getPermissions())){
                String[] args = Arrays.copyOfRange(commandSplit, 1, commandSplit.length);
                command.execute(event, args);

            } else {
                noPermission(event.getChannel());

            }

        }

    }

    private void noPermission(MessageChannel messageChannel){

        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setDescription(":x: Vous n'avez pas les permissions nécessaires pour exécuter cette commande.");
        embedBuilder.setColor(new Color(31,31,31));

        MessageEmbed embed = embedBuilder.build();
        messageChannel.sendMessage(embed).queue();

    }

}
