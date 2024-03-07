package de.holarse.web.controller;

import de.holarse.backend.db.Article;
import de.holarse.backend.db.ArticleRevision;
import de.holarse.backend.db.NodeSlug;
import de.holarse.backend.db.repositories.ArticleRepository;
import de.holarse.backend.db.repositories.ArticleRevisionRepository;
import de.holarse.backend.db.repositories.NodeSlugRepository;
import de.holarse.backend.types.NodeType;
import de.holarse.backend.view.ArticleView;
import de.holarse.web.defines.WebDefines;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import static de.holarse.web.defines.WebDefines.REVISION_DEFAULT_PAGE_SIZE;

@Controller
public class RevisionController {

    private final static transient Logger logger = LoggerFactory.getLogger(RevisionController.class);

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ArticleRevisionRepository articleRevisionRepository;

    @Autowired
    private NodeSlugRepository nodeSlugRepository;

    @GetMapping("/wiki/{nodeId}/revisions")
    public ModelAndView revisions(@PathVariable final Integer nodeId, @PageableDefault(sort={"revision"}, value=REVISION_DEFAULT_PAGE_SIZE, direction = Sort.Direction.DESC) final Pageable pageable, final ModelAndView mv) {
        mv.setViewName("layouts/bare");
        mv.addObject("title", "Die Linuxspiele-Seite für Linuxspieler");
        mv.addObject(WebDefines.DEFAULT_VIEW_ATTRIBUTE_NAME, "sites/revisions/index");

        final Article currentArticle = articleRepository.findByNodeId(nodeId).orElseThrow(EntityNotFoundException::new);
        final Page<ArticleRevision> revisions = articleRevisionRepository.findHistory(nodeId, pageable);
        final NodeSlug mainSlug = nodeSlugRepository.findMainSlug(nodeId, NodeType.article).orElseThrow(EntityNotFoundException::new);

        mv.addObject("view", ArticleView.of(currentArticle.getArticleRevision(), mainSlug));
        mv.addObject("revisions", articleRevisionRepository.findHistory(nodeId, pageable));

        return mv;
    }

    @GetMapping("/wiki/{nodeId}/revisions/{revision}/view")
    public ModelAndView show(@PathVariable final Integer nodeId, @PathVariable final Integer revision, final ModelAndView mv) {
        mv.setViewName("layouts/bare");
        mv.addObject("title", "Die Linuxspiele-Seite für Linuxspieler");
        mv.addObject(WebDefines.DEFAULT_VIEW_ATTRIBUTE_NAME, "sites/revisions/show");



        return mv;
    }

}
