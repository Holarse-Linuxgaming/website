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
package de.holarse.api.admin;

import de.holarse.auth.web.HolarsePrincipal;
import de.holarse.backend.db.DrückblickEntry;
import de.holarse.backend.db.EntityWriteLock;
import de.holarse.backend.db.repositories.DrückblickRepository;
import de.holarse.backend.db.repositories.EntityWriteLockRepository;
import de.holarse.backend.types.NodeType;
import de.holarse.web.services.EntityLockService;
import java.util.List;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author comrad
 */
@Secured({"ROLE_API_ADMIN", "ROLE_ADMIN"})
@RestController(value = "AdminDrückblick")
@RequestMapping(value = "/admin/api/drueckblick/")
public class Drückblick {
    
    private final static transient Logger log = LoggerFactory.getLogger(de.holarse.api.admin.Drückblick.class);    
    
    @Autowired
    DrückblickRepository dblRepo;    
    
    @Autowired
    EntityLockService elw;
    
    @Autowired
    EntityWriteLockRepository elwRepo;
    
    @PutMapping("mark_dirty")
    public ResponseEntity<String> markDirty(@RequestParam("id") final int id, final Authentication authentication) throws Exception {
        final DrückblickEntry dbl = dblRepo.findById(id).orElseThrow(EntityNotFoundException::new);
        
        elw.lock(dbl, ((HolarsePrincipal) authentication.getPrincipal()).getUser());
        
        return new ResponseEntity<>("", HttpStatus.ACCEPTED);
    }
    
    @GetMapping(value = "get_dirty_marks", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<EntityWriteLock> getDirtyMarks() {
        return elwRepo.findAllByType(NodeType.drückblick);
    }
    
}
