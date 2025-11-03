package com.example.directoryservice.service.impl;

import com.example.directoryservice.dto.ContactDto;
import com.example.directoryservice.entity.Contact;
import com.example.directoryservice.entity.Person;
import com.example.directoryservice.repository.ContactRepository;
import com.example.directoryservice.repository.PersonRepository;
import com.example.directoryservice.service.ContactService;
import org.springframework.stereotype.Service;


@Service
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;
    private final PersonRepository personRepository;

    public ContactServiceImpl(ContactRepository contactRepository, PersonRepository personRepository) {
        this.contactRepository = contactRepository;
        this.personRepository = personRepository;
    }

    @Override
    public Contact createContact(ContactDto contactDto) {
        Contact contact = new Contact();
        contact.setContactType(contactDto.getContactType());
        contact.setContactDetail(contactDto.getContactDetail());
        Person person = personRepository.findById(contactDto.getPersonId())
                .orElseThrow(() -> new RuntimeException("Person not found"));
        contact.setPerson(person);
        if (contactDto.getLocation() != null) {
            contact.setLocation(contactDto.getLocation());
        }
        return contactRepository.save(contact);
    }

    @Override
    public ContactDto toContactDto(Contact contact) {
        if (contact == null) {
            return null; // Null entity için null DTO döndür.
        }

        // Entity'den DTO'ya dönüştürüyoruz
        ContactDto contactDto = new ContactDto();
        contactDto.setId(contact.getId()); // ID değerini aktarıyoruz
        contactDto.setContactType(contact.getContactType());
        contactDto.setContactDetail(contact.getContactDetail());
        contactDto.setLocation(contact.getLocation());

        // Eğer Contact ile bir Person ilişkiliyse, onun ID'sini de DTO'ya koyabiliriz
        if (contact.getPerson() != null) {
            contactDto.setPersonId(contact.getPerson().getId());
        }

        return contactDto;
    }

    @Override
    public void deleteContact(Long contactId) {
        if (contactId == null) {
            throw new IllegalArgumentException("Contact ID cannot be null or blank");
        }

        Contact contact = contactRepository.findById(contactId)
                .orElseThrow(() -> new RuntimeException("Contact not found"));

        if (contact.getPerson() != null) {
            Person person = contact.getPerson();
            person.getConstacts().remove(contact);
            personRepository.save(person);
        }

        contactRepository.delete(contact);
    }
}
