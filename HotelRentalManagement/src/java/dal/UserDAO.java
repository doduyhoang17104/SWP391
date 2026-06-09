package dal;

import dal.DBcontext;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import model.OfflineBookingUser;
import model.PasswordUtil;
import model.User;

/**
 *
 * @author ddhoang
 */
public class UserDAO extends DBcontext {

    public User getUserByUsernamePassword(String userName, String password) {
        String sql = "SELECT * FROM [dbo].[User] WHERE Username = ? AND Hashed_Password = ?";
        try {
            // Set the parameters for the prepared statement
            PreparedStatement st = c.prepareStatement(sql);
            st.setString(1, userName);
            byte[] salt = Objects.requireNonNull(getUserSalt(userName));
            byte[] hashedPassword = PasswordUtil.hashPassword(password.toCharArray(), salt);
            st.setBytes(2, hashedPassword);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            rs.getString("User_Id"), // id
                            rs.getString("Full_Name"), // name
                            rs.getString("Username"), // username
                            rs.getString("Email"), // email ✅
                            rs.getString("Phone_Number"), // phone ✅
                            rs.getString("Address"), // address ✅
                            rs.getString("Image"),
                            rs.getInt("Status"), // status ✅
                            rs.getInt("Role_Id"), // roleid ✅
                            rs.getInt("Points")
                    );

                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;  // Return null if no record is found or an exception occurs
    }

    public void incrementUserPoints(int userId) {
        String sql = "UPDATE [User] SET Points = ISNULL(Points, 0) + 1 WHERE User_Id = ?";

        try {
            PreparedStatement st = c.prepareStatement(sql);
            st.setInt(1, userId);
            st.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private byte[] getUserSalt(String username) {
        byte[] salt = null;
        String sql = "select Salt from [User] where Username = ?";
        try {
            PreparedStatement st = c.prepareStatement(sql);

            st.setString(1, username);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                salt = rs.getBytes("Salt");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return salt;
    }

    public User getUserByEmail(String email) {
        User user = null;
        try {

            String sql = "SELECT * FROM [User] WHERE Email = ?";
            PreparedStatement st = c.prepareStatement(sql);
            st.setString(1, email);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getString("User_Id"), // id
                        rs.getString("Full_Name"), // name
                        rs.getString("Username"), // username
                        rs.getString("Email"), // email ✅
                        rs.getString("Phone_Number"), // phone ✅
                        rs.getString("Address"), // address ✅
                        rs.getString("Image"),
                        rs.getInt("Status"), // status ✅
                        rs.getInt("Role_Id"), // roleid ✅
                        rs.getInt("Points")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public User getUserByUsername(String username) {
        User user = null;
        try {

            String sql = "SELECT * FROM [User] WHERE Username = ?";
            PreparedStatement st = c.prepareStatement(sql);
            st.setString(1, username);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getString("User_Id"), // id
                        rs.getString("Full_Name"), // name
                        rs.getString("Username"), // username
                        rs.getString("Email"), // email ✅
                        rs.getString("Phone_Number"), // phone ✅
                        rs.getString("Address"), // address ✅
                        rs.getString("Image"),
                        rs.getInt("Status"), // status ✅
                        rs.getInt("Role_Id"), // roleid ✅
                        rs.getInt("Points")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public User getUserById(String id) {
        User user = null;
        try {

            String sql = "SELECT * FROM [User] WHERE User_Id = ?";
            PreparedStatement st = c.prepareStatement(sql);
            st.setString(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                user = new User();
                return new User(
                        rs.getString("User_Id"), // id
                        rs.getString("Full_Name"), // name
                        rs.getString("Username"), // username
                        rs.getString("Email"), // email ✅
                        rs.getString("Phone_Number"), // phone ✅
                        rs.getString("Address"), // address ✅
                        rs.getString("Image"),
                        rs.getInt("Status"), // status ✅
                        rs.getInt("Role_Id"), // roleid ✅
                        rs.getInt("Points")
                );
                // set thêm nếu có
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public List<User> getAllUser() {
        List<User> users = new ArrayList<>();
        try {
            String sql = "SELECT * FROM [User]";
            PreparedStatement st = c.prepareStatement(sql);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                User user = new User(
                        rs.getString("User_Id"), // id
                        rs.getString("Full_Name"), // name
                        rs.getString("Username"), // username
                        rs.getString("Email"), // email ✅
                        rs.getString("Phone_Number"), // phone ✅
                        rs.getString("Address"), // address ✅
                        rs.getString("Image"),
                        rs.getInt("Status"), // status ✅
                        rs.getInt("Role_Id"), // roleid ✅
                        rs.getInt("Points")
                );
                users.add(user);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return users;
    }

    public void insertGoogleAccount(String email, String fullName, byte[] hashedPassword, byte[] salt) {
        String sql = "INSERT INTO [User] (Email, Full_Name, Salt, Hashed_Password, Role_Id, Username, Phone_Number, Address,Status) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?,?)";
        try (PreparedStatement st = c.prepareStatement(sql)) {
            st.setString(1, email);
            st.setString(2, fullName);
            st.setBytes(3, salt);
            st.setBytes(4, hashedPassword);
            st.setInt(5, 1); // Mặc định: roleId = 1 (customer)
            st.setString(6, email); // Username dùng email
            st.setString(7, ""); // Phone_Number trống
            st.setString(8, ""); // Address trống
            st.setString(9, "1"); // Address trống
            st.executeUpdate();
        } catch (Exception e) {
            System.out.println("Lỗi khi insert tài khoản Google:");
            e.printStackTrace();
        }
    }

    public String generateRandomPassword(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }

    public void updateUserProfile(String id, String name, String phone, String address, String image) {
        String sql;
        if (image == null || image.isEmpty()) {
            sql = "UPDATE [User] SET Full_Name = ?, Phone_Number = ?, Address = ? WHERE User_Id = ?";
        } else {
            sql = "UPDATE [User] SET Full_Name = ?, Phone_Number = ?, Address = ?, Image = ? WHERE User_Id = ?";
        }

        try {
            PreparedStatement st = c.prepareStatement(sql);
            st.setString(1, name);
            st.setString(2, phone);
            st.setString(3, address);

            if (image == null || image.isEmpty()) {
                st.setString(4, id);
            } else {
                st.setString(4, image);
                st.setString(5, id);
            }

            st.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean editUser(String id, String fullName, String username, String email, String phone, String address, int status, int roleId) {
        String sql = "UPDATE [User] SET "
                + "Full_Name = ?, "
                + "Username = ?, "
                + "Email = ?, "
                + "Phone_Number = ?, "
                + "Address = ?, "
                + "Status = ?, "
                + "Role_Id = ? "
                + "WHERE User_Id = ?";

        try (PreparedStatement st = c.prepareStatement(sql)) {
            st.setString(1, fullName);
            st.setString(2, username);
            st.setString(3, email);
            st.setString(4, phone);
            st.setString(5, address);
            st.setInt(6, status);
            st.setInt(7, roleId);
            st.setString(8, id);

            int rows = st.executeUpdate();
            return rows > 0; // ✅ Nếu cập nhật thành công
        } catch (Exception e) {
            e.printStackTrace();
            return false; // ✅ Nếu có lỗi
        }
    }

//
//    public User checkUserExits(String gmail) {
//        String sql = "select * from {User] where Email = ? ";
//        try {
//            PreparedStatement st = c.prepareStatement(sql);
//            st.setString(1, gmail);
//            ResultSet rs = st.executeQuery();
//            if (rs.next()) {
//
//                return new User(rs.getString("id"),
//                        rs.getString("name"),
//                        rs.getString("username"),
//                        rs.getString("password"),
//                        rs.getString("gmail"),
//                        rs.getString("phone"),
//                        rs.getString("role")
//                );
//            }
//        } catch (Exception e) {
//
//        }
//        return null;
//    }
    public void addUser(String email, String username, String fullName, byte[] hashedPassword, byte[] salt, String phone, String address) {
        String sql = "INSERT INTO [dbo].[User] "
                + "([Full_Name], [Username], [Hashed_Password], [Salt], [Email], [Phone_Number], [Address], [Status], [Role_Id]) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement st = c.prepareStatement(sql);
            st.setString(1, fullName);
            st.setString(2, username);
            st.setBytes(3, hashedPassword);
            st.setBytes(4, salt);
            st.setString(5, email);
            st.setString(6, phone);
            st.setString(7, address);
            st.setInt(8, 1); // Status = true (active)
            st.setInt(9, 1); // giả sử 2 là Role_Id cho "customer"

            st.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace(); // nên log lỗi thay vì để trống
        }
    }

    public void addUserForAdmin(String email, String username, String fullName, byte[] hashedPassword, byte[] salt, String phone, String address, int role) {
        String sql = "INSERT INTO [dbo].[User] "
                + "([Full_Name], [Username], [Hashed_Password], [Salt], [Email], [Phone_Number], [Address], [Status], [Role_Id]) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement st = c.prepareStatement(sql);
            st.setString(1, fullName);
            st.setString(2, username);
            st.setBytes(3, hashedPassword);
            st.setBytes(4, salt);
            st.setString(5, email);
            st.setString(6, phone);
            st.setString(7, address);
            st.setInt(8, 1); // Status = true (active)
            st.setInt(9, role); // giả sử 2 là Role_Id cho "customer"

            st.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace(); // nên log lỗi thay vì để trống
        }
    }

    public void deleteUser(String id) {
        try {
            String deleteBookingSql = "DELETE FROM Booking WHERE Customer_Id = ?";
            PreparedStatement bookingStmt = c.prepareStatement(deleteBookingSql);
            bookingStmt.setString(1, id);
            bookingStmt.executeUpdate();

            String deleteUserSql = "DELETE FROM [User] WHERE User_Id = ?";
            PreparedStatement userStmt = c.prepareStatement(deleteUserSql);
            userStmt.setString(1, id);
            int rowsAffected = userStmt.executeUpdate();

            System.out.println("Deleted user and related bookings. Rows affected: " + rowsAffected);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean updateNewPassword(String newPassword, String email) {
        String sql = "UPDATE [User]\n"
                + "SET [Salt] = ? \n"
                + "  ,[Hashed_Password] = ? \n"
                + "WHERE Email = ?";
        try {
            PreparedStatement st = c.prepareStatement(sql);
            byte[] salt = PasswordUtil.generateSalt();
            byte[] hashedNewPassword = PasswordUtil.hashPassword(newPassword.toCharArray(), salt);
            st.setBytes(1, salt);
            st.setBytes(2, hashedNewPassword);
            st.setString(3, email);
            int rowCount = st.executeUpdate();
            return rowCount > 0;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    public boolean checkPassword(String password, String username) {
        try {
            byte[] salt = getUserSalt(username);
            byte[] expectedHashPassword = PasswordUtil.hashPassword(password.toCharArray(), salt);
            String sql = "select [Hashed_Password] from [User] where Username = ?";
            PreparedStatement st = c.prepareStatement(sql);
            st.setString(1, username);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                byte[] hashPassword = rs.getBytes("Hashed_Password");
                String expectedHex = bytesToHex(expectedHashPassword);
                String actualHex = bytesToHex(hashPassword);
                // Compare the hexadecimal representations
                return expectedHex.equals(actualHex);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public boolean checkUserEmail(String email) {
        String sql = "SELECT COUNT(*) FROM [User] WHERE Email = ?";

        try {
            PreparedStatement st = c.prepareStatement(sql);
            st.setString(1, email);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0; // Nếu có ít nhất 1 kết quả -> Email tồn tại
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }

//    public static void main(String[] args) {
//        try {
//            UserDAO userDAO = new UserDAO(); // Đảm bảo UserDAO đã khởi tạo Connection
//
//            String email = "johndoe@example.com";
//            String username = "johndoe";
//            String fullName = "John Doe";
//            String password = "password123";
//            String phone = "0123456789";
//            String address = "123 Main Street";
//
//            // Tạo salt ngẫu nhiên
//            byte[] salt = generateSalt();
//
//            // Hash password với salt
//            byte[] hashedPassword = hashPassword(password, salt);
//
//            // Gọi phương thức addUser
//            userDAO.addUser(email, username, fullName, hashedPassword, salt, phone, address);
//
//            System.out.println("User added successfully!");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    // Hàm tạo salt ngẫu nhiên
    public static byte[] generateSalt() {
        SecureRandom sr = new SecureRandom();
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }

    // Hàm hash mật khẩu với salt
    public static byte[] hashPassword(String password, byte[] salt) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(salt);
        return md.digest(password.getBytes("UTF-8"));
    }

    public String generateCustomerEmail() {
        String prefix = "customer";
        String domain = "@gmail.com";
        int counter = 1;

        String email = prefix + counter + domain;

        while (checkUserEmail(email)) {
            counter++;
            email = prefix + counter + domain;
        }

        return email;
    }

    public int registerSafeUser(String email, String username, String fullName, String rawPassword, String phone, String address) {
        int userId = -1;
        try {
            if (email == null || email.trim().isEmpty()) {
                email = generateCustomerEmail();
            }

            if (rawPassword == null || rawPassword.trim().isEmpty()) {
                rawPassword = "Hotel123";
            }

            byte[] salt = PasswordUtil.generateSalt();
            byte[] hashedPassword = PasswordUtil.hashPassword(rawPassword.toCharArray(), salt);

            String sql = """
            INSERT INTO [User] 
            ([Full_Name], [Username], [Hashed_Password], [Salt], [Email], [Phone_Number], [Address], [Status], [Role_Id])
            OUTPUT INSERTED.User_Id
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

            try (PreparedStatement st = c.prepareStatement(sql)) {
                st.setString(1, fullName);
                st.setString(2, username);
                st.setBytes(3, hashedPassword);
                st.setBytes(4, salt);
                st.setString(5, email);
                st.setString(6, phone);
                st.setString(7, address);
                st.setInt(8, 1);
                st.setInt(9, 1);

                ResultSet rs = st.executeQuery();
                if (rs.next()) {
                    userId = rs.getInt("User_Id");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userId;
    }

    public void addOfflineBookingUser(String fullName, String phoneNumber, String address, String identityCard,
            int bookingId, int bookedByReceptionistId) {

        String sql = "INSERT INTO OfflineBookingUser "
                + "(Full_Name, Phone_Number, Address, Identity_Card, Booking_Id, Booked_By_Receptionist_Id) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, fullName);
            ps.setString(2, phoneNumber);
            ps.setString(3, address);
            ps.setString(4, identityCard);
            ps.setInt(5, bookingId);
            ps.setInt(6, bookedByReceptionistId);

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void increasePointsByOne(int userId) {
        String sql = "UPDATE [dbo].[User] SET Points = ISNULL(Points, 0) + 1 WHERE User_Id = ?";
        try {
            PreparedStatement st = c.prepareStatement(sql);
            st.setInt(1, userId);
            st.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace(); // nên thay bằng logger trong thực tế
        }
    }

    public boolean subtractUserPoints(int userId, int pointsToSubtract) {
        String sql = "UPDATE [User] SET Points = Points - ? WHERE User_Id = ? AND Points >= ?";

        try (PreparedStatement st = c.prepareStatement(sql)) {
            st.setInt(1, pointsToSubtract);
            st.setInt(2, userId);
            st.setInt(3, pointsToSubtract); // đảm bảo không âm

            int rowsAffected = st.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public static void main(String[] args) {
        UserDAO dao = new UserDAO();  // Khởi tạo DAO
        dao.deleteUser("3");
    }

    public List<User> getAllUserSorted(String sortBy, String sortDir) {
        List<User> users = new ArrayList<>();
        String column;
        switch (sortBy) {
            case "id":
                column = "User_Id";
                break;
            case "name":
                column = "Full_Name";
                break;
            case "username":
                column = "Username";
                break;
            case "phone":
                column = "Phone_Number";
                break;
            case "email":
                column = "Email";
                break;
            case "address":
                column = "Address";
                break;
            case "roleid":
                column = "Role_Id";
                break;
            default:
                column = "User_Id";
        }
        String direction = "asc".equalsIgnoreCase(sortDir) ? "ASC" : "DESC";
        String sql = "SELECT * FROM [User] ORDER BY " + column + " " + direction;
        try {
            PreparedStatement st = c.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                User user = new User(
                        rs.getString("User_Id"),
                        rs.getString("Full_Name"),
                        rs.getString("Username"),
                        rs.getString("Email"),
                        rs.getString("Phone_Number"),
                        rs.getString("Address"),
                        rs.getString("Image"),
                        rs.getInt("Status"),
                        rs.getInt("Role_Id"),
                        rs.getInt("Points")
                );
                users.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    public List<User> getFilteredUsers(String name, String username, String phone, String email, String address, String roleid, String sortBy, String sortDir) {
        List<User> users = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM [User] WHERE 1=1");
        List<Object> params = new ArrayList<>();
        if (name != null && !name.isEmpty()) {
            sql.append(" AND Full_Name LIKE ?");
            params.add("%" + name + "%");
        }
        if (username != null && !username.isEmpty()) {
            sql.append(" AND Username LIKE ?");
            params.add("%" + username + "%");
        }
        if (phone != null && !phone.isEmpty()) {
            sql.append(" AND Phone_Number LIKE ?");
            params.add("%" + phone + "%");
        }
        if (email != null && !email.isEmpty()) {
            sql.append(" AND Email LIKE ?");
            params.add("%" + email + "%");
        }
        if (address != null && !address.isEmpty()) {
            sql.append(" AND Address LIKE ?");
            params.add("%" + address + "%");
        }
        if (roleid != null && !roleid.isEmpty()) {
            sql.append(" AND Role_Id = ?");
            params.add(Integer.parseInt(roleid));
        }
        String column;
        switch (sortBy) {
            case "id":
                column = "User_Id";
                break;
            case "name":
                column = "Full_Name";
                break;
            case "username":
                column = "Username";
                break;
            case "phone":
                column = "Phone_Number";
                break;
            case "email":
                column = "Email";
                break;
            case "address":
                column = "Address";
                break;
            case "roleid":
                column = "Role_Id";
                break;
            default:
                column = "User_Id";
        }
        String direction = "asc".equalsIgnoreCase(sortDir) ? "ASC" : "DESC";
        sql.append(" ORDER BY ").append(column).append(" ").append(direction);
        try {
            PreparedStatement st = c.prepareStatement(sql.toString());
            for (int i = 0; i < params.size(); i++) {
                st.setObject(i + 1, params.get(i));
            }
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                User user = new User(
                        rs.getString("User_Id"),
                        rs.getString("Full_Name"),
                        rs.getString("Username"),
                        rs.getString("Email"),
                        rs.getString("Phone_Number"),
                        rs.getString("Address"),
                        rs.getString("Image"),
                        rs.getInt("Status"),
                        rs.getInt("Role_Id"),
                        rs.getInt("Points")
                );
                users.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    public int countAllUsers() {
        String sql = "SELECT COUNT(*) FROM [User]";
        try (PreparedStatement st = c.prepareStatement(sql); ResultSet rs = st.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int countActiveUsers() {
        String sql = "SELECT COUNT(*) FROM [User] WHERE Status = 1";
        try (PreparedStatement st = c.prepareStatement(sql); ResultSet rs = st.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int countLockedUsers() {
        String sql = "SELECT COUNT(*) FROM [User] WHERE Status = 0";
        try (PreparedStatement st = c.prepareStatement(sql); ResultSet rs = st.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int countByRole(int roleId) {
        String sql = "SELECT COUNT(*) FROM [User] WHERE Role_Id = ?";
        try (PreparedStatement st = c.prepareStatement(sql)) {
            st.setInt(1, roleId);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public User getUserByBookingId(int bookingId) {
        String sql = """
        SELECT u.*
        FROM Booking b
        JOIN [User] u ON b.Customer_Id = u.User_Id
        WHERE b.Booking_Id = ?
    """;

        try (PreparedStatement st = c.prepareStatement(sql)) {
            st.setInt(1, bookingId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getString("User_Id"),
                        rs.getString("Full_Name"),
                        rs.getString("Username"),
                        rs.getString("Email"),
                        rs.getString("Phone_Number"),
                        rs.getString("Address"),
                        rs.getString("Image"),
                        rs.getInt("Status"),
                        rs.getInt("Role_Id"),
                        rs.getInt("Points")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public OfflineBookingUser getByBookingId(int bookingId) {
        String sql = "SELECT * FROM OfflineBookingUser WHERE Booking_Id = ?";
        try (PreparedStatement st = c.prepareStatement(sql)) {
            st.setInt(1, bookingId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return new OfflineBookingUser(
                        rs.getInt("Offline_User_Id"),
                        rs.getString("Full_Name"),
                        rs.getString("Phone_Number"),
                        rs.getString("Address"),
                        rs.getString("Identity_Card"),
                        rs.getInt("Booking_Id"),
                        rs.getInt("Booked_By_Receptionist_Id"),
                        rs.getTimestamp("Created_At").toLocalDateTime()
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public OfflineBookingUser getOfflineBookingUserByBookingId(int bookingId) {
        OfflineBookingUser user = null;
        String sql = "SELECT * FROM OfflineBookingUser WHERE Booking_Id = ?";

        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, bookingId); 

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                user = new OfflineBookingUser();
                user.setOfflineUserId(rs.getInt("Offline_User_Id"));
                user.setFullName(rs.getString("Full_Name"));
                user.setPhoneNumber(rs.getString("Phone_Number"));
                user.setAddress(rs.getString("Address"));
                user.setIdentityCard(rs.getString("Identity_Card"));
                user.setBookingId(rs.getInt("Booking_Id"));
                user.setBookedByReceptionistId(rs.getInt("Booked_By_Receptionist_Id"));
                user.setCreatedAt(rs.getTimestamp("Created_At").toLocalDateTime());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }
}
