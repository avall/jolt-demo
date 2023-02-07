package org.dkf.jolt.model;

public class JoltExample {
    public String title;
    public String name;
    public String type;

    public JoltExample(String title, String name, String type) {
        this.title = title;
        this.name = name;
        this.type = type;
    }

    public JoltExample() {
        this.title = null;
        this.name = null;
        this.type = null;
    }
}
