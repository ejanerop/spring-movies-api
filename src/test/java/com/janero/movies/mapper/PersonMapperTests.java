package com.janero.movies.mapper;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.Date;
import com.janero.movies.domain.dto.PersonDTO;
import com.janero.movies.domain.mapper.PersonMapper;
import com.janero.movies.domain.model.Person;
import com.janero.movies.domain.query.PersonQuery;
import com.janero.movies.domain.request.PersonRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PersonMapperTests {

    @Autowired
    private PersonMapper personMapper;

    @Test
    public void testMaptoDTONullPerson() {
        assertThrows(NullPointerException.class, () -> {
            personMapper.mapToDTO(null);
        });
    }

    @Test
    public void testMaptoDTOEmptyPerson() {
        Person person = new Person();
        assertThrows(IllegalArgumentException.class, () -> {
            personMapper.mapToDTO(person);
        });
    }

    @Test
    public void testMaptoDTO() {
        Person person = new Person();
        person.setId(1L);
        person.setName("John");
        person.setBiography("biography");
        person.setBirthday(new Date());
        person.setPlaceOfBirth("Some place");
        person.setAdult(true);

        PersonDTO personDTO = personMapper.mapToDTO(person);

        assertAll(() -> {
            assertEquals(person.getId(), personDTO.getId());
            assertEquals(person.getName(), personDTO.getName());
            assertEquals(person.getBiography(), personDTO.getBiography());
            assertEquals(person.getBirthday(), personDTO.getBirthday());
            assertEquals(person.getPlaceOfBirth(), personDTO.getPlaceOfBirth());
            assertEquals(person.getAdult(), personDTO.getAdult());
        });
    }

    @Test
    public void testMapToEntityNullRequest() {
        PersonRequest request = null;
        assertThrows(NullPointerException.class, () -> {
            personMapper.mapToEntity(request);
        });
    }

    @Test
    public void testMaptoEntityFromRequest() {
        PersonRequest request = new PersonRequest();
        request.setName("John");
        request.setBiography("biography");
        request.setBirthday(new Date());
        request.setDeathday(new Date());
        request.setPlaceOfBirth("Some place");
        request.setAdult(true);

        Person person = personMapper.mapToEntity(request);

        assertAll(() -> {
            assertEquals(request.getName(), person.getName());
            assertEquals(request.getBiography(), person.getBiography());
            assertEquals(request.getBirthday(), person.getBirthday());
            assertEquals(request.getDeathday(), person.getDeathday());
            assertEquals(request.getPlaceOfBirth(), person.getPlaceOfBirth());
            assertEquals(request.getAdult(), person.getAdult());
        });
    }

    @Test
    public void testMapToEntityNullQuery() {
        PersonQuery query = null;
        assertThrows(NullPointerException.class, () -> {
            personMapper.mapToEntity(query);
        });
    }

    @Test
    public void testMaptoEntityFromQuery() {
        PersonQuery query = new PersonQuery();
        query.setName("Test");
        query.setBiography("biography");
        query.setAdult(true);

        Person person = personMapper.mapToEntity(query);

        assertAll(() -> {
            assertEquals(query.getName(), person.getName());
            assertEquals(query.getBiography(), person.getBiography());
            assertEquals(query.getAdult(), person.getAdult());
        });
    }
}
