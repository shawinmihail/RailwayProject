package db.executors;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class Executor
{

    public Connection connection;
    public HashMap[] responseMaps;

    public void execute(String queryText) {}

    //method for get some types data from ResultSet object
    public Object getFromResultSet(String name, String type, ResultSet resultSet) {

        final String typeInteger1 = "INTEGER";
        final String typeInteger2 = "integer";
        final String typeInteger3 = "int";
        final String typeText1 = "TEXT";
        final String typeText2 = "text";
        final String typeBoolean1 = "BOOLEAN";
        final String typeBoolean2 = "boolean";
        final String typeDate1 = "DATE";
        final String typeDate2 = "date";
        try {
            switch (type) {
                case typeInteger1:
                    return resultSet.getInt(name);
                case typeInteger2:
                    return resultSet.getInt(name);
            case typeInteger3:
                return resultSet.getInt(name);
                case typeText1:
                    return resultSet.getString(name);
                case typeText2:
                     return resultSet.getString(name);
                case typeBoolean1:
                    return resultSet.getBoolean(name);
                case typeBoolean2:
                    return resultSet.getBoolean(name);
                default:
                    return null;
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    //counter number of lines in ResultSet
    public int getResultSetLength(String queryCountText){

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSetQueryCount = statement.executeQuery(queryCountText);
            int queryCount = resultSetQueryCount.getInt(1);
            statement.close();
            return queryCount;
        }
        catch (SQLException e){
            e.printStackTrace();
            return 0;
        }
    }
}
