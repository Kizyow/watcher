package fr.kizyow.watcher.commands.defaults.mod;

import fr.kizyow.watcher.commands.Command;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.util.List;

public class BanCommand extends Command {

    public BanCommand(){
        super("ban", "Bannir un joueur", Permission.BAN_MEMBERS);
    }

    @Override
    public void execute(GuildMessageReceivedEvent event, String[] args) {

        TextChannel textChannel = event.getChannel();
        Guild guild = event.getGuild();

        List<Member> memberList = event.getMessage().getMentionedMembers();
        if(memberList.isEmpty()){
            textChannel.sendMessage("Couldn't find the mentioned user, please retry!").queue();
            return;
        }

        Member target = memberList.get(0);
        User user = target.getUser();

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
