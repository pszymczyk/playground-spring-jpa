package com.displate.playgroundspringjpa;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.util.Collection;
import java.util.Optional;

public interface PersonRepository extends CrudRepository<Person, String>, QueryByExampleExecutor<Person> {

    Collection<Person> findAllByAgeAfter(Integer age);

    Optional<Person> findByFirstname(String firstname);

    Collection<PersonProjection> findAllProjectedBy();

    Collection<PersonDTO> findAllDtoBy();

    Collection<PersonNickname> findAllNicknamesBy();

    @Query("SELECT p FROM Person p WHERE p.lastname = 'White'")
    Collection<Person> findAllWhiteFamily();
}
