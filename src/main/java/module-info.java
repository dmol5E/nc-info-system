module infosystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.logging.log4j;
    requires com.google.gson;

    opens com.nc.unc to javafx.fxml;
    exports com.nc.unc;
}
