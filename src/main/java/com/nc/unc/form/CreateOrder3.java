package com.nc.unc.form;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class CreateOrder3 extends Application {
    @FXML
    public Button next;
    @FXML
    public Button back;
    @FXML
    public Button create;
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
        Parent root = FXMLLoader.load(getClass().getResource("/com/nc/unc/form/CreateOrder3.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    public void returnBack(ActionEvent actionEvent) throws Exception {
        ((Stage)(((Button)actionEvent.getSource()).getScene().getWindow())).close();

        CreateOrder2 createOrder2=new CreateOrder2();
        createOrder2.start(new Stage());
    }

    @FXML
    public void goNext(ActionEvent actionEvent) {
    }
}
