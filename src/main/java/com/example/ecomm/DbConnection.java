package com.example.ecomm;
import java.sql.*;
public class DbConnection {
    private final String url = "jdbc:mysql://localhost:3306/ecommerce";
    private final String uname = "root";
    private final String password = "1234";

    Statement getStatement(){
        try{
            Connection connection = DriverManager.getConnection(url,uname,password);
            Statement statement = connection.createStatement();
            return statement;

        }catch (Exception e){
            e.printStackTrace();
        }
return null;
    }

    ResultSet getQueryTable(String query){
        try {
            Statement statement = getStatement();
           return statement.executeQuery(query);
        }catch (Exception e){
            e.printStackTrace();
        }
      return null;
    }

    int updateDataBase(String query){
        try {
            Statement statement = getStatement();
            return statement.executeUpdate(query);
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    public static void main(String[] args) {
        DbConnection dbConnection = new DbConnection();
        ResultSet rs = dbConnection.getQueryTable("select * from customer");
        if(rs!= null){
            System.out.println("Connection successfull");
        }else {
            System.out.println("Connection failed");
        }
    }
}
