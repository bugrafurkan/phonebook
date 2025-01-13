package com.example.directoryservice.kafka;

import com.example.directoryservice.dto.DirectoryEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

public class DirectoryEventsProducer {
    @Component
    public class KafkaProducer {
        private final KafkaTemplate<String, DirectoryEvent> kafkaTemplate;

        public KafkaProducer(KafkaTemplate<String, DirectoryEvent> kafkaTemplate) {
            this.kafkaTemplate = kafkaTemplate;
        }

        public void sendPersonEvent(DirectoryEvent event) {
            kafkaTemplate.send("directory-events", event.getPersonId(), event);
        }
    }
}
