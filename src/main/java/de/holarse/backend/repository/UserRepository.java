/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.holarse.backend.repository;

import de.holarse.view.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author comrad
 */
public interface UserRepository extends JpaRepository<User, Long> {
    
    User findByLogin(String login);
    
}