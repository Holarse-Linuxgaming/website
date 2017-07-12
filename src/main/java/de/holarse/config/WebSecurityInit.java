/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.holarse.config;

import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.context.*;

public class WebSecurityInit
      extends AbstractSecurityWebApplicationInitializer {

    public WebSecurityInit() {
        super(SecurityConfig.class);
    }
}
