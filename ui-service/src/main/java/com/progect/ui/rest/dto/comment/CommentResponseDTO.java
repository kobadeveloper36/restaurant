package com.progect.ui.rest.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CommentResponseDTO {
    private Long commentId;
    private LocalDateTime creationDate;
    private String text;
    private Integer rating;
}
