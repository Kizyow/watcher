package fr.kizyow.bot;

import com.google.common.base.Stopwatch;
import fr.kizyow.bot.commands.CommandListener;
import fr.kizyow.bot.commands.CommandManager;
import fr.kizyow.bot.commands.commons.HelpCommand;
import fr.kizyow.bot.configurations.BotConfig;
import fr.kizyow.bot.database.Database;
import fr.kizyow.bot.database.SQLData;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.LoginException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class Bot {

    private final BotConfig botConfig;
    private final CommandManager commandManager;
    private final Database database;
    private final JDABuilder jdaBuilder;
    private final Logger logger = LoggerFactory.getLogger(Bot.class);

    private JDA jda;

    public Bot(String token, BotConfig botConfig) {
        this.botConfig = botConfig;
        this.commandManager = new CommandManager();
        this.database = new Database();
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
    }

    public void registerCommand() {
        logger.info("Registering commands");

        HelpCommand helpCommand = new HelpCommand(this);
        commandManager.registerCommand(helpCommand);

    }

    public void build() {

        try {
            jda = jdaBuilder.build().awaitReady();
            this.postBuild();
        } catch (LoginException | InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void a() {

        ResultSet resultSet = database.executeQuery("SELECT * FROM logs");
        if(resultSet == null) return;

        List<SQLData> dataList = SQLData.fromResultSet(resultSet);
        dataList.forEach(data -> logger.info(data.getLong("message_id") + ", "
                + data.getString("message_content") + ", "
                + data.getDate("message_date") + ", "
                + data.getLong("user_id") + ", "
                + data.getLong("id_ticket")));

    }

    public void b() {
        // seconde methode, très rapide mais traitement brut des donnees
        if (database.isConnected()) {

            try (Statement statement = database.getConnection().createStatement()) {

                ResultSet resultSet = statement.executeQuery("SELECT * FROM logs");

                while (resultSet.next()) {
                    logger.info(resultSet.getString("message_id") + ", "
                            + resultSet.getString("message_content") + ", "
                            + resultSet.getString("message_date") + ", "
                            + resultSet.getString("user_id") + ", "
                            + resultSet.getString("id_ticket"));
                }

                resultSet.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

    }

    public void postBuild() {
        if (jda == null) {
            logger.error("JDA is not setup properly, please restart the bot or contact an administrator", new NullPointerException());
            System.exit(1);
        }

        Stopwatch stopwatch = Stopwatch.createStarted();
        this.a();
        stopwatch.stop();
        logger.info(stopwatch.elapsed(TimeUnit.MILLISECONDS) + "ms elapsed (fake)");

        Stopwatch stopwatchB = Stopwatch.createStarted();
        this.b();
        stopwatchB.stop();
        logger.info(stopwatchB.elapsed(TimeUnit.MILLISECONDS) + "ms elapsed (fake)");

        Stopwatch stopwatchD = Stopwatch.createStarted();
        this.a();
        stopwatchD.stop();
        logger.info(stopwatchD.elapsed(TimeUnit.MILLISECONDS) + "ms elapsed ");

        Stopwatch stopwatchC = Stopwatch.createStarted();
        this.b();
        stopwatchC.stop();
        logger.info(stopwatchC.elapsed(TimeUnit.MILLISECONDS) + "ms elapsed ");

    }

    public BotConfig getConfig() {
        return botConfig;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public Database getDatabase() {
        return database;
    }

    public JDA getJDA() {
        if (jda == null) throw new NullPointerException("The bot isn't ready");
        return jda;
    }

}
