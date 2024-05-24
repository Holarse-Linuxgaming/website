package de.holarse.exceptions;

import de.holarse.backend.db.EntityWriteLock;
import de.holarse.backend.db.LockableEntity;

public class EntityLockedException extends RuntimeException {

    public EntityLockedException(final LockableEntity entity, EntityWriteLock lock) {

    }

}
