package fr.kizyow.bot.commands.commons;

import fr.kizyow.bot.Bot;
import fr.kizyow.bot.commands.Command;
import fr.kizyow.bot.commands.CommandManager;
import fr.kizyow.bot.commands.GuildCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.time.Instant;
import java.util.Arrays;

public class HelpCommand extends GuildCommand {

    private final CommandManager commandManager;
    private final String COMMAND_PREFIX;

    public HelpCommand(Bot bot) {
        super("help", "Afficher la liste des commandes");
        this.commandManager = bot.getCommandManager();
        this.COMMAND_PREFIX = bot.getConfig().getDefaultCommandPrefix();
    }

    @Override
    public void execute(GuildMessageReceivedEvent event, String[] args) {

        TextChannel channel = event.getChannel();
        Member member = event.getMember();

        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("Liste des commandes");

        for (Command command : commandManager.getCommandList()) {
            if (member != null && member.hasPermission(command.getPermissions())) {
                if (command.getAliases().length > 0) {
                    embedBuilder.addField(COMMAND_PREFIX + command.getName() + " " + Arrays.toString(command.getAliases()), command.getDescription(), true);
                } else {
                    embedBuilder.addField(COMMAND_PREFIX + command.getName(), command.getDescription(), true);
                }
            }
        }

        embedBuilder.setTimestamp(Instant.now());
        embedBuilder.setColor(new Color(255, 97, 110, 255));

        MessageEmbed embed = embedBuilder.build();
        channel.sendMessageEmbeds(embed).queue();

    }

}
