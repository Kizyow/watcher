package fr.kizyow.watcher.commands.commons;

import fr.kizyow.watcher.commands.Command;
import fr.kizyow.watcher.commands.CommandCategory;
import fr.kizyow.watcher.commands.CommandManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.time.Instant;

public class HelpCommand extends Command {

    private final CommandManager commandManager;

    public HelpCommand(CommandManager commandManager){
        super("help", "Afficher la liste de toutes les commandes");
        this.commandManager = commandManager;

    }

    @Override
    public void execute(GuildMessageReceivedEvent event, String[] args){
        embed(event.getMember(), event.getChannel());

    }

    private void embed(Member member, MessageChannel messageChannel){

        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("Liste des commandes");

        for(Command command : commandManager.getCommandList()){
            embedBuilder.addField(commandManager.COMMAND_PREFIX + command.getCommand(), command.getDescription(), true);

        }

        embedBuilder.setFooter("Request time: " + messageChannel.getJDA().getRestPing().complete() + "ms", member.getUser().getAvatarUrl());
        embedBuilder.setTimestamp(Instant.now());
        embedBuilder.setColor(new Color(31,31,31));

        MessageEmbed embed = embedBuilder.build();
        messageChannel.sendMessage(embed).queue();

    }


}
