package com.paizuze.bestWorstBlog.dto;

import com.paizuze.bestWorstBlog.model.BlogPost;

public class BlogPostDTO {
    private Long id;
    private String title;
    private String textBody;
    private Long authorId;

    public BlogPost toBlogPost() {
        return new BlogPost(title, textBody);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTextBody() {
        return textBody;
    }

    public void setTextBody(String textBody) {
        this.textBody = textBody;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }
}
