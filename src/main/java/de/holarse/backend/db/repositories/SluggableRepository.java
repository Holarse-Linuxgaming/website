package de.holarse.backend.db.repositories;

import java.util.Optional;

public interface SluggableRepository<N> {

    Optional<N> findBySlug(String slug);

}
