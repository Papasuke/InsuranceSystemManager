package org.system;

import org.system.DataConnection.SupabaseJDBC;

public class Main {
    public static void main(String[] args) {
        if (SupabaseJDBC.testConnection()) {
            System.out.println("Connected to database");
        } else {
            System.out.println("Fail to connect to database");
        }
    }
}