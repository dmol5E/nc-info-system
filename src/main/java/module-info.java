module infosystem {
    requires org.slf4j;

    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.datatype.jsr310;

    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.base;

    opens com.nc.unc to javafx.fxml, com.fasterxml.jackson.databind;
    opens com.nc.unc.repositories.impl to com.fasterxml.jackson.databind;
    opens com.nc.unc.controller;
    opens com.nc.unc.repositories;
    opens com.nc.unc.model;
    opens com.nc.unc.enums;
    opens com.nc.unc.util.json;
    opens com.nc.unc.util.serialize;


    exports com.nc.unc.util.json;
    exports com.nc.unc.controller;
    exports com.nc.unc.repositories;
    exports com.nc.unc.model;
    exports com.nc.unc.enums;
    exports com.nc.unc;
}
