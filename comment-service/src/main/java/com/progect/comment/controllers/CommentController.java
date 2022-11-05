package com.progect.comment.controllers;

import com.progect.comment.controllers.dto.CommentRequestDTO;
import com.progect.comment.controllers.dto.CommentResponseDTO;
import com.progect.comment.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/{commentId}")
    public CommentResponseDTO getComment(@PathVariable Long commentId) {
        return new CommentResponseDTO(commentService.getCommentById(commentId));
    }

    @PostMapping("/")
    public Long createComment(@RequestBody CommentRequestDTO commentRequestDTO) {
        return commentService.createComment(commentRequestDTO);
    }

    @PutMapping("/{commentId}")
    public CommentResponseDTO updateComment(@PathVariable Long commentId, @RequestBody CommentRequestDTO commentRequestDTO) {
        return commentService.updateComment(commentId, commentRequestDTO);
    }

    @DeleteMapping("/{commentId}")
    public CommentResponseDTO deleteDish(@PathVariable Long commentId) {
        return commentService.deleteComment(commentId);
    }
    @GetMapping("/user/{userId}")
    public List<CommentResponseDTO> getCommentsById(@PathVariable Long userId){
        return commentService.getCommentsById(userId);
    }
}
