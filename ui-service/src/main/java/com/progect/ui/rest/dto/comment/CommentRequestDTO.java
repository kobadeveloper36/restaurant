package com.progect.ui.rest.dto.comment;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentRequestDTO {
    private String text;
    private Integer rating;
    private Long userId;
}
