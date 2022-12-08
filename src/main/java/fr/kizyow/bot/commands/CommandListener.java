package fr.kizyow.bot.commands;

import fr.kizyow.bot.Bot;
import fr.kizyow.bot.levels.LevelManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Arrays;
import java.util.Optional;

public class CommandListener extends ListenerAdapter {

    private final CommandManager commandManager;
    private final String COMMAND_PREFIX;

    public CommandListener(Bot bot) {
        this.commandManager = bot.getCommandManager();
        this.COMMAND_PREFIX = bot.getConfig().getDefaultCommandPrefix();
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {

        if(event.isFromGuild()) {

            User user = event.getAuthor();
            String message = event.getMessage().getContentRaw();
            MessageChannel channel = event.getChannel();

            if (user.isBot()) return;
            if (!message.startsWith(COMMAND_PREFIX)) {
                LevelManager.update(user, event.getMessage());
                return;
            }

            String[] messageCommandArgs = message.substring(COMMAND_PREFIX.length()).split(" ");
            String commandName = messageCommandArgs[0];
            Optional<Command> optionalCommand = commandManager.getGuildCommand(commandName);

            optionalCommand.ifPresent(command -> {

                GuildCommand guildCommand = (GuildCommand) command;
                Member member = event.getMember();
                String[] args = Arrays.copyOfRange(messageCommandArgs, 1, messageCommandArgs.length);

                if (command.getPermissions() == null || Arrays.equals(guildCommand.getPermissions(), Permission.EMPTY_PERMISSIONS)) {
                    guildCommand.execute(event, args);
                    return;
                }

                if (member.hasPermission(guildCommand.getPermissions())) {
                    guildCommand.execute(event, args);

                } else {

                    MessageEmbed embed = new EmbedBuilder()
                            .setDescription("Vous n'avez pas les permissions nécessaires pour exécuter cette commande.")
                            .setColor(Color.pink)
                            .build();

                    channel.sendMessageEmbeds(embed).queue();
                }

            });

        } else {

            User user = event.getAuthor();
            String message = event.getMessage().getContentRaw();

            if (user.isBot()) return;
            if (!message.startsWith(COMMAND_PREFIX)) return;

            String[] messageCommand = message.substring(COMMAND_PREFIX.length()).split(" ");
            String messageCommandArgs = messageCommand[0];
            Optional<Command> optionalCommand = commandManager.getPrivateCommand(messageCommandArgs);

            optionalCommand.ifPresent(command -> {
                PrivateCommand privateCommand = (PrivateCommand) command;
                String[] args = Arrays.copyOfRange(messageCommand, 1, messageCommand.length);

                privateCommand.execute(event, args);
            });

        }

    }

}
