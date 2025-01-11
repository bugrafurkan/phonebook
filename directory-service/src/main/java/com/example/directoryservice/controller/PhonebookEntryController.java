package com.example.directoryservice.controller;

import com.example.directoryservice.dto.PhonebookEntryDto;
import com.example.directoryservice.service.PhonebookEntryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/phonebook-entries")
public class PhonebookEntryController {
    private final PhonebookEntryService phonebookEntryService;

    public PhonebookEntryController(PhonebookEntryService phonebookEntryService) {
        this.phonebookEntryService = phonebookEntryService;
    }

    @GetMapping("/{entryId}")
    public ResponseEntity<PhonebookEntryDto> getEntryById(@PathVariable Long entryId) {
        PhonebookEntryDto dto = phonebookEntryService.getPhonebookEntryById(entryId);
        if (dto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<PhonebookEntryDto>> getAllEntries() {
        List<PhonebookEntryDto> allEntries = phonebookEntryService.getAllPhonebookEntries();
        return ResponseEntity.ok(allEntries);
    }

    @PostMapping
    public ResponseEntity<Void> addEntry(@RequestBody PhonebookEntryDto dto) {
        PhonebookEntryDto created = phonebookEntryService.addPhonebookEntry(dto);
        String createdEntryId = created.getId().toString();
        URI location = URI.create("/api/phonebook-entries/" + createdEntryId);
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{entryId}")
    public ResponseEntity<PhonebookEntryDto> updateEntry(
            @PathVariable Long entryId,
            @RequestBody PhonebookEntryDto dto) {

        PhonebookEntryDto updated = phonebookEntryService.updatePhonebookEntry(entryId, dto);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{entryId}")
    public ResponseEntity<Void> deleteEntry(@PathVariable Long entryId) {
        phonebookEntryService.deletePhonebookEntry(entryId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<PhonebookEntryDto>> getEntriesOfOwner(@PathVariable String ownerId) {
        List<PhonebookEntryDto> entries = phonebookEntryService.findEntriesOfOwner(ownerId);
        return ResponseEntity.ok(entries);
    }
}
