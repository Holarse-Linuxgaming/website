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
package de.holarse.admin.web;

import de.holarse.backend.db.repositories.ApiUserRepository;
import static de.holarse.utils.ModelAndViewFactory.makeAdminLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author comrad
 */
@Controller
public class AdminApiUsers {

    @Autowired
    private ApiUserRepository apiUserRepository;
    
    @GetMapping(value = "/admin/apiusers")
    public ModelAndView index(final ModelAndView mv) {
        makeAdminLayout(mv, "sites/admin/apiusers");
        
        mv.addObject("items", apiUserRepository.findAll());
        
        return mv;
    }
    
}
