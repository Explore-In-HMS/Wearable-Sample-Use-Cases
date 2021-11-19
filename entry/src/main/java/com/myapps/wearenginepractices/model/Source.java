package com.myapps.wearenginepractices.model;

import java.io.Serializable;

public class Source implements Serializable {

    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

}
