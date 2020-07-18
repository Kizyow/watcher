package fr.kizyow.watcher.loggers;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;

public class LoggerObject {

    private final long messageId;
    private String contentRaw;
    private final User user;
    private final MessageChannel messageChannel;

    public LoggerObject(long messageId, String contentRaw, User user, MessageChannel messageChannel) {
        this.messageId = messageId;
        this.contentRaw = contentRaw;
        this.user = user;
        this.messageChannel = messageChannel;

    }

    public long getMessageId(){
        return messageId;

    }

    public String getContentRaw(){
        return contentRaw;

    }

    public void setContentRaw(String contentRaw){
        this.contentRaw = contentRaw;

    }

    public User getUser(){
        return user;

    }

    public MessageChannel getMessageChannel(){
        return messageChannel;

    }

}
