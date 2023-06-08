package com.diginamic.species.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.hibernate.validator.constraints.Length;

import com.diginamic.species.enums.Sex;

import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PreRemove;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Ce champ ne doit pas être vide")
    @Length(max = 50, message = "50 caractères max.")
    private String name;

    @NotBlank(message = "Ce champ ne doit pas être vide")
    @Length(max = 50, message = "50 caractères max.")
    private String color;

    @Convert(converter = Sex.Converter.class)
    private Sex sex;

    @ManyToOne
    @JoinColumn(name = "species_id", nullable = false)
    @NotNull(message = "Ce champ est requis")
    private Species species;

    @ManyToMany(mappedBy = "animals", fetch = FetchType.EAGER)
    @Setter(value = AccessLevel.NONE)
    private Set<Person> owners = new HashSet<>();

    public Animal(String name, String color, Sex sex, Species species) {
        this.name = name;
        this.color = color;
        this.sex = sex;
        this.species = species;
    }

    @PreRemove
    private void clearOwners() {
        owners.forEach((el) -> el.removeAnimal(this));
    }

    @Override
    public String toString() {
        return new StringBuilder(getClass().getSimpleName() + ": ")
            .append("id = " + id)
            .append(", name = " + name)
            .append(", color = " + color)
            .append(", sex = " + sex.getAbbreviation())
            .append(", species = " +  species.getCommonName())
            .append(", owners = " + owners.stream().map((el) -> el.getFullName()).collect(Collectors.toList()))
            .toString();
    }

    public String getIdentity() {
        return String.format("%s (%s)", name, species.getCommonName());
    }

    public List<Person> getOwners() {
        return Collections.unmodifiableList(new ArrayList<>(owners));
    }

    public void setOwners(Collection<Person> owners) {
        List<Person> toRemove = new ArrayList<>(this.owners);
        toRemove.removeAll(owners);
        toRemove.forEach((el) -> el.removeAnimal(this));
        List<Person> toAdd = new ArrayList<>(owners);
        toAdd.removeAll(this.owners);
        toAdd.forEach((el) -> el.addAnimal(this));
    }

}
