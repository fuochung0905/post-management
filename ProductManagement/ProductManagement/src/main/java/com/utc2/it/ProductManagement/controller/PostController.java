package com.utc2.it.ProductManagement.controller;

import com.utc2.it.ProductManagement.config.AppConstants;
import com.utc2.it.ProductManagement.dto.ApiResponse;
import com.utc2.it.ProductManagement.dto.PostDto;
import com.utc2.it.ProductManagement.dto.PostResponse;
import com.utc2.it.ProductManagement.service.FileService;
import com.utc2.it.ProductManagement.service.PostService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {
    @Autowired
    private PostService postService;
    @Autowired
    private FileService fileService;
    @Value("project.image")
    String path;
    // create
    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto>createPost(
            @RequestBody PostDto postDto,
            @PathVariable Integer userId,
            @PathVariable Integer categoryId){
        PostDto createPost= this.postService.createPost(postDto,categoryId,userId);
       return new ResponseEntity<PostDto>(createPost, HttpStatus.CREATED);

    }
    // get by user
    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostDto>>getPostsByUser(@PathVariable Integer userId){
        List<PostDto>posts=this.postService.getPostByUser(userId);
        return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
    }
    // get by category
    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<List<PostDto>>getPostsByCategory(@PathVariable Integer categoryId){
        List<PostDto>posts=this.postService.getPostByCategory(categoryId);
        return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
    }
    //get all post
    @GetMapping("/post")
    public ResponseEntity<PostResponse>getAllPost(
            @RequestParam(value = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
            @RequestParam(value ="pageSize",defaultValue =AppConstants.PAGE_SIZE,required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue =AppConstants.SORT_BY,required = false)String sortBy,
            @RequestParam(value = "sortDir",defaultValue = AppConstants.SORT_DIR,required = false)String sortDir
    ){
        PostResponse postResponse=this.postService.getAllPost(pageSize,pageNumber,sortBy,sortDir);
        return new ResponseEntity<>(postResponse,HttpStatus.OK);
    }
    // get Post By Id
    @GetMapping("/post/{postId}")
    public ResponseEntity<PostDto>getPostById(@PathVariable Integer postId){
        PostDto post=this.postService.getPostById(postId);
        return new ResponseEntity<>(post,HttpStatus.OK);
    }
    @DeleteMapping("/post/{postId}")
    public ApiResponse deletePost(@PathVariable Integer postId){
        this.postService.deletePost(postId);
        return new ApiResponse("Post is successfully delete",true);
    }
    @PutMapping("/post/{postId}")
    public ResponseEntity<PostDto>updatePost(@RequestBody PostDto postDto,@PathVariable Integer postId){
        PostDto updatePost=this.postService.updatePost(postDto,postId);
        return new ResponseEntity<>(updatePost,HttpStatus.OK);
    }
    //search
    @GetMapping("/posts/search/{keyword}")
    public ResponseEntity<List<PostDto>>searchPostByTitle(@PathVariable("keyword") String keyword){
        List<PostDto>result=this.postService.searchPost(keyword);// searchByTitle("%"+keyword+"%")
        return new ResponseEntity<>(result,HttpStatus.OK);
    }
    @PostMapping("/post/image/upload/{postId}")
    public ResponseEntity<PostDto>uploadPostImage(
            @RequestParam("image")MultipartFile image,
            @PathVariable Integer postId
            ) throws IOException {
        PostDto postDto=this.postService.getPostById(postId);
        String fileName=this.fileService.uploadImage(path,image);
        postDto.setImageName(fileName);
        PostDto updatePost=this.postService.updatePost(postDto,postId);
    return new ResponseEntity<>(updatePost,HttpStatus.OK);
    }
    @GetMapping(value = "post/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(@PathVariable("imageName")String imageName,
                              HttpServletResponse response) throws IOException {
        InputStream resource= this.fileService.getResource(path,imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());
    }



}
