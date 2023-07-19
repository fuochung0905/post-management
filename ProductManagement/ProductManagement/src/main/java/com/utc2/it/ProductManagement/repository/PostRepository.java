package com.utc2.it.ProductManagement.repository;

import com.utc2.it.ProductManagement.entity.Category;
import com.utc2.it.ProductManagement.entity.Post;
import com.utc2.it.ProductManagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface PostRepository extends JpaRepository<Post,Integer> {
    List<Post>findByUser(User user);
    List<Post>findByCategory(Category category);
//    @Query("select p from Post p where p.title like :key")
    //List<Post>searchByTitle@Param("key")String title;
    List<Post>findByTitleContaining(String title);
}
