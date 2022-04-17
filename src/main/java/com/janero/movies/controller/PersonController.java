package com.janero.movies.controller;

import java.util.NoSuchElementException;
import javax.validation.Valid;
import com.janero.movies.domain.dto.PersonDTO;
import com.janero.movies.domain.query.PersonQuery;
import com.janero.movies.domain.request.PersonRequest;
import com.janero.movies.domain.response.ResponseMessage;
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
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException.BadRequest;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "List actors",
            description = "List persons that appears in a movie as actor.")
    public @ResponseBody Page<PersonDTO> getActors(PersonQuery query) {

        int page = query.getPage() == null ? 0 : query.getPage();
        int size = query.getSize() == null ? Constants.DEFAULT_PAGE_SIZE : query.getSize();

        Person person = personMapper.mapToEntity(query);

        Pageable pageable = PageRequest.of(page, size);

        Page<Person> entities = personService.getActors(person, pageable);

        return (Page<PersonDTO>) entities.map(personMapper::mapToDTO);
    }

    @GetMapping(value = "/directors")
    @Operation(summary = "List directors",
            description = "List persons that are related to a movie as director.")
    public @ResponseBody Page<PersonDTO> getDirectors(PersonQuery query) {

        int page = query.getPage() == null ? 0 : query.getPage();
        int size = query.getSize() == null ? Constants.DEFAULT_PAGE_SIZE : query.getSize();

        Person person = personMapper.mapToEntity(query);

        Pageable pageable = PageRequest.of(page, size);

        Page<Person> entities = personService.getDirectors(person, pageable);

        return (Page<PersonDTO>) entities.map(personMapper::mapToDTO);
    }

    @GetMapping(value = "/{id}")
    @Operation(summary = "Get person", description = "Get a person's information by id.")
    public @ResponseBody ResponseEntity<PersonDTO> getPerson(@PathVariable Long id) {
        PersonDTO personDTO = personMapper.mapToDTO(personService.getPerson(id));
        return ResponseEntity.ok().body(personDTO);
    }

    @PostMapping()
    @Operation(summary = "Add person", description = "Adds a new person.")
    public ResponseEntity<PersonDTO> savePerson(@RequestBody @Valid PersonRequest request) {
        Person person = personMapper.mapToEntity(request);
        personService.savePerson(person);
        return ResponseEntity.status(HttpStatus.CREATED).body(personMapper.mapToDTO(person));
    }

    @PutMapping(value = "/{id}")
    @Operation(summary = "Update person", description = "Update person's information by id.")
    public ResponseEntity<PersonDTO> updatePerson(@PathVariable Long id,
            @RequestBody @Valid PersonRequest request) {
        Person person = personMapper.mapToEntity(request);
        person.setId(id);
        personService.savePerson(person);
        return ResponseEntity.status(HttpStatus.OK).body(personMapper.mapToDTO(person));
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Delete person", description = "Deletes a person by id.")
    public ResponseEntity<ResponseMessage> deletePerson(@PathVariable Long id) {
        personService.deletePerson(personService.getPerson(id));
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseMessage(200, "Person deleted", true));
    }

    // ----------------------------------------------------------------------------------
    // Exception handlers
    // ----------------------------------------------------------------------------------

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ResponseMessage> handleNotFound() {
        ResponseMessage message = new ResponseMessage(404, "Person not found!", false);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<ResponseMessage> handleDataIntegrity() {
        ResponseMessage message =
                new ResponseMessage(422, "Can't delete a director of a movie", false);
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(message);
    }

    @ExceptionHandler(BadRequest.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<ResponseMessage> handleBadRequest() {
        ResponseMessage message = new ResponseMessage(422, "Bad Request!", false);
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(message);
    }


}
