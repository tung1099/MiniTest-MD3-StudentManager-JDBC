package model;

public class Students {
    private int id;
    private String name;
    private String address;
    private int class_id;

    public Students() {
    }

    public Students(int id, String name, String address, int class_id) {
        super();
        this.id = id;
        this.name = name;
        this.address = address;
        this.class_id = class_id;
    }
    public Students(String name, String address, int class_id) {
        super();
        this.name = name;
        this.address = address;
        this.class_id = class_id;
    }

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getClass_id() {
        return class_id;
    }

    public void setClass_id(int class_id) {
        this.class_id = class_id;
    }
}
