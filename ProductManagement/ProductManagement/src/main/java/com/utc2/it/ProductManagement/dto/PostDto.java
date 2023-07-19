package com.utc2.it.ProductManagement.dto;
import com.utc2.it.ProductManagement.entity.Category;
import com.utc2.it.ProductManagement.entity.Comment;
import com.utc2.it.ProductManagement.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    private Integer id;
    private String title;
    private String content;
    private String imageName;
    private Date addDate;
    private UserDto user;
    private CategoryDto category;
    private Set<CommentDto>comments= new HashSet<>();

}
