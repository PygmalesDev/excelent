module net.pygmales.excelent {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.apache.logging.log4j.core;
    requires org.apache.logging.log4j;

    opens net.pygmales.excelent to javafx.fxml;
    exports net.pygmales.excelent;
    exports net.pygmales.excelent.controller;
    opens net.pygmales.excelent.controller to javafx.fxml;
}