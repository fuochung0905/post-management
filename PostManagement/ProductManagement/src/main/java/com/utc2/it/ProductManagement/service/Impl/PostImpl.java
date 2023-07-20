package com.utc2.it.ProductManagement.service.Impl;

import com.utc2.it.ProductManagement.dto.PostDto;
import com.utc2.it.ProductManagement.dto.PostResponse;
import com.utc2.it.ProductManagement.entity.Category;
import com.utc2.it.ProductManagement.entity.Post;
import com.utc2.it.ProductManagement.entity.User;
import com.utc2.it.ProductManagement.exception.ResourceNotFoundException;
import com.utc2.it.ProductManagement.repository.CategoryRepository;
import com.utc2.it.ProductManagement.repository.PostRepository;
import com.utc2.it.ProductManagement.repository.UserRepository;
import com.utc2.it.ProductManagement.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostImpl implements PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public PostDto createPost(PostDto postDto, Integer categoryId, Integer userId) {
        Category category=this.categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("category","categoryId",categoryId));
        System.out.println(category.toString());
        User user=this.userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("user","userId",userId));
        Post post=this.modelMapper.map(postDto,Post.class);
        post.setImageName("default.png");
        post.setAddDate(new Date());
        post.setUser(user);
        post.setCategory(category);
        Post newPost=this.postRepository.save(post);
        return this.modelMapper.map(newPost,PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {
        Post post=this.postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("post","postId",postId));
        post.setTitle(postDto.getTitle());
//        post.setAddDate(new Date());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());
        Post updatePost= this.postRepository.save(post);
        return this.modelMapper.map(updatePost,PostDto.class);
    }

    @Override
    public void deletePost(Integer postId) {
        Post post=this.postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("post","postId",postId));
        this.postRepository.delete(post);
    }

    @Override
    public PostResponse getAllPost(Integer pageSize, Integer pageNumber,String sortBy,String sortDir) {
        Sort sort=null;
        if(sortDir.equalsIgnoreCase("asc")){
            sort=Sort.by(sortBy).ascending();
        }else {
            sort=Sort.by(sortBy).descending();
        }
        Pageable p = PageRequest.of(pageNumber,pageSize, sort);
        Page<Post>pagePost=this.postRepository.findAll(p);
        List<Post>allPost=pagePost.getContent();
        List<PostDto>postDto=allPost.stream().map((post -> this.modelMapper.map(post,PostDto.class))).collect(Collectors.toList());
        PostResponse postResponse= new PostResponse();
        postResponse.setContent(postDto);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalPage(pagePost.getTotalPages());
        postResponse.setTotalElement(pagePost.getTotalElements());
        postResponse.setLastPage(pagePost.isLast());


        return postResponse;
    }

    @Override
    public PostDto getPostById(Integer postId) {
        Post post=this.postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("post","postId",postId));
        PostDto postDto=this.modelMapper.map(post,PostDto.class);
        return postDto;
    }

    @Override
    public List<PostDto> getPostByCategory(Integer categoryId) {
        Category category=this.categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("category","category",categoryId));
        List<Post>posts=this.postRepository.findByCategory(category);
        List<PostDto>postDtos=posts.stream().map((post)->this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
        return postDtos;
    }

    @Override
    public List<PostDto> getPostByUser(Integer userId) {
        User user=this.userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("user","userId",userId));
        List<Post>posts=this.postRepository.findByUser(user);
        List<PostDto> postDtos=posts.stream().map((post)->this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
        return postDtos;
    }

    @Override
    public List<PostDto> searchPost(String keyword) {
        List<Post>posts=this.postRepository.findByTitleContaining(keyword);
        List<PostDto>postDtos=posts.stream().map((post)->this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
        return postDtos;
    }
}
