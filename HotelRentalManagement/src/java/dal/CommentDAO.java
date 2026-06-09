package dal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Comment;

public class CommentDAO extends DBcontext {

    public void addComment(int postId, int userId, String content) {
        String sql = "INSERT INTO Comments (Post_Id, User_Id, Content, Created_At) VALUES (?, ?, ?, GETDATE())";
        try (PreparedStatement st = c.prepareStatement(sql)) {
            st.setInt(1, postId);
            st.setInt(2, userId);
            st.setString(3, content);
            st.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public List<Comment> getCommentsByPostId(int postId) {
        List<Comment> list = new ArrayList<>();

        String sql = "SELECT c.Comment_Id, c.Post_Id, c.User_Id, c.Content, c.Created_At, "
                + "u.Full_Name, u.Image "
                + "FROM Comments c "
                + "JOIN [User] u ON c.User_Id = u.User_Id "
                + "WHERE c.Post_Id = ? "
                + "ORDER BY c.Created_At DESC";

        try {
            PreparedStatement st = c.prepareStatement(sql);
            st.setInt(1, postId);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Comment comment = new Comment(
                        rs.getString("Content"),
                        rs.getString("Full_Name"),
                        rs.getString("Image"),
                        rs.getInt("Comment_Id"),
                        rs.getInt("User_Id"),
                        rs.getTimestamp("Created_At")
                );
                list.add(comment);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public Comment getCommentById(int commentId) {
        String sql = "SELECT c.Comment_Id, c.Post_Id, c.User_Id, c.Content, c.Created_At, "
                + "u.Full_Name, u.Image "
                + "FROM Comments c JOIN [User] u ON c.User_Id = u.User_Id "
                + "WHERE c.Comment_Id = ?";
        try (PreparedStatement st = c.prepareStatement(sql)) {
            st.setInt(1, commentId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return new Comment(
                        rs.getString("Content"),
                        rs.getString("Full_Name"),
                        rs.getString("Image"),
                        rs.getInt("Comment_Id"),
                        rs.getInt("User_Id"),
                        rs.getInt("Post_Id"),
                        rs.getTimestamp("Created_At")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int countAllComments(String id, String author, String content, String date) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM Comments c JOIN [User] u ON c.User_Id = u.User_Id WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (id != null && !id.trim().isEmpty()) {
            sql.append(" AND c.Comment_Id = ?");
            params.add(Integer.parseInt(id));
        }
        if (author != null && !author.trim().isEmpty()) {
            sql.append(" AND u.Full_Name LIKE ?");
            params.add("%" + author + "%");
        }
        if (content != null && !content.trim().isEmpty()) {
            sql.append(" AND c.Content LIKE ?");
            params.add("%" + content + "%");
        }
        if (date != null && !date.trim().isEmpty()) {
            sql.append(" AND CONVERT(DATE, c.Created_At) = ?");
            params.add(date);
        }

        try (PreparedStatement st = c.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                st.setObject(i + 1, params.get(i));
            }
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Comment> searchComments(String id, String author, String content, String date, int pageIndex, int pageSize) {
        List<Comment> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT c.Comment_Id, c.Post_Id, c.User_Id, c.Content, c.Created_At, u.Full_Name, u.Image "
                + "FROM Comments c JOIN [User] u ON c.User_Id = u.User_Id WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (id != null && !id.trim().isEmpty()) {
            sql.append(" AND c.Comment_Id = ?");
            params.add(Integer.parseInt(id));
        }
        if (author != null && !author.trim().isEmpty()) {
            sql.append(" AND u.Full_Name LIKE ?");
            params.add("%" + author + "%");
        }
        if (content != null && !content.trim().isEmpty()) {
            sql.append(" AND c.Content LIKE ?");
            params.add("%" + content + "%");
        }
        if (date != null && !date.trim().isEmpty()) {
            sql.append(" AND CONVERT(DATE, c.Created_At) = ?");
            params.add(date);
        }

        sql.append(" ORDER BY c.Created_At DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");
        params.add((pageIndex - 1) * pageSize);
        params.add(pageSize);

        try (PreparedStatement st = c.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                st.setObject(i + 1, params.get(i));
            }
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Comment comment = new Comment(
                        rs.getString("Content"),
                        rs.getString("Full_Name"),
                        rs.getString("Image"),
                        rs.getInt("Comment_Id"),
                        rs.getInt("User_Id"),
                        rs.getInt("Post_Id"),
                        rs.getTimestamp("Created_At")
                );
                list.add(comment);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean deleteCommentById(String id) {
        try (PreparedStatement st = c.prepareStatement("DELETE FROM Comments WHERE Comment_Id = ?")) {
            st.setString(1, id);
            return st.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateCommentContent(int commentId, String newContent) {
    String sql = "UPDATE Comments SET Content = ?, Created_At = GETDATE() WHERE Comment_Id = ?";
    try (PreparedStatement st = c.prepareStatement(sql)) {
        st.setString(1, newContent);
        st.setInt(2, commentId);
        
        int rows = st.executeUpdate();
        System.out.println("Số dòng bị ảnh hưởng: " + rows);
        
        return rows > 0;
    } catch (Exception e) {
        e.printStackTrace();
    }
    return false;
}

    
}  
