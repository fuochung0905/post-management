package com.utc2.it.ProductManagement.service;

import com.utc2.it.ProductManagement.dto.CommentDto;

public interface CommentService {
     CommentDto createComment(CommentDto commentDto, Integer postId);
     void deleteComment(Integer commentId);
}
