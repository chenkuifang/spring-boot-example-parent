package com.example.springbootjava8new.staticTest;

/**
 * 描述
 *
 * @author: Quifar
 * @version: 1.0
 */
public class Father {

    {
        System.err.println("father code block");
    }

    public Father() {
        System.err.println("father constructor");
    }

    static {
        System.err.println("father static block");
    }
}
