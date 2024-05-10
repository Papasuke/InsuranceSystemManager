module InsuranceSystemManager {
    requires org.postgresql.jdbc;
    requires java.sql;
    requires javafx.fxml;
    requires de.jensd.fx.glyphs.fontawesome;
    requires javafx.controls;

    opens org.system to javafx.fxml;
    opens org.system.Controller to javafx.fxml;
    exports org.system;
    exports org.system.Controller;
    exports org.system.Application;
}