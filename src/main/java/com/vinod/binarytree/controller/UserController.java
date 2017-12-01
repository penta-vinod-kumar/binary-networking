package com.vinod.binarytree.controller;

import com.vinod.binarytree.model.User;
import com.vinod.binarytree.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

//@Api(value="UserRegistrationService")
@RestController
@RequestMapping(value = "/binary/user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    //@ApiOperation(consumes = MediaType.APPLICATION_JSON_VALUE, value = "/register", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE, response = User.class)
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        return new ResponseEntity(userService.saveUser(user), HttpStatus.OK);
    }
}
