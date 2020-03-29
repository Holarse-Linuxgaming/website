package de.holarse.services;

import de.holarse.backend.db.PageVisit;
import de.holarse.backend.db.repositories.PageVisitRepository;
import de.holarse.backend.views.PageVisitResult;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrafficService {
    
    @Autowired
    PageVisitRepository pageVisitRepository;
   
    final static List<String> CAMPAIGN_NAMES = Arrays.asList("pk_campaign", "piwik_campaign", "utm_campaign", "utm_source", "utm_medium");
    final static List<String> CAMPAIGN_KEYWORDS = Arrays.asList("pk_kwd", "piwik_kwd", "pk_keyword", "utm_term");
    
    final static List<String> IGNORED_URLS = Arrays.asList("/login", "/assets", "/nodes/", "/webapi/", "/api/", "/admin/", "/robots.txt", "/humans.txt", "/age.xml", "/age-de.xml", "/miracle.xml");
    
    public void saveRequest(final HttpServletRequest request, final HttpServletResponse response) {
        saveRequest(request, response, null);
    }
    
    public void saveRequest(final HttpServletRequest request, final HttpServletResponse response, final Long nodeId) {
        // Nur GETs protokollieren
        if (!request.getMethod().equalsIgnoreCase("GET")) { return; }
        
        // Ignorierte URLs nicht protokollieren
        if (IGNORED_URLS.stream().anyMatch(u -> request.getRequestURI().startsWith(u))) { return; }
       
        final PageVisit page = new PageVisit();
        page.setNodeId(nodeId);
        page.setAccessed(OffsetDateTime.now());
        page.setIpaddress(request.getRemoteAddr());
        page.setUrl(        StringUtils.substring(request.getRequestURI(),          0, 2083));
        page.setUserAgent(  StringUtils.substring(request.getHeader("User-Agent"),  0, 255));
        page.setReferer(    StringUtils.substring(request.getHeader("referer"),     0, 2083));
        page.setVisitorId(request.getRequestedSessionId());
        page.setSearchword( StringUtils.substring(request.getParameter("term"),     0, 255));
        
        if (request.getParameterNames().hasMoreElements()) {
            page.setCampaignName(extractCampaignName(request));
            page.setCampaignKeyword(extractCampaignKeyword(request));
        }
        
        page.setHttpStatus(response.getStatus());
        
        pageVisitRepository.save(page);
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
    
    /**
     * Ermittelt die Seitenbesuche innerhalb eines Zeitraums
     * @param nodeId
     * @param fromDate
     * @param untilDate
     * @return 
     */
    public List<PageVisitResult> getPageVisits(final Long nodeId, final Date fromDate, final Date untilDate) {
        LocalDateTime start, end;
        start = LocalDateTime.ofInstant(fromDate.toInstant(), ZoneId.systemDefault());
        
        // Entweder gesetzt oder jetzt
        end = untilDate == null ? LocalDateTime.now() : LocalDateTime.ofInstant(untilDate.toInstant(), ZoneId.systemDefault());
            
        return pageVisitRepository.getNodeVists(nodeId, start, end);        
    }
    
}
