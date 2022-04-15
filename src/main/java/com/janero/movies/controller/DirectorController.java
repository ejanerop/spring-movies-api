package com.janero.movies.controller;

import com.janero.movies.domain.dto.PersonDTO;
import com.janero.movies.domain.dto.criteria.PersonCriteria;
import com.janero.movies.domain.mapper.PersonMapper;
import com.janero.movies.domain.model.Constants;
import com.janero.movies.domain.model.Person;
import com.janero.movies.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RestController
@RequestMapping(path = "/directors")
public class DirectorController {

    @Autowired
    private PersonService personService;
    @Autowired
    private PersonMapper personMapper;

    @GetMapping()
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

}
