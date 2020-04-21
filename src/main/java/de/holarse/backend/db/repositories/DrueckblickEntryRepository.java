package de.holarse.backend.db.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import de.holarse.backend.db.Drueckblick;
import de.holarse.backend.db.DrueckblickEntry;

public interface DrueckblickEntryRepository extends CrudRepository<DrueckblickEntry, Long> {

    @Query("from DrueckblickEntry de where de.drueckblick is null order by id asc")
    public List<DrueckblickEntry> findAllNew();

    @Modifying
    @Query("update DrueckblickEntry de set de.drueckblick = :dbl where de.drueckblick is null and de.deleted = false")
    public void assignOpenTo(@Param("dbl") final Drueckblick dbl);

}