package fr.kizyow.bot.database.tables;

import fr.kizyow.bot.database.Database;
import fr.kizyow.bot.database.SQLData;
import fr.kizyow.bot.database.Table;
import net.dv8tion.jda.api.entities.Guild;

import java.sql.ResultSet;
import java.util.List;

public class GuildTable extends Table {

    public GuildTable() {
        super("guild");
    }

    public void createGuild(Guild guild) {
        Database.executePreparedUpdate(
                "INSERT INTO guild VALUES(?,?)",
                guild.getIdLong(), guild.getName()
        );
    }

    public boolean guildExists(Guild guild) {
        ResultSet resultSet = Database.executePreparedQuery(
                "SELECT * FROM guild WHERE id = ?",
                guild.getIdLong()
        );
        List<SQLData> dataList = SQLData.fromResultSet(resultSet);
        return !dataList.isEmpty();
    }

    @Override
    public void create() {
        Database.execute(
                "CREATE TABLE guild ( " +
                        "id bigint(20) PRIMARY KEY," +
                        "name varchar(255)" +
                        ");"
        );

    }

}
