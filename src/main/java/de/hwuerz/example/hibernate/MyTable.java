package de.hwuerz.example.hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by Hendrik on 29.07.2016.
 */

@Entity
public class MyTable {

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
}
