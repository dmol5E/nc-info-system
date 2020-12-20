package com.nc.unc.form;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class CreateOrder2  extends Application {

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

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/com/nc/unc/form/CreateOrder2.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    public void returnBack(ActionEvent actionEvent) throws Exception {
        ((Stage)(((Button)actionEvent.getSource()).getScene().getWindow())).close();

        CreateOrder1 createOrder1=new CreateOrder1();
        createOrder1.start(new Stage());
    }

    @FXML
    public void goNext(ActionEvent actionEvent) throws Exception {
        ((Stage)(((Button)actionEvent.getSource()).getScene().getWindow())).close();

        CreateOrder3 createOrder3=new CreateOrder3();
        createOrder3.start(new Stage());
    }
}
