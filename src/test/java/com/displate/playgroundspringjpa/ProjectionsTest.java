package com.displate.playgroundspringjpa;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
public class ProjectionsTest {

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
    public void findProjections() {

        Collection<PersonProjection> allProjectedBy = repository.findAllProjectedBy();

        assertThat(allProjectedBy)
                .hasSize(5)
                .anyMatch(pP -> pP.getFirstname().equals(skyler.getFirstname()))
                .anyMatch(pP -> pP.getFirstname().equals(walter.getFirstname()))
                .anyMatch(pP -> pP.getFirstname().equals(flynn.getFirstname()))
                .anyMatch(pP -> pP.getFirstname().equals(marie.getFirstname()))
                .anyMatch(pP -> pP.getFirstname().equals(hank.getFirstname()));
    }

    @Test
    public void findDTOs() {

        Collection<PersonDTO> allDtoBy = repository.findAllDtoBy();

        assertThat(allDtoBy)
                .hasSize(5)
                .anyMatch(pP -> pP.firstname().equals(skyler.getFirstname()))
                .anyMatch(pP -> pP.firstname().equals(walter.getFirstname()))
                .anyMatch(pP -> pP.firstname().equals(flynn.getFirstname()))
                .anyMatch(pP -> pP.firstname().equals(marie.getFirstname()))
                .anyMatch(pP -> pP.firstname().equals(hank.getFirstname()));
    }

    @Test
    public void findProjectionsWithTransformation() {

        Collection<PersonNickname> allNicknames = repository.findAllNicknamesBy();

        assertThat(allNicknames)
                .hasSize(5)
                .anyMatch(pP -> pP.getNickname().equals("trololo-" + skyler.getFirstname() + "-" + skyler.getLastname()));
    }

}