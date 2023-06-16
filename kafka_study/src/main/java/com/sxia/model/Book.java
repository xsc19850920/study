package com.sxia.model;


import lombok.Data;

import java.io.Serializable;
@Data
public class Book implements Serializable {
    private String name;
    private String author;


}
