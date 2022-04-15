package com.janero.movies.repository;

import com.janero.movies.domain.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface PersonRepository
        extends JpaRepository<Person, Long>, JpaSpecificationExecutor<Person> {

}

