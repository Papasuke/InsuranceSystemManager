package org.system.Controller.Login;

import org.system.Model.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.system.Model.Account;
import org.system.Model.PolicyHolder;
import org.system.DataConnection.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountDAOImpl {
    public Account login(String username, String password, String accountType) {
        String tableName = accountType;

        String sql = "SELECT * FROM " + tableName + " WHERE username = ? AND password = ?";
        try (Connection connection = SupabaseJDBC.mintDatabase();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, username);
            statement.setString(2, password); // Assuming passwords are not hashed for this example

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                // Create Account object based on the retrieved data
                return createAccountFromResultSet(resultSet, accountType);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database errors appropriately (e.g., log the error, show an error message)
        }

        return null; // Authentication failed
    }

    // Helper function to get table name based on account type
    private String getTableName(String accountType) {
        switch (accountType) {
            case "POLICYHOLDER":
                return "policyholder";
            case "DEPENDENT":
                return "dependent";
            // Add cases for other account types
            default:
                return null;
        }
    }

    // Helper function to create Account object from ResultSet
    private Account createAccountFromResultSet(ResultSet resultSet, String  accountType) throws SQLException {
        switch (accountType) {
            case "POLICYHOLDER":
                // Assuming PolicyHolder has additional fields like policyHolderId
                return new Account(resultSet.getString("policyholder_id"),resultSet.getString("username"), resultSet.getString("password"),resultSet.getString("email"),resultSet.getString("phone"), resultSet.getString("accounttype"));
//            case "DEPENDENT":
//                // Assuming Dependent has additional fields like policyHolderId
//                return new Account(
//                        resultSet.getString("username"),
//                        resultSet.getString("password"),
//                        resultSet.getString("email"),
//                        resultSet.getString("phone"),
//                        accountType,
//                        resultSet.getString("policyholder_id") // Assuming column name is policyholder_id
//                );
//            // Add cases for other account types
            default:
                return null;
        }
    }

}