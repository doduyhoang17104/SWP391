/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;

/**
 *
 * @author ddhoang
 */
public class Comment {

    private String content, authorName,userImage;
    private int commentId,authorId,postId;
    private Date createdAt;
    
    public Comment() {
    }

    public Comment(String content, String authorName, String userImage, int commentId, int authorId, int postId, Date createdAt) {
        this.content = content;
        this.authorName = authorName;
        this.userImage = userImage;
        this.commentId = commentId;
        this.authorId = authorId;
        this.postId = postId;
        this.createdAt = createdAt;
    }

    public Comment(String content, String authorName, String userImage, int commentId, int authorId, Date createdAt) {
        this.content = content;
        this.authorName = authorName;
        this.userImage = userImage;
        this.commentId = commentId;
        this.authorId = authorId;
        this.createdAt = createdAt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    @Override
    public String toString() {
        return "Comment{" + "content=" + content + ", authorName=" + authorName + ", userImage=" + userImage + ", commentId=" + commentId + ", authorId=" + authorId + ", createdAt=" + createdAt + '}';
    }

    
}
