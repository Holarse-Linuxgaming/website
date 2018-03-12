package de.holarse.services;

import de.holarse.backend.db.CommentableNode;
import org.springframework.stereotype.Service;

@Service
public class NodeService {

    /**
     * Erzeugt die Grunddaten für eine neue Node
     * @param <N>
     * @param clazz
     * @return
     * @throws Exception
     */
    public <N extends CommentableNode> N initNode(Class<N> clazz) throws Exception {
        final N node = clazz.newInstance();
        node.setCommentable(Boolean.TRUE);
        node.setArchived(Boolean.FALSE);
        node.setDeleted(Boolean.FALSE);
        node.setDraft(Boolean.FALSE);
        node.setLocked(Boolean.FALSE);
        // Wahrscheinlich später auf false, wenn Vorschau Standard ist.
        node.setPublished(Boolean.TRUE);
        
        return node;
    }
    
}
