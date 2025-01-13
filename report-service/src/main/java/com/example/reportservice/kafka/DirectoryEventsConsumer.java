package com.example.reportservice.kafka;

import com.example.directoryservice.dto.DirectoryEvent;
import com.example.reportservice.document.PersonDocument;
import com.example.reportservice.repository.PersonDocumentRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

public class DirectoryEventsConsumer {
    @Component
    public class KafkaConsumer {

        private final PersonDocumentRepository personDocRepo;

        public KafkaConsumer(PersonDocumentRepository personDocRepo) {
            this.personDocRepo = personDocRepo;
        }

        @KafkaListener(topics = "people-events", groupId = "report-service-group")
        public void consumePersonEvent(DirectoryEvent event) {
            if ("PERSON_CREATED".equals(event.getEventType())) {
                PersonDocument doc = new PersonDocument();
                doc.setId(event.getPersonId());
                doc.setFirstName(event.getFirstName());
                doc.setLastName(event.getLastName());
                doc.setCompany(event.getCompany());
                doc.setEventType(event.getEventType());
                doc.setContacts(event.getContacts());
                personDocRepo.save(doc);
            } else if ("PERSON_UPDATED".equals(event.getEventType())) {
                // Kişi bul, güncelle, repo.save()
            }
            // vb.
        }
    }
}
