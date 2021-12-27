package fr.kizyow.bot.database.tables;

import fr.kizyow.bot.database.Database;
import fr.kizyow.bot.database.SQLData;
import fr.kizyow.bot.database.Table;
import net.dv8tion.jda.api.entities.User;

import java.sql.ResultSet;
import java.time.Instant;
import java.util.List;

public class LevelTable extends Table {

    public LevelTable(String guildId) {
        super("levels_" + guildId);
    }

    public void createUser(User user) {
        Database.executePreparedUpdate(
                "INSERT INTO " + this.table + " VALUES (?, ?, ?, ?)",
                user.getIdLong(), 1, 0, 0
        );
    }

    public void updateExperience(User user, int exp) {

        if (!userExists(user)) {
            createUser(user);
        }

        Database.executePreparedUpdate(
                "UPDATE " + this.table + " SET experience = ? WHERE user_id = ?",
                exp, user.getIdLong()
        );
    }

    public void updateLevel(User user, int level) {

        if (!userExists(user)) {
            createUser(user);
        }

        Database.executePreparedUpdate(
                "UPDATE " + this.table + " SET level = ? WHERE user_id = ?",
                level, user.getIdLong()
        );
    }

    public void updateLastMessage(User user) {

        if (!userExists(user)) {
            createUser(user);
        }

        Database.executePreparedUpdate(
                "UPDATE " + this.table + " SET last_message_update = ? WHERE user_id = ?",
                Instant.now().getEpochSecond(), user.getIdLong()
        );
    }

    public int getPlace(User user) {

        if (!userExists(user)) {
            createUser(user);
        }

        ResultSet resultSet = Database.executeQuery("SELECT * FROM " + this.table + " ORDER BY level DESC, experience DESC");
        int place = 1;

        List<SQLData> dataList = SQLData.fromResultSet(resultSet);
        for (SQLData data : dataList) {
            if (data.getLong("user_id") == user.getIdLong()) break;
            place++;
        }

        return place;

    }

    public int getExperience(User user) {

        if (!userExists(user)) {
            createUser(user);
            return 0;
        }

        ResultSet resultSet = Database.executePreparedQuery(
                "SELECT experience FROM " + this.table + " WHERE user_id = ?",
                user.getIdLong()
        );

        List<SQLData> dataList = SQLData.fromResultSet(resultSet);
        return dataList.isEmpty() ? 0 : dataList.get(0).getInteger("experience");

    }

    public int getLevel(User user) {

        if (!userExists(user)) {
            createUser(user);
            return 1;
        }

        ResultSet resultSet = Database.executePreparedQuery(
                "SELECT level FROM " + this.table + " WHERE user_id = ?",
                user.getIdLong()
        );

        List<SQLData> dataList = SQLData.fromResultSet(resultSet);
        return dataList.isEmpty() ? 1 : dataList.get(0).getInteger("level");

    }

    public long getLastMessageUpdate(User user) {

        if (!userExists(user)) {
            createUser(user);
            return 0;
        }

        ResultSet resultSet = Database.executePreparedQuery(
                "SELECT last_message_update FROM " + this.table + " WHERE user_id = ?",
                user.getIdLong()
        );

        List<SQLData> dataList = SQLData.fromResultSet(resultSet);
        return dataList.isEmpty() ? 0 : dataList.get(0).getLong("last_message_update");

    }

    public boolean userExists(User user) {
        ResultSet resultSet = Database.executePreparedQuery(
                "SELECT * FROM " + this.table + " WHERE user_id = ?",
                user.getIdLong()
        );
        List<SQLData> dataList = SQLData.fromResultSet(resultSet);
        return !dataList.isEmpty();
    }

    @Override
    public void create() {

        Database.execute("CREATE TABLE " + this.table + "(" +
                "user_id bigint(20) PRIMARY KEY," +
                "level int(11)," +
                "experience int(11)," +
                "last_message_update bigint(20)" +
                ");"
        );

    }

}
