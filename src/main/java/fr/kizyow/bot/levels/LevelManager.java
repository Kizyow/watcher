package fr.kizyow.bot.levels;

import fr.kizyow.bot.database.tables.LevelTable;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;

import java.awt.*;
import java.util.Random;

public class LevelManager {

    public static final int BASE_LEVEL_XP = 201;

    public static void update(User user, Message message) {

        LevelTable levelTable = new LevelTable(message.getGuild().getId());

        Random random = new Random();
        int xpGiven = random.nextInt(10, 25) + 1;

        long timeElapsed = message.getTimeCreated().toEpochSecond() - levelTable.getLastMessageUpdate(user);

        if (timeElapsed < 60) {
            return;
        }

        levelTable.updateExperience(user, levelTable.getExperience(user) + xpGiven);
        levelTable.updateLastMessage(user);
        int userLevel = levelTable.getLevel(user);

        int xpRequired = (int) (BASE_LEVEL_XP * 0.85 * userLevel);

        if (levelTable.getExperience(user) >= xpRequired) {
            levelTable.updateLevel(user, userLevel + 1);
            levelTable.updateExperience(user, 0);

            MessageEmbed embed = new EmbedBuilder()
                    .setDescription("Bravo 👏, tu viens de passer au niveau supérieur ! `(Niveau " + (userLevel + 1) + ")`")
                    .setColor(new Color(255, 97, 110, 255))
                    .build();
            message.replyEmbeds(embed).queue();

        }

    }

}
