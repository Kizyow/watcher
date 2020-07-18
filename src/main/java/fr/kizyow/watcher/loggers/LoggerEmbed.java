package fr.kizyow.watcher.loggers;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;

import java.awt.*;
import java.time.Instant;

public class LoggerEmbed {

    public static void edited(Message message, LoggerObject loggerObject, TextChannel textChannel){

        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("Message modifié");
        embedBuilder.setDescription("[Allez à la source du message](" + message.getJumpUrl() + ")");

        embedBuilder.addField("Ancien contenu", loggerObject.getContentRaw(), true);
        embedBuilder.addField("Nouveau contenu", message.getContentRaw(), true);
        embedBuilder.addField("Canal", "<#" + loggerObject.getMessageChannel().getId() + ">", false);
        embedBuilder.addField("Auteur du message", loggerObject.getUser().getAsMention(), false);

        embedBuilder.setFooter("Request time: " + loggerObject.getUser().getJDA().getRestPing().complete() + "ms", loggerObject.getUser().getAvatarUrl());
        embedBuilder.setTimestamp(Instant.now());
        embedBuilder.setColor(new Color(31,31,31));

        MessageEmbed embed = embedBuilder.build();
        textChannel.sendMessage(embed).queue();

    }

    public static void url(Message message, LoggerObject loggerObject, TextChannel textChannel){

        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("Message contenant un lien");
        embedBuilder.setDescription("[Allez à la source du message](" + message.getJumpUrl() + ")");

        embedBuilder.addField("Contenu du message", loggerObject.getContentRaw(), true);
        embedBuilder.addField("Canal", "<#" + loggerObject.getMessageChannel().getId() + ">", false);
        embedBuilder.addField("Auteur du message", loggerObject.getUser().getAsMention(), false);

        embedBuilder.setFooter("Request time: " + loggerObject.getUser().getJDA().getRestPing().complete() + "ms", loggerObject.getUser().getAvatarUrl());
        embedBuilder.setTimestamp(Instant.now());
        embedBuilder.setColor(new Color(31,31,31));

        MessageEmbed embed = embedBuilder.build();
        textChannel.sendMessage(embed).queue();

    }

    public static void quote(Member member, TextChannel memberChannel, Guild guild, TextChannel textChannel, Message message){



        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setAuthor(message.getAuthor().getAsTag(), null, message.getAuthor().getAvatarUrl());
        embedBuilder.setDescription(message.getContentRaw());

        if(!message.getAttachments().isEmpty()){
            embedBuilder.setImage(message.getAttachments().get(0).getUrl());

        } else {
            embedBuilder.setThumbnail(message.getAuthor().getAvatarUrl());
            embedBuilder.addBlankField(true);

        }

        embedBuilder.setFooter("Quoted by " + member.getUser().getAsTag() + " | from " + guild.getName() + " in #" + textChannel.getName());
        embedBuilder.setTimestamp(Instant.now());
        embedBuilder.setColor(new Color(31,31,31));

        MessageEmbed embed = embedBuilder.build();
        memberChannel.sendMessage(embed).queue();

    }

    public static void deleted(LoggerObject loggerObject, TextChannel textChannel){

        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("Message supprimé");

        embedBuilder.addField("Contenu du message", loggerObject.getContentRaw(), false);
        embedBuilder.addField("Canal", "<#" + loggerObject.getMessageChannel().getId() + ">", false);
        embedBuilder.addField("Auteur du message", loggerObject.getUser().getAsMention(), false);

        embedBuilder.setFooter("Request time: " + loggerObject.getUser().getJDA().getRestPing().complete() + "ms", loggerObject.getUser().getAvatarUrl());
        embedBuilder.setTimestamp(Instant.now());
        embedBuilder.setColor(new Color(31,31,31));

        MessageEmbed embed = embedBuilder.build();
        textChannel.sendMessage(embed).queue();

    }

}
