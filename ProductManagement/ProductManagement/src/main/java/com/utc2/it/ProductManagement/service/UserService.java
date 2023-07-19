package com.utc2.it.ProductManagement.service;

import com.utc2.it.ProductManagement.dto.UserDto;
import java.util.List;
public interface UserService {
    UserDto createUser(UserDto userDto);
    UserDto updateUser(UserDto userDto , Integer UserId);
    UserDto getUserById(Integer userId);
    List<UserDto> getAllUser();
    void deleteUser(Integer userId);


}
