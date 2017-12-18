package main.play;

import java.io.Serializable;
import java.util.UUID;

public class User implements Serializable {
    private UUID id;
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
        id = UUID.randomUUID();
    }

    public UUID getId() {
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
