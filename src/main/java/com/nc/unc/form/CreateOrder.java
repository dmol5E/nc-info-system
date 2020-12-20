package com.nc.unc.form;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class CreateOrder extends Application {

    int phase;
    @FXML
    ProgressBar progress;
    @FXML
    Button back,next,search,add_customer;
    @FXML
    TableView table1,table2,table3;
    @FXML
    TextField input_customer, input_adress;
    @FXML
    TextFlow output_all_customer, output_customer;
    @FXML
    Label label_customer;

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/com/nc/unc/form/CreateOrder.fxml"));
        stage.setScene(new Scene(root));
        stage.show();

        //back=new Button();
        //label_customer=new Label();
        //progress=new ProgressBar();
        //next=new Button();
        //output_customer=new TextFlow();
        //table1=new TableView();
        //table2=new TableView();
        phase=0;
    }


    public void returnBack(ActionEvent actionEvent) {
        switch (phase) {
            case 1:
                phase--;
                progress.setProgress(phase*0.33);
                add_customer.setVisible(true);
                search.setVisible(true);
                back.setVisible(false);
                table1.setVisible(false);
                table2.setVisible(false);
                input_customer.setVisible(true);
                label_customer.setVisible(true);
                break;
            case 2:
                phase--;
                progress.setProgress(phase*0.33);
                table1.setVisible(true);
                table2.setVisible(true);
                next.setText("Далее");
                next.setLayoutX(660);
                next.setPrefWidth(70);
                output_customer.setVisible(false);
                table3.setVisible(false);
                input_adress.setVisible(false);
                output_customer.getChildren().clear();
                break;
        }
        //label_customer.setText(String.valueOf(phase));
    }

    public void goNext(ActionEvent actionEvent) {
        switch (phase) {
            case 0:
                phase++;
                progress.setProgress(phase*0.33);
                add_customer.setVisible(false);
                search.setVisible(false);
                back.setVisible(true);
                table1.setVisible(true);
                table2.setVisible(true);
                input_customer.setVisible(false);
                label_customer.setVisible(false);
                break;
            case 1:
                phase++;
                progress.setProgress(phase*0.33);
                table1.setVisible(false);
                table2.setVisible(false);
                next.setText("Сформировать заказ");
                next.setLayoutX(570);
                next.setPrefWidth(150);
                output_customer.setVisible(true);
                Text text_1=new Text("Клиент\n");
                Text text_2=new Text("ФИО\n");
                Text text_3=new Text("Номер телефона\n");
                Text text_4=new Text("Адрес   ");
                output_customer.getChildren().add(text_1);
                output_customer.getChildren().add(text_2);
                output_customer.getChildren().add(text_3);
                output_customer.getChildren().add(text_4);
                output_customer.setLineSpacing(20);
                output_customer.setTextAlignment(TextAlignment.LEFT);
                table3.setVisible(true);
                input_adress.setVisible(true);
                break;
        }
        //label_customer.setText(String.valueOf(phase));
    }

    public void search(ActionEvent actionEvent) {
    }

    public void createNewClient(ActionEvent actionEvent) {
    }
}
