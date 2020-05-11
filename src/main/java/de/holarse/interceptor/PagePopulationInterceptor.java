package de.holarse.interceptor;

import de.holarse.auth.web.HolarsePrincipal;
import de.holarse.backend.db.User;
import de.holarse.backend.db.repositories.UserStatRepository;
import de.holarse.backend.views.PageTitleView;
import de.holarse.backend.views.View;
import de.holarse.services.MenuService;
import de.holarse.services.TrafficService;

import java.time.OffsetDateTime;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class PagePopulationInterceptor extends HandlerInterceptorAdapter {

    Logger logger = LoggerFactory.getLogger(PagePopulationInterceptor.class);
    
    //@Autowired
    //private UserRepository userRepository;

    @Autowired
    private UserStatRepository userStatsRepository;
    
    @Autowired
    private TrafficService trafficService;  
    
    //@Autowired
    //private TagRepository tagRepository;
    
    @Autowired
    private MenuService menuService;
    
    @Value("${git.commit.id.describe}")
    private String commitIdDescribe;
    
    @Value("${git.commit.id}")
    private String commitId;    
    
    protected User getCurrentUser() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication != null) {
            if (authentication instanceof AnonymousAuthenticationToken) {
                // Gast
            } else {
                final HolarsePrincipal hp = (HolarsePrincipal) authentication.getPrincipal();
                logger.debug("User: " + hp.getUsername() + ", Roles: " + hp.getAuthorities());
                return hp.getUser();
            }
        }
        
        return null;
    }

    // @Transactional
    // public void updateLastAction(final User current) {
    //     userStatsRepository.updateLastAction(current, OffsetDateTime.now());
    // }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView mav) {       
        if (mav != null) {
            // Aktuellen Benutzer einfügen
            var currentUser = getCurrentUser();
            mav.getModel().putIfAbsent("currentUser", currentUser);
            // if (currentUser != null) {
            //     updateLastAction(currentUser);
            // }

            // Standardtitel setzen
            if (mav.getModel().containsKey("view") && mav.getModel().get("view") instanceof View) {
                final PageTitleView ptv = (PageTitleView) mav.getModel().get("view");   
                mav.getModel().computeIfAbsent("title", k -> ptv.getPageTitle());
            } else {
                mav.getModel().computeIfAbsent("title", k -> "Eure deutschsprache Linuxspiele-Community");
            }
            
            final Long nodeId = (Long) mav.getModel().getOrDefault("nodeId", 0L);
            trafficService.saveRequest(request, response, nodeId);
            
            // Menü erstellen
            mav.getModel().putIfAbsent("menuitems", menuService.buildMainMenu());
        }
    }
   
}
