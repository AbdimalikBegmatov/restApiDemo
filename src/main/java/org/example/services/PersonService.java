package org.example.services;

import org.example.dto.PersonCreateRequest;
import org.example.models.Person;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface PersonService {
    ResponseEntity<HttpStatus> save(PersonCreateRequest person);
    ResponseEntity<List<Person>> getAll();
    ResponseEntity<Person> getById(@PathVariable("id")Long id);
    ResponseEntity<Long> edit(Long id,PersonCreateRequest personCreateRequest);
}
