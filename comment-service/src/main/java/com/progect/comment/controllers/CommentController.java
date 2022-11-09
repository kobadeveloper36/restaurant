package com.progect.comment.controllers;

import com.progect.comment.controllers.dto.CommentRequestDTO;
import com.progect.comment.controllers.dto.CommentResponseDTO;
import com.progect.comment.services.CommentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentController {

    private final CommentService commentService;

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
    @GetMapping("/user/{userName}")
    public List<CommentResponseDTO> getCommentsById(@PathVariable String userName){
        return commentService.getCommentsByUserName(userName);
    }

    @GetMapping("/")
    public List<CommentResponseDTO> getAllComments(){
        return commentService.getAllComments();
    }
}
