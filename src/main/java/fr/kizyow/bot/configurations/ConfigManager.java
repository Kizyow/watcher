package fr.kizyow.bot.configurations;

import com.google.common.base.CaseFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.introspector.Property;
import org.yaml.snakeyaml.introspector.PropertyUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class ConfigManager<T> {

    private final Class<T> configClass;
    private final Logger logger = LoggerFactory.getLogger(ConfigManager.class);

    public ConfigManager(Class<T> configClass) {
        if (configClass == null)
            logger.error("You must specify a config class to use the ConfigManager", new NullPointerException());
        this.configClass = configClass;
    }

    /**
     * Load a config to use in the program or create if it doesn't exist
     *
     * @param name The name of the config
     * @return An instance of the config
     */
    public T loadConfig(String name) {
        logger.debug("Loading '" + configClass.getName() + "' instance with custom properties");
        Constructor constructor = new Constructor(configClass);
        CustomProperty property = new CustomProperty();
        constructor.setPropertyUtils(property);

        File file = new File(name);
        if (!file.isFile()) this.copyConfig(name);

        Yaml yaml = new Yaml(constructor);
        InputStream inputStream = null;

        try {
            inputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            logger.error("The file wasn't found, maybe '" + name + "' is missing in the resources folder", e.getCause());
        }

        return yaml.load(inputStream);

    }

    /**
     * Copy the default config from the resources in the current folder
     *
     * @param name The name of the config
     */
    public void copyConfig(String name) {
        try (InputStream inputStream = configClass.getClassLoader().getResourceAsStream(name)) {
            logger.debug("Copying default '" + name + "' outside the JAR");

            if (inputStream == null) {
                logger.error("The file wasn't found in the resources folder", new NullPointerException());
                return;
            }

            Files.copy(inputStream, Paths.get(name), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            logger.error("File copy failed: ", e.getCause());
        }

    }

    /**
     * Replace the hypens parameter of the config to camelCase parameters for convenience
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


