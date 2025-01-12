package com.example.directoryservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "contacts")
@Getter
@Setter
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

    @Enumerated(EnumType.STRING)
    private ContactType contactType;

    @Column(name = "location")
    private String location; // Yeni alan

    @PrePersist
    protected void onCreate() {
        this.location = fetchDynamicLocation();
    }

    private String fetchDynamicLocation() {
        return "İstanbul, Türkiye";
    }

}
