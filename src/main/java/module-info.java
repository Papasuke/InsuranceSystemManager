module InsuranceSystemManager {
    requires org.postgresql.jdbc;
    requires java.sql;
    requires javafx.fxml;
    requires de.jensd.fx.glyphs.fontawesome;
    requires javafx.controls;
    requires jsoup;

    opens org.system to javafx.fxml;
    opens org.system.Controller to javafx.fxml;
    opens org.system.Model;
    exports org.system;
    exports org.system.Controller;
    exports org.system.Application;
    exports org.system.Controller.Customer;
    opens org.system.Controller.Customer to javafx.fxml;
    exports org.system.Controller.Admin;
    opens org.system.Controller.Admin to javafx.fxml;
    exports org.system.CreateTablesDB;
    opens org.system.CreateTablesDB to javafx.fxml;
    exports org.system.Controller.Login;
    opens org.system.Controller.Login to javafx.fxml;
}