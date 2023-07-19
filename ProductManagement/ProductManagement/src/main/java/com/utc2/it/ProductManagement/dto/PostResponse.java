package com.utc2.it.ProductManagement.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@NoArgsConstructor
@Getter
@Setter
public class PostResponse {
    private List<PostDto>content;
    private Integer pageNumber;
    private Integer pageSize;
    private long totalElement;
    private Integer totalPage;
    private boolean lastPage;
}
