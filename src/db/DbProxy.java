package db;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

import java.io.File;
import java.io.IOException;
import java.lang.Class;import java.lang.ClassNotFoundException;import java.lang.String;import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DbProxy
{
    public static Connection connection;

    public DbProxy()
    {
        try
        {
            Class.forName("org.sqlite.JDBC");
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        start();
    }

    public void open(String dbPath)
    {
        if (connection != null)
        {
            return;
        }

        try
        {
            connection = DriverManager.getConnection("jdbc:sqlite:"+ dbPath);
            connection.setAutoCommit(true);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void close()
    {
        if (connection == null)
        {
            return;
        }
        try
        {
            connection.close();
            connection = null;
        }
        catch (SQLException e)
        {
            connection = null;
            e.printStackTrace();
        }
    }

    private void create_schema() {

        String script;
        String sqlScriptPath = "db_scheme2.sql";
        try {
            script = Resources.toString(Resources.getResource(sqlScriptPath), Charsets.UTF_8);
            try {
                Statement e = this.connection.createStatement();
                e.executeUpdate(script);
                e.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {

        String dbPath = "RailwayNet2.sqlite";
        try {
            if(!new File(dbPath).exists()) {
                new File(dbPath).createNewFile();
                this.open(dbPath);
                this.create_schema();
            } else {
                this.open(dbPath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

