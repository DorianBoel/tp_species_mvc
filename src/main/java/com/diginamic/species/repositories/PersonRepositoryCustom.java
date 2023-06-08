package com.diginamic.species.repositories;

public interface PersonRepositoryCustom {

    void deleteAllWithoutAnimals();

    void generateRandomPersons(int numberToGenerate);
}
