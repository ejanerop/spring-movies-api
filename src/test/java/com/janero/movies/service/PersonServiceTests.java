package com.janero.movies.service;

import java.util.Date;
import java.util.NoSuchElementException;
import com.janero.movies.domain.model.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@AutoConfigureTestDatabase
public class PersonServiceTests {

    @Autowired
    private PersonService personService;

    @Test
    public void contextLoads() {}

    @Test
    public void testSaveAndGetPerson() {
        Person person = new Person();
        person.setName("Eric");
        person.setBiography("biography");
        person.setAdult(true);
        person.setBirthday(new Date());
        personService.savePerson(person);


        Person found = personService.getPerson(person.getId());

        assertThat(person.getId()).isEqualTo(found.getId());
    }

    @Test
    public void testDeletePerson() {
        Person person = new Person();
        person.setName("Eric");
        person.setBiography("biography");
        person.setAdult(true);
        person.setBirthday(new Date());
        personService.savePerson(person);

        Person found = personService.getPerson(person.getId());

        assertThat(person.getId()).isEqualTo(found.getId());

        personService.deletePerson(found);

        assertThrows(NoSuchElementException.class, () -> {
            personService.getPerson(found.getId());
        });
    }

    @Test
    public void testUpdatePerson() {
        Person person = new Person();
        person.setName("Eric");
        person.setBiography("biography");
        person.setAdult(true);
        person.setBirthday(new Date());
        personService.savePerson(person);

        Person found = personService.getPerson(person.getId());

        assertThat(person.getId()).isEqualTo(found.getId());

        found.setName("Eric2");
        found.setBiography("biography2");
        found.setAdult(false);
        found.setBirthday(new Date());
        personService.savePerson(found);

        Person updated = personService.getPerson(person.getId());

        assertAll(() -> {
            assertThat(updated.getName()).isEqualTo("Eric2");
            assertThat(updated.getBiography()).isEqualTo("biography2");
            assertThat(updated.getAdult()).isEqualTo(false);
        });
    }

}
