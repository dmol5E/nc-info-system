module infosystem {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.nc.unc to javafx.fxml;
    exports com.nc.unc;
}
