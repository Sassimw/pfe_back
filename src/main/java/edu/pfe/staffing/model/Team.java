package edu.pfe.staffing.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
public class Team {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)

    private long id;
    private String name;
    @OneToMany(mappedBy = "team")
    @JsonIgnore
    private List<User> userList;
    @OneToOne
    private User manager;

    public User getManager() {
        return manager;
    }

    public void setManager(User manager) {
        this.manager = manager;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
}
