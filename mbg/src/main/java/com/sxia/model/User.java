package com.sxia.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
@Data
@AllArgsConstructor
public class User implements Serializable {
    private Integer id;

    private String name;

    private static final long serialVersionUID = 1L;

}