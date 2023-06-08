package com.sxia.validate.controller;

import com.sxia.validate.service.IUserService;
import com.sxia.validate.vo.User;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;


@RestController
@RequestMapping("/api/")
//这个注解是针对get的
@Validated
public class UserController {

    @Resource
    private IUserService userService;

    @PostMapping("users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user){
        User savedUser = userService.createUser(user);
        return new ResponseEntity<User>(savedUser, HttpStatus.CREATED);
    }
    @GetMapping("getUsers")
    public ResponseEntity<User> getUsers(@NotBlank(message = "名字不能为空") String name) {
        User dbUsers = userService.getUsers(name);
        return new ResponseEntity<User>(dbUsers, HttpStatus.OK);
    }
}