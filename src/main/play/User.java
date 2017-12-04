package main.play;

import java.io.Serializable;

public class User implements Serializable {
    private int id;
    private String name;
    private String password;
    private String address;
    private Gender gender;

    public User(String name, String password, String address, Gender gender) {
        //Randomly generate id
        this.name = name;
        this.password = password;
        this.address = address;
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getAddress() {
        return address;
    }

    public Gender getGender() {
        return gender;
    }
}
