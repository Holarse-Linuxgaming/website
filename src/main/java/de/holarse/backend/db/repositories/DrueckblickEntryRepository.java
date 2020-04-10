package de.holarse.backend.db.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import de.holarse.backend.db.DrueckblickEntry;

public interface DrueckblickEntryRepository extends CrudRepository<DrueckblickEntry, Long> {

    @Query("from DrueckblickEntry de where de.drueckblick is null order by id asc")
    public List<DrueckblickEntry> findAllNew();

}