package fr.kizyow.watcher.loggers;

import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageDeleteEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.MessageUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.util.Optional;

public class LoggerListener extends ListenerAdapter {

    private final LoggerManager loggerManager;

    public LoggerListener(LoggerManager loggerManager){
        this.loggerManager = loggerManager;

    }

    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event){

        Message message = event.getMessage();
        User user = event.getAuthor();

        if(user.isBot()){
            return;

        }

        if(event.isFromType(ChannelType.PRIVATE)){
            System.out.printf("[PRIVATE] %s: %s\n", event.getAuthor().getName(), event.getMessage().getContentDisplay());
            return;

        }

        if(loggerManager.isIgnored(event.getTextChannel().getIdLong())){
            return;

        }

        System.out.printf("[%s][%s] %s: %s\n", event.getGuild().getName(), event.getTextChannel().getName(), event.getMember().getEffectiveName(), event.getMessage().getContentDisplay());

        LoggerObject loggerObject = new LoggerObject(message.getIdLong(), message.getContentRaw(), user, event.getChannel());
        loggerManager.log(message.getIdLong(), loggerObject);

        if(loggerManager.isURL(message.getContentRaw())){
            TextChannel textChannel = event.getGuild().getTextChannelsByName("logs", true).stream().findFirst().orElse(event.getTextChannel());
            //LoggerEmbed.url(message, loggerObject, textChannel);

        }

        if(message.getContentRaw().startsWith("https://discordapp.com/channels/")){
            String[] messageRaw = message.getContentRaw().split(" ");
            String[] quoteRaw = messageRaw[0].split("/");
            String guildId = quoteRaw[4];
            String channelId = quoteRaw[5];
            String messageId = quoteRaw[6];

            try {
                Guild guild = event.getJDA().getGuildById(guildId);
                TextChannel textChannel = guild.getTextChannelById(channelId);
                Message messageQuote = textChannel.retrieveMessageById(messageId).complete();

                LoggerEmbed.quote(event.getMember(), event.getTextChannel(), guild, textChannel, messageQuote);

            } catch (Exception exception){
                event.getTextChannel().sendMessage("Couldn't quote the specified message!").queue();

            }

        }

    }

    @Override
    public void onMessageUpdate(@Nonnull MessageUpdateEvent event){

        User user = event.getAuthor();

        if(user.isBot()){
            return;

        }

        if(event.isFromType(ChannelType.PRIVATE)){
            return;

        }

        if(loggerManager.isIgnored(event.getTextChannel().getIdLong())){
            return;

        }

        Message message = event.getMessage();
        long messageId = event.getMessageIdLong();
        TextChannel textChannel = event.getGuild().getTextChannelsByName("logs", true).stream().findFirst().orElse(event.getTextChannel());

        Optional<LoggerObject> optionalLoggerObject = loggerManager.retrieveData(messageId);
        if(optionalLoggerObject.isPresent()){
            LoggerObject loggerObject = optionalLoggerObject.get();
            //LoggerEmbed.edited(message, loggerObject, textChannel);
            loggerObject.setContentRaw(message.getContentRaw());

        }

    }

    @Override
    public void onMessageDelete(@Nonnull MessageDeleteEvent event){

        if(event.isFromType(ChannelType.PRIVATE)){
            return;

        }

        if(loggerManager.isIgnored(event.getTextChannel().getIdLong())){
            return;

        }

        long messageId = event.getMessageIdLong();
        TextChannel textChannel = event.getGuild().getTextChannelsByName("logs", true).stream().findFirst().orElse(event.getTextChannel());

        Optional<LoggerObject> loggerObject = loggerManager.retrieveData(messageId);
        //loggerObject.ifPresent(logger -> LoggerEmbed.deleted(logger, textChannel));
        loggerManager.remove(messageId);

    }

}
