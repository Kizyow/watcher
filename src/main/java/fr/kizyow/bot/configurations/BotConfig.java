package fr.kizyow.bot.configurations;

public class BotConfig {

    private String token;
    private String defaultCommandPrefix;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDefaultCommandPrefix() {
        return defaultCommandPrefix;
    }

    public void setDefaultCommandPrefix(String defaultCommandPrefix) {
        this.defaultCommandPrefix = defaultCommandPrefix;
    }

}
