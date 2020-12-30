package com.nc.unc.form;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CreateOrder1 extends Application implements Initializable {

    @FXML
    public Button next;
    @FXML
    public Button back;
    @FXML
    public Button button1;
    @FXML
    public Button button2;
    @FXML
    public Button button3;
    @FXML
    public Button button4;
    @FXML
    public Button button5;
    @FXML
    public TextField input_customer;
    @FXML
    public Label label;

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/com/nc/unc/form/CreateOrder1.fxml"));
        stage.setScene(new Scene(root));
        stage.show();


    }

    @FXML
    public void returnBack(ActionEvent actionEvent) {

    }

    @FXML
    public void createNewClient(ActionEvent actionEvent) {
    }

    @FXML
    public void createNewProduct(ActionEvent actionEvent) {
    }

    @FXML
    public void addProduct(ActionEvent actionEvent) {
    }

    @FXML
    public void getStatistics(ActionEvent actionEvent) {
    }

    @FXML
    public void search(ActionEvent actionEvent) throws IOException {
        //label=new Label();
        String name_client=input_customer.getText();
        label.setText(name_client);
        //input_customer.setText(label.getText());
    }

    @FXML
    public void goNext(ActionEvent actionEvent) throws Exception {
        ((Stage)(((Button)actionEvent.getSource()).getScene().getWindow())).close();

        CreateOrder2 createOrder2=new CreateOrder2();
        createOrder2.start(new Stage());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        label=new Label();
        //input_customer.setText("");
    }
}
