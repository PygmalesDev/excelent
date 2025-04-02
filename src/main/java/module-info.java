module net.pygmales.excelent {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.apache.logging.log4j.core;
    requires org.apache.logging.log4j;
    requires java.sql;
    requires org.apache.poi.ooxml;
    requires com.fasterxml.jackson.databind;
    requires org.xerial.sqlitejdbc;
    requires com.fasterxml.jackson.datatype.jsr310;

    opens net.pygmales.excelent to javafx.fxml;
    exports net.pygmales.excelent;
    exports net.pygmales.excelent.controller;
    exports net.pygmales.excelent.record;

    opens net.pygmales.excelent.controller to javafx.fxml;
}