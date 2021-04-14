package com.nc.unc.client.form;

import com.nc.unc.client.https.HttpClientAddress;
import com.nc.unc.client.https.HttpClientCustomer;
import com.nc.unc.client.https.HttpClientOrder;
import com.nc.unc.client.https.HttpClientProduct;
import com.fasterxml.jackson.core.type.TypeReference;
import com.nc.unc.dto.*;
import com.nc.unc.model.enums.StatusOrder;
import com.nc.unc.client.util.json.JsonHelper;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;
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
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class CreateOrder extends Application {



    private Logger log = LoggerFactory.getLogger(CreateOrder.class.getSimpleName());



    /**
     * Create new Customer form
     * */
    @FXML
    private TextField c_input_name;

    @FXML
    public TextField input_customer;

    @FXML
    private TextField c_input_lastname;

    @FXML
    private TextField c_input_phone;

    @FXML
    private DatePicker c_input_date;

    @FXML
    private Button c_create_customer;

    public void createNewCustomer(ActionEvent actionEvent) throws IOException {
        Response response =
                HttpClientCustomer.createCustomer(
                        JsonHelper.toJson(CustomerDto.builder()
                                .firstName(c_input_name.getText())
                                .lastName(c_input_lastname.getText())
                                .data(c_input_date.getValue())
                                .phoneNumber(c_input_phone.getText())
                                .build()));
        if (response.code() != 200) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Input not valid");
            errorAlert.setContentText("Invalid date new customer");
            errorAlert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Customer added");
            alert.setContentText("Customer successfully added");
            alert.showAndWait();
            c_input_date.setValue(null);
            c_input_lastname.setText("");
            c_input_phone.setText("");
            c_input_name.setText("");
        }
        response.body().close();
    }


    /**
     * Statistic form
     * */
    @FXML
    private TableView<OrderDto> sk_table;
    @FXML
    private TableColumn<OrderDto, Integer> sk_key;
    @FXML
    private TableColumn<OrderDto, String> sk_name;
    @FXML
    private TableColumn<OrderDto, String> sk_lastname;
    @FXML
    private TableColumn<OrderDto, String> sk_address_sender;
    @FXML
    private TableColumn<OrderDto, String> sk_address_recipient;
    @FXML
    private TableColumn<OrderDto, LocalDate> sk_dataSent;
    @FXML
    private TableColumn<OrderDto, LocalDate> sk_dataWhen;
    @FXML
    private TableColumn<OrderDto, StatusOrder> sk_status;



    public void createOrdersTable(Event event) throws IOException {

        sk_key.setCellValueFactory(new PropertyValueFactory<>("id"));
        sk_name.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        sk_lastname.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        sk_address_recipient.setCellValueFactory(new PropertyValueFactory<>("recipientAddress"));
        sk_address_sender.setCellValueFactory(new PropertyValueFactory<>("senderAddress"));
        sk_dataWhen.setCellValueFactory(new PropertyValueFactory<>("createdWhen"));
        sk_status.setCellValueFactory(new PropertyValueFactory<>("curStatusOrder"));
        sk_dataSent.setCellValueFactory(new PropertyValueFactory<>("sentWhen"));
        sk_table.setRowFactory(tableView -> {
            final TableRow<OrderDto> row = new TableRow<>();
            final ContextMenu contextMenu = new ContextMenu();
            final MenuItem CANCELED = new MenuItem("CANCELED");
            final MenuItem SENT = new MenuItem("SENT");
            final MenuItem DELIVERED = new MenuItem("DELIVERED");
            CANCELED.setOnAction(actionEvent -> {
                sk_table.getItems().get(row.getIndex()).setCurStatusOrder(StatusOrder.CANCELED);
                try {
                    Response response = HttpClientOrder.find(row.getItem().getId());
                    if(response.code() == 200){
                        OrderDto order = JsonHelper.fromJson(response.body().string(), OrderDto.class);
                        order.setCurStatusOrder(StatusOrder.CANCELED);
                        HttpClientOrder.updateOrder(JsonHelper.toJson(order));
                        sk_table.refresh();
                    }
                    response.body().close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            });
            DELIVERED.setOnAction(actionEvent -> {
                sk_table.getItems().get(row.getIndex()).setCurStatusOrder(StatusOrder.DELIVERED);
                try {
                    Response response = HttpClientOrder.find(row.getItem().getId());
                    if(response.code() == 200){
                        OrderDto order = JsonHelper.fromJson(response.body().string(), OrderDto.class);
                        order.setCurStatusOrder(StatusOrder.DELIVERED);
                        HttpClientOrder.updateOrder(JsonHelper.toJson(order));
                        sk_table.refresh();
                    }
                    response.body().close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
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
        List<OrderDto> orders = JsonHelper.fromJson(HttpClientOrder.getAll().body().string(), new TypeReference<>() {});

        ObservableList<OrderDto> products = FXCollections.observableArrayList(orders);
        sk_table.setItems(products);
    }

    public void createDateSended(TableColumn.CellEditEvent<OrderDto, LocalDate> orderLocalDateCellEditEvent) throws IOException {
        Response response = HttpClientOrder.find(orderLocalDateCellEditEvent.getRowValue().getId());
        OrderDto order = JsonHelper.fromJson(response.body().string(), OrderDto.class);
        order.setSentWhen(orderLocalDateCellEditEvent.getNewValue());
        order.setCurStatusOrder(StatusOrder.SENT);
        HttpClientOrder.updateOrder(JsonHelper.toJson(order));


        TableRow<OrderDto> tableColumn = new TableRow<>();
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
    private TableView<ProductDto> io_table;
    @FXML
    private TableColumn<ProductDto, Integer> io_id;
    @FXML
    private TableColumn<ProductDto, String> io_product_name;
    @FXML
    private TableColumn<ProductDto, Float> io_price;
    @FXML
    private TableColumn<ProductDto, Integer> io_count;
    @FXML
    private TableColumn<ProductDto, Integer> io_add_product;


    public void createTable(Event event) throws IOException {
        io_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        io_product_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        io_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        io_count.setCellValueFactory(new PropertyValueFactory<>("count"));
        io_add_product.setEditable(true);
        stringConverterFromInteger(io_add_product);
        List<ProductDto> productDtos = JsonHelper.fromJson(HttpClientProduct.getProducts().body().string(), new TypeReference<>() {});
        ObservableList<ProductDto> products = FXCollections.observableArrayList(productDtos);
        io_table.setItems(products);
    }

    private void stringConverterFromInteger(TableColumn<ProductDto, Integer> io_add_product) {
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

    public void addNewProduct(TableColumn.CellEditEvent<ProductDto, Integer> cellEditEvent) throws IOException {
        Response response = HttpClientProduct.increase(cellEditEvent.getRowValue().getId(), cellEditEvent.getNewValue());
        if(response.code() != 200){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Input not valid");
            errorAlert.setContentText("Increase product exception");
            errorAlert.showAndWait();
        }else {
            ProductDto productDto = JsonHelper.fromJson(response.body().string(), ProductDto.class);
            cellEditEvent.getRowValue().setCount(productDto.getCount());
            io_table.edit(cellEditEvent.getTablePosition().getRow(), cellEditEvent.getTableColumn());
            io_table.refresh();
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


    public void createNewProduct(ActionEvent actionEvent) throws IOException {

        Response response =
                HttpClientProduct.create(JsonHelper.toJson(ProductDto.builder()
                        .name(p_input_name.getText())
                        .count(Integer.parseInt(p_input_count.getText()))
                        .price(Float.parseFloat(p_input_price.getText()))
                        .build()));

        if(response.code() != 200){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Input not valid");
            errorAlert.setContentText("Create new product exception");
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
    private TableView<ProductDto> store_table;
    @FXML
    private TableColumn<ProductDto, String> store_table_name;
    @FXML
    private TableColumn<ProductDto, Float> store_table_price;
    @FXML
    private TableColumn<ProductDto, Integer> store_table_count;
    @FXML
    private TableColumn<ProductDto, Integer> store_table_on_storage;


    @FXML
    private TableView<CustomerDto> or_ct_table;
    @FXML
    private TableColumn<CustomerDto, String> or_ct_name;
    @FXML
    private TableColumn<CustomerDto, String> or_ct_last_name;
    @FXML
    private TableColumn<CustomerDto, String> or_ct_phone;
    @FXML
    private TableColumn<CustomerDto, LocalDate> or_ct_data;

    @FXML
    private TableView<OrderItemDto> or_storage_table;
    @FXML
    private TableColumn<OrderItemDto, String> or_storage_name;
    @FXML
    private TableColumn<OrderItemDto, Float> or_storage_price;
    @FXML
    private TableColumn<OrderItemDto, Integer> or_storage_count;
    @FXML
    private Button createOrder;
    @FXML
    private ComboBox<AddressDto> input_address;
    @FXML
    private ComboBox<AddressDto> input_zipcode;


    public void putOrder(ActionEvent actionEvent) throws IOException {
        HttpClientOrder.createNewOrder(
                JsonHelper.toJson(AddressDto.builder()
                        .address(input_address.getValue().getAddress())
                        .zipcode(input_zipcode.getValue().getZipcode())
                        .build()));
        returnBack(new ActionEvent()); returnBack(new ActionEvent());
        or_storage_table.setItems(null);
        or_storage_table.refresh();
    }


    public void putToStorage(TableColumn.CellEditEvent<ProductDto, Integer> productIntegerCellEditEvent) {
        try {
            productIntegerCellEditEvent.getRowValue().setCount(productIntegerCellEditEvent.getRowValue().getCount());
            store_table.edit(productIntegerCellEditEvent.getTablePosition().getRow(), productIntegerCellEditEvent.getTableColumn());
            HttpClientOrder.putOrderItem(productIntegerCellEditEvent.getRowValue().getId(), productIntegerCellEditEvent.getNewValue());
            Response response = HttpClientOrder.getStorage();
            List<OrderItemDto> orderItemDtos = JsonHelper.fromJson(HttpClientOrder.getStorage().body().string(), new TypeReference<>() {});
            or_storage_table.setItems(FXCollections.observableList(orderItemDtos));
            or_storage_table.refresh();
            store_table.getItems()
                    .get(productIntegerCellEditEvent.getTablePosition().getRow())
                    .setCount(productIntegerCellEditEvent.getRowValue().getCount() - productIntegerCellEditEvent.getNewValue());
            store_table.refresh();
        } catch (Exception e){
            log.error("Increase product exception", e);
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Input not valid");
            errorAlert.setContentText("Increase product exception");
            errorAlert.showAndWait();
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/CreateOrder.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
        phase=0;
    }


    public void orStart(Event event) throws IOException {
        or_ct_name.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        or_ct_last_name.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        or_ct_phone.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        or_ct_data.setCellValueFactory(new PropertyValueFactory<>("data"));
        or_ct_table.setRowFactory(customerTableView ->  {
            final TableRow<CustomerDto> row = new TableRow<>();
            final ContextMenu contextMenu = new ContextMenu();
            final MenuItem SELECT = new MenuItem("SELECT");
            SELECT.setOnAction(actionEvent -> {
                CustomerDto customer = row.getItem();
                try {
                    HttpClientOrder.createCustomer(JsonHelper.toJson(customer));
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                try {
                    goNext(new ActionEvent());
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            });
            contextMenu.getItems().add(SELECT);
            row.contextMenuProperty().bind(
                    Bindings.when(row.emptyProperty())
                            .then((ContextMenu) null)
                            .otherwise(contextMenu)
            );
            return row;
        });
        List<CustomerDto> customerDtoList = JsonHelper.fromJson(HttpClientCustomer.getCustomers().body().string(),
                new TypeReference<>() {});
        ObservableList<CustomerDto> customers = FXCollections.observableArrayList(customerDtoList);
        or_ct_table.setItems(customers);


        input_address.setConverter(new StringConverter<>() {
            @Override
                public String toString(AddressDto address) {
                if (address != null)
                    return address.getAddress();
                return "";
            }

            @SneakyThrows
            @Override
            public AddressDto fromString(String s) {
                if(s.equals(""))
                    return null;
                Response response = HttpClientAddress.search(s);
                return response.code() == 200 ?
                        JsonHelper.fromJson(response.body().string(), AddressDto.class) :
                        AddressDto.builder().address(s).build();
            }
        });

        input_address.valueProperty().addListener((observableValue, address, t1) -> {
            if (t1 == null) {
                try {
                    List<AddressDto> addressDtos = JsonHelper.fromJson(HttpClientAddress.getAll().body().string(), new TypeReference<>() {});
                    input_zipcode.itemsProperty().setValue(FXCollections.observableList(addressDtos));
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
            else {
                try {
                    Response response = HttpClientAddress.search(t1.getAddress());
                    AddressDto addressDto = response.code() == 200 ?
                            JsonHelper.fromJson(response.body().string(), AddressDto.class) :
                            null;
                    input_zipcode.itemsProperty().setValue(FXCollections.observableArrayList(addressDto));

                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        input_zipcode.setConverter(new StringConverter<AddressDto>() {

            @Override
            public String toString(AddressDto address) {
                if(address != null)
                    return address.getZipcode();
                return null;
            }

            @SneakyThrows
            @Override
            public AddressDto fromString(String s) {
                if(s.equals(""))
                    return null;
                Response response = HttpClientAddress.search(Integer.parseInt(s));
                return response.code() == 200 ?
                        JsonHelper.fromJson(response.body().string(), AddressDto.class) :
                        AddressDto.builder().zipcode(s).build();
            }
        });
        List<AddressDto> addressDtos = JsonHelper.fromJson(HttpClientAddress.getAll().body().string(), new TypeReference<>() {});

        input_address.itemsProperty().setValue(FXCollections.observableList(addressDtos));
        input_zipcode.itemsProperty().setValue(FXCollections.observableList(addressDtos));
        or_storage_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        or_storage_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        or_storage_count.setCellValueFactory(new PropertyValueFactory<>("count"));
        or_storage_table.setRowFactory(orderItemTableView -> {
            final TableRow<OrderItemDto> row = new TableRow<>();
            final ContextMenu contextMenu = new ContextMenu();
            final MenuItem REMOVE = new MenuItem("REMOVE");
            REMOVE.setOnAction(actionEvent -> {
                ProductDto productInTable = store_table.getItems()
                        .stream()
                        .filter(product -> product.getName().equals(row.getItem().getName()))
                        .filter(product -> product.getPrice() == row.getItem().getPrice())
                        .findFirst()
                        .get();
                productInTable.setCount(productInTable.getCount() + row.getItem().getCount());
                try {
                    HttpClientOrder.remove(row.getItem().getId());
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
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
        List<OrderItemDto> orderItemDtos = JsonHelper.fromJson(HttpClientOrder.getStorage().body().string(), new TypeReference<>() {});

        or_storage_table.setItems(FXCollections.observableArrayList(orderItemDtos));

        store_table_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        store_table_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        store_table_count.setCellValueFactory(new PropertyValueFactory<>("count"));
        stringConverterFromInteger(store_table_on_storage);
        List<ProductDto> productDtos = JsonHelper.fromJson(HttpClientProduct.getProducts().body().string(), new TypeReference<>(){});
        ObservableList<ProductDto> products = FXCollections.observableArrayList(productDtos);
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
                search.setVisible(true);
                back.setVisible(false);
                next.setVisible(true);
                or_ct_table.setVisible(true);
                or_storage_table.setVisible(false);
                store_table.setVisible(false);
                label_customer.setVisible(true);
                input_customer.setVisible(true);
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

    public void goNext(ActionEvent actionEvent) throws IOException {
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
                input_customer.setVisible(false);
                break;
            case 1:
                phase++;
                progress.setProgress(phase*0.33);
                store_table.setVisible(false);
                next.setVisible(false);
                output_customer.setVisible(true);

                Response response = HttpClientOrder.getCustomer();
                Text text_1;
                Text text_2;
                Text text_3;

                if (response.code() == 200) {
                    CustomerDto customerDto = JsonHelper.fromJson(response.body().string(), CustomerDto.class);
                    text_1 = new Text(String.format("Имя: %s \n", customerDto.getFirstName()));
                    text_2 = new Text(String.format("Фамилия: %s \n", customerDto.getLastName()));
                    text_3 = new Text(String.format("Номер телефона: %s \n", customerDto.getPhoneNumber()));
                } else {
                    text_1 = new Text("Имя: %s \n");
                    text_2 = new Text("Фамилия: %s \n");
                    text_3 = new Text("Номер телефона: %s \n");
                }
                Text text_4 = new Text("Адрес   \n");
                Text text_5 = new Text("Зип код   \n");
                Text text_6 = new Text(String.format("Общая стоимость %s \n",HttpClientOrder.getSum().body().string()));
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

    public void search(ActionEvent actionEvent)  {

            try {
                List<CustomerDto> customerDtoList = null;
                if(input_customer.getText().equals("")){
                    customerDtoList = JsonHelper.fromJson(HttpClientCustomer.getCustomers().body().string(),
                            new TypeReference<>() {});
                } else {
                    customerDtoList = JsonHelper.fromJson(HttpClientCustomer.searchCustomers(input_customer.getText()).body().string(),
                            new TypeReference<>() {});

                }
                ObservableList<CustomerDto> customers = FXCollections.observableArrayList(customerDtoList);
                or_ct_table.setItems(customers);
            } catch (IOException e){
                log.error("Server don't response", e);
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("Server don't response");
                errorAlert.showAndWait();
            }

    }

}
