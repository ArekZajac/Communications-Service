package com.company;

import java.sql.*;

public class Connections {

    private static final String databaseUsername = "root";
    private static final String databasePassword = "root123";
    private static final String databaseConnection = "jdbc:mysql://localhost:3306/communications-service" + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

    public static boolean serverConnection() {
        return true;
    }

    public static boolean verifyLogin(String username, String password) throws SQLException {
//        Connection con = null;
//        Statement stmt = null;
//
//        try {
//            con = DriverManager.getConnection(databaseConnection, databaseUsername, databasePassword);
//            stmt = con.createStatement();
//            ResultSet rsNameCheck = stmt.executeQuery("select id from useraccounts where username = \'" + username + "\'");
//            rsNameCheck.next();
//            int e = rsNameCheck.getInt("id");
//            ResultSet rsPassCheck = stmt.executeQuery("select password from useraccounts where id = \'" + e + "\'");
//            rsPassCheck.next();
//            String passwordFound = rsPassCheck.getString("password");
//            if (passwordFound.matches(password)) {
//                return true;
//            }
//        } catch (Exception f) {
//            f.printStackTrace();
//        } finally {
//            if (con != null) {
//                con.close();
//            }
//            if (stmt != null) {
//                stmt.close();
//            }
//            if (con != null) {
//                con.close();
//            }
//        }
//        return false;
        return true;
    }


}
