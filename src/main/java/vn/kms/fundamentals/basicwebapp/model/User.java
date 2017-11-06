package vn.kms.fundamentals.basicwebapp.model;

import vn.kms.fundamentals.basicwebapp.annotation.Column;
import vn.kms.fundamentals.basicwebapp.annotation.Id;
import vn.kms.fundamentals.basicwebapp.annotation.Table;

import java.io.Serializable;

@Table(name = "USERS")
public class User implements Serializable {

    @Id
    @Column(name = "USERNAME")
    private String username;
    @Column(name = "PASSWORD")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
