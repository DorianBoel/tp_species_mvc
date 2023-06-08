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

import com.diginamic.species.entities.Species;
import com.diginamic.species.repositories.SpeciesRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("species")
public class SpeciesController extends AbstractController<Species> {

    @Autowired
    private SpeciesRepository speciesRepository;

    @Override
    @GetMapping("all")
    public String list(Model model) {
        List<Species> speciesList = speciesRepository.findAll();
        model.addAttribute("speciesList", speciesList);
        return "species/list";
    }

    @Override
    @GetMapping("add")
    public String add(Model model) {
        model.addAttribute("species", new Species());
        return "species/form";
    }

    @Override
    @GetMapping("edit/{id}")
    public String update(@PathVariable Integer id, Model model) {
        try {
            Species species = speciesRepository.findById(id).orElseThrow();
            model.addAttribute("species", species);
            return "species/form";
        } catch (Throwable err) {
            return handleError(err, model);
        }
    }

    @Override
    @PostMapping
    public String save(@Valid Species species, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "species/form";
        } else {
            speciesRepository.save(species);
            return "redirect:species/all";
        }
    }

    @Override
    @GetMapping("delete/{id}")
    public String delete(@PathVariable Integer id, Model model) {
        try {
            Species species = speciesRepository.findById(id).orElseThrow();
            speciesRepository.delete(species);
            return "redirect:/species/all";
        } catch (Throwable err) {
            return handleError(err, model);
        }
    }

}
