package com.sxia.validate.service.impl;

import com.sxia.validate.service.IUserService;
import com.sxia.validate.vo.User;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {
    public User createUser(User user) {
        return user;
    }

    @Override
    public User getUsers(String name) {
        return null;
    }
}
