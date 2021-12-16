package fr.kizyow.bot;

import fr.kizyow.bot.configurations.BotConfig;
import fr.kizyow.bot.configurations.ConfigManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Application {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {

        ConfigManager configManager = new ConfigManager();
        BotConfig config = configManager.loadConfig("config.yml", BotConfig.class);

        if (config == null) {
            logger.error("The config couldn't be loaded because the file is missing or corrupted", new NullPointerException());
            return;
        }

        Application application = new Application();
        application.launchBot(config);


    }

    public void launchBot(BotConfig config) {
        logger.info(config.getToken());
    }

}
