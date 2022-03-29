package fr.kizyow.bot.utils;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class FontUtils {

    FontUtils(){}

    public static Font roboto = null;

    static {

        try {

            Optional optionalFont = Optional.ofNullable(FontUtils.class.getClassLoader().getResourceAsStream("fonts/Roboto.ttf"));

            if (!optionalFont.isPresent()) {
                throw new IOException("Font not found");
            }

            roboto = Font.createFont(Font.TRUETYPE_FONT, (InputStream) optionalFont.get());

            GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
            graphicsEnvironment.registerFont(roboto);

        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

    }

}
