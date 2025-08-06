package org.example.book_my_show;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BookMyShowApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookMyShowApplication.class, args);
    }

}
