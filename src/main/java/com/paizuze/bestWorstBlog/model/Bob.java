package com.paizuze.bestWorstBlog.model;


import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "bobs")
public class Bob implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String nickname;

    public Bob(String name, String nickname) {
        this.name = name;
        this.nickname = nickname;
    }

    public Bob() {

    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        Bob other_bob = (Bob) obj;
        return other_bob.getId().longValue() == this.getId().longValue();
    }

    @Override
    public int hashCode() {
        return this.getId() != null ? this.getId().hashCode() : 0;
    }
}
