/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.holarse.backend.db.repositories;

import de.holarse.backend.db.Tag;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author comrad
 */
public interface TagRepository extends CrudRepository<Tag, Long> {
    
    Optional<Tag> findByName(String name);
    
}
