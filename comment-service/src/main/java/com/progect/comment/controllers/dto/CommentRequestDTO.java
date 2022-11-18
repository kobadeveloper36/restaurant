package com.progect.comment.controllers.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class CommentRequestDTO {
    private String text;
    private String userName;
    private LocalDate creationDate;
}
