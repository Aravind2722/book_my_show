package org.example.book_my_show;

import org.example.book_my_show.controllers.UserController;
import org.example.book_my_show.dtos.SignUpUserRequestDto;
import org.example.book_my_show.dtos.SignUpUserResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.awt.print.Book;

@SpringBootApplication
@EnableJpaAuditing
public class BookMyShowApplication implements CommandLineRunner {
    private final UserController userController;
    @Autowired
    public BookMyShowApplication(UserController userController) {
        this.userController = userController;
    }
    public static void main(String[] args) {
        SpringApplication.run(BookMyShowApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        SignUpUserRequestDto requestDto = new SignUpUserRequestDto();
//        requestDto.setName("Aravind");
//        requestDto.setAge(23);
//        requestDto.setEmail("aravind@gmail.com");
//        requestDto.setPhoneNumber("7010378072");
//        requestDto.setPassword("Aravind@bms");
//        SignUpUserResponseDto responseDto = userController.signUpUser(requestDto);
//        System.out.println(responseDto);
    }
}
