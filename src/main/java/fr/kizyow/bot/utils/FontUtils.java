package fr.kizyow.bot.utils;

import java.awt.*;
import java.io.IOException;

public class FontUtils {

    public static Font ROBOTO = null;

    static {

        try {

            ROBOTO = Font.createFont(Font.TRUETYPE_FONT, FontUtils.class.getClassLoader().getResourceAsStream("fonts/Roboto.ttf"));

            GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
            graphicsEnvironment.registerFont(ROBOTO);

        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

    }

}
