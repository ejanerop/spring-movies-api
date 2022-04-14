package com.janero.movies.controllers;

import com.janero.movies.model.Person;
import com.janero.movies.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RestController
@RequestMapping(path = "/person")
public class PersonController {
    public static final int DEFAULT_PAGE_SIZE = 10;

    @Autowired
    private PersonService personService;

    @GetMapping(name = "/actors")
    public @ResponseBody Iterable<Person> getActors(@RequestParam(required = false) String name,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) String overview,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {

        page = page == null ? 0 : page;
        size = size == null ? DEFAULT_PAGE_SIZE : size;

        Person person = new Person();
        person.setName(name);

        Pageable pageable = PageRequest.of(page, size);

        return personService.getActors(person, pageable);
    }

}
