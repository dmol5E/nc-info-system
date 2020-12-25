module infosystem {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.logging.log4j;
    requires com.google.gson;


    opens com.nc.unc to javafx.fxml;
    opens com.nc.unc.form;
    opens com.nc.unc.repositories;
    opens com.nc.unc.model;
    exports com.nc.unc.form;
    exports com.nc.unc.repositories;
    exports com.nc.unc;
}
