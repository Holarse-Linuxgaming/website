/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.holarse.backend.db.repositories;

import de.holarse.backend.db.ArticleRevision;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author comrad
 */
public interface ArticleRevisionRepository extends JpaRepository<ArticleRevision, Integer>  {    
}
