package com.diginamic.species.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.diginamic.species.entities.Species;

public interface SpeciesRepository extends JpaRepository<Species, Integer> {

    public Optional<Species> findFirstByCommonName(String commonName);

    public List<Species> findByLatinNameContainingIgnoreCase(String inLatinName);

    @Query(value = "SELECT s FROM Species s ORDER BY s.commonName ASC")
    public List<Species> findAllOrderByCommonNameAscending();

    @Query(value = "SELECT s FROM Species s WHERE s.commonName LIKE CONCAT('%', ?1, '%')")
    public List<Species> findByCommonNameLike(String substring);

}
