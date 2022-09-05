
package com.displate.playgroundspringjpa;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
public class QueriesTest {

    @Autowired
    PersonRepository repository;

    Person skyler, walter, flynn, marie, hank;

    @BeforeEach
    public void setUp() {
        repository.deleteAll();
        this.skyler = repository.save(newPerson("Skyler", "White", 45));
        this.walter = repository.save(newPerson("Walter", "White", 50));
        this.flynn = repository.save(newPerson("Walter Jr. (Flynn)", "White", 17));
        this.marie = repository.save(newPerson("Marie", "Schrader", 38));
        this.hank = repository.save(newPerson("Hank", "Schrader", 43));
    }

    private Person newPerson(String firstName, String lastName, Integer age) {
        Person person = new Person();
        person.setFirstname(firstName);
        person.setLastname(lastName);
        person.setAge(age);
        return person;
    }

    @Test
    public void findAllOlderThan40() {
        Collection<Person> allProjectedBy = repository.findAllByAgeAfter(40);

        assertThat(allProjectedBy).hasSize(3);
    }

    @Test
    public void findByFirstname() {
        Optional<Person> allProjectedBy = repository.findByFirstname(marie.getFirstname());

        assertThat(allProjectedBy).isPresent();
    }

    @Test
    public void findByNativeJPQL() {
        Collection<Person> allWhite = repository.findAllWhiteFamily();

        assertThat(allWhite).hasSize(3);
    }
}