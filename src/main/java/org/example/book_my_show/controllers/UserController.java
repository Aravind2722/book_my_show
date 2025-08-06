package org.example.book_my_show.controllers;

import org.example.book_my_show.dtos.SignUpUserRequestDto;
import org.example.book_my_show.dtos.SignUpUserResponseDto;
import org.example.book_my_show.exceptions.UserAlreadyExitsException;
import org.example.book_my_show.models.ResponseStatus;
import org.example.book_my_show.models.User;
import org.example.book_my_show.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {
    private IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }


    public SignUpUserResponseDto signUpUser(SignUpUserRequestDto requestDto) {
        SignUpUserResponseDto responseDto = new SignUpUserResponseDto();
        try {
            User user = userService.signUpUser(requestDto.getName(), requestDto.getAge(), requestDto.getEmail(), requestDto.getPhoneNumber(), requestDto.getPassword());
            responseDto.setUserId(user.getId());
            responseDto.setResponseStatus(ResponseStatus.SUCCESS);
            responseDto.setMessage("User "+user.getEmail()+", successfully signed up!");
        } catch (UserAlreadyExitsException e) {
            responseDto.setResponseStatus(ResponseStatus.FAILURE);
            responseDto.setMessage("Sign up failed");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            responseDto.setResponseStatus(ResponseStatus.FAILURE);
            responseDto.setMessage("Something went wrong!");
        }
        return responseDto;
    }

}
