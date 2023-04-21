package com.example.ecomm;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;

public class Product {
    private SimpleIntegerProperty id;
    private SimpleStringProperty name;
    private SimpleDoubleProperty price;

    private SimpleStringProperty imageName;

    public String getImageName() {
        return imageName.get();
    }



    public Product(int id, String name, double price, String imageName) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleDoubleProperty(price);
        this.imageName = new SimpleStringProperty(imageName);
    }

    static ObservableList<Product> getProductTable(){
        String query = "SELECT id,name,price,image from product";
        return fetchProductData(query);
    }

    static ObservableList<Product> fetchProductData(String query){
        ObservableList<Product> products = FXCollections.observableArrayList();
        DbConnection dbConnection = new DbConnection();

        try{
            ResultSet rs = dbConnection.getQueryTable(query);
            while (rs.next()){
                Product product = new Product(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getString("image"));
                products.add(product);
            }
            return products;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public int getId() {
        return id.get();
    }



    public String getName() {
        return name.get();
    }


    public double getPrice() {
        return price.get();
    }


}
