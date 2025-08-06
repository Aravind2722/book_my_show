package org.example.book_my_show.services;

import org.example.book_my_show.exceptions.UserAlreadyExitsException;
import org.example.book_my_show.models.User;

public interface IUserService {
    User signUpUser(String name, int age, String email, String phoneNumber, String password) throws UserAlreadyExitsException;
}
