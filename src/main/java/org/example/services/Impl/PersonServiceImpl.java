package org.example.services.Impl;

import org.example.dto.PersonCreateRequest;
import org.example.exceptions.ApiRequestException;
import org.example.models.Person;
import org.example.repository.PersonRepository;
import org.example.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PersonServiceImpl implements PersonService {
    private final PersonRepository personRepository;
    @Autowired
    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    @Transactional
    public ResponseEntity<HttpStatus> save(PersonCreateRequest request) {

        Person person =new Person();

        Optional<Person> email = personRepository.findByEmail(request.email());

        if (email.isPresent())
            throw new ApiRequestException("This email already have",HttpStatus.BAD_REQUEST);

        person.setName(request.name());
        person.setEmail(request.email());
        person.setDob(request.dob());
        person.setPassword(request.password());

        personRepository.save(person);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<Person>> getAll() {

        return new ResponseEntity<>(personRepository.findAll(),HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Person> getById(Long id) {
        Optional<Person> person = personRepository.findById(id);
        if (person.isEmpty())
            throw new ApiRequestException("Person not found",HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(person.get(),HttpStatus.OK);
    }
}
