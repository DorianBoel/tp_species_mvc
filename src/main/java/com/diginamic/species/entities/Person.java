package com.diginamic.species.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "firstname")
    @NotBlank(message = "Ce champ ne doit pas être vide")
    @Length(max = 50, message = "50 caractères max.")
    private String firstName;

    @Column(name = "lastname")
    @NotBlank(message = "Ce champ ne doit pas être vide")
    @Length(max = 50, message = "50 caractères max.")
    private String lastName;

    @Min(value = 0, message = "Doit être compris entre 0 et 120")
    @Max(value = 120, message = "Doit être compris entre 0 et 120")
    private int age;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "person_animals",
        joinColumns = @JoinColumn(name = "person_id"),
        inverseJoinColumns = @JoinColumn(name = "animals_id")
    )
    @Getter(value = AccessLevel.NONE)
    @Setter(value = AccessLevel.NONE)
    private Set<Animal> animals = new HashSet<>();

    public Person(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    @Override
    public String toString() {
        List<String> animalsList = animals.stream()
            .map((el) -> el.getIdentity())
            .collect(Collectors.toList());
        return new StringBuilder(getClass().getSimpleName() + ": ")
            .append("id = " + id)
            .append(", name = " + firstName )
            .append(" " + lastName)
            .append(", age = " + age)
            .append(", animals = " + animalsList)
            .toString();
    }

    public void addAnimal(Animal animal) {
        animals.add(animal);
    }

    public void removeAnimal(Animal animal) {
        animals.remove(animal);
    }

    public String getFullName() {
        return String.format("%s %s", firstName, lastName);
    }

    public List<Animal> getAnimals() {
        return Collections.unmodifiableList(new ArrayList<>(animals));
    }

    public void setAnimals(Collection<Animal> animals) {
        this.animals = new HashSet<>(animals);
    }

}
