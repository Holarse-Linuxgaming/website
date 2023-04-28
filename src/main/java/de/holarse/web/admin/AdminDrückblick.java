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
package de.holarse.web.admin;

import de.holarse.backend.db.DrückblickEntry;
import de.holarse.backend.db.repositories.DrückblickRepository;
import de.holarse.backend.types.DrückblickSourceType;
import de.holarse.utils.ModelAndViewFactory;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.OffsetDateTime;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

import static de.holarse.utils.ModelAndViewFactory.makeAdminLayout;

/**
 *
 * @author comrad
 */
@Controller
public class AdminDrückblick {
    
        private final static transient Logger log = LoggerFactory.getLogger(AdminDrückblick.class);

        @Autowired
        private DrückblickRepository drückblickRepository;
        
        @GetMapping("/admin/drückblick")
        public ModelAndView index(final ModelAndView mv) {
            makeAdminLayout(mv, "sites/admin/drückblick");
            
            mv.addObject("items", drückblickRepository.findUnattended());
            
            return mv;
        }
        
        @PutMapping(value = "/admin/drückblick/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<String> update(@PathVariable(name = "id") final Long id, @RequestBody final DrückblickEntry updatedEntry) {
            final DrückblickEntry entry = drückblickRepository.findById(id).orElseThrow(IllegalArgumentException::new);
            entry.setCategory(updatedEntry.getCategory());
            entry.setChangelog(updatedEntry.getChangelog());
            entry.setDone(updatedEntry.isDone());
            entry.setMessage(updatedEntry.getMessage());
            entry.setName(updatedEntry.getName());
            entry.setReporter(updatedEntry.getReporter());
            entry.setTrash(updatedEntry.isTrash());
            entry.setUrl(updatedEntry.getUrl());
            
            entry.setUpdated(OffsetDateTime.now());
            
            drückblickRepository.saveAndFlush(entry);           
            
            return new ResponseEntity<>("", HttpStatus.ACCEPTED);
        }
    
}
