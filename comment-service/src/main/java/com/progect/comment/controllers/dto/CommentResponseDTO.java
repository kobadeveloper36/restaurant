package com.progect.comment.controllers.dto;

import com.progect.comment.entities.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class CommentResponseDTO {
    private Long commentId;
    private LocalDate creationDate;
    private String text;
    private String userName;

    public CommentResponseDTO(Comment comment) {
        this.commentId = comment.getCommentId();
        this.creationDate = comment.getCreationDate();
        this.text = comment.getText();
        this.userName = comment.getUserName();
    }
}
