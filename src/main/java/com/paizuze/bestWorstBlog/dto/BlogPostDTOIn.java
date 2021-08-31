package com.paizuze.bestWorstBlog.dto;

import com.paizuze.bestWorstBlog.model.BlogPost;

public class BlogPostDTOIn {
    private String title;
    private String textBody;
    private Long author_id;

    public BlogPost toBlogPost() {
        return new BlogPost(title, textBody);
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

    public Long getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(Long author_id) {
        this.author_id = author_id;
    }
}
