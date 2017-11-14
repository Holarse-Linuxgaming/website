/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.holarse.backend.xml;

import de.holarse.backend.xml.UserLoader;
import de.holarse.entity.User;
import java.io.File;
import javax.xml.bind.JAXBException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author comrad
 */
public class UserLoaderTest {

    private UserLoader userLoader;

    @Before
    public void setUp() {
        userLoader = new UserLoader();
    }

    @Test
    public void loadUserFile() {
        File f = new File("src/test/resources/users/1.xml");
        try {
            User u = userLoader.load(f);
            assertEquals("comrad", u.getLogin());
        } catch (Exception e) {
            fail("No exception should be thrown, but " + e.getMessage());
        }
    }

}
