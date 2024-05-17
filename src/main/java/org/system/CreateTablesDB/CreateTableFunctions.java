package org.system.CreateTablesDB;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTableFunctions {
    public static void createPolicyHolderTable(Connection connection) throws SQLException {
        String dropTableSql = "DROP TABLE IF EXISTS PolicyHolder";
        String dropSequenceSql = "DROP SEQUENCE IF EXISTS policyholder_id_seq";
        String createSequenceSql = "CREATE SEQUENCE policyholder_id_seq START WITH 1 INCREMENT BY 1";
        String createTableSql = "CREATE TABLE PolicyHolder (" +
                "policyholder_id TEXT PRIMARY KEY DEFAULT 'p' || lpad(nextval('policyholder_id_seq'::regclass)::text, 6, '0'), " +
                "username TEXT NOT NULL, " +
                "password TEXT NOT NULL, " +
                "email TEXT UNIQUE NOT NULL, " +
                "phone TEXT UNIQUE NOT NULL, " +
                "claimId TEXT[], " +
                "dependentId TEXT[], " +
                "accountType TEXT NOT NULL DEFAULT 'POLICYHOLDER'" + // Added accountType column
                ")";


        try (Statement statement = connection.createStatement()) {
            // Drop the table if it exists
            statement.executeUpdate(dropTableSql);
            System.out.println("Existing table dropped (if any).");

            // Drop the sequence if it exists
            statement.executeUpdate(dropSequenceSql);
            System.out.println("Existing sequence dropped (if any).");

            // Create the sequence
            statement.executeUpdate(createSequenceSql);
            System.out.println("Sequence created successfully.");

            // Create the new table
            statement.executeUpdate(createTableSql);
            System.out.println("PolicyHolder table created successfully.");
        }
    }

}
