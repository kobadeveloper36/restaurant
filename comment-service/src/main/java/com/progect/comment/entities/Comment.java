package com.progect.comment.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "comment_id", nullable = false)
    private Long commentId;

    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate;

    @Lob
    @Column(name = "text")
    @Type(type = "org.hibernate.type.TextType")
    private String text;

    @Column(name = "user_name", nullable = false)
    private String userName;

    public Comment(String text, String userName) {
        this.creationDate = LocalDateTime.now();
        this.text = text;
        this.userName = userName;
    }
}
