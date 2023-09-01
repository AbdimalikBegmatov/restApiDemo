package org.example.services.Impl;

import org.example.dto.DataDto;
import org.example.dto.PersonCreateRequest;
import org.example.dto.PersonResponseDto;
import org.example.exceptions.ApiRequestException;
import org.example.models.Person;
import org.example.repository.PersonRepository;
import org.example.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

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
    public ResponseEntity<DataDto<PersonResponseDto>> getAll(Integer id) {
        int pageSize=4;
        if (id <= 0)
            throw new ApiRequestException("Page number must be more than 0",HttpStatus.BAD_REQUEST);

//        List<PersonResponseDto> personResponseDtos = personRepository.findAllbyCustom(id*pageSize,pageSize).stream().map(per -> {
//            return new PersonResponseDto(
//                   per.getId(),
//                   per.getName(),
//                   per.getEmail(),
//                   per.getPassword(),
//                   per.getDob(),
//                   per.getCreateAt(),
//                   per.getAge());
//        }).collect(Collectors.toList());
//
//        int countAllPage = Math.toIntExact(personRepository.count() / pageSize);
//
//        DataDto<PersonResponseDto> dataDto=new DataDto<>(
//                personResponseDtos.get(personResponseDtos.size()-1).getCreateAt(),
//                id,
//                countAllPage,
//                personResponseDtos);
        Page<Person> personPage = personRepository.findAll(PageRequest.of(id-1,pageSize, Sort.by("createAt")));

        int maxPageSize = personPage.getTotalPages();

        return new ResponseEntity<>(new DataDto<>(id,maxPageSize,personPage.getContent().stream().map(person -> {
            return new PersonResponseDto(
                    person.getId(),
                    person.getEmail(),
                    person.getName(),
                    person.getPassword(),
                    person.getDob(),
                    person.getCreateAt(),
                    person.getAge());
        }).toList()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PersonResponseDto> getById(Long id) {
        Optional<Person> person = personRepository.findById(id);
        if (person.isEmpty())
            throw new ApiRequestException("Person not found",HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(new PersonResponseDto(
                person.get().getId(),
                person.get().getName(),
                person.get().getEmail(),
                person.get().getPassword(),
                person.get().getDob(),
                person.get().getCreateAt(),
                person.get().getAge()
        ),HttpStatus.OK);
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
