package fr.kizyow.bot.levels;

import fr.kizyow.bot.commands.GuildCommand;
import fr.kizyow.bot.database.tables.LevelTable;
import fr.kizyow.bot.utils.FontUtils;
import fr.kizyow.bot.utils.StreamUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class RankCommand extends GuildCommand {

    public RankCommand() {
        super("rank", "Permet de voir son niveau");
    }

    @Override
    public void execute(GuildMessageReceivedEvent event, String[] args) {

        LevelTable levelTable = new LevelTable(event.getGuild().getId());
        User user = event.getAuthor();

        try {

            MessageEmbed embed = new EmbedBuilder()
                    .setImage("attachment://rank-card.png")
                    .setColor(new Color(253, 158, 136))
                    .build();

            File file = createRankCard(user, levelTable);
            event.getChannel().sendFile(file, "rank-card.png").setEmbeds(embed).queue();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Create a customized welcome card for members that joined the server!
     *
     * @param user A User object
     * @return An image file that contains the custom welcome image of the member
     * @throws IOException Throw an exception if the image couldn't be created
     */
    public File createRankCard(User user, LevelTable levelTable) throws IOException {

        // Get the background image and create a buffer of the image to modify it
        BufferedImage bufferImage = new BufferedImage(1000, 300, BufferedImage.TYPE_INT_ARGB);

        // Create image graphics to modify the image and applies some antialiasing
        Graphics2D graphics = bufferImage.createGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        graphics.setColor(new Color(47, 49, 54));
        graphics.fillRect(0, 0, 1000, 300);

        graphics.setColor(new Color(188, 192, 195));
        graphics.setFont(FontUtils.roboto.deriveFont(40f));
        String nameString = user.getName();
        int nameStringSize = graphics.getFontMetrics().stringWidth(nameString);
        graphics.drawString(nameString, 310, 160);

        graphics.setColor(new Color(150, 155, 161));
        graphics.setFont(FontUtils.roboto.deriveFont(30f));
        String tagString = "#" + user.getDiscriminator();
        graphics.drawString(tagString, 310 + nameStringSize, 160);

        int exp = levelTable.getExperience(user);
        int xpReach = (int) (LevelManager.BASE_LEVEL_XP * 0.85 * levelTable.getLevel(user));

        graphics.setColor(new Color(150, 155, 161));
        graphics.setFont(FontUtils.roboto.deriveFont(20f));
        String expString = exp + " / " + xpReach;
        int expStringSize = graphics.getFontMetrics().stringWidth(expString);
        graphics.drawString(expString, 900 - expStringSize, 160);

        graphics.setColor(new Color(253, 158, 136));
        graphics.setFont(FontUtils.roboto.deriveFont(30f));
        String levelString = "Niveau " + levelTable.getLevel(user);
        int levelStringSize = graphics.getFontMetrics().stringWidth(levelString);
        graphics.drawString(levelString, 900 - levelStringSize, 250);

        graphics.setColor(new Color(241, 241, 241));
        graphics.setFont(FontUtils.roboto.deriveFont(30f));
        String placeString = "Place #" + levelTable.getPlace(user);
        graphics.drawString(placeString, 310, 250);

        graphics.setColor(new Color(72, 73, 79));
        graphics.fillRoundRect(300, 180, 600, 30, 30, 30);

        graphics.setColor(new Color(253, 158, 136));
        graphics.fillRoundRect(300, 180, (600 * exp) / xpReach, 30, 30, 30);

        // Get the user's avatar from Discord server (256x256) and buffer it to apply in our current image
        // If an user doesn't have an avatar, load a default avatar from our assets
        URL defaultAvatarURL = this.getClass().getClassLoader().getResource("assets/avatar.png");
        BufferedImage bufferAvatar = user.getAvatarUrl() != null
                ? StreamUtils.createImageFromURL(user.getAvatarUrl() + "?size=256")
                : ImageIO.read(defaultAvatarURL);

        // Resize the avatar to 256x256 pixels
        bufferAvatar = StreamUtils.resizeImage(bufferAvatar, 180, 180);

        // Round the avatar and draw the image (hardcoded) to our buffer
        graphics.setClip(new Ellipse2D.Float(150 - bufferAvatar.getWidth() / 2, 150 - bufferAvatar.getHeight() / 2, 180, 180));
        graphics.drawImage(bufferAvatar, 150 - bufferAvatar.getWidth() / 2, 150 - bufferAvatar.getHeight() / 2, null);

        // Return the image file
        return StreamUtils.tempFileFromImage(bufferImage, "rank-card", ".png");

    }

}
