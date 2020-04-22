package de.holarse.backend.db.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import de.holarse.backend.db.Drueckblick;

public interface DrueckblickRepository extends JpaRepository<Drueckblick, Long> {

}