package com.example.directoryservice.service.impl;

import com.example.directoryservice.dto.PhonebookEntryDto;
import com.example.directoryservice.entity.Person;
import com.example.directoryservice.entity.PhonebookEntry;
import com.example.directoryservice.repository.PersonRepository;
import com.example.directoryservice.repository.PhonebookEntryRepository;
import com.example.directoryservice.service.PhonebookEntryService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class PhonebookEntryServiceImpl implements PhonebookEntryService {

    private final PhonebookEntryRepository phonebookEntryRepository;
    private final PersonRepository personRepository;

    public PhonebookEntryServiceImpl(PhonebookEntryRepository phonebookEntryRepository, PersonRepository personRepository) {
        this.phonebookEntryRepository = phonebookEntryRepository;
        this.personRepository = personRepository;
    }

    @Override
    public PhonebookEntryDto addPhonebookEntry(PhonebookEntryDto dto) {
        Person personOwner = personRepository.findById(dto.getOwnerId())
                .orElseThrow(() -> new RuntimeException("Owner not found with id:" + dto.getOwnerId()));
        Person personContact = personRepository.findById(dto.getContactId())
                .orElseThrow(() -> new RuntimeException("Contact not found with id:" + dto.getContactId()));

        PhonebookEntry entry = new PhonebookEntry();
        entry.setOwner(personOwner);
        entry.setContact(personContact);

        PhonebookEntry savedEntry = phonebookEntryRepository.save(entry);
        return toPhonebookEntryDto(savedEntry);
    }
    private PhonebookEntryDto toPhonebookEntryDto(PhonebookEntry entry) {
        if (entry == null) return null;
        PhonebookEntryDto dto = new PhonebookEntryDto();
        dto.setId(entry.getId());
        dto.setOwnerId(entry.getOwner().getId());
        dto.setContactId(entry.getContact().getId());
        return dto;
    }

    @Override
    public List<PhonebookEntryDto> getAllPhonebookEntries() {
        List<PhonebookEntry> entries = phonebookEntryRepository.findAll();
        return toPhonebookEntryDtoList(entries);
    }

    private List<PhonebookEntryDto> toPhonebookEntryDtoList(List<PhonebookEntry> entries) {
        if (entries == null || entries.isEmpty()) return Collections.emptyList();
        return entries.stream().map(this::toPhonebookEntryDto).toList();
    }

    @Override
    public PhonebookEntryDto getPhonebookEntryById(Long entryId) {
        return phonebookEntryRepository.findById(entryId)
                .map(this::toPhonebookEntryDto)
                .orElse(null);
    }

    @Override
    public PhonebookEntryDto updatePhonebookEntry(Long entryId, PhonebookEntryDto dto) {
        Optional<PhonebookEntry> optionalEntry = phonebookEntryRepository.findById(entryId);
        if (optionalEntry.isEmpty()) {
            return null;
        }

        PhonebookEntry entry = optionalEntry.get();

        if (dto.getOwnerId() != null) {
            Person newOwner = personRepository.findById(dto.getOwnerId())
                    .orElseThrow(() -> new RuntimeException("Owner not found with id:" + dto.getOwnerId()));
            entry.setOwner(newOwner);
        }
        if (dto.getContactId() != null) {
            Person newContact = personRepository.findById(dto.getContactId())
                    .orElseThrow(() -> new RuntimeException("Contact not found with id:" + dto.getContactId()));
            entry.setContact(newContact);
        }

        PhonebookEntry updated = phonebookEntryRepository.save(entry);
        return toPhonebookEntryDto(updated);
    }

    @Override
    public void deletePhonebookEntry(Long entryId) {
        phonebookEntryRepository.deleteById(entryId);
    }

    @Override
    public List<PhonebookEntryDto> findEntriesOfOwner(String ownerId) {
        List<PhonebookEntry> entries = phonebookEntryRepository.findByOwner_Id(ownerId);
        return toPhonebookEntryDtoList(entries);
    }
}
