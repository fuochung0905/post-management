package com.utc2.it.ProductManagement.service;

import com.utc2.it.ProductManagement.dto.LoginResponse;
import com.utc2.it.ProductManagement.dto.SignInRequest;
import com.utc2.it.ProductManagement.dto.UserDto;
import com.utc2.it.ProductManagement.entity.User;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONException;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface UserService {
    User findByEmail(String email);
    LoginResponse signin(SignInRequest request) ;
    User registerUser(UserDto userDto);

//    UserDto updateUser(UserDto userDto , Integer UserId);
//    UserDto getUserById(Integer userId);
//    List<UserDto> getAllUser();
//    void deleteUser(Integer userId);

    // save verification token
    void saveUserVerificationToken(User user,String Token);

    String validateToken(String token);
}
