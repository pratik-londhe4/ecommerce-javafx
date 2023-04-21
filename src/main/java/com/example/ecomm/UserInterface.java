package com.example.ecomm;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UserInterface {
    GridPane loginPage;
    GridPane signupPage;
    HBox headerBar;

    HBox footerBar;

    Button signInButton;
    Button signUpButton;
    Button placeOrderButton = new Button("Place Order");
    Label welcomeLabel;

    VBox body ;

    ProductList productList = new ProductList();
    VBox productPage;

    Customer loggedInCustomer;
    ObservableList<Product> itemsInCart = FXCollections.observableArrayList();

    public UserInterface(){
        createLoginPage();
        createHeaderBar();
        createSignupPage();
        createFooterBar();
    }
    BorderPane createContent(){
        BorderPane root = new BorderPane();
//        root.setCenter(loginPage);
        productPage = productList.getAllProducts();
        body = new VBox();
        welcomeLabel = new Label();
        body.setPadding(new Insets(10));
        body.setAlignment(Pos.CENTER);
        body.getChildren().add(productPage);
        root.setCenter(body);
        root.setTop(headerBar);
        root.setBottom(footerBar);
        return root;
    }

    private void createLoginPage(){
        Text usernameText = new Text("Username");
        Text passwordText = new Text("Password");

        TextField userName = new TextField("pratik@mail.com");
        userName.setPromptText("Enter username");
        PasswordField password = new PasswordField();
        password.setText("password");
        password.setPromptText("Enter Password");
        Label messageLabel = new Label();

        Button loginButton = new Button("Login");

        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String uname = userName.getText();
                String pass = password.getText();
                Login login = new Login();
               loggedInCustomer = login.customerLogin(uname,pass);
               if (loggedInCustomer != null){
                   welcomeLabel.setText("Welcome "+loggedInCustomer.getName());
                   headerBar.getChildren().add(welcomeLabel);
                   body.getChildren().remove(loginPage);
                   body.getChildren().add(productPage);

               }else {
                   messageLabel.setText("Error: wrong username or password");
               }

            }
        });
        loginPage = new GridPane();
        loginPage.setAlignment(Pos.CENTER);
        loginPage.setHgap(10);
        loginPage.setVgap(10);
        loginPage.add(usernameText,0,0);
        loginPage.add(userName,1,0);
        loginPage.add(passwordText,0,1);
        loginPage.add(password,1,1);
        loginPage.add(loginButton,1,2);
        loginPage.add(messageLabel,1,3);
    }

    private void createSignupPage(){
        Text nameText = new Text("Full Name");
        Text mobileText = new Text("Mobile No.");
        Text emailText = new Text("Email");
        Text passwordText = new Text("Password");

        TextField name = new TextField();
        TextField mobile = new TextField();
        TextField email = new TextField();
        PasswordField password = new PasswordField();


        Label messageLabel = new Label();

        Button signupButton = new Button("Signup");

        signupButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    if (email.getText().isEmpty() || mobile.getText().isEmpty() || name.getText().isEmpty() || password.getText().isEmpty()){
                        messageLabel.setText("All fields are required");
                        return;
                    }
                    String uemail = email.getText();
                    String uname = name.getText();
                    String umobile = mobile.getText();

                    MessageDigest md = MessageDigest.getInstance("SHA-512");
                    String hashedPassword = new String(md.digest(password.getText().getBytes(StandardCharsets.UTF_8)));
                    //System.out.println(hashedPassword);
                    //crete new customer and add to database
                    Customer customer = new Customer(uname,uemail,umobile,hashedPassword);
                    //adding to db
                  boolean res =  Signup.customerLogin(customer);
                  if (res){
                      createDialog("Registered Successfully");
                  }else {
                      createDialog("Registration failed");
                  }
                } catch (NoSuchAlgorithmException e) {
                    throw new RuntimeException(e);
                }

            }
        });
        signupPage = new GridPane();
        signupPage.setVgap(10);

        signupPage.setStyle("-fx-border-color: black;-fx-border-width: 2pt;-fx-padding: 10pt;-fx-max-width: 300pt");
        signupPage.setAlignment(Pos.CENTER);
        signupPage.setHgap(10);
        signupPage.setVgap(10);
        signupPage.add(nameText,0,0);
        signupPage.add(name,1,0);
        signupPage.add(emailText,0,1);
        signupPage.add(email,1,1);
        signupPage.add(mobileText,0,2);
        signupPage.add(mobile,1,2);
        signupPage.add(passwordText,0,3);
        signupPage.add(password,1,3);
        signupPage.add(messageLabel,0,4);
        signupPage.add(signupButton,1,4);

    }

    private void createHeaderBar(){
        Button homeButton = new Button();
        Image logo = new Image("file:/home/pratik/IdeaProjects/e-comm/Images/home.png");
        ImageView logoView = new ImageView(logo);
        logoView.setFitWidth(85);
        logoView.setFitHeight(20);
        homeButton.setGraphic(logoView);
        TextField search = new TextField();
        search.setPrefWidth(250);
        search.setPromptText("Type to search");

        Button searchButton = new Button("Search");
        Button cartButton = new Button("Cart");




        signInButton = new Button("Login");
        signUpButton = new Button("Signup");



        headerBar = new HBox();
        headerBar.setSpacing(10);
        headerBar.setPadding(new Insets(10));
        headerBar.setAlignment(Pos.CENTER);
        headerBar.getChildren().addAll(homeButton,search,searchButton,signInButton,signUpButton,cartButton);

        signInButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                body.getChildren().clear();
                body.getChildren().add(loginPage);
                headerBar.getChildren().remove(signInButton);
                footerBar.setVisible(false);
            }
        });
        cartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                VBox cartPage = productList.getProductsInCart(itemsInCart);
                cartPage.setAlignment(Pos.CENTER);
                cartPage.setSpacing(10);
                cartPage.getChildren().add(placeOrderButton);
                body.getChildren().clear();
                body.getChildren().add(cartPage);
                footerBar.setVisible(false);
            }
        });
        placeOrderButton.setAlignment(Pos.CENTER);
        placeOrderButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(itemsInCart == null){
                    createDialog("Please add items in cart to place order");
                    return;
                }
                if (loggedInCustomer == null){
                    createDialog("Please login first to place order");
                    return;
                }

                int count = Order.placeMultipleOrder(loggedInCustomer,itemsInCart);
                if (count != 0){
                    createDialog("Order placed for "+count+" items successfully");
                }else {
                    createDialog("Order failed");
                }
            }

        });

        homeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                body.getChildren().clear();
                body.getChildren().add(productPage);
                footerBar.setVisible(true);
                if (loggedInCustomer == null && !headerBar.getChildren().contains(signInButton))
                headerBar.getChildren().add(signInButton);
            }
        });
        signUpButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                body.getChildren().clear();
                body.getChildren().add(signupPage);
            }
        });
    }

    private void createFooterBar(){

        Button buyNowButton = new Button("Buy Now");
        Button addToCartButton = new Button("Add To Cart");

        footerBar = new HBox();
        footerBar.setSpacing(10);
        footerBar.setPadding(new Insets(10));
        footerBar.setAlignment(Pos.CENTER);
        footerBar.getChildren().addAll(buyNowButton,addToCartButton);

        buyNowButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Product product = productList.getSelectedProduct();
                if(product == null){
                    createDialog("Please select product to place order");
                    return;
                }
                if (loggedInCustomer == null){
                    createDialog("Please login first to place order");
                    return;
                }

                boolean count = Order.placeOrder(loggedInCustomer,product);
                if (count){
                    createDialog("Order placed successfully");
                }else {
                    createDialog("Order failed");
                }
            }
        });

        addToCartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Product product = productList.getSelectedProduct();
                if(product == null){
                    createDialog("Please select product to add in cart");
                    return;
                }
                itemsInCart.add(product);
                createDialog("Item added in cart");
            }
        });

    }

    void createDialog(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Message");
        alert.setContentText(message);
        alert.show();
    }
}
