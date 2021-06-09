package com.example.application.Connector;

import java.sql.*;

public class ConnectorMySQL implements Connector {

    private Connection connection;


    public ConnectorMySQL() {
        try {
            String url = "jdbc:mysql://90.228.222.153:3306/agile_library?serverTimezone=UTC&useLegacyDatetimeCode=false";
            String user = "mikael";
            String password = "newton";
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection(url, user, password);

            System.out.println("YOU ARE NOW --- Connected to Database: -> " + url + "\n");

        } catch (Exception ex) {
            System.out.println("Oops, there´s an error");
            ex.printStackTrace();
        }
    }


    public Boolean callcheck_loan(Integer usersID, Integer bookID) throws SQLException {

        Boolean bool = false;
        int boolVal;
        String sql = "Call checkLoan(?,?,?);";
        try (CallableStatement stmt = connection.prepareCall(sql)) {
            stmt.setInt(1, usersID);
            stmt.setInt(2, bookID);
            stmt.registerOutParameter(3, Types.BOOLEAN);
            stmt.execute();
            boolVal = stmt.getInt(3);

        }
        if (boolVal == 0){
             bool = false;
        }else if (boolVal == 1){
             bool = true;
        }
      return bool;
    }

}
