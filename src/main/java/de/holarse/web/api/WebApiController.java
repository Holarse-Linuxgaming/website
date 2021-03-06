package de.holarse.web.api;

import de.holarse.backend.db.Article;
import de.holarse.backend.db.News;
import de.holarse.backend.db.NodeState;
import de.holarse.backend.db.repositories.ArticleRepository;
import de.holarse.backend.db.repositories.NewsRepository;
import de.holarse.backend.db.repositories.SearchRepository;
import de.holarse.backend.views.PageVisitReport;
import de.holarse.backend.views.PageVisitResult;
import de.holarse.backend.views.SearchAutocompleteView;
import de.holarse.factories.ViewFactory;
import de.holarse.search.TagSuggestion;
import de.holarse.renderer.html.HtmlRenderer;
import de.holarse.services.TrafficService;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/webapi")
public class WebApiController {

    Logger logger = LoggerFactory.getLogger(WebApiController.class);    
    
    @Autowired
    private HtmlRenderer wikiRenderer;
    
    @Autowired
    private TrafficService trafficService;

    @Autowired
    private ArticleRepository articleRepo;

    @Autowired
    private NewsRepository newsRepo;
    
    @Autowired
    private SearchRepository searchRepository;
    
    @Autowired
    ViewFactory viewFactory;    

    @Secured("ROLE_USER")
    @PostMapping("/render/preview")
    public String renderPreview(String content) {
        return wikiRenderer.render(content);
    }

    @GetMapping(value = "/pagevisits", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PageVisitReport> pageVisits(@RequestParam("nodeId") final Long nodeId, 
            @RequestParam(name = "dateFrom", required = false) @DateTimeFormat(pattern="yyyy-MM-dd") final Date dateFrom, 
            @RequestParam(name = "dateUntil", required = false) @DateTimeFormat(pattern="yyyy-MM-dd") final Date dateUntil) {
        return new ResponseEntity<>(trafficService.getPageVisits(nodeId, dateFrom, dateUntil), HttpStatus.OK);
    }
    
    /**
     * Autovervollständigung von Tag-Eingaben
     * @param term
     * @return 
     */
    @GetMapping(value = "/autocomplete/tags")
    public ResponseEntity<List<TagSuggestion>> tagAutocomplete(@RequestParam("term") final String term) {
        return new ResponseEntity<>(searchRepository.suggestTag(term + ":*"), HttpStatus.OK);
    }

    /**
     * Autovervollständigung
     * @param query
     * @return
     */
    @GetMapping("/autocomplete/search.json")
    public ResponseEntity<List<SearchAutocompleteView>> suggestion(@RequestParam("term") final String query)
    {
        return new ResponseEntity<>(searchRepository.search(query + ":*", 0, 15)
                                    .stream().map(viewFactory::fromSearchResult)
                                    .collect(Collectors.toList()), HttpStatus.OK);
    }    

    @Transactional
    @Secured({ "ROLE_MODERATOR", "ROLE_ADMIN" })    
    @GetMapping(value = "/nodestate", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NodeState> nodeState(NodeStateCommand command) {
        NodeState nodeState;
        switch(command.getNodeType()) {
            case ARTICLE:
                // TODO Mit Streaming-API umsetzen
                Article article = articleRepo.findById(command.getNodeId()).orElseThrow(() -> new EntityNotFoundException());
                applyNodeState(article, command);
                articleRepo.save(article);

                nodeState = article;
                break;
            case NEWS:
                // TODO Mit Streaming-API umsetzen
                News news = newsRepo.findById(command.getNodeId()).orElseThrow(() -> new EntityNotFoundException());
                applyNodeState(news, command);
                newsRepo.save(news);   
                
                nodeState = news;
                break;
            default:
                throw new IllegalArgumentException("Nodetype hat keine NodeState-Konfiguration.");
        }

        return new ResponseEntity<>(nodeState, HttpStatus.OK);
    }

    protected void applyNodeState(NodeState oldNodeState, NodeState newNodeState) {
        oldNodeState.setArchived(newNodeState.getArchived());
        oldNodeState.setCommentable(newNodeState.getCommentable());
        oldNodeState.setLocked(newNodeState.getLocked());
        oldNodeState.setPublished(newNodeState.getPublished());
    }

}
