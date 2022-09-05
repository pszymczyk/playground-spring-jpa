package com.displate.playgroundspringjpa;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.ignoreCase;
import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.startsWith;
import static org.springframework.data.domain.ExampleMatcher.StringMatcher;
import static org.springframework.data.domain.ExampleMatcher.matching;

@Transactional
@SpringBootTest
public class QueryByExampleTest {

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
    public void countBySimpleExample() {

        var example = Example.of(newPerson(null, "White", null));

        assertThat(repository.count(example)).isEqualTo(3L);
    }

    @Test
    public void ignorePropertiesAndMatchByAge() {

        var example = Example.of(flynn, matching(). //
                withIgnorePaths("firstname", "lastname"));

        assertThat(repository.findOne(example)).contains(flynn);
    }

    @Test
    public void substringMatching() {
        var example = Example.of(newPerson("er", null, null), matching().withStringMatcher(StringMatcher.ENDING));

        assertThat(repository.findAll(example)).containsExactly(skyler, walter);
    }

    @Test
    public void matchStartingStringsIgnoreCase() {

        var example = Example.of(newPerson("Walter", "WHITE", null), matching(). //
                withIgnorePaths("age"). //
                withMatcher("firstname", startsWith()). //
                withMatcher("lastname", ignoreCase()));

        assertThat(repository.findAll(example)).containsExactlyInAnyOrder(flynn, walter);
    }

    @Test
    public void configuringMatchersUsingLambdas() {

        var example = Example.of(newPerson("Walter", "WHITE", null), matching(). //
                withIgnorePaths("age"). //
                withMatcher("firstname", ExampleMatcher.GenericPropertyMatcher::startsWith). //
                withMatcher("lastname", ExampleMatcher.GenericPropertyMatcher::ignoreCase));

        assertThat(repository.findAll(example)).containsExactlyInAnyOrder(flynn, walter);
    }

    @Test
    public void valueTransformer() {

        var example = Example.of(newPerson(null, "White", 99), matching(). //
                withMatcher("age", matcher -> matcher.transform(value -> Optional.of(50))));

        assertThat(repository.findAll(example)).containsExactly(walter);
    }
}