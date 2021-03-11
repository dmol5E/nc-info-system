open module infosystem {

    requires java.base;
    requires spring.web;
    requires spring.beans;
    requires spring.boot;
    requires spring.core;
    requires spring.boot.autoconfigure;
    requires spring.context;
    requires slf4j.api;
    requires spring.boot.configuration.processor;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;
    requires java.naming;
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;
    requires static lombok;
    requires spring.jdbc;
    requires spring.webmvc;
    requires modelmapper;
    requires modelmapper.module.java8.datatypes;
    requires modelmapper.module.jsr310;
    requires org.postgresql.jdbc;
    requires okhttp;
    requires tomcat.embed.core;
    requires spring.tx;

}

