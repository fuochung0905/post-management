package com.utc2.it.ProductManagement.repository;

import com.utc2.it.ProductManagement.entity.Comment;
import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Integer>{

}
