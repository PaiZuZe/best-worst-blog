package com.paizuze.bestWorstBlog.model;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "blogPosts")
public class BlogPost implements Serializable {

    @Serial
    private static final long serialVersionUID = 2L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String tittle;
    private String textBody;

    public BlogPost() {
    }

    public BlogPost(String tittle, String textBody) {
        this.tittle = tittle;
        this.textBody = textBody;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getTextBody() {
        return textBody;
    }

    public void setTextBody(String textBody) {
        this.textBody = textBody;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        BlogPost blogPost = (BlogPost) obj;
        return blogPost.getId().longValue() == this.getId().longValue();
    }

    @Override
    public int hashCode() {
        return this.getId() != null ? this.getId().hashCode() : 0;
    }
}
