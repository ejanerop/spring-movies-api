package com.janero.movies.domain.mapper;

import org.springframework.stereotype.Service;
import com.janero.movies.domain.model.Person;
import com.janero.movies.domain.dto.PersonDTO;

@Service
public class PersonMapper {

    public PersonDTO mapToDTO(Person user) {
        PersonDTO personDTO = new PersonDTO();
        personDTO.setId(user.getId());
        personDTO.setName(user.getName());
        personDTO.setBiography(user.getBiography());
        personDTO.setBirthday(user.getBirthday());
        personDTO.setAdult(user.getAdult());
        personDTO.setPlaceOfBirth(user.getPlaceOfBirth());
        return personDTO;
    }

    public PersonDTO mapToMoviePersonDTO(Person user) {
        PersonDTO personDTO = new PersonDTO();
        personDTO.setId(user.getId());
        personDTO.setName(user.getName());
        return personDTO;
    }
}
