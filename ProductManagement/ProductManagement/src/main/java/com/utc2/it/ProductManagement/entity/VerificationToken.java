package com.utc2.it.ProductManagement.entity;

import com.utc2.it.ProductManagement.dto.UserDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Calendar;
import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@Entity
public class VerificationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String token;
    private Date expirationTime;
    private static final int Expiration = 15;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    public VerificationToken(String token, User user){
        this.token=token;
        this.user=user;
        this.expirationTime=this.getTokenExpirationTime();
    }
    public VerificationToken(String token){
        this.token=token;
        this.expirationTime=this.getTokenExpirationTime();
    }
    public Date getTokenExpirationTime(){
        Calendar calendar=Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE,Expiration);
        return new Date(calendar.getTime().getTime());
    }
}
