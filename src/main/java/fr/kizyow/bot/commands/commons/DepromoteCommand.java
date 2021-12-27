package fr.kizyow.bot.commands.commons;

import fr.kizyow.bot.commands.GuildCommand;
import fr.kizyow.bot.database.tables.AdminTable;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;

import static net.dv8tion.jda.api.requests.ErrorResponse.UNKNOWN_USER;

public class DepromoteCommand extends GuildCommand {

    public DepromoteCommand() {
        super("depromote", "Permet de retirer les permissions administrateur du bot");
    }

    @Override
    public void execute(GuildMessageReceivedEvent event, String[] args) {

        User user = event.getAuthor();
        TextChannel channel = event.getChannel();
        Guild guild = event.getGuild();

        if (!user.getId().equalsIgnoreCase("310000732034301953")) {
            MessageEmbed embed = new EmbedBuilder()
                    .setDescription("❌ Vous ne pouvez pas utiliser `depromote` car cette commande est strictement réservée au développeur du bot.")
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

        if (!adminTable.hasRights(targetUser)) {
            MessageEmbed embed = new EmbedBuilder()
                    .setDescription("❌ L'utilisateur n'a pas les droits administrateur sur le bot")
                    .setColor(Color.red)
                    .build();
            channel.sendMessageEmbeds(embed).queue();
            return;
        }

        adminTable.depromoteUser(targetUser);

        MessageEmbed embed = new EmbedBuilder()
                .setDescription("✅ `" + user.getAsTag() +"` n'a plus les droits administrateurs sur le bot !")
                .setColor(new Color(255, 97, 110, 255))
                .build();
        channel.sendMessageEmbeds(embed).queue();

    }

}
