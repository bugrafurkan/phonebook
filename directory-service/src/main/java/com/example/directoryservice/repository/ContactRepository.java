package com.example.directoryservice.repository;

import com.example.directoryservice.entity.Contact;
import com.example.directoryservice.entity.ContactType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    // Özel bir sorgu: ContactType'ına göre Contact'ları bul
    List<Contact> findByContactType(ContactType contactType);

    // Özel bir sorgu: Person ID'sine göre Contact'lar
    List<Contact> findByPerson_Id(String personId);
}