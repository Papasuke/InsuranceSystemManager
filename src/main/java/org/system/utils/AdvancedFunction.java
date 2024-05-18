package org.system.utils;

import org.system.DataConnection.SupabaseJDBC;

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
}
