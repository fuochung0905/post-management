package com.utc2.it.ProductManagement.service;

import com.utc2.it.ProductManagement.dto.PostDto;
import com.utc2.it.ProductManagement.dto.PostResponse;
import com.utc2.it.ProductManagement.entity.Post;

import java.util.List;

public interface PostService {
    //create
    PostDto createPost(PostDto postDto, Integer categoryId, Integer userId);
    //update
    PostDto updatePost(PostDto postDto ,Integer postId);
    //delete
    void deletePost(Integer postId);
    //getAll
    PostResponse getAllPost(Integer pageSize, Integer pageNumber,String sortBy,String sortDir);
    //getSinglePost
    PostDto getPostById(Integer postId);
    //get all post by category
    List<PostDto> getPostByCategory(Integer categoryId);
    // get all post by user
    List<PostDto>getPostByUser(Integer userId);
    //search Post
    List<PostDto>searchPost(String keyword);
}
