package com.company;

import java.sql.*;

class Connections {

    private static final String databaseUsername = "root";
    private static final String databasePassword = "root123";
    private static final String databaseConnection = "jdbc:mysql://localhost:3306/communications-service" + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

    static boolean serverConnection() {
        return true;
    }

    static boolean verifyLogin(String username, String password){

        try (Connection con = DriverManager.getConnection(databaseConnection, databaseUsername, databasePassword); Statement stmt = con.createStatement()) {
            ResultSet rsNameCheck = stmt.executeQuery("select id from useraccounts where username = \'" + username + "\'");
            rsNameCheck.next();
            int e = rsNameCheck.getInt("id");
            ResultSet rsPassCheck = stmt.executeQuery("select password from useraccounts where id = \'" + e + "\'");
            rsPassCheck.next();
            String passwordFound = rsPassCheck.getString("password");
            if (passwordFound.matches(password)) {
                return true;
            }
        } catch (Exception f) {
            f.printStackTrace();
        }
        return false;
    }


}
