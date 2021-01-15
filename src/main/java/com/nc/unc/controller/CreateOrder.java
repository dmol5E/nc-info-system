package com.nc.unc.controller;

import com.nc.unc.enums.StatusOrder;
import com.nc.unc.exception.BadRequestException;
import com.nc.unc.model.Customer;
import com.nc.unc.model.Order;
import com.nc.unc.model.OrderItem;
import com.nc.unc.model.Product;
import com.nc.unc.service.CustomerService;
import com.nc.unc.service.OrderService;
import com.nc.unc.service.StorageService;
import com.nc.unc.service.StoreService;
import com.nc.unc.service.impl.CustomerServiceImpl;
import com.nc.unc.service.impl.OrderServiceImpl;
import com.nc.unc.service.impl.StorageServiceImpl;
import com.nc.unc.service.impl.StoreServiceImpl;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class CreateOrder extends Application {

    private final CustomerService customerService;
    private final OrderService orderService;
    private final StorageService storageService;
    private final StoreService storeService;

    /**
     * Create new Customer form
     * */
    @FXML
    private TextField c_input_name;

    @FXML
    private TextField c_input_lastname;

    @FXML
    private TextField c_input_phone;

    @FXML
    private DatePicker c_input_date;

    @FXML
    private Button c_create_customer;

    public void createNewCustomer(ActionEvent actionEvent) {

    }

    /**
     * Statistic form
     * */
    @FXML
    private TableView<Order> sk_table;

    @FXML
    private TableColumn<Order, Long> sk_key;

    @FXML
    private TableColumn<Order, String> sk_name;

    @FXML
    private TableColumn<Order, String> sk_lastname;

    @FXML
    private TableColumn<Order, String> sk_address_sender;

    @FXML
    private TableColumn<Order, String> sk_address_recipient;

    @FXML
    private TableColumn<Order, LocalDate> sk_dataSent;

    @FXML
    private TableColumn<Order, LocalDate> sk_dataWhen;

    @FXML
    private TableColumn<Order, StatusOrder> sk_status;


    public void createOrdersTable(Event event) {

        sk_key.setCellValueFactory(new PropertyValueFactory<>("key"));
        sk_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        sk_lastname.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        sk_address_recipient.setCellValueFactory(new PropertyValueFactory<>("recipientAddress"));
        sk_address_sender.setCellValueFactory(new PropertyValueFactory<>("senderAddress"));
        sk_dataWhen.setCellValueFactory(new PropertyValueFactory<>("createdWhen"));
        sk_status.setCellValueFactory(new PropertyValueFactory<>("curStatus"));
        sk_dataSent.setCellValueFactory(new PropertyValueFactory<>("sentWhen"));
        sk_table.setRowFactory(tableView -> {
            final TableRow<Order> row = new TableRow<>();
            final ContextMenu contextMenu = new ContextMenu();
            final MenuItem CANCELED = new MenuItem("CANCELED");
            final MenuItem SENT = new MenuItem("SENT");
            final MenuItem DELIVERED = new MenuItem("DELIVERED");
            CANCELED.setOnAction(actionEvent -> {
                sk_table.getItems().get(row.getIndex()).setCurStatus(StatusOrder.CANCELED);
                //order.getByKey(order.getKey()).setCurStatus(StatusOrder.CANCELED);
                sk_table.refresh();
            });
            DELIVERED.setOnAction(actionEvent -> {
                Order order = sk_table.getItems().get(row.getIndex()).setCurStatus(StatusOrder.DELIVERED);
                //orderRepository.getByKey(order.getKey()).setCurStatus(StatusOrder.DELIVERED);
                sk_table.refresh();
            });
            contextMenu.getItems().add(CANCELED);
            contextMenu.getItems().add(SENT);
            contextMenu.getItems().add(DELIVERED);

            row.contextMenuProperty().bind(
                    Bindings.when(row.emptyProperty())
                            .then((ContextMenu) null)
                            .otherwise(contextMenu)
            );

            return row;
        });

        sk_dataSent.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<>() {

            private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            @Override
            public String toString(LocalDate localDate) {
                if (localDate == null){
                    return null;
                }
                return localDate.format(formatter);
            }

            @Override
            public LocalDate fromString(String s) {
                try {
                    return LocalDate.parse(s);
                }catch (Exception exp){
                    return null;
                }
            }
        }));
        ObservableList<Order> products = FXCollections.observableArrayList(this.orderService.getAll().values());
        sk_table.setItems(products);
    }

    public void createDateSended(TableColumn.CellEditEvent<Order, LocalDate> orderLocalDateCellEditEvent) {
        orderLocalDateCellEditEvent.getRowValue().setSentWhen(orderLocalDateCellEditEvent.getNewValue()).setCurStatus(StatusOrder.SENT);
        orderService.updateOrderStatus(orderLocalDateCellEditEvent.getRowValue().getKey(), orderLocalDateCellEditEvent.getNewValue());
        this.sk_table.edit(orderLocalDateCellEditEvent.getTablePosition().getRow(), orderLocalDateCellEditEvent.getTableColumn());
        this.sk_table.refresh();
    }

    /**
     * Replenish product form
     * */

    @FXML
    private TableView<Product> io_table;

    @FXML
    private TableColumn<Product, Long> io_id;

    @FXML
    private TableColumn<Product, String> io_product_name;

    @FXML
    private TableColumn<Product, Float> io_price;

    @FXML
    private TableColumn<Product, Long> io_count;

    @FXML
    private TableColumn<Product, Integer> io_add_product;

    public void createTable(Event event) {
        io_id.setCellValueFactory(new PropertyValueFactory<>("key"));
        io_product_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        io_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        io_count.setCellValueFactory(new PropertyValueFactory<>("count"));
        io_add_product.setEditable(true);
        stringConverterFromInteger(io_add_product);
        ObservableList<Product> products = FXCollections.observableArrayList(new ArrayList<>(this.storeService.getAll().values()));
        io_table.setItems(products);
    }

    private void stringConverterFromInteger(TableColumn<Product, Integer> io_add_product) {
        io_add_product.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<>() {
            @Override
            public String toString(Integer integer) {
                if (integer == null) {
                    return null;
                }
                return Integer.toString(integer);
            }

            @Override
            public Integer fromString(String s) {
                try {
                    return Integer.parseInt(s);
                }catch (NumberFormatException ex){
                    return null;
                }
            }
        }));
    }

    public void addNewProduct(TableColumn.CellEditEvent<Product, Integer> cellEditEvent) {
        Product product = cellEditEvent.getRowValue();
        cellEditEvent.getRowValue().setCount(product.getCount() + cellEditEvent.getNewValue());
        io_table.edit(cellEditEvent.getTablePosition().getRow(), cellEditEvent.getTableColumn());
        io_table.refresh();
    }
    /**
     * CreateOrderItem form
     * */

    @FXML
    private TextField p_input_name;

    @FXML
    private TextField p_input_price;

    @FXML
    private TextField p_input_count;

    @FXML
    private Button create_product;



    public void createNewProduct(ActionEvent actionEvent) {
        try {
            this.storeService.put(p_input_name.getText(), p_input_price.getText(), p_input_count.getText());
        }catch (BadRequestException ex){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Input not valid");
            errorAlert.showAndWait();
        }
    }
    /**
     * CreateOrder form
     * */
    int phase;
    @FXML
    private ProgressBar progress;

    @FXML
    private Button back,next,search,add_customer;



    @FXML
    private TextField input_customer, input_address;

    @FXML
    private TextFlow output_customer;

    @FXML
    private Label label_customer;




    @FXML
    private TableView<Product> store_table;
    @FXML
    private TableColumn<Product, String> store_table_name;
    @FXML
    private TableColumn<Product, Float> store_table_price;
    @FXML
    private TableColumn<Product, Integer> store_table_count;
    @FXML
    private TableColumn<Product, Integer> store_table_on_storage;



    @FXML
    private TableView<Customer> or_ct_table;
    @FXML
    private TableColumn<Customer, String> or_ct_name;
    @FXML
    private TableColumn<Customer, String> or_ct_last_name;
    @FXML
    private TableColumn<Customer, String> or_ct_phone;
    @FXML
    private TableColumn<Customer, LocalDate> or_ct_data;



    @FXML
    private TableView<OrderItem> or_storage_table;
    @FXML
    private TableColumn<OrderItem, String> or_storage_name;
    @FXML
    private TableColumn<OrderItem, Float> or_storage_price;
    @FXML
    private TableColumn<OrderItem, Integer> or_storage_count;
    @FXML
    private Button createOrder;

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/com/nc/unc/form/CreateOrder.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
        phase=0;
    }


    public void orStart(Event event) {
        or_ct_name.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        or_ct_last_name.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        or_ct_phone.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        or_ct_data.setCellValueFactory(new PropertyValueFactory<>("data"));
        or_ct_table.setRowFactory(customerTableView ->  {
            final TableRow<Customer> row = new TableRow<>();
            final ContextMenu contextMenu = new ContextMenu();
            final MenuItem SELECT = new MenuItem("SELECT");
            SELECT.setOnAction(actionEvent -> {
                //toDo SERVICE CREATE NEW ORDER
            });
            contextMenu.getItems().add(SELECT);
            row.contextMenuProperty().bind(
                    Bindings.when(row.emptyProperty())
                            .then((ContextMenu) null)
                            .otherwise(contextMenu)
            );
            return row;
        });
        ObservableList<Customer> customers = FXCollections.observableArrayList(this.customerService.getAll().values());
        or_ct_table.setItems(customers);

        or_storage_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        or_storage_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        or_storage_count.setCellValueFactory(new PropertyValueFactory<>("count"));
        or_storage_table.setRowFactory(orderItemTableView -> {
            final TableRow<OrderItem> row = new TableRow<>();
            final ContextMenu contextMenu = new ContextMenu();
            final MenuItem REMOVE = new MenuItem("REMOVE");
            REMOVE.setOnAction(actionEvent -> {
                //toDo SERVICE REMOVE ROW ITEM ORDER
            });

            contextMenu.getItems().add(REMOVE);
            row.contextMenuProperty().bind(
                    Bindings.when(row.emptyProperty())
                            .then((ContextMenu) null)
                            .otherwise(contextMenu)
            );
            return row;
        });
        or_storage_table.setItems(null);

        store_table_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        store_table_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        store_table_count.setCellValueFactory(new PropertyValueFactory<>("count"));
        stringConverterFromInteger(store_table_on_storage);
        ObservableList<Product> products = FXCollections.observableArrayList(this.storeService.getAll().values());
        store_table.setItems(products);
    }

    @FXML
    private void initialize() {

    }

    public void returnBack(ActionEvent actionEvent) {
        switch (phase) {
            case 1:
                phase--;
                progress.setProgress(phase*0.33);
                add_customer.setVisible(true);
                search.setVisible(true);
                back.setVisible(false);
                next.setVisible(true);
                or_storage_table.setVisible(true);
                store_table.setVisible(false);
                input_customer.setVisible(true);
                label_customer.setVisible(true);
                break;
            case 2:
                phase--;
                createOrder.setVisible(false);
                progress.setProgress(phase*0.33);
                or_storage_table.setVisible(true);
                store_table.setVisible(true);
                next.setVisible(true);
                output_customer.setVisible(false);
                input_address.setVisible(false);
                output_customer.getChildren().clear();
                break;
        }
    }

    public void goNext(ActionEvent actionEvent) {
        switch (phase) {
            case 0:
                phase++;
                progress.setProgress(phase*0.33);
                add_customer.setVisible(false);
                search.setVisible(false);
                back.setVisible(true);
                or_ct_table.setVisible(false);
                or_storage_table.setVisible(true);
                store_table.setVisible(true);
                input_customer.setVisible(false);
                label_customer.setVisible(false);
                break;
            case 1:
                phase++;
                progress.setProgress(phase*0.33);
                store_table.setVisible(false);
                next.setVisible(false);
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
                createOrder.setVisible(true);
                or_storage_table.setVisible(true);
                input_address.setVisible(true);
                break;
        }
    }

    public void search(ActionEvent actionEvent) {

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

        Label label_phone = new Label("Телефон");
        label_phone.setLayoutX(10);
        label_phone.setLayoutY(95);
        TextField input_phone=new TextField();
        input_phone.setLayoutX(65);
        input_phone.setLayoutY(90);

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
                    //Customer customer=new Customer(customerRepository.getEntities().size()+1,name1,name2,phone, LocalDate.of(1999,5,25));
                    //customerRepository.put(customer);
                    //Text text = new Text(customer.getFirstName() + " " + customer.getLastName()+"\n");
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
                Customer customer1=new Customer(0,"Алексей","Афанасьев","0657650047", LocalDate.of(1999,5,25));
                Customer customer2=new Customer(1,"Николай","Новиков","1237", LocalDate.of(1999,5,25));
                Customer customer3=new Customer(2,"Роман","Романов","56476647", LocalDate.of(1999,5,25));
                //customerRepository.put(customer1);
                //customerRepository.put(customer2);
                //customerRepository.put(customer3);
                break;
            case 1:
                Product product1=new Product(1,0,"Маска",7);
                product1.setCount(50);
                Product product2=new Product(2,0,"Респиратор",70);
                product1.setCount(5);
                //productRepository.put(product1);
                //productRepository.put(product2);
                ObservableList<Product> usersData = FXCollections.observableArrayList();
                usersData.add(product1);
                usersData.add(product2);
                store_table.setItems(usersData);
                or_storage_table.setItems(null);
                break;
        }
    }
}
