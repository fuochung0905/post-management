package com.utc2.it.ProductManagement.service;

import com.utc2.it.ProductManagement.dto.UserDto;
import com.utc2.it.ProductManagement.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User>findByEmail(String email);
    User registerUser(UserDto userDto);

//    UserDto updateUser(UserDto userDto , Integer UserId);
//    UserDto getUserById(Integer userId);
//    List<UserDto> getAllUser();
//    void deleteUser(Integer userId);

    // save verification token
    void saveUserVerificationToken(User user,String Token);

    String validateToken(String token);
}
