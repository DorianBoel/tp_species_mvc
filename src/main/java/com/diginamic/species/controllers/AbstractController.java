package com.diginamic.species.controllers;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

public abstract class AbstractController<T> {

    protected String handleError(Throwable err, Model model) {
        model.addAttribute("error", err.getClass().getSimpleName());
        model.addAttribute("msg", err.getMessage());
        return "error";
    }

    public abstract String list(Model model);

    public abstract String add(Model model);

    public abstract String update(Integer id, Model model);

    public abstract String save(T entity, BindingResult result, Model model);

    public abstract String delete(Integer id, Model model);

}
