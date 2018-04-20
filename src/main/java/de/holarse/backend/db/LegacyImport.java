/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.holarse.backend.db;

/**
 *
 * @author comrad
 */
public interface LegacyImport {
    
    void setOldId(Long oldId);
    Long getOldId();
    
}
