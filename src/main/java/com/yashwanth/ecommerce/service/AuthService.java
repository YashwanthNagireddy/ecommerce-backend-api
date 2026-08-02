package com.yashwanth.ecommerce.service;


import com.yashwanth.ecommerce.entity.User;
import com.yashwanth.ecommerce.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthService {


    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;



    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService
    ){

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;

    }



    public User register(User user){


        if(userRepository.findByEmail(user.getEmail()).isPresent()){

            throw new RuntimeException(
                    "Email already registered"
            );

        }


        user.setPassword(
                passwordEncoder.encode(
                        user.getPassword()
                )
        );


        return userRepository.save(user);

    }





    public String login(
            String email,
            String password
    ){


        System.out.println("LOGIN EMAIL : " + email);


        User user =
                userRepository.findByEmail(email)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "User not found : " + email
                                )
                        );



        boolean match =
                passwordEncoder.matches(
                        password,
                        user.getPassword()
                );


        if(!match){

            throw new RuntimeException(
                    "Invalid password"
            );

        }



        return jwtService.generateToken(email);


    }


}