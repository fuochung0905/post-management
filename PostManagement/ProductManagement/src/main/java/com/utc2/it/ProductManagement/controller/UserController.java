package com.utc2.it.ProductManagement.controller;


import com.utc2.it.ProductManagement.dto.LoginResponse;
import com.utc2.it.ProductManagement.dto.SignInRequest;
import com.utc2.it.ProductManagement.dto.UserDto;
import com.utc2.it.ProductManagement.entity.User;
import com.utc2.it.ProductManagement.entity.VerificationToken;
import com.utc2.it.ProductManagement.event.RegistrationCompleteEvent;
import com.utc2.it.ProductManagement.repository.VerificationTokenRepository;
import com.utc2.it.ProductManagement.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ApplicationEventPublisher publisher;
    private final VerificationTokenRepository verificationTokenRepository;

    // register account
    @PostMapping("/register")
    public String creatUser( @RequestBody UserDto userDto, final HttpServletRequest request){
        User createUserDto= this.userService.registerUser(userDto);
        publisher
                .publishEvent(new RegistrationCompleteEvent(createUserDto,applicationUrl(request)));
        return "Success! Please check email for to complete your registration";
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody SignInRequest request)  {
     LoginResponse response=userService.signin(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/verifyEmail")
    public String verifyEmail(@RequestParam("token")String token){
        VerificationToken verificationToken=verificationTokenRepository.findByToken(token);
        if(verificationToken.getUser().getIsEnable()){
            return "This account has already been verified, please,login";
        }
        String verificationResult= userService.validateToken(token);
        if(verificationResult.equalsIgnoreCase("valid")){
            return "Email verified successfully. Now you can login to you account";
        }
        return "Invalid verification token";
    }

    public String applicationUrl(HttpServletRequest request) {
        return "http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
    }

//    //GET
//    @GetMapping("/")
//    public ResponseEntity<List<UserDto>>getAllUser(){
//        return ResponseEntity.ok(this.userService.getAllUser());
//    }
//    // GET user_Id
//    @GetMapping("/{userId}")
//    public ResponseEntity<UserDto>getUser(@PathVariable("userId")Integer id){
//        return ResponseEntity.ok(this.userService.getUserById(id));
//    }
    //DELETE
//    @DeleteMapping("/{userId}")
//    public ResponseEntity<?>deleteUser(@PathVariable("userId")Integer id){
//        userService.deleteUser(id);
//        return new ResponseEntity(new ApiResponse("Delete user successfully",true),HttpStatus.OK);
//    }
//    //PUT
//    @PutMapping("/{userId}")
//    public ResponseEntity<UserDto>updateUser(@Valid @RequestBody UserDto userDto, @PathVariable("userId")Integer id){
//        UserDto updateUserDto=this.userService.updateUser(userDto,id);
//        return ResponseEntity.ok(updateUserDto);
//    }

}
