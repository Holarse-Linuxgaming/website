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
import java.util.Optional;

import de.holarse.exceptions.EntityLockedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final static transient Logger logger = LoggerFactory.getLogger(EntityLockService.class);

    @Autowired
    EntityWriteLockRepository ewlRepo;
    
    public void lock(final LockableEntity lockable, final User lockingUser) {
        final EntityWriteLock ewl = new EntityWriteLock();
        ewl.setEntity(lockable.getNodeType());
        ewl.setRowId(lockable.getId());
        ewl.setWriteLockUpdated(OffsetDateTime.now());
        ewl.setWriteLockUser(lockingUser);
        
        ewlRepo.saveAndFlush(ewl);
    }

    public void tryToLock(final LockableEntity lockable, final User lockingUser) throws EntityLockedException {
        logger.debug("Checking for lock on id={} type={}", lockable.getId(), lockable.getNodeType());
        // Check if entity is already locked?
        var lock = getLock(lockable);
        if (lock.isPresent()) {
            throw new EntityLockedException(lockable, lock.get());
        }

        logger.debug("No valid locks on id={} type={}. Purging old locks.", lockable.getId(), lockable.getNodeType());
        ewlRepo.unlockAll(lockable.getId(), lockable.getNodeType());

        logger.debug("Locking id={} type={} for {}", lockable.getId(), lockable.getNodeType(), lockingUser.getLogin());
        lock(lockable, lockingUser);
    }
    
    public void unlock(final LockableEntity lockable, final User lockingUser) {
        ewlRepo.unlock(lockable.getId(), lockable.getNodeType(), lockingUser);
    }

    public Optional<EntityWriteLock> getLock(final LockableEntity lockable) {
        var lockAge = OffsetDateTime.now().minusMinutes(15);
        return ewlRepo.findByLockId(lockable.getId(), lockAge);
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    public void unlockAll(final LockableEntity lockable, final User lockingUser) {
        ewlRepo.unlockAll(lockable.getId(), lockable.getNodeType());
    }    
    
    public boolean isLocked(final LockableEntity lockable) {
        return ewlRepo.existsLock(lockable.getId(), lockable.getNodeType());
    }
    
}
