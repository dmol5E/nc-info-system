module infosystem {
    requires org.apache.logging.log4j;
    requires com.google.gson;
    requires javafx.controls;
    requires javafx.fxml;

    opens com.nc.unc to javafx.fxml;
    exports com.nc.unc;
}
