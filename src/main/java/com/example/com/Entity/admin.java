package com.example.com.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class admin {
    @Id
    int id;
    String name;
    String email;
    String password;
    int status;
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status=status;
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



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
