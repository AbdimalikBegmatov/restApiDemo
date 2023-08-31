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
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

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
    public ResponseEntity<HttpStatus> save(PersonCreateRequest request, BindingResult bindingResult) {

        Person person =new Person();

        if (bindingResult.hasErrors()){
            throw new ApiRequestException(handleBindingResult(bindingResult),HttpStatus.BAD_REQUEST);
        }

        Optional<Person> email = personRepository.findByEmail(request.getEmail());

        if (email.isPresent())
            throw new ApiRequestException("This email already have",HttpStatus.BAD_REQUEST);

        person.setName(request.getName());
        person.setEmail(request.getEmail());
        person.setDob(request.getDob());
        person.setPassword(request.getPassword());

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

    @Transactional
    @Override
    public ResponseEntity<Long> edit(Long id, PersonCreateRequest personCreateRequest,BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            throw new ApiRequestException(handleBindingResult(bindingResult),HttpStatus.BAD_REQUEST);
        }
        Optional<Person> person= personRepository.findById(id);
        if (person.isEmpty())
            throw new ApiRequestException("Person not found",HttpStatus.NOT_FOUND);

        person.get().setPassword(personCreateRequest.getPassword());
        person.get().setDob(personCreateRequest.getDob());
        person.get().setEmail(personCreateRequest.getEmail());
        person.get().setName(personCreateRequest.getName());

        personRepository.save(person.get());
        return new ResponseEntity<>(person.get().getId(),HttpStatus.OK);
    }

    private String handleBindingResult(BindingResult bindingResult){
        StringBuilder stringBuilder=new StringBuilder();
        for (FieldError error: bindingResult.getFieldErrors()
        ) {
            stringBuilder.append(error.getField()).append("-").append(error.getDefaultMessage()).append(";");
        }
        return stringBuilder.toString();
    }
}
