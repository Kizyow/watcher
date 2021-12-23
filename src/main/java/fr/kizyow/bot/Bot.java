package fr.kizyow.bot;

import fr.kizyow.bot.commands.CommandListener;
import fr.kizyow.bot.commands.CommandManager;
import fr.kizyow.bot.commands.commons.*;
import fr.kizyow.bot.configurations.BotConfig;
import fr.kizyow.bot.listeners.GuildListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.LoginException;

public class Bot {

    private final BotConfig botConfig;
    private final CommandManager commandManager;
    private final JDABuilder jdaBuilder;
    private final Logger logger = LoggerFactory.getLogger(Bot.class);

    private JDA jda;

    public Bot(String token, BotConfig botConfig) {
        this.botConfig = botConfig;
        this.commandManager = new CommandManager();
        this.jdaBuilder = JDABuilder.createDefault(token);

        // Configuration of the bot to properly use/listen/cache everything
        jdaBuilder.enableIntents(GatewayIntent.GUILD_PRESENCES, GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_MESSAGES, GatewayIntent.DIRECT_MESSAGES);
        jdaBuilder.setChunkingFilter(ChunkingFilter.ALL);
        jdaBuilder.setMemberCachePolicy(MemberCachePolicy.ALL);
        jdaBuilder.enableCache(CacheFlag.CLIENT_STATUS, CacheFlag.ACTIVITY);
    }

    public void registerListener() {
        logger.info("Registering listeners");

        CommandListener commandListener = new CommandListener(this);
        jdaBuilder.addEventListeners(commandListener);

        GuildListener guildListener = new GuildListener();
        jdaBuilder.addEventListeners(guildListener);
    }

    public void registerCommand() {
        logger.info("Registering commands");

        HelpCommand helpCommand = new HelpCommand(this);
        commandManager.registerCommand(helpCommand);

        ReloadCommand reloadCommand = new ReloadCommand(this);
        commandManager.registerCommand(reloadCommand);

        ShutdownCommand shutdownCommand = new ShutdownCommand(this);
        commandManager.registerCommand(shutdownCommand);

        PromoteCommand promoteCommand = new PromoteCommand();
        commandManager.registerCommand(promoteCommand);

        DepromoteCommand depromoteCommand = new DepromoteCommand();
        commandManager.registerCommand(depromoteCommand);

    }

    public void build() {

        try {
            jda = jdaBuilder.build().awaitReady();
            this.postBuild();
        } catch (LoginException | InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void postBuild() {
        if (jda == null) {
            logger.error("JDA is not setup properly, please restart the bot or contact an administrator", new NullPointerException());
            System.exit(1);
        }

    }

    public BotConfig getConfig() {
        return botConfig;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public JDA getJDA() {
        if (jda == null) throw new NullPointerException("The bot isn't ready");
        return jda;
    }

}
