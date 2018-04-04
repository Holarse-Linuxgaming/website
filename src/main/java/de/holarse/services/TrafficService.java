package de.holarse.services;

import de.holarse.backend.db.PageVisit;
import de.holarse.backend.db.repositories.PageVisitRepository;
import java.time.OffsetDateTime;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class TrafficService {
    
    @Autowired
    PageVisitRepository pageVisitRepository;
    
    final String[] campaignNames = new String[] {"pk_campaign", "piwik_campaign", "utm_campaign", "utm_source", "utm_medium"};
    final String[] campaignKeywords = new String[] {"pk_kwd", "piwik_kwd", "pk_keyword", "utm_term"};
    
    @Async
    public void saveRequest(final HttpServletRequest request) {
        saveRequest(request, null, null);
    }
    
    @Async
    public void saveRequest(final HttpServletRequest request, final Long nodeId) {
        saveRequest(request, nodeId, null);
    }    
    
    @Async
    public void saveSearchRequest(final HttpServletRequest request, final String search) {
        saveRequest(request, null, search);
    }    
    
    @Async
    public void saveRequest(final HttpServletRequest request, final Long nodeId, final String search) {
        final PageVisit page = new PageVisit();
        page.setCreated(OffsetDateTime.now());
        page.setNodeId(nodeId);
        page.setIpaddress(request.getRemoteAddr());
        page.setUrl(request.getRequestURI());
        page.setUserAgent(request.getHeader("User-Agent"));
        page.setReferer(request.getHeader("referer"));
        page.setVisitorId(request.getRequestedSessionId());
        page.setSearchword(search);
        
        if (request.getParameterNames().hasMoreElements()) {
            page.setCampaignName(extractCampaignName(request));
            page.setCampaignKeyword(extractCampaignKeyword(request));
        }
        
        pageVisitRepository.save(page);
    }
    
    protected String extractCampaignName(final HttpServletRequest req) {
        for (final String campaignName : campaignNames) {
            final String name = req.getParameter(campaignName);
            if (name != null) {
                return name;
            }
        }
        return null;
    }
    
    protected String extractCampaignKeyword(final HttpServletRequest req) {
        for (final String campaignKeyword : campaignKeywords) {
            final String keyword = req.getParameter(campaignKeyword);
            if (keyword != null) {
                return keyword;
            }
        }
        return null;
    }
    
}
