package com.diginamic.species.repositories;

import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

public class PersonRepositoryImpl implements PersonRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public void deleteAllWithoutAnimals() {
        Query q = em.createNativeQuery("DELETE p FROM person p LEFT JOIN person_animals pa ON p.id = pa.person_id WHERE pa.animals_id IS NULL");
        q.executeUpdate();
    }

    @Override
    @Transactional
    public void generateRandomPersons(int numberToGenerate) {

    }

}
