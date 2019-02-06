package com.sep.scientificcentre.scientificcentre.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.sep.scientificcentre.scientificcentre.entity.User;
import com.sep.scientificcentre.scientificcentre.entity.dto.LoginDto;
import com.sep.scientificcentre.scientificcentre.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;
    @Value("${scientific_centre_name}")
    private String scName;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
        this.modelMapper = new ModelMapper();
    }

    @PostMapping(value = "/login")
    private ResponseEntity<?> login(@RequestBody LoginDto loginDto){
        User user = userService.login(loginDto.getUsername(), loginDto.getPassword());
        if(user == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        LoginDto response = modelMapper.map(user, LoginDto.class);
        response.setScName(scName);
        return ResponseEntity.ok(response);

    }

    @GetMapping(value = "/get-token/{name}")
    public ResponseEntity<String> getToken(@PathVariable String name) throws UnsupportedEncodingException {
        Algorithm algorithm = Algorithm.HMAC256("s4T2zOIWHMM1sxq");
        String token = JWT.create()
                .withClaim("client", name)
                .sign(algorithm);
        return ResponseEntity.ok(token);
    }
}
