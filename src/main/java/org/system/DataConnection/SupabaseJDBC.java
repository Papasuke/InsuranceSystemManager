package org.system.DataConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SupabaseJDBC {
    private static final String url = "jdbc:postgresql://aws-0-us-west-1.pooler.supabase.com:5432/postgres?user=postgres.nnbvogonnevqblzrnddt&password=Nmkk0934149833";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(url);
    }

    public static boolean testConnection() {
        try (Connection connection = connect()) {
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
