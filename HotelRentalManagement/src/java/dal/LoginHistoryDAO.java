/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author ddhoang
 */
public class LoginHistoryDAO extends DBcontext {

    public void insertLogin(String userId) {
        String sql = "INSERT INTO Login_History (User_Id) VALUES (?)";
        try {
            PreparedStatement st = c.prepareStatement(sql);
            st.setString(1, userId);
            st.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int countLoginToday() {
        String sql = "SELECT COUNT(*) FROM Login_History WHERE CAST(Login_Time AS DATE) = CAST(GETDATE() AS DATE)";
        try {
            PreparedStatement st = c.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

}
