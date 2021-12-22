package fr.kizyow.bot.database;

import java.sql.ResultSet;
import java.util.List;

public abstract class Table {

    protected String table;

    public Table(String table) {
        this.table = table;

        if(Database.isConnected() && !Database.tableExist(table)){
            this.create();
        }

    }

    public List<SQLData> all() {
        ResultSet resultSet = Database.executeQuery("SELECT * FROM " + this.table);
        return SQLData.fromResultSet(resultSet);
    }

    public abstract void create();

}
