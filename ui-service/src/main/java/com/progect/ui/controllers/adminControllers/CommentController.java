package com.progect.ui.controllers.adminControllers;

import com.progect.ui.rest.dto.comment.CommentRequestDTO;
import com.progect.ui.services.CommentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Controller
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/admin/comment/add")
    public String addComment(@RequestParam String creationDate,
                             @RequestParam String text, @RequestParam String login) {
        CommentRequestDTO commentRequestDTO = new CommentRequestDTO(text,
                login, LocalDate.parse(creationDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        commentService.createComment(commentRequestDTO);
        return "redirect:/admin/comments";
    }

    @PostMapping("/admin/comment/edit/{commentId}")
    public String editComment(@PathVariable Long commentId, @RequestParam String creationDate,
                              @RequestParam String text, @RequestParam String login) {
        CommentRequestDTO commentRequestDTO = new CommentRequestDTO(text,
                login, LocalDate.parse(creationDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        commentService.updateCommentById(commentId, commentRequestDTO);
        return "redirect:/admin/comments";
    }

    @GetMapping("/admin/comment/delete/{commentId}")
    public String deleteComment(@PathVariable Long commentId) {
        commentService.deleteCommentById(commentId);
        return "redirect:/admin/comments";
    }
}
