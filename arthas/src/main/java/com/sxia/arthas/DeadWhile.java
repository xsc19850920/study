package com.sxia.arthas;

import com.sxia.city.entity.CityCn;

import java.util.ArrayList;
import java.util.List;

public class DeadWhile {
    public static void main(String[] args) {
        List<CityCn> list = new ArrayList<>() ;
        while (true){
            list.add(new CityCn());
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
