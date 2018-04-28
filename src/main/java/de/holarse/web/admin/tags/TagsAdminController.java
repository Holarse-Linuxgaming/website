package de.holarse.web.admin.tags;

import de.holarse.backend.db.Tag;
import de.holarse.backend.db.repositories.TagGroupRepository;
import de.holarse.backend.db.repositories.TagRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@RequestMapping("/admin/tags")
@Controller
public class TagsAdminController {

    Logger logger = LoggerFactory.getLogger(TagsAdminController.class);

    @Autowired TagRepository tagRepository;

    @Autowired TagGroupRepository tagGroupRepository;

    // INDEX
    @GetMapping("/")
    public String index(Model map) {
        map.addAttribute("tags", tagRepository.findAll());
        return "admin/tags/index";
    }
    
    // EDIT
    @GetMapping("/{tagid}/edit")
    public String edit(@PathVariable("tagid") Long tagId, Model map, TagCommand command) {
        final Tag tag = tagRepository.findById(tagId).get();
        
        map.addAttribute("tag", tag);
        
        command.setName(tag.getName());
        command.setTagGroup(tag.getTagGroup());
        command.setAlias(tag.getAlias());
        
        map.addAttribute("tagCommand", command);
        
        map.addAttribute("tags", tagRepository.findAll());
        map.addAttribute("tagGroups", tagGroupRepository.findAll());
        
        return "admin/tags/edit";
    }
    
    // UPDATE
    @PostMapping("/{tagid}")
    public ModelAndView update(@PathVariable("tagid") Long tagId, @ModelAttribute TagCommand command) {
        final Tag tag = tagRepository.findById(tagId).get();

        logger.debug(command.getAlias().toString());

        tag.setName(command.getName());
        tag.setTagGroup(command.getTagGroup().getId() != null ? command.getTagGroup() : null);
        tag.setAlias(command.getAlias().getId() != null ? command.getAlias() : null);
        
        tagRepository.save(tag);
        
        return new ModelAndView(new RedirectView("/admin/tags/", false, false, true));
    }
    
}
