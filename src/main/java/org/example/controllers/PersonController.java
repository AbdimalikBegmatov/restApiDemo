package org.example.controllers;

import jakarta.validation.Valid;
import org.example.dto.DataDto;
import org.example.dto.PersonCreateRequest;
import org.example.dto.PersonResponseDto;
import org.example.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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

    @GetMapping("/page/{id}")
    public ResponseEntity<DataDto<PersonResponseDto>> getAll(@PathVariable("id") Integer id){
        return personService.getAll(id);
    }
    @PostMapping()
    public ResponseEntity<HttpStatus> create(@Valid @RequestBody PersonCreateRequest request,
                                             BindingResult bindingResult){
        return personService.save(request,bindingResult);
    }
    @GetMapping("/{id}")
    public ResponseEntity<PersonResponseDto> getById(@PathVariable("id") Long id){
        return personService.getById(id);
    }
    @GetMapping("/edit/{id}")
    public ResponseEntity<PersonResponseDto> edit(@PathVariable("id") Long id){
        return personService.getById(id);
    }
    @PostMapping("/edit/{id}")
    public ResponseEntity<Long> edit(@PathVariable("id") Long id,
                                     @RequestBody
                                     @Valid
                                     PersonCreateRequest personCreateRequest,
                                     BindingResult bindingResult)    {
        return personService.edit(id,personCreateRequest,bindingResult);
    }
}
