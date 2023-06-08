package com.sxia.validate.vo;

import com.sxia.validate.common.validata.CustomValid;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
@Data
public class User implements Serializable{

    private long id;


    // user name should not be null or empty
    // user name should have at least 2 characters
    @NotEmpty
    @Size(min = 2, message = "user name should have at least 2 characters")
    private String name;

    // email should be a valid email format
    // email should not be null or empty
    @NotEmpty
    @Email
    private String email;

    // password should not be null or empty
    // password should have at least 8 characters
    @NotEmpty
    @Size(min = 8, message = "password should have at least 8 characters")
    private String password;
    @CustomValid(values = {"0","1"},message = "性别参数异常,正确:0,1")
    private String sex;
    @CustomValid(values = {"0","1","2"},message = "用户类型参数异常,正确:0,1,2")
    private String userType;

}