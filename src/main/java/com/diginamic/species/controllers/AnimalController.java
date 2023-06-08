package com.diginamic.species.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.diginamic.species.entities.Animal;
import com.diginamic.species.enums.Sex;
import com.diginamic.species.repositories.AnimalRepository;
import com.diginamic.species.repositories.PersonRepository;
import com.diginamic.species.repositories.SpeciesRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("animal")
public class AnimalController extends AbstractController<Animal> {

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private SpeciesRepository speciesRepository;

    @Autowired
    private PersonRepository personRepository;

    private String initFormModel(Model model) {
        model.addAttribute("sexValues", Sex.values());
        model.addAttribute("speciesValues", speciesRepository.findAll());
        model.addAttribute("personValues", personRepository.findAll());
        return "animal/form";
    }

    @Override
    @GetMapping("all")
    public String list(Model model) {
        List<Animal> animalList = animalRepository.findAll();
        model.addAttribute("animalList", animalList);
        return "animal/list";
    }

    @Override
    @GetMapping("add")
    public String add(Model model) {
        model.addAttribute("animal", new Animal());
        return initFormModel(model);
    }

    @Override
    @GetMapping("edit/{id}")
    public String update(@PathVariable Integer id, Model model) {
        try {
            Animal animal = animalRepository.findById(id).orElseThrow();
            model.addAttribute("animal", animal);
            return initFormModel(model);
        } catch (Throwable err) {
            return handleError(err, model);
        }
    }

    @Override
    @PostMapping
    public String save(@Valid Animal animal, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return initFormModel(model);
        } else {
            animalRepository.save(animal);
            return "redirect:animal/all";
        }
    }

    @Override
    @GetMapping("delete/{id}")
    public String delete(@PathVariable Integer id, Model model) {
        try {
            Animal animal = animalRepository.findById(id).orElseThrow();
            animalRepository.delete(animal);
            return "redirect:/animal/all";
        } catch (Throwable err) {
            return handleError(err, model);
        }
    }

}
