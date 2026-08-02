package com.yashwanth.ecommerce.controller;

import com.yashwanth.ecommerce.entity.User;
import com.yashwanth.ecommerce.repository.UserRepository;
import com.yashwanth.ecommerce.util.JwtUtil;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;


    public UserController(UserRepository userRepository,
                          PasswordEncoder passwordEncoder,
                          JwtUtil jwtUtil) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }



    // Register User
    @PostMapping
    public User addUser(@RequestBody User user) {

        user.setPassword(
                passwordEncoder.encode(user.getPassword())
        );

        return userRepository.save(user);
    }



    // Login User
    @PostMapping("/login")
    public Map<String, String> login(@RequestBody User loginUser) {


        Optional<User> user =
                userRepository.findByEmail(loginUser.getEmail());


        Map<String, String> response = new HashMap<>();


        if (user.isPresent()
                && passwordEncoder.matches(
                loginUser.getPassword(),
                user.get().getPassword())) {


            String token =
                    jwtUtil.generateToken(
                            user.get().getEmail()
                    );


            response.put("message", "Login Successful");
            response.put("token", token);


        } else {

            response.put("message",
                    "Invalid Email or Password");
        }


        return response;
    }




    // Get All Users
    @GetMapping
    public List<User> getUsers() {

        return userRepository.findAll();
    }




    // Get User By Id
    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {

        return userRepository.findById(id)
                .orElse(null);
    }





    // Delete User
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {

        userRepository.deleteById(id);

        return "User deleted successfully";
    }

}