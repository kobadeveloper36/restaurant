package com.progect.ui.rest.dto.comment;

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
}
