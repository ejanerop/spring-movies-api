package com.janero.movies.controller;

import java.util.NoSuchElementException;
import javax.validation.Valid;
import com.janero.movies.domain.dto.PersonDTO;
import com.janero.movies.domain.dto.query.PersonQuery;
import com.janero.movies.domain.dto.request.PersonRequest;
import com.janero.movies.domain.dto.response.Response;
import com.janero.movies.domain.dto.response.ResponseMessage;
import com.janero.movies.domain.mapper.PersonMapper;
import com.janero.movies.domain.model.Constants;
import com.janero.movies.domain.model.Person;
import com.janero.movies.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping(path = "/persons")
public class PersonController {

    @Autowired
    private PersonService personService;
    @Autowired
    private PersonMapper personMapper;

    @GetMapping(value = "/actors")
    public @ResponseBody Iterable<PersonDTO> getActors(PersonQuery query) {

        int page = query.getPage() == null ? 0 : query.getPage();
        int size = query.getSize() == null ? Constants.DEFAULT_PAGE_SIZE : query.getSize();

        Person person = personMapper.mapToEntity(query);

        Pageable pageable = PageRequest.of(page, size);

        Page<Person> entities = personService.getActors(person, pageable);

        return entities.map(personMapper::mapToDTO);
    }

    @GetMapping(value = "/directors")
    public @ResponseBody Iterable<PersonDTO> getDirectors(PersonQuery query) {

        int page = query.getPage() == null ? 0 : query.getPage();
        int size = query.getSize() == null ? Constants.DEFAULT_PAGE_SIZE : query.getSize();

        Person person = personMapper.mapToEntity(query);

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
    public ResponseEntity<Response> savePerson(@RequestBody @Valid PersonRequest request) {
        try {
            Person person = personMapper.mapToEntity(request);
            personService.savePerson(person);
            return ResponseEntity.status(HttpStatus.CREATED).body(personMapper.mapToDTO(person));
        } catch (Exception e) {
            ResponseMessage message = new ResponseMessage(422, e.getMessage(), false);
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(message);
        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Response> updatePerson(@PathVariable Long id,
            @RequestBody @Valid PersonRequest request) {
        try {
            Person person = personMapper.mapToEntity(request);
            person.setId(id);
            personService.savePerson(person);
            return ResponseEntity.status(HttpStatus.OK).body(personMapper.mapToDTO(person));
        } catch (DataIntegrityViolationException e) {
            ResponseMessage message = new ResponseMessage(422, e.getMessage(), false);
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(message);
        } catch (NoSuchElementException e) {
            ResponseMessage message = new ResponseMessage(404, e.getMessage(), false);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Response> deletePerson(@PathVariable Long id) {
        try {
            personService.deletePerson(personService.getPerson(id));
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseMessage(200, "Person deleted", true));
        } catch (NoSuchElementException e) {
            ResponseMessage message = new ResponseMessage(404, e.getMessage(), false);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        } catch (DataIntegrityViolationException e) {
            ResponseMessage message = new ResponseMessage(422, "Can't delete a director of a movie", false);
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(message);
        } catch (Exception e) {
            e.printStackTrace();
            ResponseMessage message = new ResponseMessage(500, e.getMessage(), false);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
        }
    }

}
