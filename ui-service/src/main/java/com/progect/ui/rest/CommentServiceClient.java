package com.progect.ui.rest;

import com.progect.ui.rest.dto.comment.CommentRequestDTO;
import com.progect.ui.rest.dto.comment.CommentResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "comment-service")
public interface CommentServiceClient {
    @RequestMapping(value = "comments/{commentId}", method = RequestMethod.GET)
    CommentResponseDTO getCommentById(@PathVariable Long commentId);

    @RequestMapping(value = "comments/", method = RequestMethod.POST)
    Long createComment(CommentRequestDTO commentRequestDTO);

    @RequestMapping(value = "comments/{commentId}", method = RequestMethod.PUT)
    CommentResponseDTO updateCommentById(@PathVariable Long commentId, CommentRequestDTO commentRequestDTO);

    @RequestMapping(value = "comments/{commentId}", method = RequestMethod.DELETE)
    CommentResponseDTO deleteCommentById(@PathVariable Long commentId);

    @GetMapping("comments/user/{userName}")
    List<CommentResponseDTO> getCommentsByUserName(@PathVariable String userName);

    @GetMapping("comments/")
    List<CommentResponseDTO> getAllComments();
}
