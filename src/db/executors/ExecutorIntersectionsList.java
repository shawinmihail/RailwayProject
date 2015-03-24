package db.executors;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class ExecutorIntersectionsList extends Executor {

    String queryCountText;
    String queryText;

    //Constructor
    public ExecutorIntersectionsList(Connection connection) {

        this.connection = connection;
        queryText = "select * from Intersections";
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
                answer.put("intersection_id", resultSetQuery.getInt("intersection_id"));
                answer.put("first_station_name", resultSetQuery.getString("first_station_name"));
                answer.put("second_station_name", resultSetQuery.getString("second_station_name"));
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
