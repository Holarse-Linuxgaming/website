/*
 * Copyright (C) 2023 comrad
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.holarse.web.controller.admin;

import de.holarse.backend.db.ApiUser;
import de.holarse.backend.db.repositories.ApiUserRepository;
import de.holarse.backend.view.ApiUserView;
import de.holarse.config.RoleApiTypes;
import static de.holarse.utils.ModelAndViewFactory.makeAdminLayout;
import static de.holarse.web.defines.WebDefines.ADMIN_USERS_DEFAULT_PAGE_SIZE;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import java.time.OffsetDateTime;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author comrad
 */
@Controller
@RequestMapping(value = {"/admin/apiusers", "/admin/apiusers/"})
public class AdminApiUsers {

    private final static transient Logger log = LoggerFactory.getLogger(AdminApiUsers.class);
    
    @Autowired
    private ApiUserRepository apiUserRepository;
    
    private final static List<String> apiTypes = RoleApiTypes.getTypes();

    @GetMapping
    public ModelAndView index(@PageableDefault(sort={"login"}, value=ADMIN_USERS_DEFAULT_PAGE_SIZE) final Pageable pageable, final ModelAndView mv) {
        makeAdminLayout(mv, "sites/admin/apiusers/list");
        mv.addObject("apiusers", apiUserRepository.findAll(pageable));
        return mv;
    }
    
    @GetMapping("new")
    public ModelAndView create(final ModelAndView mv) {
        makeAdminLayout(mv, "sites/admin/apiusers/form");
        mv.addObject("apiuser", new ApiUserView());
        mv.addObject("apiTypes", apiTypes);        
        return mv;
    }
    
    @GetMapping("{apiUserId}")
    public ModelAndView show(@PathVariable("apiUserId") final Integer userId, final ModelAndView mv) {
        makeAdminLayout(mv, "sites/admin/apiusers/form");  
        mv.addObject("apiuser", apiUserRepository.findById(userId).orElseThrow(EntityNotFoundException::new));
        return mv;
    }
    
    @GetMapping("{apiUserId}/edit")
    public ModelAndView edit(@PathVariable("apiUserId") final Integer userId, final ModelAndView mv) {
        makeAdminLayout(mv, "sites/admin/apiusers/form");  
        mv.addObject("apiuser", apiUserRepository.findById(userId).orElseThrow(EntityNotFoundException::new));
        return mv;
    }    

    public ModelAndView update(final ApiUser backendUser, ApiUser user, final BindingResult result, final ModelAndView mv) {
        backendUser.setLogin(user.getLogin());
        backendUser.setRoleName(user.getRoleName());
        backendUser.setToken(user.getToken());
        backendUser.setValidUntil(user.getValidUntil());
        backendUser.setActive(user.isActive());
        backendUser.setUpdated(OffsetDateTime.now());
        
        apiUserRepository.saveAndFlush(backendUser);
        return new ModelAndView("redirect:.");
    }
    
    @PostMapping
    public ModelAndView save(@Valid @ModelAttribute("apiuser") ApiUser user, final BindingResult result, final ModelAndView mv) {
        if (result.hasErrors()) {
            makeAdminLayout(mv, "sites/admin/apiusers/list");
            return mv;
        }

        ApiUser backendUser;
        if (user.getId() == null) {
            backendUser = new ApiUser();
            backendUser.setCreated(OffsetDateTime.now());
        } else {
            backendUser = apiUserRepository.findById(user.getId()).orElseThrow(EntityNotFoundException::new);    
        }

        return update(backendUser, user, result, mv);
    }    
    
}
