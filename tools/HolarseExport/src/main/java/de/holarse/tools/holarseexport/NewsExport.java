package de.holarse.tools.holarseexport;

import de.holarse.backend.export.Attachment;
import de.holarse.backend.export.Content;
import de.holarse.backend.export.News;
import de.holarse.backend.export.Revision;
import de.holarse.backend.export.State;
import de.holarse.tools.helper.AttachmentHelper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.StringUtils;

public class NewsExport implements Export {
    
    final static Logger log = Logger.getLogger(NewsExport.class.getName());      
    
    final static String NEWS_QUERY =  "select n.nid, n.title, n.type, from_unixtime(n.created) as created, from_unixtime(n.changed) as changed, r.title, r.body, r.log, r.vid, n.status, n.comment, u.name "
    + "from node n "
    + "inner join node_revisions r on r.vid = n.vid "
    + "inner join users u on u.uid = r.uid "
    + "where n.type in ('story', 'shortnews')";    
    
    final static String TAG_QUERY = "select name, tid from term_data where tid in (select tid from term_node where nid = ? and vid = ?)";
    final static String LINK_QUERY = "select field_homepage_value, delta from content_field_homepage where nid = ? and vid = ?";
    final static String VIDEO_QUERY = "select field_videos_value from content_field_videos where nid = ? and vid = ?";
    final static String IMAGE_QUERY = "select filepath from content_field_screenshots cfs inner join files f on cfs.field_screenshots_fid = f.fid where nid = ? and vid = ? order by delta";
    
    @Override
    public void export(Connection c) throws Exception {
        long start = System.currentTimeMillis();

        final List<News> newses = new ArrayList<>(10000);
        // Initial

        final PreparedStatement p = c.prepareStatement(NEWS_QUERY);
        int count = 0;
        try (final ResultSet result = p.executeQuery()) {
            while (result.next()) {
                log.log(Level.FINE, "{0}) Nid: {1}, title: {2}", new Object[]{count++, result.getLong("nid"), result.getString("title")});

                if (StringUtils.isBlank(result.getString("body"))) {
                    log.log(Level.WARNING, "Article {0} is empty. Skipping.", new Object[] { result.getLong("nid")});
                    continue;
                }                
                
                News news = new News();
                news.setUid(result.getLong("nid"));
                               
                news.setCreated(new Date(result.getTimestamp("created").getTime()));
                
                log.log(Level.FINEST, "Database date: {0} => {1}", new Object[]{result.getString("created"), news.getCreated()});                
                news.setVid(result.getLong("vid")); // Transient

                // Revision
                Revision revision = new Revision();
                revision.setAuthor(result.getString("name"));
                revision.setChangelog(result.getString("comment"));
                final Date changed = result.getTimestamp("changed");
                if (changed != null) {
                    revision.setCreated(new Date(changed.getTime())); // Wann die Revision erzeugt wurde
                }
                news.setRevision(revision);
                
                // Titles
                news.setTitle(result.getString("title"));

                news.setCategory("DEFAULT");
                
                // Newstype (Normale, oder Kurznews)
                switch(result.getString("type")) {
                    case "story": 
                        news.setNewsType("REPORT");
                        break;
                    case "shortnews":
                        news.setNewsType("SHORT");
                        break;
                    default:
                        throw new IllegalArgumentException("Unbekannter Node-Type " + result.getString("type"));
                }
                
                // State
                final State state = new State();
                state.setArchived(false);
                state.setCommentable(result.getLong("comment") == 2);
                state.setDeleted(false);
                state.setLocked(false);
                state.setPublished(result.getLong("status") == 1);
                news.setState(state);

                // Content
                final Content content = new Content();
                content.setFormat("WIKI");
                content.setValue(result.getString("body"));
                news.setContent(content);
                
                newses.add(news);
            }
        }

        final PreparedStatement tagPs = c.prepareStatement(TAG_QUERY);
        final PreparedStatement linkPs = c.prepareStatement(LINK_QUERY);
        final PreparedStatement videoPs = c.prepareStatement(VIDEO_QUERY);         
        final PreparedStatement imagePs = c.prepareStatement(IMAGE_QUERY);                 
        

        // Iterate and extend
        for (News news : newses) {
            log.log(Level.FINE, "Extending news nid {0}", news.getUid());
            // Attachments
            final List<Attachment> attachments = new ArrayList<>();

            // Links
            linkPs.setLong(1, news.getUid());
            linkPs.setLong(2, news.getVid());            
            try (final ResultSet result = linkPs.executeQuery()) {
                while (result.next()) {
                    log.log(Level.INFO, "att: {0} (sql: {1}, news: {2})", new Object[] { "", linkPs, news.getUid()});
                    
                    final String field = result.getString("field_homepage_value");
                    if (StringUtils.isBlank(field)) { continue; }
                    
                    final Attachment att = new Attachment();                    
                    att.setType("LINK");
                    att.setPrio(result.getLong("delta"));
                    att.setGroup("WEBSITE");
                    try {
                        AttachmentHelper.extractInto(field, att);
                    } catch (IllegalArgumentException iae) {
                        continue;
                    }
                    attachments.add(att);
                }
            }
            
            // VIDEOS
            videoPs.setLong(1, news.getUid());
            videoPs.setLong(2, news.getVid());
            try (final ResultSet result = videoPs.executeQuery()) {
                while (result.next()) {
                    final String video = result.getString("field_videos_value");
                    if (StringUtils.isNotBlank(video)) {
                        final Attachment att = new Attachment();
                        att.setType("YOUTUBE");
                        att.setGroup("VIDEO");
                        att.setContent(video);
                        
                        attachments.add(att);                        
                    }
                }
            }      
            
            // IMAGES
            imagePs.setLong(1, news.getUid());
            imagePs.setLong(2, news.getVid());
            try (final ResultSet result = imagePs.executeQuery()) {
                long prio = 1;
                while (result.next()) {
                    final String image = result.getString("filepath");
                    if (StringUtils.isNotBlank(image)) {
                        final Attachment att = new Attachment();
                        att.setType("SCREENSHOT");
                        att.setGroup("IMAGE");
                        att.setContent(image);
                        att.setPrio(prio++);
                        
                        attachments.add(att);                        
                    }
                }
            }        
            
            news.setAttachments(attachments);
            
            ExportWriter.writeXml(news, "news", news.getUid());
        }
        
        log.log(Level.INFO, "Complete after {0} ms.", System.currentTimeMillis() - start);
    }
    

}
