package org.system.utils;

import org.system.DataConnection.SupabaseJDBC;
import org.system.Model.Dependent;
import org.system.Model.PolicyHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class AdvancedFunction {
    public static boolean isUniqueCustomer(Connection conn, String column, String value) throws SQLException {
        String query = "SELECT COUNT(*) FROM Dependent WHERE " + column + " = ? UNION ALL SELECT COUNT(*) FROM PolicyHolder WHERE " + column + " = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, value);
            pstmt.setString(2, value);
            try (ResultSet rs = pstmt.executeQuery()) {
                int dependentCount = 0;
                int policyHolderCount = 0;
                if (rs.next()) {
                    dependentCount = rs.getInt(1);
                }
                if (rs.next()) {
                    policyHolderCount = rs.getInt(1);
                }
                return dependentCount == 0 && policyHolderCount == 0;
            }
        }
    }
    public static  boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }
    public static int countDependents(String policyHolderId) {
        String query = "SELECT COUNT(*) FROM Dependent WHERE policyholder_id = ?";
        try (Connection conn = SupabaseJDBC.mintDatabase();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, policyHolderId);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Error counting dependents: " + e.getMessage());
        }
        return 0;
    }
    public static PolicyHolder getPolicyholderFromDatabase(String accountId) {
        try (Connection connection = SupabaseJDBC.mintDatabase()) {
            String sql = "SELECT * FROM PolicyHolder WHERE policyholder_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, accountId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        // Process the result set and create a Policyholder object
                        String policyholderId = resultSet.getString("policyholder_id");
                        String username = resultSet.getString("username");
                        String password = resultSet.getString("password");
                        String email = resultSet.getString("email");
                        String phone = resultSet.getString("phone");
                        String fullName = resultSet.getString("fullname");
                        int insuranceFee = resultSet.getInt("insuranceFee");
                        // ... fetch claimId and dependentId (you'll need to handle the array data type)
                        return new PolicyHolder(policyholderId, username, password, email, phone, "POLICYHOLDER", fullName, insuranceFee /*, claimId, dependentId */);
                    } else {
                        // Handle case where no Policyholder is found for the given accountId
                        return null;
                    }
                }
            }
        } catch (SQLException e) {
            // Handle SQL exceptions
            System.err.println("Error fetching policyholder from database: " + e.getMessage());
        }
        return null;
    }
    public static Dependent getDependentFromDatabase(String accountId) {
        try (Connection connection = SupabaseJDBC.mintDatabase()) {
            String sql = "SELECT * FROM dependent WHERE dependent_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, accountId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        // Process the result set and create a Policyholder object
                        String dependentId = resultSet.getString("dependent_id");
                        String username = resultSet.getString("username");
                        String password = resultSet.getString("password");
                        String email = resultSet.getString("email");
                        String phone = resultSet.getString("phone");
                        String fullName = resultSet.getString("fullname");
                        String accType = resultSet.getString("acctype");
                        String holderId = resultSet.getString("policyholder_id");
                        int insuranceFee = resultSet.getInt("insuranceFee");
                        return new Dependent(dependentId, username, password, email, phone, accType, holderId,fullName, insuranceFee);
                    } else {
                        // Handle case where no Policyholder is found for the given accountId
                        return null;
                    }
                }
            }
        } catch (SQLException e) {
            // Handle SQL exceptions
            System.err.println("Error fetching policyholder from database: " + e.getMessage());
        }
        return null;
    }
}
