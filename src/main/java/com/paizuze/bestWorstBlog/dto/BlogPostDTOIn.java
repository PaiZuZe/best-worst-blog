package com.paizuze.bestWorstBlog.dto;

import com.paizuze.bestWorstBlog.model.BlogPost;

public class BlogPostDTOIn {
    private String title;
    private String textBody;

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
}
