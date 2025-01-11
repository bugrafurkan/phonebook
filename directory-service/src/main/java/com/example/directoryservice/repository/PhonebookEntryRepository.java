package com.example.directoryservice.repository;

import com.example.directoryservice.entity.PhonebookEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhonebookEntryRepository extends JpaRepository<PhonebookEntry, Long> {
    // Özel bir sorgu: Owner ID'ye göre PhonebookEntry getir
    List<PhonebookEntry> findByOwner_Id(String ownerId);

    // Özel bir sorgu: Contact ID'ye göre PhonebookEntry getir
    List<PhonebookEntry> findByContact_Id(String contactId);
}