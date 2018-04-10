package de.holarse.web.admin.tags;

import de.holarse.backend.db.Tag;
import de.holarse.backend.db.repositories.TagRepository;
import de.holarse.web.admin.tags.TagCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@RequestMapping("/admin/tags")
@Controller
public class TagsAdminController {
    
    @Autowired
    TagRepository tagRepository;
    
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
        command.setAliasId(tag.getAlias() != null ? tag.getAlias().getId() : null);
        
        map.addAttribute("tagCommand", command);
        
        map.addAttribute("allTags", tagRepository.findAll());
        
        return "admin/tags/edit";
    }
    
    // UPDATE
    @PutMapping("/{tagid}")
    public ModelAndView update(@PathVariable("tagid") Long tagId, @ModelAttribute TagCommand command) {
        final Tag tag = tagRepository.findById(tagId).get();
        
        tag.setName(command.getName());
        tag.setTagGroup(command.getTagGroup());
        tag.setAlias(command.getAliasId() != null ? tagRepository.findById(command.getAliasId()).get() : null);
        
        tagRepository.save(tag);
        
        return new ModelAndView(new RedirectView("/admin/tags/", false, false, true));
    }
    
}
