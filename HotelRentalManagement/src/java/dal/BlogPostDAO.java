/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

/**
 *
 * @author ADMIN
 */
import java.util.ArrayList;
import java.util.List;
import model.BlogPost;
import java.sql.*;
import dal.DBcontext;
import static java.util.Collections.list;

/**
 *
 * @author ADMIN
 */
public class BlogPostDAO extends DBcontext {

    public static List<BlogPost> getAllPosts() {
        List<BlogPost> posts = new ArrayList<>();
        Connection conn = new dal.DBcontext().c;

        try {
            String sql = "                     SELECT b.*, u.Full_Name AS AuthorName, u.Image AS AuthorImage \n"
                    + "                     FROM BlogPosts b\n"
                    + "                     JOIN [User] u ON b.Author_Id = u.User_Id\n"
                    + "                     ORDER BY b.Created_At DESC";
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                BlogPost post = new BlogPost(
                        rs.getInt("Post_Id"),
                        rs.getString("Title"),
                        rs.getString("Content"),
                        rs.getInt("Author_Id"),
                        rs.getTimestamp("Created_At"),
                        rs.getTimestamp("Updated_At"),
                        rs.getString("Image"),
                        rs.getString("AuthorName"),
                        rs.getString("authorImage")
                );
                posts.add(post);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return posts;
    }

    public static int getTotalBlogCount() {
        String sql = "SELECT COUNT(*) FROM BlogPosts";
        try {
            Connection conn = new DBcontext().c;
            PreparedStatement st = conn.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt(1); // Lấy giá trị đếm đầu tiên
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0; // Trả về 0 nếu có lỗi
    }

    public static boolean addPost(BlogPost post) {
        Connection conn = new DBcontext().c;

        if (conn == null) {
            return false;
        }

        String sql = """
        INSERT INTO BlogPosts (Title, Content, Author_Id, Created_At, Updated_At, Image)
        VALUES (?, ?, ?, ?, ?, ?)
        """;

        try {
            conn.setAutoCommit(false); // Bắt đầu transaction

            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, post.getTitle());
            stmt.setString(2, post.getContent());
            stmt.setInt(3, post.getAuthorId());
            Timestamp now = new Timestamp(System.currentTimeMillis());
            stmt.setTimestamp(4, now); // Created_At
            stmt.setNull(5, java.sql.Types.TIMESTAMP);
            // Updated_At
            stmt.setString(6, post.getImage());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                conn.rollback();
                return false;
            }

            // Nếu sau này cần lấy Post_Id mới tạo:
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int postId = generatedKeys.getInt(1);
                    post.setPostId(postId); // Gán ngược lại nếu cần
                }
            }

            conn.commit(); // Thành công
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            try {
                conn.rollback(); // Nếu có lỗi, rollback transaction
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return false;
        } finally {
            try {
                conn.setAutoCommit(true); // Reset lại
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean deletePost(int postId) {
        Connection conn = new dal.DBcontext().c;
        try {
            // 1. (Nếu có bảng liên kết hình ảnh blog, ví dụ BlogImages, thì xóa ở đây)
            // Ví dụ:
            // String deleteImagesSql = "DELETE FROM BlogImages WHERE Post_Id = ?";
            // PreparedStatement stmt1 = db.connection.prepareStatement(deleteImagesSql);
            // stmt1.setInt(1, postId);
            // stmt1.executeUpdate();

            // 2. Xóa bài viết khỏi bảng BlogPosts
            String deletePostSql = "DELETE FROM BlogPosts WHERE Post_Id = ?";
            PreparedStatement stmt2 = conn.prepareStatement(deletePostSql);
            stmt2.setInt(1, postId);
            int rowsAffected = stmt2.executeUpdate();

            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean editPost(BlogPost post) {
        Connection conn = new DBcontext().c;

        if (conn == null) {
            System.out.println("Không thể kết nối tới database.");
            return false;
        }

        String updateSql = """
        UPDATE BlogPosts
        SET Title = ?, Content = ?, Updated_At = ?, Image = ?
        WHERE Post_Id = ?
        """;

        try {
            conn.setAutoCommit(false);

            try (PreparedStatement ps = conn.prepareStatement(updateSql)) {
                ps.setString(1, post.getTitle());
                ps.setString(2, post.getContent());
                ps.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
                ps.setString(4, post.getImage());
                ps.setInt(5, post.getPostId());

                int rows = ps.executeUpdate();
                if (rows == 0) {
                    conn.rollback();
                    System.out.println("Không tìm thấy bài viết để cập nhật.");
                    return false;
                }

                conn.commit();
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return false;
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static BlogPost getPostById(int postId) throws SQLException {
        Connection conn = new DBcontext().c;

        if (conn == null) {
            return null;
        }

        String sql = "    SELECT b.*, u.Full_Name AS AuthorName, u.Image AS AuthorImage\n"
                + "    FROM BlogPosts b\n"
                + "    JOIN [User] u ON b.Author_Id = u.User_Id\n"
                + "    WHERE b.Post_Id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, postId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                BlogPost post = new BlogPost();
                post.setPostId(rs.getInt("Post_Id"));
                post.setTitle(rs.getString("Title"));
                post.setContent(rs.getString("Content"));
                post.setImage(rs.getString("Image"));
                post.setCreatedAt(rs.getTimestamp("Created_At"));
                post.setUpdatedAt(rs.getTimestamp("Updated_At"));
                post.setAuthorImage(rs.getString("AuthorImage"));
                post.setAuthorName(rs.getString("AuthorName"));
                return post;
            }
        }
        return null;
    }

    public static List<BlogPost> searchPostsByMultiple(String title, String author, String createdDate) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public List<BlogPost> pagingBlog(int index) {
        String sql = "SELECT b.*, u.Full_Name AS AuthorName, u.Image AS AuthorImage, "
                + "COUNT(c.Comment_Id) AS CommentCount "
                + "FROM BlogPosts b "
                + "JOIN [User] u ON b.Author_Id = u.User_Id "
                + "LEFT JOIN Comments c ON b.Post_Id = c.Post_Id "
                + "GROUP BY b.Post_Id, b.Title, b.Content, b.Image, b.Created_At, b.Updated_At, b.Author_Id, "
                + "u.Full_Name, u.Image "
                + "ORDER BY b.Post_Id "
                + "OFFSET ? ROWS FETCH NEXT 8 ROWS ONLY";
        List<BlogPost> list = new ArrayList<>();
        try {
            PreparedStatement st = c.prepareStatement(sql);
            st.setInt(1, (index - 1) * 8);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                BlogPost post = new BlogPost(rs.getInt("Post_Id"),
                        rs.getString("Title"),
                        rs.getString("Content"),
                        rs.getInt("Author_Id"),
                        rs.getTimestamp("Created_At"),
                        rs.getTimestamp("Updated_At"),
                        rs.getString("Image"),
                        rs.getString("AuthorName"),
                        rs.getString("authorImage"),
                        rs.getInt("CommentCount"));
//                BlogPost post = new BlogPost(
//                        rs.getInt("Post_Id"),
//                        rs.getString("Title"),
//                        rs.getString("Content"),
//                        rs.getInt("Author_Id"),
//                        rs.getTimestamp("Created_At"),
//                        rs.getTimestamp("Updated_At"),
//                        rs.getString("Image"),
//                        rs.getString("AuthorName"),
//                        rs.getString("authorImage"),
//                        rs.getInt("CommentCount")
//                );
                list.add(post);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return list;
    }

    public static List<BlogPost> get3NewPosts() {
        List<BlogPost> posts = new ArrayList<>();
        Connection conn = new dal.DBcontext().c;

        try {
            String sql = "SELECT TOP 3 b.*, u.Full_Name AS AuthorName, u.Image AS AuthorImage, "
                    + "COUNT(c.Comment_Id) AS CommentCount "
                    + "FROM BlogPosts b "
                    + "JOIN [User] u ON b.Author_Id = u.User_Id "
                    + "LEFT JOIN Comments c ON b.Post_Id = c.Post_Id "
                    + "GROUP BY b.Post_Id, b.Title, b.Content, b.Image, b.Created_At, b.Updated_At, b.Author_Id, "
                    + "u.Full_Name, u.Image "
                    + "ORDER BY b.Created_At DESC";

            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                BlogPost post = new BlogPost(
                        rs.getInt("Post_Id"),
                        rs.getString("Title"),
                        rs.getString("Content"),
                        rs.getInt("Author_Id"),
                        rs.getTimestamp("Created_At"),
                        rs.getTimestamp("Updated_At"),
                        rs.getString("Image"),
                        rs.getString("AuthorName"),
                        rs.getString("authorImage"),
                        rs.getInt("CommentCount")
                );
                posts.add(post);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return posts;
    }

    public List<BlogPost> searchPostsFlexible(String keyword, String author, String date) {
        List<BlogPost> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "SELECT b.Post_Id, b.Title, b.Content, b.Author_Id, b.Created_At, b.Updated_At, b.Image, "
                + "u.Full_Name AS AuthorName, u.Image AS AuthorImage, COUNT(c.Comment_Id) AS CommentCount "
                + "FROM BlogPosts b "
                + "JOIN [User] u ON b.Author_Id = u.User_Id "
                + "LEFT JOIN Comments c ON b.Post_Id = c.Post_Id "
                + "WHERE 1=1 ");

        List<Object> params = new ArrayList<>();

        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append("AND b.Title LIKE ? ");
            params.add("%" + keyword.trim() + "%");
        }

        if (author != null && !author.trim().isEmpty()) {
            sql.append("AND u.Full_Name LIKE ? ");
            params.add("%" + author.trim() + "%");
        }

        if (date != null && !date.trim().isEmpty()) {
            sql.append("AND CAST(b.Created_At AS DATE) = ? ");
            params.add(date.trim());
        }

        sql.append(
                "GROUP BY b.Post_Id, b.Title, b.Content, b.Image, b.Created_At, b.Updated_At, b.Author_Id, "
                + "u.Full_Name, u.Image "
                + "ORDER BY b.Created_At DESC");

        try {
            PreparedStatement st = c.prepareStatement(sql.toString());

            for (int i = 0; i < params.size(); i++) {
                st.setObject(i + 1, params.get(i));
            }

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                BlogPost post = new BlogPost(
                        rs.getInt("Post_Id"),
                        rs.getString("Title"),
                        rs.getString("Content"),
                        rs.getInt("Author_Id"),
                        rs.getTimestamp("Created_At"),
                        rs.getTimestamp("Updated_At"),
                        rs.getString("Image"),
                        rs.getString("AuthorName"),
                        rs.getString("AuthorImage"),
                        rs.getInt("CommentCount") // Thêm trường này nếu BlogPost có
                );
                list.add(post);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public static void main(String[] args) {
        BlogPost post = new BlogPost();
        post.setTitle("Bài viết test");
        post.setContent("Đây là nội dung bài viết test.");
        post.setAuthorId(1); // Đảm bảo user_id = 1 tồn tại trong DB
        post.setImage("https://example.com/test.jpg");

        boolean success = BlogPostDAO.addPost(post);

        if (success) {
            System.out.println("✅ Thêm bài viết thành công! ID mới: " + post.getPostId());
        } else {
            System.out.println("❌ Thêm bài viết thất bại.");
        }
    }
    public List<BlogPost> advancedSearchPosts(Integer postId, String title, String author, String content,
                                          String createdFrom, String createdTo,
                                          String updatedFrom, String updatedTo) {
    List<BlogPost> list = new ArrayList<>();

    StringBuilder sql = new StringBuilder(
        "SELECT b.*, u.Full_Name AS AuthorName, u.Image AS AuthorImage " +
        "FROM BlogPosts b " +
        "JOIN [User] u ON b.Author_Id = u.User_Id " +
        "WHERE 1=1 "
    );

    List<Object> params = new ArrayList<>();

    if (postId != null) {
        sql.append("AND b.Post_Id = ? ");
        params.add(postId);
    }
    if (title != null && !title.trim().isEmpty()) {
        sql.append("AND b.Title LIKE ? ");
        params.add("%" + title.trim() + "%");
    }
    if (author != null && !author.trim().isEmpty()) {
        sql.append("AND u.Full_Name LIKE ? ");
        params.add("%" + author.trim() + "%");
    }
    if (content != null && !content.trim().isEmpty()) {
        sql.append("AND b.Content LIKE ? ");
        params.add("%" + content.trim() + "%");
    }
    if (createdFrom != null && !createdFrom.isEmpty()) {
        sql.append("AND CAST(b.Created_At AS DATE) >= ? ");
        params.add(createdFrom);
    }
    if (createdTo != null && !createdTo.isEmpty()) {
        sql.append("AND CAST(b.Created_At AS DATE) <= ? ");
        params.add(createdTo);
    }
    if (updatedFrom != null && !updatedFrom.isEmpty()) {
        sql.append("AND CAST(b.Updated_At AS DATE) >= ? ");
        params.add(updatedFrom);
    }
    if (updatedTo != null && !updatedTo.isEmpty()) {
        sql.append("AND CAST(b.Updated_At AS DATE) <= ? ");
        params.add(updatedTo);
    }

    sql.append("ORDER BY b.Created_At DESC");

    try (PreparedStatement st = c.prepareStatement(sql.toString())) {
        for (int i = 0; i < params.size(); i++) {
            st.setObject(i + 1, params.get(i));
        }

        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            BlogPost post = new BlogPost(
                rs.getInt("Post_Id"),
                rs.getString("Title"),
                rs.getString("Content"),
                rs.getInt("Author_Id"),
                rs.getTimestamp("Created_At"),
                rs.getTimestamp("Updated_At"),
                rs.getString("Image"),
                rs.getString("AuthorName"),
                rs.getString("AuthorImage")
            );
            list.add(post);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    return list;
}

}
