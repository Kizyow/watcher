package fr.kizyow.bot.database.tables;

import fr.kizyow.bot.database.Database;
import fr.kizyow.bot.database.SQLData;
import fr.kizyow.bot.database.Table;
import net.dv8tion.jda.api.entities.User;

import java.sql.ResultSet;
import java.util.List;

public class AdminTable extends Table {

    public AdminTable() {
        super("admin");
    }

    public void promoteUser(User user) {
        Database.executePreparedUpdate(
                "INSERT INTO admin VALUES(?)",
                user.getIdLong()
        );
    }

    public void depromoteUser(User user) {
        Database.executePreparedUpdate(
                "DELETE FROM admin WHERE id = ?",
                user.getIdLong()
        );
    }

    public boolean hasRights(User user) {
        ResultSet resultSet = Database.executePreparedQuery(
                "SELECT * FROM admin WHERE id = ?",
                user.getIdLong()
        );
        List<SQLData> dataList = SQLData.fromResultSet(resultSet);
        return !dataList.isEmpty();
    }

    @Override
    public void create() {
        Database.execute("CREATE TABLE admin (" +
                "id bigint(20) PRIMARY KEY" +
                ");"
        );
    }
}
