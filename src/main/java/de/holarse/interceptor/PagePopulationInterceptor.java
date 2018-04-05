/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.holarse.interceptor;

import de.holarse.backend.db.User;
import de.holarse.backend.db.repositories.UserRepository;
import de.holarse.services.TrafficService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class PagePopulationInterceptor extends HandlerInterceptorAdapter {

    Logger logger = LoggerFactory.getLogger(PagePopulationInterceptor.class);
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private TrafficService trafficService;
    
    protected User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication != null) {
            logger.debug("Roles: " + authentication.getAuthorities());
         
            if (!(authentication instanceof AnonymousAuthenticationToken)) {
                String currentUserName = authentication.getName();
                return userRepository.findByLogin(currentUserName);
            }
        }
        
        return null;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView mav) {
        trafficService.saveRequest(request, response);
        
        if (mav != null) {
            mav.addObject("currentUser", getCurrentUser());
        }
    }
    
}
