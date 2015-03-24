package db.executors;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class ExecutorRoute extends Executor {

    String queryCountText;
    String queryText;

    //Constructor
    public ExecutorRoute(Connection connection, String train_name) {

        this.connection = connection;
        queryText = "select * from Routes where train_name = '"+train_name+"'";
        queryCountText = "select count(*) from ("+queryText+")";
        execute(queryText);
    }

    @Override
    public void execute(String queryText) {

        int ResultSetLength = getResultSetLength(queryCountText);
        if (ResultSetLength != 0)
        {
            this.responseMaps = new HashMap[ResultSetLength];
        }
        else
        {
            this.responseMaps = null;
            return;
        }

        try
        {
            int answerNumber = 0;
            Statement statementQuery = connection.createStatement();
            ResultSet resultSetQuery = statementQuery.executeQuery(queryText);
            HashMap<String, Object> answer = new HashMap();
            while (resultSetQuery.next())
            {
                answer.put("name", resultSetQuery.getString("train_name"));
                answer.put("station_index", resultSetQuery.getInt("station_index"));
                answer.put("station_name", resultSetQuery.getString("station_name"));
                this.responseMaps[answerNumber] = new HashMap();
                this.responseMaps[answerNumber].putAll(answer);
                answerNumber++;
            }
            statementQuery.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
