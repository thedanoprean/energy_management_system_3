package com.example.energymanagement.loader;

import com.example.energymanagement.entities.Role;
import com.example.energymanagement.entities.Users;
import com.example.energymanagement.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class UserDataLoader implements CommandLineRunner {

    private final UserRepository userRepository;

    @Autowired
    public UserDataLoader(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        List<Users> users = Arrays.asList(
                new Users("3438de50-8bed-43b9-9072-eb1770f43659", "Cosmin","cosmin","$2a$10$1qJlex.0ApniLxHEVVN5b.frSaOtpEq.cWj8iNErWmFOVyXqXVGiG", Role.ADMIN),
                new Users("82221f60-2280-4abe-87d9-558241b8be72", "Andrei","vasy","$2a$10$1qJlex.0ApniLxHEVVN5b.frSaOtpEq.cWj8iNErWmFOVyXqXVGiG",Role.ADMIN),
                new Users("1934b2d3-de7e-41c1-99e4-c3fcfd693e77", "Dan","dan","$2a$10$1qJlex.0ApniLxHEVVN5b.frSaOtpEq.cWj8iNErWmFOVyXqXVGiG",Role.USER),
                new Users("478da28e-8ef8-4093-9776-e216d4672560", "Meli","meli","$2a$10$1qJlex.0ApniLxHEVVN5b.frSaOtpEq.cWj8iNErWmFOVyXqXVGiG",Role.USER)
        );

        userRepository.saveAll(users);

    }
}
