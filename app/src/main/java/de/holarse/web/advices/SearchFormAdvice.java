package de.holarse.web.advices;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import de.holarse.web.controller.commands.SearchForm;

@ControllerAdvice
public class SearchFormAdvice {
    
    @ModelAttribute("searchForm")
    public SearchForm searchForm() {
        return SearchForm.create();
    }

}
