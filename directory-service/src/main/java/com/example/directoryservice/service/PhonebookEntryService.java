package com.example.directoryservice.service;

import com.example.directoryservice.dto.PhonebookEntryDto;
import com.example.directoryservice.entity.PhonebookEntry;

import java.util.List;

public interface PhonebookEntryService {

    PhonebookEntryDto addPhonebookEntry(PhonebookEntryDto dto);

    List<PhonebookEntryDto> getAllPhonebookEntries();

    PhonebookEntryDto getPhonebookEntryById(Long entryId);

    PhonebookEntryDto updatePhonebookEntry(Long entryId, PhonebookEntryDto dto);

    void deletePhonebookEntry(Long entryId);

    List<PhonebookEntryDto> findEntriesOfOwner(String ownerId);
}
