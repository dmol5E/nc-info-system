package com.nc.unc;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Hello world!
 *
 */
public class App extends Application
{
    public static void main( String[] args )
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(App.class.getResource("AddOrderItem.fxml"));
        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        //Pane pane = new Pane();
        //pane.setPrefSize(300,250);

        //Button button1 = new Button("Добавить нового клиента");
        //Button button2 = new Button("Сформировать заказ");
        //Button button3 = new Button("Добавить товар");
        //Button button4 = new Button("Пополнить товар");
        //Button button5 = new Button("Статистика");

        //StackPane stackPane=new StackPane();
        //stackPane.getChildren().add(button1);
        //Scene scene=new Scene(stackPane,300,250);
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
