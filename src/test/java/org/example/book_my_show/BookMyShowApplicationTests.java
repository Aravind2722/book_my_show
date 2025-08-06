package org.example.book_my_show;

import org.example.book_my_show.controllers.UserController;
import org.example.book_my_show.dtos.SignUpUserRequestDto;
import org.example.book_my_show.dtos.SignUpUserResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class BookMyShowApplicationTests {
    @Autowired
    private UserController userController;
    @Test
    void contextLoads() {
    }

    @Test
    @Transactional
    @Rollback
    void testSignUpUser() {
        SignUpUserRequestDto requestDto = new SignUpUserRequestDto();
        requestDto.setName("Aravind");
        requestDto.setAge(23);
        requestDto.setEmail("aravind@gmail.com");
        requestDto.setPhoneNumber("7010378072");
        requestDto.setPassword("Aravind@bms");
        SignUpUserResponseDto responseDto = userController.signUpUser(requestDto);
        System.out.println(responseDto);
    }
}
