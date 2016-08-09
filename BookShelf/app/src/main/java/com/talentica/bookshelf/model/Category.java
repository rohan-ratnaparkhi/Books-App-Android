package com.talentica.bookshelf.model;

/**
 * Created by rohanr on 8/8/16.
 */
public class Category {
    private int id;
    private String name;

    Category(int id, String name){
        this.id = id;
        this.name = name;
    }

    public int getId(){
        return this.id;
    }

    public void setId(int id){
        this.id = id;
        return;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
        return;
    }
}
