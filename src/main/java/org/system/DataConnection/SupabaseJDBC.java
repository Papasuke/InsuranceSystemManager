package org.system.DataConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SupabaseJDBC {
    private static final String urlMint = "jdbc:postgresql://aws-0-ap-southeast-1.pooler.supabase.com:5432/postgres?user=postgres.ximobxforphecpxrsmsd&password=Tranngocminh123";

    public static Connection mintDatabase(){
        try{
            Connection connect = DriverManager.getConnection(urlMint);
            return connect;
        }catch(Exception e){e.printStackTrace();}
        return null;
    }
}
