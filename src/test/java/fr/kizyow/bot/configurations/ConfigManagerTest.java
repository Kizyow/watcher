package fr.kizyow.bot.configurations;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class ConfigManagerTest {

    @Test
    public void testLoadConfig(){
        ConfigManager configManager = new ConfigManager();
        TestConfig testConfig = configManager.loadConfig("test.yml", TestConfig.class);

        Assertions.assertThat(testConfig).isNotNull();

        Assertions.assertThat(testConfig.getTest())
                .isNotNull()
                .isNotEmpty()
                .isNotBlank()
                .isEqualTo("test123");

    }

    @Test
    public void testLoadConfigNotFound() {

        ConfigManager configManager = new ConfigManager();
        TestConfig testConfig = configManager.loadConfig("error.yml", TestConfig.class);

        Assertions.assertThat(testConfig).isNull();

    }

}
