package com.progect.comment.services;

import com.progect.comment.controllers.dto.CommentRequestDTO;
import com.progect.comment.controllers.dto.CommentResponseDTO;
import com.progect.comment.entities.Comment;
import com.progect.comment.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Comment getCommentById(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("Comment with id: " + commentId + "not found"));
    }

    public Long createComment(CommentRequestDTO commentRequestDTO) {
        Comment newComment = new Comment(commentRequestDTO.getText(),
                commentRequestDTO.getUserName(), commentRequestDTO.getCreationDate());
        return commentRepository.save(newComment).getCommentId();
    }

    public CommentResponseDTO updateComment(Long commentId, CommentRequestDTO commentRequestDTO) {
        Comment comment = new Comment();
        comment.setCommentId(commentId);
        comment.setText(commentRequestDTO.getText());
        comment.setCreationDate(commentRequestDTO.getCreationDate());
        comment.setUserName(commentRequestDTO.getUserName());
        return new CommentResponseDTO(commentRepository.save(comment));
    }

    public CommentResponseDTO deleteComment(Long commentId) {
        Comment commentById = getCommentById(commentId);
        commentRepository.deleteById(commentId);
        return new CommentResponseDTO(commentById);
    }

    public List<CommentResponseDTO> getCommentsByUserName(String userName) {
        return commentRepository.findAllByUserName(userName).stream().map(CommentResponseDTO::new).collect(Collectors.toList());
    }

    public List<CommentResponseDTO> getAllComments() {
        return commentRepository.findAll().stream().map(CommentResponseDTO::new).collect(Collectors.toList());
    }
}
