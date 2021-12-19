package fr.kizyow.bot.configurations;

import com.google.common.base.CaseFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.introspector.Property;
import org.yaml.snakeyaml.introspector.PropertyUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class ConfigManager {

    private final Logger logger = LoggerFactory.getLogger(ConfigManager.class);

    /**
     * Load a config to use in the program or create if it doesn't exist
     *
     * @param name        The name of the config
     * @param configClass The class that will hold the config
     * @return An instance of the config
     */
    public <T> T loadConfig(String name, Class<?> configClass) {
        if (name == null || name.isEmpty() || name.isBlank())
            throw new NullPointerException("The config name should be valid!");
        if (configClass == null) throw new NullPointerException("The config class should be valid!");
        logger.info("Loading '" + name + "'");

        logger.debug("Loading '" + configClass.getSimpleName() + "' instance with custom properties");
        Constructor constructor = new Constructor(configClass);
        CustomProperty property = new CustomProperty();
        constructor.setPropertyUtils(property);

        File file = new File(name);
        if (!file.isFile()) {
            this.copyConfig(name);
        }

        Yaml yaml = new Yaml(constructor);
        InputStream inputStream;

        try {
            inputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            logger.error("The file wasn't found, '" + name + "' is missing in the resources folder");
            return null;
        }

        return yaml.load(inputStream);

    }

    /**
     * Override the current config with the news values
     * NB: The comments will be deleted too
     *
     * @param config The config object to save
     * @param name   The name of the config
     */
    public void saveConfig(Object config, String name) {

        DumperOptions options = new DumperOptions();
        options.setIndent(2);
        options.setPrettyFlow(true);
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        Yaml yaml = new Yaml(options);
        File file = new File(name);

        try {
            PrintWriter printWriter = new PrintWriter(file);
            yaml.dump(config, printWriter);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }

    /**
     * Copy the default config from the resources in the current folder
     *
     * @param name The name of the config
     */
    public void copyConfig(String name) {
        if (name == null || name.isEmpty() || name.isBlank())
            throw new NullPointerException("The config name should be valid!");

        try (InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(name)) {
            logger.debug("Copying default '" + name + "' outside the JAR");

            if (inputStream == null) {
                logger.error("Copy failed, the file wasn't found in the resources folder", new NullPointerException());
                return;
            }

            Files.copy(inputStream, Paths.get(name), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            logger.error("File copy failed: ", e.getCause());
        }

    }

    /**
     * Replace the hyphens' parameter of the config to camelCase parameters for convenience
     */
    static class CustomProperty extends PropertyUtils {

        private final Logger logger = LoggerFactory.getLogger(CustomProperty.class);

        @Override
        public Property getProperty(Class<?> type, String name) {
            logger.debug("Parsing parameter '" + name + "' to the instance");
            if (name.indexOf('-') > -1) name = CaseFormat.LOWER_HYPHEN.to(CaseFormat.LOWER_CAMEL, name);
            return super.getProperty(type, name);
        }

    }

}


