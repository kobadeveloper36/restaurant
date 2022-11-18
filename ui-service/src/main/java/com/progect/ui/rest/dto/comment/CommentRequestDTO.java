package com.progect.ui.rest.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequestDTO {
    private String text;
    private String userName;
    private LocalDate creationDate;
}
