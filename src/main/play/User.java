package play;

import java.io.Serializable;
import java.util.UUID;

public class User implements Serializable {
    private UUID id;
    private String name;
    private String mail;
    private String password;
    private String address;
    private Gender gender;

    public User(String name, String mail, String password, String address, Gender gender) {
        this.name = name;
        this.mail = mail;
        this.password = password;
        this.address = address;
        this.gender = gender;
        id = UUID.randomUUID();
    }

    public User(String id, String mail, String name, String password, String address, Gender gender) {
        this.name = name;
        this.mail = mail;
        this.password = password;
        this.address = address;
        this.gender = gender;
        this.id = UUID.fromString(id);
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMail() {
        return mail;
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

    @Override
    public boolean equals(Object obj) {
        return getId() == ((User) obj).getId();
    }
}
