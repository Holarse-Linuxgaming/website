/*
 * Copyright (C) 2023 comrad
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.holarse.web.interceptors;

import de.holarse.backend.db.PageVisit;
import de.holarse.backend.db.repositories.PageVisitRepository;
import java.util.Arrays;
import java.util.List;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * Schreibt die HTTP-Requests in die Logging-Tabelle
 * @author comrad
 */
@Component
public class RequestLoggingInterceptor implements HandlerInterceptor {

    private final static transient Logger logger = LoggerFactory.getLogger(RequestLoggingInterceptor.class);
    
    @Autowired
    private PageVisitRepository pageVisitRepo;
    
    final static List<String> IGNORED_URLS = Arrays.asList("/login", "/assets", "/nodes/", "/webapi/", "/api/", "/admin/", 
                                                           "/robots.txt", "/humans.txt", "/age.xml", "/age-de.xml", "/miracle.xml");

    final public static List<String> CAMPAIGN_NAMES = Arrays.asList("pk_campaign", "piwik_campaign", "utm_campaign", "utm_source", "utm_medium");
    final public static List<String> CAMPAIGN_KEYWORDS = Arrays.asList("pk_kwd", "piwik_kwd", "pk_keyword", "utm_term");    
    
    @Override
    public void afterCompletion(final HttpServletRequest request, final HttpServletResponse response, final Object handler, final Exception ex) throws Exception {
        final String requestURI = request.getRequestURI();
        // Ignorierte URLs nun ja... ignorieren
        if (IGNORED_URLS.stream().anyMatch(i -> requestURI.startsWith(i))) {
            return;
        }
        
        // Nur GETs protokollieren
        if (!request.getMethod().equalsIgnoreCase("GET")) { 
            return; 
        }        
        
        final PageVisit pageVisit = new PageVisit();
        pageVisit.setUrl(StringUtils.substring(requestURI, 0, 2083));
        pageVisit.setHttpStatus(response.getStatus());
        pageVisit.setUserAgent(StringUtils.substring(request.getHeader("User-Agent"), 0, 255));
        pageVisit.setIpAddress(request.getRemoteAddr());
        pageVisit.setReferer(StringUtils.substring(request.getHeader("referer"), 0, 255));
        pageVisit.setVisitorId(request.getRequestedSessionId());
        pageVisit.setSearchWord(StringUtils.substring(request.getParameter("q"), 0, 255));
        
        if (request.getParameterNames().hasMoreElements()) {
            pageVisit.setCampaginName(extractCampaignName(request));
            pageVisit.setCampaignKeyWord(extractCampaignKeyword(request));
        }        
        
        logger.debug("filter before save: {0}", request.getQueryString());
        pageVisitRepo.save(pageVisit);
    }
    
    protected String extractCampaignName(final HttpServletRequest req) {
        for (final String campaignName : CAMPAIGN_NAMES) {
            final String name = req.getParameter(campaignName);
            if (name != null) {
                return StringUtils.substring(name, 0, 255);
            }
        }
        return null;
    }
    
    protected String extractCampaignKeyword(final HttpServletRequest req) {
        for (final String campaignKeyword : CAMPAIGN_KEYWORDS) {
            final String keyword = req.getParameter(campaignKeyword);
            if (keyword != null) {
                return StringUtils.substring(keyword, 0, 255);
            }
        }
        return null;
    }    
}
