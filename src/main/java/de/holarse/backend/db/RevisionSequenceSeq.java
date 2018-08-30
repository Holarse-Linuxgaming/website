package de.holarse.backend.db;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

/**
 * Sequence für Revisionen
 * @author britter
 */
@Entity
@SequenceGenerator(name = "revision_sequence-gen", sequenceName = "revision_sequence", allocationSize = 25)
public class RevisionSequenceSeq implements Serializable {
    
   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "revision_sequence-gen")
   private long id;  
    
}
