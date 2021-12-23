package fr.kizyow.bot.commands.commons;

import fr.kizyow.bot.commands.GuildCommand;
import fr.kizyow.bot.database.tables.AdminTable;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;

import static net.dv8tion.jda.api.requests.ErrorResponse.UNKNOWN_USER;

public class PromoteCommand extends GuildCommand {

    public PromoteCommand() {
        super("promote", new String[]{"rank"}, "Permet de donner les permissions administrateur du bot");
    }

    @Override
    public void execute(GuildMessageReceivedEvent event, String[] args) {

        User user = event.getAuthor();
        TextChannel channel = event.getChannel();
        Guild guild = event.getGuild();

        if (!user.getId().equalsIgnoreCase("310000732034301953")) {
            MessageEmbed embed = new EmbedBuilder()
                    .setDescription("❌ Vous ne pouvez pas utiliser `promote` car cette commande est strictement réservée au développeur du bot.")
                    .setColor(Color.red)
                    .build();

            event.getChannel().sendMessageEmbeds(embed).queue();
            return;
        }

        if (args.length < 1) {
            MessageEmbed embed = new EmbedBuilder()
                    .setDescription("❌ Vous devez mentionner un utilisateur ou son identifiant Discord")
                    .setColor(Color.red)
                    .build();

            event.getChannel().sendMessageEmbeds(embed).queue();
            return;
        }

        String targetUserId = args[0].replaceAll("[^0-9.]", "");

        if (targetUserId.isEmpty()) {
            MessageEmbed embed = new EmbedBuilder()
                    .setDescription("❌ Vous devez mentionner un utilisateur ou son identifiant Discord")
                    .setColor(Color.red)
                    .build();
            channel.sendMessageEmbeds(embed).queue();
            return;
        }

        User targetUser = guild.getJDA().retrieveUserById(targetUserId).onErrorFlatMap(UNKNOWN_USER::test, throwable -> {
            MessageEmbed embed = new EmbedBuilder()
                    .setDescription("❌ L'utilisateur n'a pas été trouvé sur les serveurs Discord")
                    .setColor(Color.red)
                    .build();
            channel.sendMessageEmbeds(embed).queue();
            throw new NullPointerException();
        }).complete();

        AdminTable adminTable = new AdminTable();

        if (adminTable.hasRights(targetUser)) {
            MessageEmbed embed = new EmbedBuilder()
                    .setDescription("❌ L'utilisateur a déjà les droits administrateur sur le bot")
                    .setColor(Color.red)
                    .build();
            channel.sendMessageEmbeds(embed).queue();
            return;
        }

        adminTable.promoteUser(targetUser);

        MessageEmbed embed = new EmbedBuilder()
                .setDescription("⚠️ `" + user.getAsTag() +"` a désormais les droits administrateurs sur le bot !")
                .setColor(new Color(255, 97, 110, 255))
                .build();
        channel.sendMessageEmbeds(embed).queue();

    }

}
