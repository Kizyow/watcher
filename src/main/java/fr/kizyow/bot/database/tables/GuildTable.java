package fr.kizyow.bot.database.tables;

import fr.kizyow.bot.database.Database;
import fr.kizyow.bot.database.Table;
import net.dv8tion.jda.api.entities.Guild;

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
