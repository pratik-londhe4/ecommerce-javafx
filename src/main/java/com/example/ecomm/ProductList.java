package com.example.ecomm;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class ProductList {
    TableView<Product> productTable;

    VBox createProductTable( ObservableList<Product> data){
        VBox vBox = new VBox();

        TableColumn id = new TableColumn("ID");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn name = new TableColumn("Name");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn price = new TableColumn("Price");
        price.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<Product , String> image = new TableColumn<>("Image");
        image.setCellValueFactory(new PropertyValueFactory<>("imageName"));

        image.setCellFactory(col ->{
            return new TableCell<>(){
                @Override
                protected void updateItem(String imageName, boolean empty) {
                    super.updateItem(imageName, empty);
                    if (empty || imageName == null) {
                        setGraphic(null);
                    } else {
                        // Load image from file name

                        ImageView imageView = new ImageView(new Image("file:/home/pratik/IdeaProjects/e-comm/Images/"+imageName+".jpg"));
                        imageView.setFitWidth(150);
                        imageView.setFitHeight(150);
                        setGraphic(imageView);
                    }
                }

            };
        });



        productTable = new TableView<>();
        productTable.getColumns().addAll(id,name,price,image);
        productTable.setItems(data);
        productTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        vBox.setPadding(new Insets(10));
        vBox.getChildren().add(productTable);

        return vBox;
    }

    VBox getAllProducts(){
        ObservableList<Product> data = Product.getProductTable();
        return createProductTable(data);
    }

    Product getSelectedProduct(){
        return productTable.getSelectionModel().getSelectedItem();
    }

    VBox getProductsInCart(ObservableList<Product> data){
        return createProductTable(data);
    }
}
