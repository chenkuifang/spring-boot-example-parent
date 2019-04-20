package com.example.springbootjava8new.collection.test;

import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 描述
 *
 * @author: Quifar
 * @version: 1.0
 */
public class ArrayListTest {

    @Test
    public void createArrayList() {
        List arraylist = new ArrayList();
    }


    @Test
    public void deleteLinkedList() {
        List ll = new LinkedList();

        ll.add("a");
        ll.add("b");
        ll.add("c");
        ll.add("d");

        ll.remove("c");


    }


}
