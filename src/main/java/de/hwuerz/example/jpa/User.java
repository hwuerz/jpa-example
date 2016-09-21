package de.hwuerz.example.jpa;

import javax.persistence.*;

/**
 * Created by Hendrik on 29.07.2016.
 */

@Entity
public class User {

    @Id
    private int id;

    @Column(length = 32)
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void change() {
        name = name + "_";
    }
}
