package com.nc.unc.controller;

import com.nc.unc.App;
import com.nc.unc.enums.StatusOrder;
import com.nc.unc.exception.BadRequestException;
import com.nc.unc.model.*;
import com.nc.unc.service.*;
import com.nc.unc.util.json.JsonHelper;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;



public class CreateOrder extends Application {


    private Logger log = LoggerFactory.getLogger(CreateOrder.class.getSimpleName());

    private CustomerService customerService;
    private OrderService orderService;
    private StoreService storeService;
    private AddressService addressService;

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
        if(c_input_name.getText().equals("") ||
                c_input_lastname.getText().equals("") ||
                c_input_phone.getText().equals("") ||
                c_input_date.getValue().equals(""))
        {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Input not valid");
            errorAlert.setContentText("Invalid date new customer");
            errorAlert.showAndWait();
        }

            customerService.putCustomer(
                    Customer.builder()
                            .firstName(c_input_name.getText())
                            .lastName(c_input_lastname.getText())
                            .data(c_input_date.getValue())
                            .phoneNumber(c_input_phone.getText())
                            .build());
            log.info("added new customer {} ", JsonHelper.toJson(customerService.getAll()));

    }

    /**
     * Statistic form
     * */
    @FXML
    private TableView<Order> sk_table;
    @FXML
    private TableColumn<Order, Integer> sk_key;
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
        sk_status.setCellValueFactory(new PropertyValueFactory<>("curStatusOrder"));
        sk_dataSent.setCellValueFactory(new PropertyValueFactory<>("sentWhen"));
        sk_table.setRowFactory(tableView -> {
            final TableRow<Order> row = new TableRow<>();
            final ContextMenu contextMenu = new ContextMenu();
            final MenuItem CANCELED = new MenuItem("CANCELED");
            final MenuItem SENT = new MenuItem("SENT");
            final MenuItem DELIVERED = new MenuItem("DELIVERED");
            CANCELED.setOnAction(actionEvent -> {
                sk_table.getItems().get(row.getIndex()).setCurStatusOrder(StatusOrder.CANCELED);
                Order order = orderService.findOrderById(row.getItem().getKey());
                order.setCurStatusOrder(StatusOrder.CANCELED);
                orderService.updateOrder(order.getKey(), order);
                sk_table.refresh();
            });
            DELIVERED.setOnAction(actionEvent -> {
                sk_table.getItems().get(row.getIndex()).setCurStatusOrder(StatusOrder.DELIVERED);
                Order order = orderService.findOrderById(row.getItem().getKey());
                order.setCurStatusOrder(StatusOrder.DELIVERED);
                orderService.updateOrder(order.getKey(), order);
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
        Order order = orderService.findOrderById(orderLocalDateCellEditEvent.getRowValue().getKey());
        order.setSentWhen(orderLocalDateCellEditEvent.getNewValue());
        order.setCurStatusOrder(StatusOrder.SENT);
        orderService.updateOrder(order.getKey(), order);


        TableRow<Order> tableColumn = new TableRow<>();
        tableColumn.setUserData(order);

        orderLocalDateCellEditEvent.getRowValue().setCurStatusOrder(StatusOrder.SENT);
        sk_table.getItems()
                .get(orderLocalDateCellEditEvent
                        .getTablePosition()
                        .getRow())
                .setCurStatusOrder(StatusOrder.SENT);
        sk_table.getItems()
                .get(orderLocalDateCellEditEvent
                        .getTablePosition()
                        .getRow())
                .setSentWhen(order.getSentWhen());
        sk_table.refresh();
    }

    /**
     * Replenish product form
     * */

    @FXML
    private TableView<Product> io_table;
    @FXML
    private TableColumn<Product, Integer> io_id;
    @FXML
    private TableColumn<Product, String> io_product_name;
    @FXML
    private TableColumn<Product, Float> io_price;
    @FXML
    private TableColumn<Product, Integer> io_count;
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
        try {
            Product product = storeService.update(cellEditEvent.getRowValue().getKey(), cellEditEvent.getNewValue());
            cellEditEvent.getRowValue().setCount(product.getCount());
            io_table.edit(cellEditEvent.getTablePosition().getRow(), cellEditEvent.getTableColumn());
            io_table.refresh();
        } catch (BadRequestException e){
            log.error("Increase product exception", e);
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Input not valid");
            errorAlert.setContentText("Increase product exception");
            errorAlert.showAndWait();
        }

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
            this.storeService.put(p_input_name.getText(), p_input_count.getText(), p_input_price.getText());
        }catch (BadRequestException ex){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Input not valid");
            errorAlert.showAndWait();
        }
    }
    /**
     * CreateOrder form
     * */
    private int phase;

    @FXML
    private ProgressBar progress;
    @FXML
    private Button back,next,search;




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
    @FXML
    private ComboBox<Address> input_address;
    @FXML
    private ComboBox<Address> input_zipcode;


    public void putOrder(ActionEvent actionEvent){
        try {
            orderService.createNewOrder(Address.builder()
                    .address(input_address.getValue().getAddress())
                    .zipCode(input_zipcode.getValue().getZipCode())
                    .build());
            returnBack(new ActionEvent()); returnBack(new ActionEvent());
            or_storage_table.setItems(null);
            or_storage_table.refresh();
        } catch (BadRequestException e) {
            log.warn("Create new order exception ", e);
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Input not valid");
            errorAlert.setContentText("Create new order");
            errorAlert.showAndWait();
        }

    }


    public void putToStorage(TableColumn.CellEditEvent<Product, Integer> productIntegerCellEditEvent) {
        try {
            productIntegerCellEditEvent.getRowValue().setCount(productIntegerCellEditEvent.getRowValue().getCount());
            store_table.edit(productIntegerCellEditEvent.getTablePosition().getRow(), productIntegerCellEditEvent.getTableColumn());
            orderService.putOrderItem(productIntegerCellEditEvent.getRowValue().getKey(), productIntegerCellEditEvent.getNewValue());
            or_storage_table.setItems(FXCollections.observableList(orderService.getStorage()));
            or_storage_table.refresh();
            store_table.getItems()
                    .get(productIntegerCellEditEvent.getTablePosition().getRow())
                    .setCount(productIntegerCellEditEvent.getRowValue().getCount() - productIntegerCellEditEvent.getNewValue());
            store_table.refresh();
        } catch (BadRequestException e){
            log.error("Increase product exception", e);
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Input not valid");
            errorAlert.setContentText("Increase product exception");
            errorAlert.showAndWait();
        }
    }

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
                Customer customer = row.getItem();
                orderService.addOrderCustomer(customer);
                goNext(new ActionEvent());
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


        input_address.setConverter(new StringConverter<>() {
            @Override
            public String toString(Address address) {
                if (address != null)
                    return address.getAddress();
                return "";
            }

            @Override
            public Address fromString(String s) {
                return addressService.getByAddress(s).orElse(Address.builder().address(s).build());
            }
        });

        input_address.valueProperty().addListener((observableValue, address, t1) -> {
            if (t1 == null)
                input_zipcode.itemsProperty().setValue(FXCollections.observableList(new ArrayList<>(addressService.getAll().values())));
            else
                input_zipcode.itemsProperty().setValue(FXCollections.observableArrayList(addressService.getByAddress(t1.getAddress()).orElse(null)));
        });

        input_zipcode.setConverter(new StringConverter<Address>() {

            @Override
            public String toString(Address address) {
                return Integer.toString(address.getZipCode());
            }

            @Override
            public Address fromString(String s) {
                if(s.equals(""))
                    return null;
                return addressService.getByZipcode(
                        Integer.parseInt(s)).orElse(Address.builder().zipCode(Integer.parseInt(s)).build());
            }
        });

        input_address.itemsProperty().setValue(FXCollections.observableList(new ArrayList<>(addressService.getAll().values())));
        input_zipcode.itemsProperty().setValue(FXCollections.observableList(new ArrayList<>(addressService.getAll().values())));
        or_storage_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        or_storage_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        or_storage_count.setCellValueFactory(new PropertyValueFactory<>("count"));
        or_storage_table.setRowFactory(orderItemTableView -> {
            final TableRow<OrderItem> row = new TableRow<>();
            final ContextMenu contextMenu = new ContextMenu();
            final MenuItem REMOVE = new MenuItem("REMOVE");
            REMOVE.setOnAction(actionEvent -> {
                Product productInTable = store_table.getItems()
                        .stream()
                        .filter(product -> product.getName().equals(row.getItem().getName()))
                        .filter(product -> product.getPrice() == row.getItem().getPrice())
                        .findFirst()
                        .get();
                productInTable.setCount(productInTable.getCount() + row.getItem().getCount());
                orderService.removeOrderItem(row.getItem().getKey());
                store_table.refresh();
                or_storage_table.getItems().remove(row.getItem());
                or_storage_table.refresh();
            });

            contextMenu.getItems().add(REMOVE);
            row.contextMenuProperty().bind(
                    Bindings.when(row.emptyProperty())
                            .then((ContextMenu) null)
                            .otherwise(contextMenu)
            );
            return row;
        });
        or_storage_table.setItems(FXCollections.observableArrayList(orderService.getStorage()));

        store_table_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        store_table_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        store_table_count.setCellValueFactory(new PropertyValueFactory<>("count"));
        stringConverterFromInteger(store_table_on_storage);
        ObservableList<Product> products = FXCollections.observableArrayList(this.orderService.getStore());
        store_table.setItems(products);
    }

    @FXML
    private void initialize() {
        this.orderService = App.orderService;
        this.storeService = App.storeService;
        this.customerService = App.customerService;
        this.addressService = App.addressService;
    }

    public void returnBack(ActionEvent actionEvent) {
        switch (phase) {
            case 1:
                phase--;
                progress.setProgress(phase*0.33);
                search.setVisible(true);
                back.setVisible(false);
                next.setVisible(true);
                or_ct_table.setVisible(true);
                or_storage_table.setVisible(false);
                store_table.setVisible(false);
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
                input_zipcode.setVisible(false);
                output_customer.getChildren().clear();
                break;
        }
    }

    public void goNext(ActionEvent actionEvent) {
        switch (phase) {
            case 0:
                phase++;
                progress.setProgress(phase*0.33);
                search.setVisible(false);
                back.setVisible(true);
                or_ct_table.setVisible(false);
                or_storage_table.setVisible(true);
                store_table.setVisible(true);
                label_customer.setVisible(false);
                break;
            case 1:
                phase++;
                progress.setProgress(phase*0.33);
                store_table.setVisible(false);
                next.setVisible(false);
                output_customer.setVisible(true);
                Text text_1=new Text(String.format("Имя: %s \n", orderService.getOrderCustomer().getFirstName()));
                Text text_2=new Text(String.format("Фамилия: %s \n", orderService.getOrderCustomer().getLastName()));
                Text text_3=new Text(String.format("Номер телефона: %s \n", orderService.getOrderCustomer().getPhoneNumber()));
                Text text_4=new Text("Адрес   \n");
                Text text_5=new Text("Зип код   \n");
                Text text_6=new Text(String.format("Общая стоимость %s \n", orderService.getSum()));
                output_customer.getChildren().add(text_1);
                output_customer.getChildren().add(text_2);
                output_customer.getChildren().add(text_3);
                output_customer.getChildren().add(text_4);
                output_customer.getChildren().add(text_5);
                output_customer.getChildren().add(text_6);
                output_customer.setLineSpacing(20);
                output_customer.setTextAlignment(TextAlignment.LEFT);
                createOrder.setVisible(true);
                or_storage_table.setVisible(true);
                input_address.setVisible(true);
                input_zipcode.setVisible(true);
                break;
        }
    }

    public void search(ActionEvent actionEvent) {

    }

}
