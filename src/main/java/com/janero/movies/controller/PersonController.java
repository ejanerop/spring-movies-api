package com.janero.movies.controller;

import java.util.NoSuchElementException;
import javax.validation.Valid;
import com.janero.movies.domain.dto.PersonDTO;
import com.janero.movies.domain.dto.criteria.PersonCriteria;
import com.janero.movies.domain.dto.request.CreatePersonRequest;
import com.janero.movies.domain.dto.response.Response;
import com.janero.movies.domain.dto.response.ResponseMessage;
import com.janero.movies.domain.mapper.PersonMapper;
import com.janero.movies.domain.model.Constants;
import com.janero.movies.domain.model.Person;
import com.janero.movies.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping(path = "/persons")
public class PersonController {

    @Autowired
    private PersonService personService;
    @Autowired
    private PersonMapper personMapper;

    @GetMapping(value = "/actors")
    public @ResponseBody Iterable<PersonDTO> getActors(PersonCriteria criteria) {

        int page = criteria.getPage() == null ? 0 : criteria.getPage();
        int size = criteria.getSize() == null ? Constants.DEFAULT_PAGE_SIZE : criteria.getSize();

        Person person = new Person();
        person.setName(criteria.getName());
        person.setBiography(criteria.getBiography());
        if (criteria.getAdult() != null) {
            person.setAdult(criteria.getAdult());
        }

        Pageable pageable = PageRequest.of(page, size);

        Page<Person> entities = personService.getActors(person, pageable);

        return entities.map(personMapper::mapToDTO);
    }

    @GetMapping(value = "/directors")
    public @ResponseBody Iterable<PersonDTO> getDirectors(PersonCriteria criteria) {

        int page = criteria.getPage() == null ? 0 : criteria.getPage();
        int size = criteria.getSize() == null ? Constants.DEFAULT_PAGE_SIZE : criteria.getSize();

        Person person = new Person();
        person.setName(criteria.getName());
        person.setBiography(criteria.getBiography());
        if (criteria.getAdult() != null) {
            person.setAdult(criteria.getAdult());
        }

        Pageable pageable = PageRequest.of(page, size);

        Page<Person> entities = personService.getDirectors(person, pageable);

        return entities.map(personMapper::mapToDTO);
    }

    @GetMapping(value = "/{id}")
    public @ResponseBody ResponseEntity<Response> getPerson(@PathVariable Long id) {
        try {
            PersonDTO personDTO = personMapper.mapToDTO(personService.getPerson(id));
            return ResponseEntity.ok().body(personDTO);
        } catch (NoSuchElementException e) {
            ResponseMessage message = new ResponseMessage(404, e.getMessage(), false);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
    }

    @PostMapping()
    public ResponseEntity<Response> savePerson(@RequestBody @Valid CreatePersonRequest request) {
        try {
            Person person = personMapper.mapToEntity(request);
            personService.createPerson(person);
            return ResponseEntity.status(HttpStatus.CREATED).body(personMapper.mapToDTO(person));
        } catch (Exception e) {
            ResponseMessage message = new ResponseMessage(422, e.getMessage(), false);
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(message);
        }
    }

}
