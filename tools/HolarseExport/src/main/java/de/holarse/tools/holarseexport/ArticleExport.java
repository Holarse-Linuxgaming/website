package de.holarse.tools.holarseexport;

import de.holarse.backend.export.Article;
import de.holarse.backend.export.Attachment;
import de.holarse.backend.export.Content;
import de.holarse.backend.export.Revision;
import de.holarse.backend.export.State;
import de.holarse.backend.export.Tag;
import de.holarse.backend.export.Title;
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

public class ArticleExport implements Export {
    
    final static Logger log = Logger.getLogger(ArticleExport.class.getName());      
    
    final static String ARTICLES_QUERY =  "select n.nid, n.title, from_unixtime(n.created) as created, from_unixtime(n.changed) as changed, r.title, r.body, r.log, r.vid, n.status, n.comment, u.name "
    + "from node n "
    + "inner join node_revisions r on r.vid = n.vid "
    + "inner join users u on u.uid = r.uid "
    + "where n.type = 'page'";    
    
    final static String TAG_QUERY = "select name, tid from term_data where tid in (select tid from term_node where nid = ? and vid = ?)";
    final static String LINK_QUERY = "select field_homepage_value, delta from content_field_homepage where nid = ? and vid = ?";
    final static String WINE_QUERY = "select field_winehq_value, field_protondb_value, field_official_proton_value, field_crossoverdb_value from content_type_page where nid = ? and vid = ?";
    final static String STATE_QUERY = "select field_ftpavail_value, field_ftpavail_tools_value, field_release_value from content_type_page where nid = ? and vid = ?";
    final static String SHOP_QUERY = "select field_steam_value, field_humblestore_value, field_gog_value, field_ownshop_value, field_itchio_value from content_type_page where nid = ? and vid = ?";
    final static String VIDEO_QUERY = "select field_videos_value from content_field_videos where nid = ? and vid = ?";
    final static String IMAGE_QUERY = "select filepath from content_field_screenshots cfs inner join files f on cfs.field_screenshots_fid = f.fid where nid = ? and vid = ? order by delta";
    final static String FILE_QUERY = "select filepath from content_field_attachments cfs inner join files f on cfs.field_attachments_fid = f.fid where nid = ? and vid = ? order by delta";
    
    @Override
    public void export(Connection c) throws Exception {
        long start = System.currentTimeMillis();

        final List<Article> articles = new ArrayList<>(10000);
        // Initial

        final PreparedStatement p = c.prepareStatement(ARTICLES_QUERY);
        int count = 0;
        try (final ResultSet result = p.executeQuery()) {
            while (result.next()) {
                log.log(Level.FINE, "{0}) Nid: {1}, title: {2}", new Object[]{count++, result.getLong("nid"), result.getString("title")});

                if (StringUtils.isBlank(result.getString("body"))) {
                    log.log(Level.WARNING, "Article {0} is empty. Skipping.", new Object[] { result.getLong("nid")});
                    continue;
                }                
                
                Article article = new Article();
                article.setUid(result.getLong("nid"));
                               
                article.setCreated(new Date(result.getTimestamp("created").getTime()));
                
                log.finest("Database date: " + result.getString("created") + " => " + article.getCreated());                
                article.setVid(result.getLong("vid")); // Transient

                // Revision
                Revision revision = new Revision();
                revision.setAuthor(result.getString("name"));
                revision.setChangelog(result.getString("comment"));
                final Date changed = result.getTimestamp("changed");
                if (changed != null) {
                    revision.setCreated(new Date(changed.getTime())); // Wann die Revision erzeugt wurde
                }
                article.setRevision(revision);
                
                // Titles
                final Title title = new Title();
                title.setType("MAIN");
                title.setValue(result.getString("title"));
                final List<Title> titles = new ArrayList<>();
                titles.add(title);
                article.setTitles(titles);

                // State
                final State state = new State();
                state.setArchived(false);
                state.setCommentable(result.getLong("comment") == 2);
                state.setDeleted(false);
                state.setDraft(false);
                state.setLocked(false);
                state.setPublished(result.getLong("status") == 1);
                article.setState(state);

                // Content
                final Content content = new Content();
                content.setFormat("WIKI");
                content.setValue(result.getString("body"));
                article.setContent(content);
                
                articles.add(article);
            }
        }

        final PreparedStatement tagPs = c.prepareStatement(TAG_QUERY);
        final PreparedStatement linkPs = c.prepareStatement(LINK_QUERY);
        final PreparedStatement winePs = c.prepareStatement(WINE_QUERY);
        final PreparedStatement shopPs = c.prepareStatement(SHOP_QUERY);             
        final PreparedStatement statePs = c.prepareStatement(STATE_QUERY);        
        final PreparedStatement videoPs = c.prepareStatement(VIDEO_QUERY);         
        final PreparedStatement imagePs = c.prepareStatement(IMAGE_QUERY);                 
        final PreparedStatement filePs = c.prepareStatement(FILE_QUERY);                
        

        // Iterate and extend
        for (Article article : articles) {
            log.log(Level.FINE, "Extending article nid {0}", article.getUid());
            // Tags
            tagPs.setLong(1, article.getUid());
            tagPs.setLong(2, article.getVid());
            final List<Tag> tags = new ArrayList<>();
            try (final ResultSet result = tagPs.executeQuery()) {
                while (result.next()) {
                    final Tag tag = new Tag();
                    tag.setValue(result.getString("name"));
                    tags.add(tag);
                }
            }
            article.setTags(tags);

            // Attachments
            final List<Attachment> attachments = new ArrayList<>();

            // Links
            linkPs.setLong(1, article.getUid());
            linkPs.setLong(2, article.getVid());            
            try (final ResultSet result = linkPs.executeQuery()) {
                while (result.next()) {
                    log.log(Level.INFO, "att: {0} (sql: {1}, article: {2})", new Object[] { "", linkPs, article.getUid()});
                    
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

            // Wine and stuff
            winePs.setLong(1, article.getUid());
            winePs.setLong(2, article.getVid());
            try (final ResultSet result = winePs.executeQuery()) {
                while (result.next()) {
                    // WineHQ
                    final String wineHq = result.getString("field_winehq_value");
                    if (StringUtils.isNotBlank(wineHq)) {
                        final Attachment att = new Attachment();
                        att.setType("WINEHQ");
                        att.setContent(wineHq);
                        att.setGroup("WINE");
                        attachments.add(att);
                    } 
                    
                    // Protondb
                    final String protonDb = result.getString("field_protondb_value");
                    if (StringUtils.isNotBlank(protonDb)) {
                        final Attachment att = new Attachment();
                        att.setType("PROTONDB");
                        att.setContent(protonDb);
                        att.setGroup("WINE");
                        attachments.add(att);
                    }       
                    
                    // Proton Official
                    final Boolean protonOfficial = result.getBoolean("field_official_proton_value");
                    final Attachment att = new Attachment();
                    att.setType("PROTONOFFICIAL");
                    att.setContent(protonOfficial.toString());
                    att.setGroup("WINE");
                    attachments.add(att);
                    
                    // CrossoverDB
                    final String crossoverDb = result.getString("field_crossoverdb_value");
                    if (StringUtils.isNotBlank(crossoverDb)) {
                        final Attachment att2 = new Attachment();
                        att2.setType("CROSSOVERDB");
                        att2.setContent(crossoverDb);
                        att2.setGroup("WINE");
                        
                        attachments.add(att2);
                    }       
                }
            }
            
            // SHOPS
            shopPs.setLong(1, article.getUid());
            shopPs.setLong(2, article.getVid());
            try (final ResultSet result = shopPs.executeQuery()) {
                while (result.next()) {
                    final String steamUrl = result.getString("field_steam_value");
                    if (StringUtils.isNotBlank(steamUrl)) {
                        final Attachment att = new Attachment();
                        att.setType("STEAM");
                        att.setGroup("SHOP");
                        att.setContent(steamUrl);
                        
                        attachments.add(att);                        
                    }
                    
                    final String humbleUrl = result.getString("field_humblestore_value");
                    if (StringUtils.isNotBlank(humbleUrl)) {
                        final Attachment att = new Attachment();
                        att.setType("HUMBLE");
                        att.setGroup("SHOP");
                        att.setContent(humbleUrl);
                        
                        attachments.add(att);                        
                    }                    
                    
                    final String gogUrl = result.getString("field_gog_value");
                    if (StringUtils.isNotBlank(gogUrl)) {
                        final Attachment att = new Attachment();
                        att.setType("GOG");
                        att.setGroup("SHOP");
                        att.setContent(gogUrl);
                        
                        attachments.add(att);                        
                    }
                    
                    final String ownshopUrl = result.getString("field_ownshop_value");
                    if (StringUtils.isNotBlank(ownshopUrl)) {
                        final Attachment att = new Attachment();
                        att.setType("OWNSHOP");
                        att.setGroup("SHOP");
                        att.setContent(ownshopUrl);
                        
                        attachments.add(att);                        
                    }
                    
                    final String itchUrl = result.getString("field_itchio_value");
                    if (StringUtils.isNotBlank(itchUrl)) {
                        final Attachment att = new Attachment();
                        att.setType("ITCH");
                        att.setGroup("SHOP");
                        att.setContent(itchUrl);
                        
                        attachments.add(att);
                    }                    
                }
            }
            
            // VIDEOS
            videoPs.setLong(1, article.getUid());
            videoPs.setLong(2, article.getVid());
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
            imagePs.setLong(1, article.getUid());
            imagePs.setLong(2, article.getVid());
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
            
            // FILES
            filePs.setLong(1, article.getUid());
            filePs.setLong(2, article.getVid());
            try (final ResultSet result = filePs.executeQuery()) {
                while (result.next()) {
                    final String file = result.getString("filepath");
                    if (StringUtils.isNotBlank(file)) {
                        final Attachment att = new Attachment();
                        att.setType("FILE");
                        att.setGroup("FILE");
                        att.setContent(file);
                        
                        attachments.add(att);                        
                    }
                }
            }                
            
            article.setAttachments(attachments);
            
            // FTP / FTPTOOLS / RELEASEDATE
            final State state = article.getState() != null ? article.getState() : new State();
            statePs.setLong(1, article.getUid());
            statePs.setLong(2, article.getVid());
            try (final ResultSet result = statePs.executeQuery()) {
                while (result.next()) {
                    final Boolean ftp = result.getBoolean("field_ftpavail_value");
                    final Boolean ftpTools = result.getBoolean("field_ftpavail_tools_value");
                    final String releaseDate = result.getString("field_release_value");
                    
                    state.setFtp(ftp);
                    state.setFtpTools(ftpTools);
                    state.setReleaseDate(releaseDate);
                }
            }
            article.setState(state);

            

            ExportWriter.writeXml(article, "article", article.getUid());
        }
        
        log.log(Level.INFO, "Complete after {0} ms.", System.currentTimeMillis() - start);
    }
    

}
