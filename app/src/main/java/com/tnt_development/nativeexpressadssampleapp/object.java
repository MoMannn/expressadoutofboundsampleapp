package com.tnt_development.nativeexpressadssampleapp;

/**
 * Created by tadejvengust1 on 10. 11. 16.
 */

public class object {

    String name;
    int id;


    public object() {
    }

    public object(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
