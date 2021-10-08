package com.paizuze.bestWorstBlog.dto;

import com.paizuze.bestWorstBlog.model.BlogPost;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BlogPostDTO {
    private Long id;
    private String title;
    private String textBody;
    private Long authorId;

    public BlogPostDTO(){}

    public BlogPostDTO(BlogPost blogPost) {
        this();
        this.setId(blogPost.getId());
        this.setTitle(blogPost.getTitle());
        this.setTextBody(blogPost.getTextBody());
        this.setAuthorId(blogPost.getAuthor().getId());
    }
}
