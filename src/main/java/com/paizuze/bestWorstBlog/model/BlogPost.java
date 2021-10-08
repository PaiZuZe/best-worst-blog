package com.paizuze.bestWorstBlog.model;

import com.paizuze.bestWorstBlog.dto.BlogPostDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "blogPosts", uniqueConstraints = {@UniqueConstraint(columnNames = {"author", "title"})})
public class BlogPost implements Serializable {

    @Serial
    private static final long serialVersionUID = 2L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;
    @Column(length = 4000)
    private String textBody;

    @ManyToOne
    @JoinColumn(name="author")
    private Author author;

    public BlogPost() {}

    public BlogPost(String title, String textBody) {
        this();
        this.setTitle(title);
        this.setTextBody(textBody);
    }

    public BlogPost(BlogPostDTO blogPostDTO) {
        this();
        this.setTitle(blogPostDTO.getTitle());
        this.setTextBody(blogPostDTO.getTextBody());
    }
}
