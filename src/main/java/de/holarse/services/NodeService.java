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
    
    private String[] removeWords = new String[]{"a","an","as","at","before","but","by","for","from","is","in","into","like","of","off","on","onto","per","since","than","the","this","that","to","up","via","with"};
    
    public String slugify(final String name) {
        return name.toLowerCase()
                .replaceAll(" of ", " ")
                .replaceAll("^of ", " ")
                .replaceAll(" the ", " ")
                .replaceAll("^the ", " ")
                .replaceAll(" to ", " ")
                .replaceAll("^to ", " ")
                .replaceAll(" this ", " ")
                .replaceAll("^this ", " ")                
                .replaceAll("_", " ")  
                .trim()
                .replaceAll(" ", "_")
                .replaceAll("\\[", "")
                .replaceAll("\\]", "")
                .replaceAll("\\{", "")
                .replaceAll("\\}", "")                
                .replaceAll("-", " ")
                .replaceAll("\\.", "")
                .replaceAll("&", "")                
                .replaceAll("!", "")                               
                .replaceAll(":", "")
                .replaceAll("'", "")
                .replaceAll("\\+", "")
                .trim()
                .replaceAll(" ", "_")                
                .replaceAll("_+", "_");

    }
    
}
