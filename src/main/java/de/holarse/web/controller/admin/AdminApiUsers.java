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
import static de.holarse.utils.ModelAndViewFactory.makeAdminLayout;
import static de.holarse.web.defines.WebDefines.ADMIN_USERS_DEFAULT_PAGE_SIZE;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import java.time.OffsetDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

/**
 *
 * @author comrad
 */
@Controller
@RequestMapping(value = "/admin/apiusers")
public class AdminApiUsers {

    private final static transient Logger log = LoggerFactory.getLogger(AdminApiUsers.class);
    
    @Autowired
    private ApiUserRepository apiUserRepository;

    @GetMapping
    public ModelAndView index(@PageableDefault(sort={"login"}, value=ADMIN_USERS_DEFAULT_PAGE_SIZE) final Pageable pageable, final ModelAndView mv) {
        makeAdminLayout(mv, "sites/admin/apiusers");
        var result = apiUserRepository.findAll(pageable).map(ApiUserView::of);
        log.debug("apiusers: {}", result);
        mv.addObject("apiusers", result);
        return mv;
    }
    
    @GetMapping("{apiUserId}")
    public ModelAndView show(@PathVariable("apiUserId") final Integer userId, final ModelAndView mv) {
        makeAdminLayout(mv, "sites/admin/apiuser");  
        mv.addObject("apiuser", apiUserRepository.findById(userId).map(ApiUserView::of).get());
        return mv;
    }

    @PostMapping("{apiUserId}")
    public RedirectView save(@Valid @ModelAttribute("apiuser") ApiUserView user, @PathVariable int apiUserId) {
        if (apiUserId != user.getId()) {
            log.error("Userid ({}) does not match form userid. Form: {}", apiUserId, user);
            throw new IllegalArgumentException("userid does not match form userid");
        }

        final ApiUser backendUser = apiUserRepository.findById(apiUserId).orElseThrow(EntityNotFoundException::new);
        backendUser.setLogin(user.getLogin());
        backendUser.setRoleName(user.getRoleName());
        backendUser.setToken(user.getToken());
        backendUser.setValidUntil(user.getValidUntil());
        backendUser.setActive(user.isActive());
        backendUser.setUpdated(OffsetDateTime.now());

        apiUserRepository.saveAndFlush(backendUser);

        return new RedirectView("../apiusers");
    }    
    
    
}
