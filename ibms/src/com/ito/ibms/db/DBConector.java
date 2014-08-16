package com.ito.ibms.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

class DBConector {
    private static Connection sConnection = null;

    public static Connection getConnection() {
        if (sConnection != null) {
            Statement statement;
            try {
                statement = sConnection.createStatement();
                statement.execute("SELECT * FROM TIPO_CURSO ORDER BY NOME");
            } catch (SQLException e) {
                sConnection = null;
            }
        }

        if (sConnection == null) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                String connURL = "jdbc:mysql://dbmy0062.whservidor.com:3306/rjvalentes_1";
                sConnection = DriverManager.getConnection(connURL, "rjvalentes_1", "960751x0");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return sConnection;
    }
}
