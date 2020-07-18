package fr.kizyow.watcher.commands.defaults;

import fr.kizyow.watcher.commands.Command;
import fr.kizyow.watcher.utils.TimeUnit;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class UserCommand extends Command {

    public UserCommand(){
        super("userinfo", "Afficher les informations relative à un membre");

    }

    @Override
    public void execute(GuildMessageReceivedEvent event, String[] args){

        Member member = event.getMember();
        TextChannel textChannel = event.getChannel();

        if(args.length > 0){
            List<Member> memberList = event.getMessage().getMentionedMembers();
            if(memberList.isEmpty()){
                textChannel.sendMessage("Couldn't find the mentioned user, please retry!").queue();
                return;

            }

            Member target = memberList.get(0);
            embed(target, textChannel);

        } else {
            embed(member, textChannel);

        }

    }

    private void embed(Member member, MessageChannel messageChannel){

        User user = member.getUser();

        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setAuthor(user.getAsTag(), null, user.getAvatarUrl());
        embedBuilder.setThumbnail(user.getAvatarUrl());

        embedBuilder.addField("Pseudonyme", nickname(member), true);
        embedBuilder.addField("Statut", member.getOnlineStatus(ClientType.DESKTOP).getKey(), true);
        embedBuilder.addField("En train de jouer", playing(member), true);
        embedBuilder.addField("Mention", member.getAsMention(), true);
        embedBuilder.addField("ID", user.getId(), true);
        embedBuilder.addField("A rejoint le serveur", date(member.getTimeJoined()), false);

        if(member.getTimeBoosted() != null)
        embedBuilder.addField("Booster de serveurs depuis", date(member.getTimeBoosted()), false);

        embedBuilder.addField("Rôles", roleList(member), false);



        embedBuilder.setFooter("Created: " + date(member.getTimeCreated()));
        embedBuilder.setColor(new Color(31,31,31));

        MessageEmbed embed = embedBuilder.build();
        messageChannel.sendMessage(embed).queue();

    }

    private String nickname(Member member){
        String nickname = member.getNickname();
        return nickname != null ? nickname : "n/a";

    }

    private String date(OffsetDateTime offsetDateTime){

        long tempsRestant = (System.currentTimeMillis() - offsetDateTime.toInstant().toEpochMilli()) / 1000;
        int annees = 0;
        int mois = 0;
        int semaine = 0;
        int jours = 0;
        int heures = 0;
        int minutes = 0;


        while(tempsRestant >= TimeUnit.ANNEES.getToSecond()){
            annees++;
            tempsRestant -= TimeUnit.ANNEES.getToSecond();

        }

        while(tempsRestant >= TimeUnit.MOIS.getToSecond()){
            mois++;
            tempsRestant -= TimeUnit.MOIS.getToSecond();

        }

        while(tempsRestant >= TimeUnit.SEMAINES.getToSecond()){
            semaine++;
            tempsRestant -= TimeUnit.SEMAINES.getToSecond();

        }

        while(tempsRestant >= TimeUnit.JOURS.getToSecond()){
            jours++;
            tempsRestant -= TimeUnit.JOURS.getToSecond();

        }

        while(tempsRestant >= TimeUnit.HEURES.getToSecond()){
            heures++;
            tempsRestant -= TimeUnit.HEURES.getToSecond();

        }

        while(tempsRestant >= TimeUnit.MINUTES.getToSecond()){
            minutes++;
            tempsRestant -= TimeUnit.MINUTES.getToSecond();

        }

        return annees + " " + TimeUnit.ANNEES.getName() + " "
                + mois + " " + TimeUnit.MOIS.getName() + " "
                + semaine + " " + TimeUnit.SEMAINES.getName() + " "
                + jours + " " + TimeUnit.JOURS.getName() + " "
                + heures + " " + TimeUnit.HEURES.getName() + " "
                + minutes + " " + TimeUnit.MINUTES.getName() + " Ago (" + offsetDateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a")) + ")";


    }

    private String roleList(Member member){

        StringBuilder stringBuilder = new StringBuilder("@everyone");
        for(Role role : member.getRoles()){
            stringBuilder.append(", ");
            stringBuilder.append(role.getName());

        }

        return stringBuilder.toString();

    }

    private String playing(Member member){

        List<Activity> activities = member.getActivities();
        if(activities.isEmpty()){
            return "n/a";

        }

        for(Activity activity : activities){
            return activity.getName();

        }

        return "n/a";

    }

}
