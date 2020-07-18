package fr.kizyow.watcher.listeners;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.util.concurrent.TimeUnit;

public class GuildJoinListener extends ListenerAdapter {

    @Override
    public void onGuildMemberJoin(@Nonnull GuildMemberJoinEvent event){
        Guild guild = event.getGuild();

        if(guild.getId().equalsIgnoreCase("687385094385696875")){
            Member member = event.getMember();
            Role role = event.getGuild().getRoleById("719161941100068975");

            member.modifyNickname("NOM PRÉNOM (RP)").queueAfter(30, TimeUnit.SECONDS);
            guild.addRoleToMember(member, role).queueAfter(30, TimeUnit.SECONDS);
            /*guild.getTextChannelsByName("welcome", true).stream()
                    .findAny()
                    .ifPresent(textChannel -> textChannel.sendMessage("Welcome " + member.getAsMention() + " to the server, you will have the role " + role.getAsMention() + " in 30 seconds \n" +
                    "Please rename yourself to this format \"NOM PRÉNOM (RP)\" or you will never able to play on our server! Don't forget to read the rules.").queue());*/
            guild.getTextChannelsByName("bienvenue", true).stream()
                    .findAny()
                    .ifPresent(textChannel -> textChannel.sendMessage("Bienvenue " + member.getAsMention() + " sur le serveur, vous allez avoir le rôle " + role.getAsMention() + " dans 30 secondes \n" +
                            "Merci de vous renommer en respectant ce format \"NOM PRÉNOM (RP)\" sinon vous ne pourrez jamais rejoindre le serveur ! Et n'oubliez pas de lire les règles :)").complete().delete().queueAfter(30, TimeUnit.SECONDS));


        }

    }

}
