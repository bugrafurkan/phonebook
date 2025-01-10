package com.example.directoryservice.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "persons")
public class Person {
    @Id
    @Column(name = "id")
    private String id;

    public String firstName;
    public String lastName;
    public String company;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Contact> constacts = new ArrayList<>();
}
