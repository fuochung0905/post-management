package com.utc2.it.ProductManagement.repository;

import com.utc2.it.ProductManagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    User findByEmail(String email);
    @Query("select u from User u where u.email=:email and u.isEnable=:active")
    User findUserByEmailAndIsEnable(@Param("email")String email,@Param("active")boolean active);
    Optional<User>findUserByEmail(String email);
}
