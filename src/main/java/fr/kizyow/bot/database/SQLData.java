package fr.kizyow.bot.database;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;

public class SQLData {

    private final Map<String, Object> dataMap;
    private ResultSetMetaData metaData;


    private SQLData(ResultSet resultSet) {
        this.dataMap = new HashMap<>();

        try {

            if (resultSet != null) {
                this.metaData = resultSet.getMetaData();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        this._initWith(resultSet);

    }


    public String getString(String key) {
        Object object = this.getObject(key);
        return String.valueOf(object);
    }

    public Integer getInteger(String key) {
        Object object = this.getObject(key);

        if (object instanceof Integer) return (Integer) object;
        else return null;
    }

    public Long getLong(String key) {
        Object o = this.getObject(key);

        if (o instanceof Long) return (Long) o;
        else return null;
    }

    public Float getFloat(String key) {
        Object o = this.getObject(key);

        try {
            return Float.parseFloat(o.toString());
        } catch (Exception ignored) {
        }

        return null;
    }

    public Object getObject(String key) {
        return this.dataMap.get(key);
    }

    public Date getDate(String key) {
        Object o = this.getObject(key);

        if (o instanceof Date) return (Date) o;
        else return null;
    }


    public int getNbColumns() {
        return this.dataMap.size();
    }

    public ResultSetMetaData getMetaData() {
        return this.metaData;
    }

    public Object getValueAt(int index) {
        return this.dataMap.values().toArray()[index];
    }


    private void _initWith(ResultSet set) {
        if (set == null) return;

        try {

            int columns = set.getMetaData().getColumnCount();
            if (!set.next()) return;

            for (int i = 1; i <= columns; i++)
                this.dataMap.put(set.getMetaData().getColumnLabel(i), set.getObject(i));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String toString() {
        return "{SQLDataSet (keys=" + Arrays.deepToString(this.dataMap.keySet().toArray()) + " values=" + Arrays.deepToString(this.dataMap.values().toArray()) + ")}";
    }


    public static List<SQLData> fromResultSet(ResultSet resultSet) {
        List<SQLData> dataSets = new ArrayList<>();
        SQLData dataSet;

        do {
            dataSet = new SQLData(resultSet);

            if (dataSet.dataMap.size() == 0) dataSet = null;
            else dataSets.add(dataSet);
        }
        while (dataSet != null);

        try {
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dataSets;
    }

}
