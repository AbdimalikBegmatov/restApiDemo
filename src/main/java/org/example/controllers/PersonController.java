package org.example.controllers;

import org.example.dto.PersonCreateRequest;
import org.example.models.Person;
import org.example.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {
    private final PersonService personService;
    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping()
    public ResponseEntity<List<Person>> getAll(){
        return personService.getAll();
    }
    @PostMapping()
    public ResponseEntity<HttpStatus> create(@RequestBody PersonCreateRequest request){
        return personService.save(request);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Person> getById(@PathVariable("id") Long id){
        return personService.getById(id);
    }
}
