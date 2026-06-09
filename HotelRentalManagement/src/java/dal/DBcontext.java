package dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBcontext {

    public Connection c;

    public DBcontext() {
        try {
            String username = "sa";
            String password = "123";
            String url = "jdbc:sqlserver://localhost:1433;databaseName=HotelRentalManagement";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            c = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace(); // ✅ Ghi log lỗi thật
        }
    }

    public static void main(String[] args) {
        DBcontext db = new DBcontext();
        Connection conn = db.c;

        if (conn != null) {
            System.out.println("✅ Kết nối đến database thành công!");
        } else {
            System.out.println("❌ Kết nối đến database thất bại!");
        }
    }
}
