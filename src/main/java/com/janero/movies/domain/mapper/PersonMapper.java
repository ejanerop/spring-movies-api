package com.janero.movies.domain.mapper;

import org.springframework.stereotype.Service;
import com.janero.movies.domain.model.Person;
import javax.validation.Valid;
import com.janero.movies.domain.dto.PersonDTO;
import com.janero.movies.domain.query.PersonQuery;
import com.janero.movies.domain.request.PersonRequest;

@Service
public class PersonMapper implements Mapper<Person, PersonDTO> {

    @Override
    public PersonDTO mapToDTO(Person user) {
        PersonDTO personDTO = new PersonDTO();
        if (user.getId() == null) {
            throw new IllegalArgumentException("Person id cannot be null");
        }
        personDTO.setId(user.getId());
        personDTO.setName(user.getName());
        personDTO.setBiography(user.getBiography());
        personDTO.setBirthday(user.getBirthday());
        personDTO.setAdult(user.getAdult());
        personDTO.setPlaceOfBirth(user.getPlaceOfBirth());
        return personDTO;
    }

    public Person mapToEntity(@Valid PersonRequest request) {
        Person person = new Person();
        person.setName(request.getName());
        person.setBiography(request.getBiography());
        person.setBirthday(request.getBirthday());
        person.setDeathday(request.getDeathday());
        person.setPlaceOfBirth(request.getPlaceOfBirth());
        person.setAdult(request.getAdult());
        return person;
    }

    public Person mapToEntity(PersonQuery query) {
        Person person = new Person();
        person.setName(query.getName());
        person.setBiography(query.getBiography());
        if (query.getAdult() != null) {
            person.setAdult(query.getAdult());
        }
        return person;
    }

    public PersonDTO mapToMoviePersonDTO(Person person) {
        PersonDTO personDTO = new PersonDTO();
        personDTO.setId(person.getId());
        personDTO.setName(person.getName());
        return personDTO;
    }
}
