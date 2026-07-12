/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.holarse.tools.holarseexport;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author comrad
 */
public final class Configuration {

        public static final String CONFIG_FILE = "export.properties";
        
        private static Properties prop = null;

        public static void init(final String configFile) throws IOException {
                prop = load(configFile);
        }

        private static Properties load(final String configFile) throws IOException {
                final Properties p = new Properties();
                p.load(new FileReader(configFile));
                return p;
        }

        public static String get(final String key) {
                return prop.getProperty(key);
        }

        public static String get(final String key,  final String defaultValue) {
                return prop.getProperty(key, defaultValue);
        }

}
