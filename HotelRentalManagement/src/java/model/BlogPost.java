package model;

import java.util.Date;

public class BlogPost {

    private int postId;
    private String title;
    private String content;
    private int authorId;
    private Date createdAt;
    private Date updatedAt;
    private String image;
    private String authorName; // Tên tác giả
    private String authorImage;
    private int noComment;

    public BlogPost() {
    }

    public BlogPost(int postId, String title, String content, int authorId, Date createdAt, Date updatedAt, String image, String authorName, String authorImage, int noComment) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.authorId = authorId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.image = image;
        this.authorName = authorName;
        this.authorImage = authorImage;
        this.noComment = noComment;
    }

    public BlogPost(int postId, String title, String content, int authorId, Date createdAt, Date updatedAt, String image, String authorName, String authorImage) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.authorId = authorId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.image = image;
        this.authorName = authorName;
        this.authorImage = authorImage;
    }
//    public BlogPost(int postId, String title, String content, int authorId, Date createdAt, Date updatedAt, String image, String authorName) {
//        this.postId = postId;
//        this.title = title;
//        this.content = content;
//        this.authorId = authorId;
//        this.createdAt = createdAt;
//        this.updatedAt = updatedAt;
//        this.image = image;
//        this.authorName = authorName;
//    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
public String getAuthorImage() {
        return authorImage;
    }

    public void setAuthorImage(String authorImage) {
        this.authorImage = authorImage;
    }

    public int getNoComment() {
        return noComment;
    }

    public void setNoComment(int noComment) {
        this.noComment = noComment;
    }

    @Override
    public String toString() {
        return "BlogPost{" + "postId=" + postId + ", title=" + title + ", content=" + content + ", authorId=" + authorId + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", image=" + image + ", authorName=" + authorName + ", authorImage=" + authorImage + '}';
    }


}

