package com.sxia.springbootcache.controller;

import com.github.pagehelper.PageInfo;
import com.sxia.model.User;
import com.sxia.springbootcache.service.UserService;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user/")
public class UserController {
    @Resource
    private UserService userService;
    @PostMapping("selectPages/{pn}")
    public PageInfo<User> selectUser(@PathVariable("pn") Integer pn, @RequestBody User user){
        return userService.selectByPage(user, PageRequest.of(pn, 5));
    }

    @PostMapping("selectById/{id}")
    public User selectById(@PathVariable("id") Integer id){
        return userService.selectById(id);
    }

    @PostMapping("updateById")
    public User updateById(@RequestBody User user){
        return userService.updateById(user);
    }

    @PostMapping("deleteById")
    public void deleteById(@RequestBody User user){
         userService.deleteById(user);
    }
    @PostMapping("add")
    public User add(@RequestBody User user){
        return userService.add(user);
    }
}
