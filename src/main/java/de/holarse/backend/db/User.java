package de.holarse.backend.db;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "users")
public class User implements Serializable {
    
    @Id
    @GeneratedValue
    private Long id;
    
    @Column(unique = true)
    private String login;

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", login=" + login + '}';
    }
    
}
