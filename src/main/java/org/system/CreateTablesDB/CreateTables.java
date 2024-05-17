package org.system.CreateTablesDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CreateTables {
    public static void main(String[] args) {

        String url = "jdbc:postgresql://localhost:5432/your_database_name";
        String user = "your_username";
        String password = "your_password";
        String testUrl = "jdbc:postgresql://aws-0-ap-southeast-1.pooler.supabase.com:5432/postgres?user=postgres.ximobxforphecpxrsmsd&password=Tranngocminh123";

        try (Connection connection = DriverManager.getConnection(testUrl)) {
            // Connection successful
            System.out.println("Connected to the PostgreSQL server successfully.");

            // Create the PolicyHolder table
            CreateTableFunctions.createPolicyHolderTable(connection);
            CreateTableFunctions.createDependentTable(connection);

        } catch (SQLException e) {
            System.out.println("Connection failure.");
            e.printStackTrace();
        }
    }
}
