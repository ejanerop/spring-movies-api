package com.janero.movies.service;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.Predicate;
import com.janero.movies.domain.model.Person;
import com.janero.movies.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.convert.QueryByExamplePredicateBuilder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    @Autowired
    PersonRepository personRepository;

    public Page<Person> getActors(Person person, Pageable pageable) {
        return personRepository.findAll(getPerson(person, "moviesAsActor"), pageable);
    }

    public Page<Person> getDirectors(Person person, Pageable pageable) {
        return personRepository.findAll(getPerson(person, "moviesAsDirector"), pageable);
    }

    public Specification<Person> getPerson(Person person, String relation) {
        return (Specification<Person>) (root, query, builder) -> {
            ExampleMatcher matcher = ExampleMatcher.matching()
                    .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING).withIgnoreCase()
                    .withIgnoreNullValues();
            final List<Predicate> predicates = new ArrayList<>();

            predicates.add(builder.isNotEmpty(root.get(relation)));

            predicates.add(QueryByExamplePredicateBuilder.getPredicate(root, builder,
                    Example.of(person, matcher)));

            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

}
