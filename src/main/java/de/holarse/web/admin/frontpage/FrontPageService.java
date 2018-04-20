package de.holarse.web.admin.frontpage;

import de.holarse.backend.db.FrontPageItem;
import de.holarse.backend.db.Frontpagable;
import de.holarse.backend.db.repositories.FrontPageRepository;
import java.time.OffsetDateTime;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FrontPageService {

    @Autowired FrontPageRepository frontPageRepository;
    
    public FrontPageItem autoDiscoverFrontPageItem(final Frontpagable frontpagableItem) {
        FrontPageItem fpi = getOrUpdate(frontpagableItem.getNodeId());
        return buildFrontPageItem(fpi, frontpagableItem);
    }
    
    /**
     * Überträgt die Daten in ein das FrontPageItem
     * @param item
     * @param frontpagableItem
     * @return 
     */
    public FrontPageItem buildFrontPageItem(final FrontPageItem item, final Frontpagable frontpagableItem) {
        item.setNodeId(frontpagableItem.getNodeId());
        item.setNodeType(frontpagableItem.getNodeType());
        item.setTitle(frontpagableItem.getTitle());
        item.setTeaser(frontpagableItem.getTeaser());
        item.setUrl(frontpagableItem.getUrl());
        
        if (item.getId() != null) {
            item.setUpdated(OffsetDateTime.now());
        } else {
            item.setCreated(OffsetDateTime.now());
        }
        
        // Standard-Cooldown von 2 Tagen
        item.setCooldownUntil(OffsetDateTime.now().plusDays(2));        
        
        return item;
    }
    
    public void save(final FrontPageItem fpi) {
        frontPageRepository.save(fpi);
    }
    
    /**
     * Hole ein FrontPageItem, dass zu einer Node gehört. Ist der Cooldown bereits abgelaufen, können
     * wir ein neues FrontPageItem erzeugen und reposten.
     * @param nodeId
     * @return 
     */
    public FrontPageItem getOrUpdate(final Long nodeId) {
        final Optional<FrontPageItem> item = frontPageRepository.findFirstByNodeIdOrderByCreatedDesc(nodeId);
        
        // Ist der Cooldown schon vorbei oder nicht gesetzt (geht nie zuende), dann können wir ein neues posten
        return item.filter(f -> f.getCooldownUntil() == null || OffsetDateTime.now().isBefore(f.getCooldownUntil())).orElseGet(FrontPageItem::new);        
    }
    
}
