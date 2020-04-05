package de.holarse.backend.db.repositories;

import org.springframework.data.repository.CrudRepository;
import de.holarse.backend.db.DrueckblickEntry;

public interface DrueckblickEntryRepository extends CrudRepository<DrueckblickEntry, Long> {

}