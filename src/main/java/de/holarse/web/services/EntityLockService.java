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
package de.holarse.web.services;

import de.holarse.backend.db.EntityWriteLock;
import de.holarse.backend.db.LockableEntity;
import de.holarse.backend.db.User;
import de.holarse.backend.db.repositories.EntityWriteLockRepository;
import java.time.OffsetDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Role;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

/**
 *
 * @author comrad
 */
@Service
public class EntityLockService {
    
    @Autowired
    EntityWriteLockRepository ewlRepo;
    
    public void lock(final LockableEntity lockable, final User lockingUser) {
        EntityWriteLock ewl = new EntityWriteLock();
        ewl.setEntity(lockable.getNodeType());
        ewl.setRowId(lockable.getId());
        ewl.setWriteLockUpdated(OffsetDateTime.now());
        ewl.setWriteLockUser(lockingUser);
        
        ewlRepo.save(ewl);
    }
    
    public void unlock(final LockableEntity lockable, final User lockingUser) {
        ewlRepo.unlock(lockable.getId(), lockable.getNodeType(), lockingUser.getId());
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    public void unlockAll(final LockableEntity lockable, final User lockingUser) {
        ewlRepo.unlockAll(lockable.getId(), lockable.getNodeType());
    }    
    
    public boolean isLocked(final LockableEntity lockable) {
        return ewlRepo.existsLock(lockable.getId(), lockable.getNodeType());
    }
    
}
