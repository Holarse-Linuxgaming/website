package de.holarse.web.controller;

import com.github.difflib.DiffUtils;
import com.github.difflib.text.DiffRow;
import com.github.difflib.text.DiffRowGenerator;
import de.holarse.backend.db.Article;
import de.holarse.backend.db.ArticleRevision;
import de.holarse.backend.db.NodeSlug;
import de.holarse.backend.db.repositories.ArticleRepository;
import de.holarse.backend.db.repositories.ArticleRevisionRepository;
import de.holarse.backend.db.repositories.NodeSlugRepository;
import de.holarse.backend.types.NodeType;
import de.holarse.backend.view.ArticleView;
import de.holarse.web.defines.WebDefines;
import de.holarse.web.services.ArticleService;
import jakarta.persistence.EntityNotFoundException;
import org.apache.commons.lang3.StringUtils;
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
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class RevisionController {

    private final static transient Logger logger = LoggerFactory.getLogger(RevisionController.class);

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ArticleRevisionRepository articleRevisionRepository;

    @Autowired
    private ArticleService articleService;

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

        mv.addObject("view", ArticleView.of(currentArticle.getNodeRevision(), mainSlug));
        mv.addObject("revisions", articleRevisionRepository.findHistory(nodeId, pageable));



        return mv;
    }

    @GetMapping("/wiki/{nodeId}/revisions/{revision}/view")
    public ModelAndView show(@PathVariable final Integer nodeId, @PathVariable final Integer revision, final ModelAndView mv) {
        mv.setViewName("layouts/bare");
        mv.addObject("title", "Die Linuxspiele-Seite für Linuxspieler");
        mv.addObject(WebDefines.DEFAULT_VIEW_ATTRIBUTE_NAME, "sites/revisions/show");

        final ArticleRevision articleRevision = articleRevisionRepository.findByRevisionId(nodeId, revision).orElseThrow(EntityNotFoundException::new);

        final Article article = articleRepository.findByNodeId(nodeId).orElseThrow(EntityNotFoundException::new);
        final ArticleView view = articleService.buildArticleView(article, articleRevision);

        mv.addObject("nodeid", articleRevision.getNodeId());
        mv.addObject("view", view);

        return mv;
    }

    @GetMapping("/wiki/{nodeId}/revisions/view/{revision1}/{revision2}")
    public ModelAndView diff(@PathVariable final Integer nodeId, @PathVariable final Integer revision1, @PathVariable final Integer revision2, final ModelAndView mv) {
        mv.setViewName("layouts/bare");
        mv.addObject("title", "Die Linuxspiele-Seite für Linuxspieler");
        mv.addObject(WebDefines.DEFAULT_VIEW_ATTRIBUTE_NAME, "sites/revisions/diff");

        final ArticleRevision articleRevision1 = articleRevisionRepository.findByRevisionId(nodeId, revision1).orElseThrow(EntityNotFoundException::new);
        final ArticleRevision articleRevision2 = articleRevisionRepository.findByRevisionId(nodeId, revision2).orElseThrow(EntityNotFoundException::new);

        final Article article = articleRepository.findByNodeId(nodeId).orElseThrow(EntityNotFoundException::new);

        final ArticleView view1 = articleService.buildArticleView(article, articleRevision1);
        final ArticleView view2 = articleService.buildArticleView(article, articleRevision2);

        final DiffRowGenerator diffGenerator = DiffRowGenerator.create()
                .showInlineDiffs(true)
                .inlineDiffByWord(true)
                .oldTag(f -> "~")
                .newTag(f -> "**")
                .build();

        //
        // Linke Seite bauen
        //
        final StringBuilder contentLeft = new StringBuilder();
        contentLeft.append(StringUtils.defaultIfBlank(view1.getTitle1(), "")).append("\n");
        contentLeft.append(StringUtils.defaultIfBlank(view1.getTitle2(), "")).append("\n");
        contentLeft.append(StringUtils.defaultIfBlank(view1.getTitle3(), "")).append("\n");
        contentLeft.append(StringUtils.defaultIfBlank(view1.getTitle4(), "")).append("\n");
        contentLeft.append(StringUtils.defaultIfBlank(view1.getTitle5(), "")).append("\n");
        contentLeft.append(StringUtils.defaultIfBlank(view1.getTitle6(), "")).append("\n");
        contentLeft.append(StringUtils.defaultIfBlank(view1.getTitle7(), "")).append("\n");

        // Tags
        if (articleRevision1.getTagslist() != null) {
            contentLeft.append(articleRevision1.getTagslist().getTags().stream().sorted().collect(Collectors.joining(",")));
        } else {
            contentLeft.append("");
        }
        contentLeft.append("\n");

        // Hauptinhalt
        contentLeft.append(view1.getContent());

        //
        // Rechte Seite bauen
        //
        final StringBuilder contentRight = new StringBuilder();
        contentRight.append(StringUtils.defaultIfBlank(view2.getTitle1(), "")).append("\n");
        contentRight.append(StringUtils.defaultIfBlank(view2.getTitle2(), "")).append("\n");
        contentRight.append(StringUtils.defaultIfBlank(view2.getTitle3(), "")).append("\n");
        contentRight.append(StringUtils.defaultIfBlank(view2.getTitle4(), "")).append("\n");
        contentRight.append(StringUtils.defaultIfBlank(view2.getTitle5(), "")).append("\n");
        contentRight.append(StringUtils.defaultIfBlank(view2.getTitle6(), "")).append("\n");
        contentRight.append(StringUtils.defaultIfBlank(view2.getTitle7(), "")).append("\n");

        // Tags
        if (articleRevision2.getTagslist() != null) {
            contentRight.append(articleRevision2.getTagslist().getTags().stream().sorted().collect(Collectors.joining(",")));
        } else {
            contentRight.append("");
        }
        contentRight.append("\n");

        // Hauptinhalt
        contentRight.append(view2.getContent()).append("\n");


        final List<DiffRow> diffRows = diffGenerator.generateDiffRows(
                Arrays.asList(contentLeft.toString().split("\n")),
                Arrays.asList(contentRight.toString().split("\n")));

        for (final DiffRow dr : diffRows) {
            logger.debug("old: {}, new: {}", dr.getOldLine(), dr.getNewLine());
        }

        mv.addObject("nodeid", article.getNodeId());
        mv.addObject("view1", view1);
        mv.addObject("view2", view2);
        mv.addObject("diffRows", diffRows);

        return mv;
    }
}
