package com.example.directoryservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "phonebook_entries")
@Getter
@Setter
public class PhonebookEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Person owner;

    @ManyToOne
    @JoinColumn(name = "contact_id")
    private Person contact;

}
