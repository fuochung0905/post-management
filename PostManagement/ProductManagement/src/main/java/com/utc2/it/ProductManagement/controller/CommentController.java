package com.utc2.it.ProductManagement.controller;

import com.utc2.it.ProductManagement.dto.ApiResponse;
import com.utc2.it.ProductManagement.dto.CommentDto;
import com.utc2.it.ProductManagement.entity.Comment;
import com.utc2.it.ProductManagement.service.CommentService;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final ModelMapper modelMapper;
    @PostMapping("/post/{postId}/comment")
    public ResponseEntity<CommentDto>createComment(@RequestBody CommentDto commentDto, @PathVariable Integer postId){
        CommentDto saveComment=this.commentService.createComment(commentDto,postId);
        return new ResponseEntity<>(saveComment, HttpStatus.CREATED);
    }
    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<ApiResponse>deleteComment(@PathVariable Integer commentId){
        this.commentService.deleteComment(commentId);
        return new ResponseEntity<>(new ApiResponse("comment delete successfully",true),HttpStatus.OK);

    }


}
