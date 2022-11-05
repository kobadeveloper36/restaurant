package com.progect.comment.services;

import com.progect.comment.controllers.dto.CommentRequestDTO;
import com.progect.comment.controllers.dto.CommentResponseDTO;
import com.progect.comment.entities.Comment;
import com.progect.comment.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Comment getCommentById(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("Comment with id: " + commentId + "not found"));
    }

    public Long createComment(CommentRequestDTO commentRequestDTO) {
        Comment newComment = new Comment(commentRequestDTO.getText(), commentRequestDTO.getRating(),
                commentRequestDTO.getUserId());
        return commentRepository.save(newComment).getCommentId();
    }

    public CommentResponseDTO updateComment(Long commentId, CommentRequestDTO commentRequestDTO) {
        Comment comment = new Comment();
        comment.setCommentId(commentId);
        comment.setText(commentRequestDTO.getText());
        comment.setCreationDate(LocalDateTime.now());
        comment.setRating(commentRequestDTO.getRating());
        return new CommentResponseDTO(commentRepository.save(comment));
    }

    public CommentResponseDTO deleteComment(Long commentId) {
        Comment commentById = getCommentById(commentId);
        commentRepository.deleteById(commentId);
        return new CommentResponseDTO(commentById);
    }

    public List<CommentResponseDTO> getCommentsById(Long userId){
        return commentRepository.findAllByUserId(userId).stream().map(CommentResponseDTO::new).collect(Collectors.toList());
    }
}
