/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.holarse.web.admin.imports;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/imports/articles/")
public class ArticleImportController {

    Logger logger = LoggerFactory.getLogger(ArticleImportController.class);
    
    @GetMapping
    public String form(Model map, UploadCommand command) {
        map.addAttribute("command", command);
        return "admin/imports/article-form";
    }

    @PostMapping
    public String submit(@ModelAttribute UploadCommand command, Model map) {
        
        logger.debug("Upload file: " + command.getFile().getOriginalFilename());
        
        return "admin/imports/article-form";
    }

}
