package com.utc2.it.ProductManagement.service.Impl;

import com.utc2.it.ProductManagement.dto.CommentDto;
import com.utc2.it.ProductManagement.entity.Comment;
import com.utc2.it.ProductManagement.entity.Post;
import com.utc2.it.ProductManagement.exception.ResourceNotFoundException;
import com.utc2.it.ProductManagement.repository.CommentRepository;
import com.utc2.it.ProductManagement.repository.PostRepository;
import com.utc2.it.ProductManagement.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service

public class CommentImpl implements CommentService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;
    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId) {
        Post post=this.postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("post","postId",postId));
        Comment comment=this.modelMapper.map(commentDto,Comment.class);
        comment.setPost(post);
         Comment saveComment=this.commentRepository.save(comment);

        return this.modelMapper.map(saveComment,CommentDto.class);
    }

    @Override
    public void deleteComment(Integer commentId) {
        Comment comment=this.commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("comment","commentId",commentId));
        this.commentRepository.delete(comment);

    }
}
