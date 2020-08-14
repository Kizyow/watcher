package fr.kizyow.watcher.commands.mod;

import fr.kizyow.watcher.commands.Command;
import fr.kizyow.watcher.commands.CommandCategory;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.util.List;

public class KickCommand extends Command {

    public KickCommand(){
        super("kick", "Exclure un joueur", Permission.KICK_MEMBERS);
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

        if (user.getId().equals(event.getAuthor().getId())){
            textChannel.sendMessage("You can't punish yourself.").queue();
            return;
        }

        for(String string : BanCommand.getPlayer()){
            if(target.getId().equals(string)){
                textChannel.sendMessage("Those people are immune, they're basically gods.").queue();
                return;
            }
        }

        guild.kick(target).queue();
        createEmbed(target, textChannel);
    }


    private void createEmbed(Member member, TextChannel textChannel){
        EmbedBuilder embedBuilder = new EmbedBuilder();

        embedBuilder.setColor(Color.GREEN);
        embedBuilder.setDescription(":white_check_mark: You kicked " + member.getUser().getAsMention());

        MessageEmbed messageEmbed = embedBuilder.build();
        textChannel.sendMessage(messageEmbed).queue();

    }
}
