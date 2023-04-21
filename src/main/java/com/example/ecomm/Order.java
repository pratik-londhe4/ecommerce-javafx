package com.example.ecomm;

import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.util.ResourceBundle;

public class Order {

    static boolean placeOrder(Customer customer, Product product){
        String groupOrderId = "select max(group_order_id)+1  id from orders";

        try
        {
            DbConnection dbConnection = new DbConnection();
            ResultSet rs = dbConnection.getQueryTable(groupOrderId);
            if (rs.next()){
                String placeOrder = "insert into orders(group_order_id,customer_id,product_id) " +
                        "values("+rs.getInt("id")+","+customer.getId()+","+product.getId()+")";
                return dbConnection.updateDataBase(placeOrder)!= 0;

            }




        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    static int placeMultipleOrder(Customer customer, ObservableList<Product>  products){
        String groupOrderId = "select max(group_order_id)+1  id from orders";
        try
        {
            DbConnection dbConnection = new DbConnection();
            ResultSet rs = dbConnection.getQueryTable(groupOrderId);
            int count = 0;
            if (rs.next()){
                for (Product product : products){
                    String placeOrder = "insert into orders(group_order_id,customer_id,product_id) " +
                            "values("+rs.getInt("id")+","+customer.getId()+","+product.getId()+")";
                    count+=dbConnection.updateDataBase(placeOrder);
                }

                return count;

            }




        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }
}
