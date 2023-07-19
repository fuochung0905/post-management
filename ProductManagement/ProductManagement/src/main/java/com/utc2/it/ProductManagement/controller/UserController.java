package com.utc2.it.ProductManagement.controller;

import com.utc2.it.ProductManagement.dto.ApiResponse;
import com.utc2.it.ProductManagement.dto.UserDto;
import com.utc2.it.ProductManagement.service.UserService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    // POST
    @PostMapping("/")
    public ResponseEntity<UserDto>creatUser(@Validated @RequestBody UserDto userDto){
        UserDto createUserDto= this.userService.createUser(userDto);
        return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
    }
    //GET
    @GetMapping("/")
    public ResponseEntity<List<UserDto>>getAllUser(){
        return ResponseEntity.ok(this.userService.getAllUser());
    }
    // GET user_Id
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto>getUser(@PathVariable("userId")Integer id){
        return ResponseEntity.ok(this.userService.getUserById(id));
    }
    //DELETE
    @DeleteMapping("/{userId}")
    public ResponseEntity<?>deleteUser(@PathVariable("userId")Integer id){
        userService.deleteUser(id);
        return new ResponseEntity(new ApiResponse("Delete user successfully",true),HttpStatus.OK);
    }
    //PUT
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto>updateUser(@Valid @RequestBody UserDto userDto, @PathVariable("userId")Integer id){
        UserDto updateUserDto=this.userService.updateUser(userDto,id);
        return ResponseEntity.ok(updateUserDto);
    }

}
