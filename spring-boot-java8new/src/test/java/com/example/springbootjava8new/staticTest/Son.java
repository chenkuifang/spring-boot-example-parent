package com.example.springbootjava8new.staticTest;

/**
 * 描述
 *
 * @author: Quifar
 * @version: 1.0
 */
public class Son extends Father{
    {
        System.err.println("son code block");
    }

    public Son() {
        System.err.println("son constructor");
    }

    static {
        System.err.println("son static block");
    }
}
