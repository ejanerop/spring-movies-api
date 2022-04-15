package com.janero.movies.controller;

import com.janero.movies.domain.dto.criteria.PersonCriteria;
import com.janero.movies.domain.model.Constants;
import com.janero.movies.domain.model.Person;
import com.janero.movies.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RestController
@RequestMapping(path = "/actors")
public class ActorController {

    @Autowired
    private PersonService personService;

    @GetMapping()
    public @ResponseBody Iterable<Person> getActors(PersonCriteria criteria) {

        int page = criteria.getPage() == null ? 0 : criteria.getPage();
        int size = criteria.getSize() == null ? Constants.DEFAULT_PAGE_SIZE : criteria.getSize();

        Person person = new Person();
        person.setName(criteria.getName());
        person.setBiography(criteria.getBiography());
        if (criteria.getAdult() != null) {
            person.setAdult(criteria.getAdult());
        }

        Pageable pageable = PageRequest.of(page, size);

        return personService.getActors(person, pageable);
    }

}
