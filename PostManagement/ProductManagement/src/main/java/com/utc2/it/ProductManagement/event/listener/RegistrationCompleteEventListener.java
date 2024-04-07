package com.utc2.it.ProductManagement.event.listener;

import com.utc2.it.ProductManagement.dto.UserDto;
import com.utc2.it.ProductManagement.entity.User;
import com.utc2.it.ProductManagement.event.RegistrationCompleteEvent;
import com.utc2.it.ProductManagement.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.UUID;
@Slf4j
@Component
@RequiredArgsConstructor
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {
    private final UserService userService;
    private final JavaMailSender mailSender;
    private User theUser;
    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        // 1 get the newly registered user
        theUser = event.getUser();
        // create verification token for user
        String verificationToken = UUID.randomUUID().toString();
        //save verification token for user
        userService.saveUserVerificationToken(theUser,verificationToken);
        //build the verification url to be sent to the user
        String url=event.getApplicationUrl()+"/api/v1/auth/verifyEmail?token="+verificationToken;
        //send the email
        try {
            sendVerificationEmail(url);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        log.info("click the link to verify your registration :{}",url);
    }
    public void sendVerificationEmail(String url) throws MessagingException, UnsupportedEncodingException {
        String subject="Email Verification";
        String sender="User Register Portal Service";
        String mailContent="<p> Dear, "+ theUser.getName()+ "</p>"+
                "<p>Thank you for register with us, "+""+
                "Please, follow the link below to complete your registration.</p>"+
                "<a href=\"" +url+ "\">Verify your email to activate your account</a>"+
                "<p>Thank you <br>User registration portal service";
        MimeMessage message=mailSender.createMimeMessage();
        var messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom("phuochungnguyen.work@gmail.com",sender);
        messageHelper.setTo(theUser.getEmail());
        messageHelper.setSubject(subject);
        messageHelper.setText(mailContent,true);
        this.mailSender.send(message);
    }
}
