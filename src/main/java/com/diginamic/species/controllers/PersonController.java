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

import com.diginamic.species.entities.Person;
import com.diginamic.species.repositories.AnimalRepository;
import com.diginamic.species.repositories.PersonRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("person")
public class PersonController extends AbstractController<Person> {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private AnimalRepository animalRepository;

    private String initFormModel(Model model) {
        model.addAttribute("animalValues", animalRepository.findAll());
        return "person/form";
    }

    @Override
    @GetMapping("all")
    public String list(Model model) {
        List<Person> personList = personRepository.findAll();
        model.addAttribute("personList", personList);
        return "person/list";
    }

    @Override
    @GetMapping("add")
    public String add(Model model) {
        model.addAttribute("person", new Person());
        return initFormModel(model);
    }

    @Override
    @GetMapping("edit/{id}")
    public String update(@PathVariable Integer id, Model model) {
        try {
            Person person = personRepository.findById(id).orElseThrow();
            model.addAttribute("person", person);
            return initFormModel(model);
        } catch (Throwable err) {
            return handleError(err, model);
        }
    }

    @Override
    @PostMapping
    public String save(@Valid Person person, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return initFormModel(model);
        } else {
            personRepository.save(person);
            return "redirect:person/all";
        }
    }

    @Override
    @GetMapping("delete/{id}")
    public String delete(@PathVariable Integer id, Model model) {
        try {
            Person person = personRepository.findById(id).orElseThrow();
            personRepository.delete(person);
            return "redirect:/person/all";
        } catch (Throwable err) {
            return handleError(err, model);
        }
    }

}
