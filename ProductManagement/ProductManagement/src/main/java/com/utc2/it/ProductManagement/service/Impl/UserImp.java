package com.utc2.it.ProductManagement.service.Impl;

import com.utc2.it.ProductManagement.dto.UserDto;
import com.utc2.it.ProductManagement.entity.User;
import com.utc2.it.ProductManagement.entity.VerificationToken;
import com.utc2.it.ProductManagement.exception.ResourceNotFoundException;
import com.utc2.it.ProductManagement.exception.UserAlreadExitsException;
import com.utc2.it.ProductManagement.repository.UserRepository;
import com.utc2.it.ProductManagement.repository.VerificationTokenRepository;
import com.utc2.it.ProductManagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserImp implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final VerificationTokenRepository verificationTokenRepository;

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User registerUser(UserDto userDto) {
        Optional<User>findUser=this.findByEmail(userDto.email());
            if(findUser.isPresent()){
            throw new UserAlreadExitsException("User with email" +userDto.email()+" already exists");
        }
        var newuser= new User();
        newuser.setName(userDto.name());
        newuser.setEmail(userDto.email());
        newuser.setPassword(passwordEncoder.encode(userDto.password()));

        newuser.setRole(userDto.role());

        return this.userRepository.save(newuser);
    }


    @Override
    public void saveUserVerificationToken(User user, String Token) {
        User theUser=this.modelMapper.map(user,User.class);
        var verificationToken= new VerificationToken(Token,theUser);
        verificationTokenRepository.save(verificationToken);
    }

    @Override
    public String validateToken(String token) {
        VerificationToken verificationToken=this.verificationTokenRepository.findByToken(token);
       if(token==null){
           return "Invalid verification token";
       }
       User user=verificationToken.getUser();
        Calendar calendar=Calendar.getInstance();
        if((verificationToken.getExpirationTime().getTime()-calendar.getTime().getTime()<=0)){
            this.verificationTokenRepository.delete(verificationToken);
            return "Token already expired ";
        }
        user.setEnable(true);
        userRepository.save(user);
        return "valid";

    }


}
