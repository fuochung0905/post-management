package com.utc2.it.ProductManagement.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;



public record UserDto (
        String name,
       String email,
        String password,
        String role,
        String about){

}




