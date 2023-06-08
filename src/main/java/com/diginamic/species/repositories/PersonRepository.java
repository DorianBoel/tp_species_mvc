package com.diginamic.species.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.diginamic.species.entities.Animal;
import com.diginamic.species.entities.Person;

public interface PersonRepository extends JpaRepository<Person, Integer>, PersonRepositoryCustom {

    List<Person> findByLastNameOrFirstName(String lastName, String firstName);

    List<Person> findByAgeGreaterThanEqual(int age);

    @Query(value = "SELECT p FROM Person p WHERE p.age BETWEEN ?1 and ?2")
    List<Person> findByAgeBetween(int minAge, int maxAge);

    @Query(value = "SELECT p FROM Person p WHERE ?1 MEMBER OF p.animals")
    List<Person> findOwnersOfAnimal(Animal animal);

}
