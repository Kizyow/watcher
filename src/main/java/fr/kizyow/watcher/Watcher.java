package fr.kizyow.watcher;

import fr.kizyow.watcher.commands.CommandListener;
import fr.kizyow.watcher.commands.CommandManager;
import fr.kizyow.watcher.commands.defaults.*;
import fr.kizyow.watcher.commands.defaults.mod.BanCommand;
import fr.kizyow.watcher.commands.defaults.mod.KickCommand;
import fr.kizyow.watcher.listeners.GuildJoinListener;
import fr.kizyow.watcher.loggers.LoggerListener;
import fr.kizyow.watcher.loggers.LoggerManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;

public class Watcher {

    private final JDABuilder jdaBuilder;
    private final LoggerManager loggerManager;
    private final CommandManager commandManager;

    public Watcher(String botToken){
        this.jdaBuilder = JDABuilder.createDefault(botToken);
        this.loggerManager = new LoggerManager();
        this.commandManager = new CommandManager();

    }

    public void registerLogger(){
        LoggerListener loggerListener = new LoggerListener(loggerManager);
        jdaBuilder.addEventListeners(loggerListener);
        jdaBuilder.addEventListeners(new GuildJoinListener());

    }

    public void registerCommands(){
        CommandListener commandListener = new CommandListener(commandManager);
        jdaBuilder.addEventListeners(commandListener);

        commandManager.registerCommand(new HelpCommand(commandManager));
        commandManager.registerCommand(new IgnoreCommand(loggerManager));
        commandManager.registerCommand(new UserCommand());
        commandManager.registerCommand(new ShutdownCommand());
        commandManager.registerCommand(new CreditCommand());
        commandManager.registerCommand(new SpecialCharacterCommand());
        commandManager.registerCommand(new ServerCommand());
        commandManager.registerCommand(new LauCommand());
        commandManager.registerCommand(new BanCommand());
        commandManager.registerCommand(new KickCommand());
    }

    public void activity(String activity){
        jdaBuilder.setActivity(Activity.playing(activity));

    }

    public void cache(){
        jdaBuilder.enableIntents(GatewayIntent.GUILD_PRESENCES, GatewayIntent.GUILD_MEMBERS);
        jdaBuilder.enableCache(CacheFlag.CLIENT_STATUS, CacheFlag.ACTIVITY);

    }

    public JDA build(){

        try {
            return jdaBuilder.build().awaitReady();

        } catch (InterruptedException | LoginException e){
            e.printStackTrace();

        }

        throw new RuntimeException("Failed to build the application");

    }

}
