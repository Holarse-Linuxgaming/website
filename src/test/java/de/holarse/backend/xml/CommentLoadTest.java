/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.holarse.backend.xml;

import de.holarse.backend.xml.CommentLoader;
import de.holarse.entity.importer.Comment;
import java.io.File;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author comrad
 */
public class CommentLoadTest {

    private CommentLoader commentLoader;

    @Before
    public void setUp() {
        commentLoader = new CommentLoader();
    }

    @Test
    public void loadUserFile() {
        File f = new File("src/test/resources/comments/1.xml");
        try {
            Comment c = commentLoader.load(f);
            assertEquals("comrad", c.getAuthor());
        } catch (Exception e) {
            fail("No exception should be thrown, but " + e.getMessage());
        }
    }

}
