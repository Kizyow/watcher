package fr.kizyow.bot;

import fr.kizyow.bot.configurations.BotConfig;
import fr.kizyow.bot.configurations.ConfigManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Application {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {

        Application application = new Application();
        application.launchBot();

    }

    public Bot launchBot(){

        ConfigManager configManager = new ConfigManager();
        BotConfig botConfig = configManager.loadConfig("config.yml", BotConfig.class);

        if (botConfig == null) {
            logger.error("The config couldn't be loaded because the file is missing or corrupted", new NullPointerException());
            return null;
        }

        String token = botConfig.getToken();
        if (token == null || token.isBlank() || token.isEmpty()) {
            logger.error("The token wasn't found on the config.yml!", new NullPointerException());
            System.exit(1);
        }

        Bot bot = new Bot(token, botConfig);
        bot.registerListener();
        bot.registerCommand();
        bot.build();

        return bot;

    }

}
