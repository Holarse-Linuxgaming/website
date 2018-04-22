package de.holarse.renderer.wiki;

import de.holarse.renderer.Renderer;
import de.holarse.services.NodeService;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("wikiRenderer")
public class WikiRenderer implements Renderer {

    final static Pattern header5Pattern = Pattern.compile("=====\\s*(.+?)\\s*=====");
    final static  Pattern header4Pattern = Pattern.compile("====\\s*(.+?)\\s*====");
    final static  Pattern header3Pattern = Pattern.compile("===\\s*(.+?)\\s*===");
    final static  Pattern header2Pattern = Pattern.compile("==\\s*(.+?)\\s*==");
    
    final static  Pattern internalLinkPattern = Pattern.compile("\\[\\[(.+?)\\]\\]");
    final static  Pattern externalLinkPattern = Pattern.compile("\\[(.+?)\\]");    
    
    final static  Pattern linebreakPattern = Pattern.compile("[\\r\\n]+");        
    
    @Autowired NodeService nodeService;

    @Override
    public String render(final String source) {
        if (StringUtils.isBlank(source)) {
            return "";
        }

        String output = source;

        // Header 5
        final Matcher matcherHeader5 = header5Pattern.matcher(output);
        while (matcherHeader5.find()) {
            output = matcherHeader5.replaceAll("<h5>" + matcherHeader5.group(1).trim() + "</h5>");
        }

        // Header 4        
        final Matcher matcherHeader4 = header4Pattern.matcher(output);
        while (matcherHeader4.find()) {
            output = matcherHeader4.replaceAll("<h4>" + matcherHeader4.group(1).trim() + "</h4>");
        }

        // Header 3        
        final Matcher matcherHeader3 = header3Pattern.matcher(output);
        while (matcherHeader3.find()) {
            output = matcherHeader3.replaceAll("<h3>" + matcherHeader3.group(1).trim() + "</h3>");
        }

        // Header 2
        final Matcher matcherHeader2 = header2Pattern.matcher(output);
        while (matcherHeader2.find()) {
            output = matcherHeader2.replaceAll("<h2>" + matcherHeader2.group(1).trim() + "</h2>");
        }
        
        // Internal Links
        System.out.println("........................................................................... anfang");
        System.out.println(output);
        System.out.println("........................................................................... output");        
        final Matcher matcherInternalLink = internalLinkPattern.matcher(output);
        while (matcherInternalLink.find()) {
            final String name = matcherInternalLink.group(1).trim();
            System.out.println("matcher internal " + name);
            System.out.println("Group: " + matcherInternalLink.group());            
            if (name.contains("|")) {
                // Different Name
                final String[] components = name.split("\\|");
                final String slug = nodeService.slugify(components[0].trim());
                output = matcherInternalLink.replaceAll("<a href=\"/wiki/" + slug + "\">" + components[1].trim() + "</a>");                
            } else {
                // Internal Link
                final String slug = nodeService.slugify(name);
                output = matcherInternalLink.replaceAll("<a href=\"/wiki/" + slug + "\">" + name + "</a>");
            }
        }       
        System.out.println("........................................................................... output folgt");        
        System.out.println(output);
        System.out.println("........................................................................... ende");
        
        // External Links
        final Matcher matcherExternalLink = externalLinkPattern.matcher(output);
        while (matcherExternalLink.find()) {
            final String name = matcherExternalLink.group(1).trim();
            if (name.contains("|")) {
                // Anderer Anzeigename mit Pipe
                final String[] components = name.split("\\|");
                output = matcherExternalLink.replaceAll("<a href=\"" + components[0].trim() + "\">" + components[1].trim() + "</a>");                
            } else if (name.contains(" ")){
                // Anderer Anzeigename mit Leerzeichen                
                final int firstWhitespace = name.indexOf(" ");
                final String link = name.substring(0, firstWhitespace);
                final String label = name.substring(firstWhitespace);
                output = matcherExternalLink.replaceAll("<a href=\"" + link.trim() + "\">" + label.trim() + "</a>");                                
            } else {
                // Internal Link
                output = matcherExternalLink.replaceAll("<a href=\"" + name + "\">" + name + "</a>");
            }
        }   

        // Linebreak
        final Matcher matcherLinebreaks = linebreakPattern.matcher(output);
        while (matcherLinebreaks.find()) {
            output = matcherLinebreaks.replaceAll("<br />");
        }

        return output.trim();
    }

    public NodeService getNodeService() {
        return nodeService;
    }

    public void setNodeService(NodeService nodeService) {
        this.nodeService = nodeService;
    }

}
