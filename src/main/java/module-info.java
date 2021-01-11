module infosystem {
    requires org.apache.logging.log4j;
    requires com.google.gson;
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;

    opens com.nc.unc to javafx.fxml;
    opens com.nc.unc.controller;
    opens com.nc.unc.repositories;
    opens com.nc.unc.model;
    exports com.nc.unc.controller;
    exports com.nc.unc.repositories;
    exports com.nc.unc.model;
    exports com.nc.unc;
}
