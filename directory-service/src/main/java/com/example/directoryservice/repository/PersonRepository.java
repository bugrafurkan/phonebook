package com.example.directoryservice.repository;

import com.example.directoryservice.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, String> {

    List<Person> findByLastName(String lastName);
}