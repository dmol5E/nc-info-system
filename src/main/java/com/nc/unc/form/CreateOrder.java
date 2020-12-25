package com.nc.unc.form;

import com.nc.unc.repositories.*;
import com.nc.unc.model.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.Map;

public class CreateOrder extends Application {

    int phase;
    @FXML
    ProgressBar progress;
    @FXML
    Button back,next,search,add_customer;
    @FXML
    TableView<Product> table1;
    @FXML
    TableView<Product> table2;
    @FXML
    TableView table3;
    @FXML
    TextField input_customer, input_address;
    @FXML
    TextFlow output_all_customer, output_customer;
    @FXML
    Label label_customer;
    @FXML
    TableColumn<Product, String> table2_name;
    @FXML
    TableColumn<Product, Integer> table2_count, table2_price;
    @FXML
    TableColumn<Product, CheckBox> table2_select;
    @FXML
    TableColumn<Product, String> table1_name;
    @FXML
    TableColumn<Product, Integer> table1_price;
    @FXML
    TableColumn<Product, TextField> table1_count;
    //@FXML
    //TableColumn<Product, Integer> table1_price;
    //@FXML
    //TableColumn<Product, Te> table1_price;

    private CustomerRepository customerRepository = new CustomerRepository();
    private AddressRepository addressRepository = new AddressRepository();
    private ProductRepository productRepository = new ProductRepository();
    private OrderItemRepository orderItemRepository = new OrderItemRepository();
    private OrderRepository orderRepository = new OrderRepository();

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/com/nc/unc/form/CreateOrder.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
        phase=0;
    }

    @FXML
    private void initialize() {
        start();
        //table2_name=new TableColumn();
        table2_count.setCellValueFactory(new PropertyValueFactory<Product,Integer>("count"));
        table2_name.setCellValueFactory(new PropertyValueFactory<Product,String>("name"));
        table2_select.setCellValueFactory(new PropertyValueFactory<Product,CheckBox>("select"));
        table2_price.setCellValueFactory(new PropertyValueFactory<Product,Integer>("price"));


        table1_count.setCellValueFactory(new PropertyValueFactory<Product,TextField>("count"));
        table1_name.setCellValueFactory(new PropertyValueFactory<Product,String>("name"));
        table1_price.setCellValueFactory(new PropertyValueFactory<Product,Integer>("price"));
    }

    public void start(){

        Customer customer1=new Customer(1,"Алексей","Афанасьев","0657650047", LocalDate.of(1999,5,25));
        Customer customer2=new Customer(2,"Николай","Новиков","1237", LocalDate.of(1999,5,25));
        Customer customer3=new Customer(3,"Роман","Романов","56476647", LocalDate.of(1999,5,25));
        customerRepository.put(customer1);
        customerRepository.put(customer2);
        customerRepository.put(customer3);

        //repository = new CustomerRepository();
        //repository.put(customer1);
        //repository.put(customer2);
        //repository.put(customer3);
        for (Map.Entry<Long, Customer> i: customerRepository.getEntities().entrySet()) {
            Text text = new Text(i.getValue().getFirstName() + " " + i.getValue().getLastName()+"\n");
            output_all_customer.getChildren().add(text);
        }
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
                input_address.setVisible(false);
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
                input_address.setVisible(true);
                break;
        }
        //label_customer.setText(String.valueOf(phase));
    }

    public void search(ActionEvent actionEvent) {

        String name=input_customer.getText();
        //System.out.println("GG"+name);
        Map<Long, Customer> customerMap=customerRepository.getCustomer(name);
        output_all_customer.getChildren().clear();
        for (Map.Entry<Long, Customer> i: customerMap.entrySet()) {
            Text text = new Text(i.getValue().getFirstName() + " " + i.getValue().getLastName()+"\n");
            output_all_customer.getChildren().add(text);
        }
    }

    public void createNewClient(ActionEvent actionEvent) {

        Label label_first_name = new Label("Имя");
        label_first_name.setLayoutX(10);
        label_first_name.setLayoutY(15);
        TextField input_first_name=new TextField();
        input_first_name.setLayoutX(65);
        input_first_name.setLayoutY(10);

        Label label_second_name = new Label("Фамилия");
        label_second_name.setLayoutX(10);
        label_second_name.setLayoutY(55);
        TextField input_second_name=new TextField();
        input_second_name.setLayoutX(65);
        input_second_name.setLayoutY(50);
//
        Label label_phone = new Label("Телефон");
        label_phone.setLayoutX(10);
        label_phone.setLayoutY(95);
        TextField input_phone=new TextField();
        input_phone.setLayoutX(65);
        input_phone.setLayoutY(90);
//
        Label label_date = new Label("Дата рождения");
        label_date.setLayoutX(10);
        label_date.setLayoutY(125);

        Button add_customer=new Button("Добавить");
        add_customer.setLayoutX(10);
        add_customer.setLayoutY(145);
        add_customer.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                boolean cheak=true;
                String name1="",name2="",phone="";

                if (input_first_name.getText().trim().isEmpty()){
                    //input_first_name.setText("Введите имя");
                    input_first_name.setPromptText("Введите имя");
                    cheak=false;
                }
                else {
                    name1=input_first_name.getText();
                }

                if (input_second_name.getText().trim().isEmpty()){
                    input_second_name.setPromptText("Введите фамилию");
                    name2=input_second_name.getText();
                    cheak=false;
                }
                else {
                    name2=input_second_name.getText();
                }

                if (input_phone.getText().trim().isEmpty()){
                    input_phone.setPromptText("Введите телефон");
                    phone=input_phone.getText();
                    cheak=false;
                }
                else {
                    phone=input_phone.getText();
                }

                if (cheak){
                    //customerRepository.getEntities().size();
                    Customer customer=new Customer(customerRepository.getEntities().size()+1,name1,name2,phone, LocalDate.of(1999,5,25));
                    customerRepository.put(customer);
                    Text text = new Text(customer.getFirstName() + " " + customer.getLastName()+"\n");
                    output_all_customer.getChildren().add(text);
                    //output_all_customer.getChildren().clear();
                    //for (Map.Entry<Long, Customer> i: customerRepository.getEntities().entrySet()) {
                    //    Text text = new Text(i.getValue().getFirstName() + " " + i.getValue().getLastName()+"\n");
                    //    output_all_customer.getChildren().add(text);
                    //    System.out.print("GF_");
                    //}
                    Stage stage = (Stage) add_customer.getScene().getWindow();
                    stage.close();
                }
            }
        });

        Button close_add_customer=new Button("Закрыть");
        close_add_customer.setLayoutX(150);
        close_add_customer.setLayoutY(145);
        close_add_customer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage stage = (Stage) close_add_customer.getScene().getWindow();
                stage.close();
            }
        });

        AnchorPane secondaryLayout = new AnchorPane();
        secondaryLayout.getChildren().add(label_first_name);
        secondaryLayout.getChildren().add(input_first_name);
        secondaryLayout.getChildren().add(label_second_name);
        secondaryLayout.getChildren().add(input_second_name);
        secondaryLayout.getChildren().add(label_phone);
        secondaryLayout.getChildren().add(input_phone);
        secondaryLayout.getChildren().add(label_date);
        secondaryLayout.getChildren().add(add_customer);
        secondaryLayout.getChildren().add(close_add_customer);

        Scene secondScene = new Scene(secondaryLayout, 400, 300);

        Stage newWindow = new Stage();
        newWindow.setTitle("Добавление нового клиента");
        newWindow.setScene(secondScene);

        newWindow.show();
    }

    public void Test(ActionEvent actionEvent) {
        switch (phase){
            case 0:
                Customer customer1=new Customer(1,"Алексей","Афанасьев","0657650047", LocalDate.of(1999,5,25));
                Customer customer2=new Customer(2,"Николай","Новиков","1237", LocalDate.of(1999,5,25));
                Customer customer3=new Customer(3,"Роман","Романов","56476647", LocalDate.of(1999,5,25));

                customerRepository.put(customer1);
                customerRepository.put(customer2);
                customerRepository.put(customer3);

                output_all_customer.getChildren().clear();
                for (Map.Entry<Long, Customer> i: customerRepository.getEntities().entrySet()) {
                    Text text = new Text(i.getValue().getFirstName() + " " + i.getValue().getLastName()+"\n");
                    output_all_customer.getChildren().add(text);
                }
                break;
            case 1:
                Product product1=new Product(1,"Маска",7);
                product1.setCount(50);
                Product product2=new Product(2,"Респиратор",70);
                product1.setCount(5);
                productRepository.put(product1);
                productRepository.put(product2);
                ObservableList<Product> usersData = FXCollections.observableArrayList();
                usersData.add(product1);
                usersData.add(product2);
                table2.setItems(usersData);
                table1.setItems(usersData);
                break;
        }

    }
}
