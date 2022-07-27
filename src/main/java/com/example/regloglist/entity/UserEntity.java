package com.example.regloglist.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "usr")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private int id;

    @Column(nullable = false)
    private Boolean activity;

    private String username;
    private String password;

    @ElementCollection(targetClass = Role.class)
    @CollectionTable(name = "roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Set<Role> user_role = new HashSet<>();

    public UserEntity() {
    }

    public UserEntity(Boolean activity, String username, String password, Set<Role> user_role) {
        this.activity = activity;
        this.username = username;
        this.password = password;
        this.user_role = user_role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Boolean getActivity() {
        return activity;
    }

    public void setActivity(Boolean activity) {
        this.activity = activity;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<Role> getUser_role() {
        return user_role;
    }

    public void setUser_role(Set<Role> user_role) {
        this.user_role = user_role;
    }
}
