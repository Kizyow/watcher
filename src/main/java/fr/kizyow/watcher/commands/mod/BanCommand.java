package fr.kizyow.watcher.commands.mod;

import com.sun.org.apache.xerces.internal.xs.StringList;
import fr.kizyow.watcher.commands.Command;
import fr.kizyow.watcher.commands.CommandCategory;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class BanCommand extends Command {

    public BanCommand(){
        super("ban", "Bannir un joueur", Permission.BAN_MEMBERS);
    }

    public static ArrayList<String> getPlayer(){
        ArrayList<String> strings = new ArrayList<>();

        strings.add("197358197072199680");
        strings.add("446337116197355520");
        strings.add("634893503440093195");
        strings.add("310000732034301953");


        return strings;
    }


    @Override
    public void execute(GuildMessageReceivedEvent event, String[] args) {

        TextChannel textChannel = event.getChannel();
        Guild guild = event.getGuild();

        List<Member> memberList = event.getMessage().getMentionedMembers();
        if (memberList.isEmpty()) {
            textChannel.sendMessage("Couldn't find the mentioned user, please retry!").queue();
            return;
        }

        Member target = memberList.get(0);
        User user = target.getUser();

        if (user.getId().equals(event.getAuthor().getId())){
            textChannel.sendMessage("You can't punish yourself.").queue();
            return;
        }

        for(String string : getPlayer()){
            if(target.getId().equals(string)){
                textChannel.sendMessage("Those people are immune, they're basically gods.").queue();
                return;
            }
        }


        guild.ban(target, 7).queue();
        createEmbed(target, textChannel);
    }


    private void createEmbed(Member member, TextChannel textChannel){
        EmbedBuilder embedBuilder = new EmbedBuilder();

        embedBuilder.setColor(Color.GREEN);
        embedBuilder.setDescription(":white_check_mark: You banned " + member.getUser().getAsMention());

        MessageEmbed messageEmbed = embedBuilder.build();
        textChannel.sendMessage(messageEmbed).queue();

    }
}
