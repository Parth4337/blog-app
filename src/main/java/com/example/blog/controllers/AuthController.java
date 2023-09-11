package com.example.blog.controllers;

import com.example.blog.payloads.JwtAuthRequest;
import com.example.blog.payloads.JwtAuthResponse;
import com.example.blog.payloads.UserDto;
import com.example.blog.security.JwtTokenHelper;
import com.example.blog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtTokenHelper jwtTokenHelper;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest jwtAuthRequest) throws Exception {
        authenticate(jwtAuthRequest.getUsername(), jwtAuthRequest.getPassword());


        String token = jwtTokenHelper.generateToken(userDetailsService.loadUserByUsername(jwtAuthRequest.getUsername()));
        return new ResponseEntity<>(new JwtAuthResponse(token), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto) {
        UserDto savedUser = userService.registerUser(userDto);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    private void authenticate(String username, String password) throws Exception {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        try {
            authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        } catch (BadCredentialsException e) {
            System.out.println("Bad Credentials");
            throw new BadCredentialsException("Invalid Credentials");
        }
//        catch (DisabledException e){
//            System.out.println("User is disabled: " + e.getMessage());
//            throw e;
//        }

    }
}
