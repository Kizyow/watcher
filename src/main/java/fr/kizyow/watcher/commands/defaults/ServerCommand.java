package fr.kizyow.watcher.commands.defaults;

import fr.kizyow.watcher.commands.Command;
import fr.kizyow.watcher.utils.TimeUnit;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ServerCommand extends Command {

    public ServerCommand(){
        super("serverinfo", "Afficher les informations du serveur");

    }

    @Override
    public void execute(GuildMessageReceivedEvent event, String[] args){

        Guild guild = event.getGuild();
        TextChannel textChannel = event.getChannel();

        embed(guild, textChannel);


    }

    private void embed(Guild guild, MessageChannel messageChannel){

        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setAuthor(guild.getName(), null, guild.getIconUrl());
        embedBuilder.setThumbnail(guild.getBannerUrl());

        embedBuilder.addField("Région", guild.getRegion().getName(), true);
        embedBuilder.addField("Membres", String.valueOf(guild.getMemberCount()), true);
        embedBuilder.addField("Canaux", guild.getTextChannels().size() + " textuels / " + guild.getVoiceChannels().size() + " vocaux", true);
        embedBuilder.addField("Créateur", guild.getOwner().getUser().getAsTag(), true);
        embedBuilder.addField("Server Booster", guild.getBoostCount() + " Boost (Niveau " + guild.getBoostTier().getKey() + ")", true);
        embedBuilder.addField("Lien d'invitation", guild.getVanityUrl() == null ? "n/a" : guild.getVanityUrl(), true);
        embedBuilder.addField("ID", guild.getId(), true);
        embedBuilder.addField("Rôles", roleList(guild), false);

        embedBuilder.setFooter("Created: " + date(guild.getTimeCreated()));
        embedBuilder.setColor(new Color(31,31,31));

        MessageEmbed embed = embedBuilder.build();
        messageChannel.sendMessage(embed).queue();

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

    private String roleList(Guild guild){

        StringBuilder stringBuilder = new StringBuilder();
        for(Role role : guild.getRoles()){
            stringBuilder.append(role.getName());
            stringBuilder.append(", ");

        }

        return stringBuilder.toString().substring(0, stringBuilder.length()-2);

    }

}
