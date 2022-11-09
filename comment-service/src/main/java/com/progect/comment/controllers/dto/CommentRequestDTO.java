package com.progect.comment.controllers.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CommentRequestDTO {
    private String text;
    private String userName;
}
