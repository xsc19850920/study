package com.sxia.validate.service;

import com.sxia.validate.vo.User;

public interface IUserService {
     User createUser(User user);

     User getUsers(String name);
}
