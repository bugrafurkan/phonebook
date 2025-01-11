package com.example.directoryservice.repository;

import com.example.directoryservice.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, String> {
    // Person için CRUD işlemleri otomatik olarak sağlanır

    // Özel bir sorgu örneği (metot isimlendirmesi ile)
    List<Person> findByLastName(String lastName);
}