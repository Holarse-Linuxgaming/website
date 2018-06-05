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
        map.addAttribute("uploadCommand", command);
        return "admin/imports/article-form";
    }

    @PostMapping(headers = ("content-type=multipart/*"))
    public String submit(@ModelAttribute UploadCommand command, final Model map) {
        logger.debug("Command: {}", command);
        logger.debug("Upload file: {}", command.getFile());
        
        map.addAttribute("msg", command.getFile());
        
        return "admin/imports/article-form";
    }

}
