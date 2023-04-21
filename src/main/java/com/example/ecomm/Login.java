package com.example.ecomm;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login {

    Customer customerLogin(String username, String password )  {
        DbConnection dbConnection = new DbConnection();
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        String hashedPassword = new String(md.digest(password.getBytes(StandardCharsets.UTF_8)));
        ResultSet rs = dbConnection.getQueryTable("select * from customer where email = '"+username+"' and password = '"+hashedPassword+"'");
        try {
            if(rs.next()){
               return new Customer(rs.getInt("id"),rs.getString("name"),rs.getString("email"),
                       rs.getString("mobile"),rs.getString("address"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
