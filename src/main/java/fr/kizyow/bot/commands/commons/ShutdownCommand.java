package fr.kizyow.bot.commands.commons;

import fr.kizyow.bot.Bot;
import fr.kizyow.bot.commands.CommandManager;
import fr.kizyow.bot.commands.GuildCommand;
import fr.kizyow.bot.database.Database;
import fr.kizyow.bot.database.tables.AdminTable;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class ShutdownCommand extends GuildCommand {

    private final Bot bot;

    public ShutdownCommand(Bot bot) {
        super("stop", "Permet d'éteindre le bot");
        this.bot = bot;
    }

    @Override
    public void execute(MessageReceivedEvent event, String[] args) {

        User user = event.getAuthor();
        AdminTable adminTable = new AdminTable();

        if (!adminTable.hasRights(user) && !user.getId().equalsIgnoreCase("310000732034301953") && !user.getId().equalsIgnoreCase("271630245130534912")) {
            MessageEmbed embed = new EmbedBuilder()
                    .setDescription("❌ Vous ne pouvez pas utiliser `stop` car cette commande est réservée aux administrateurs du bot.")
                    .setColor(Color.red)
                    .build();

            event.getChannel().sendMessageEmbeds(embed).queue();
            return;
        }

        MessageEmbed embed = new EmbedBuilder()
                .setDescription("<a:loading:923534099333931069> Arrêt des services du bot...")
                .setColor(new Color(255, 97, 110, 255))
                .build();

        Message message = event.getChannel().sendMessageEmbeds(embed).complete();

        embed = new EmbedBuilder()
                .setDescription("<a:loading:923534099333931069> Arrêt des services du bot...\n" +
                        "`Arrêt des commandes`")
                .setColor(new Color(255, 97, 110, 255))
                .build();

        message.editMessageEmbeds(embed).completeAfter(600, TimeUnit.MILLISECONDS);

        CommandManager commandManager = bot.getCommandManager();
        new ArrayList<>(commandManager.getCommandList()).forEach(commandManager::removeCommand);

        embed = new EmbedBuilder()
                .setDescription("<a:loading:923534099333931069> Arrêt des services du bot...\n" +
                        "`Arrêt de la base de donnée`")
                .setColor(new Color(255, 97, 110, 255))
                .build();

        message.editMessageEmbeds(embed).completeAfter(600, TimeUnit.MILLISECONDS);

        Database.closeInstance();

        embed = new EmbedBuilder()
                .setDescription("✅ Arrêt des services du bot, au revoir 👋")
                .setColor(new Color(255, 97, 110, 255))
                .build();
        message.editMessageEmbeds(embed).complete();

        event.getJDA().shutdown();

    }

}
