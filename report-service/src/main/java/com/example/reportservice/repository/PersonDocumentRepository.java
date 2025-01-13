package com.example.reportservice.repository;

import com.example.reportservice.document.PersonDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PersonDocumentRepository extends MongoRepository<PersonDocument, String> {
}
