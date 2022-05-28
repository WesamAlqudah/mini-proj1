package edu.miu.batch.mini_proj1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MiniProj1Application {

    public static void main(String[] args) {
        SpringApplication.run(MiniProj1Application.class, args);
    }

}
