package com.diginamic.species.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.diginamic.species.entities.Animal;
import com.diginamic.species.entities.Species;
import com.diginamic.species.enums.Sex;

public interface AnimalRepository extends JpaRepository<Animal, Integer> {

    List<Animal> findBySpecies(Species species);

    List<Animal> findByColorIn(List<String> colors);

    @Query(value = "SELECT a FROM Animal a WHERE a.sex = ?1")
    List<Animal> findBySex(Sex sex);

    @Query(value = "SELECT a.owners IS NOT EMPTY FROM Animal a WHERE a = ?1")
    boolean hasOwner(Animal animal);

}
