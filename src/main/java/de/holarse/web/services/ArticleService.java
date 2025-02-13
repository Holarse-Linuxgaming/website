package de.holarse.web.services;

import de.holarse.backend.db.*;
import de.holarse.backend.db.repositories.AttachmentGroupRepository;
import de.holarse.backend.db.repositories.NodeSlugRepository;
import de.holarse.backend.types.AttachmentGroupType;
import de.holarse.backend.types.NodeType;
import de.holarse.backend.view.*;
import de.holarse.web.renderer.ContentRenderer;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

@Service
public class ArticleService {

    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private ObjectStorageService objectStorageService;

    @Autowired
    private AttachmentGroupRepository attachmentGroupRepository;

    @Autowired
    private NodeSlugRepository nodeSlugRepository;

    @Autowired
    private ContentRenderer renderer;

    public ArticleView buildArticleView(final Article article, final ArticleRevision articleRevision) {
        final Set<Tag> tags = article.getTags();
        final List<TagGroup> relevantTagGroups = tags.stream().map(Tag::getTagGroup).toList();
        final NodeSlug mainSlug = nodeSlugRepository.findMainSlug(articleRevision.getNodeId(), NodeType.article).orElseThrow(EntityNotFoundException::new);

        // TODO
//        if (article.getNodeStatus().isDeleted() || !article.getNodeStatus().isPublished()) {
//            logger.debug("Principal: {}", principal);
//            if (principal instanceof HolarsePrincipal holarsePrincipal) {
//                if (holarsePrincipal.getAuthorities().stream().filter(a -> a.getAuthority().equalsIgnoreCase(RoleUserTypes.ROLE_USER_ADMIN)).count() > 0) {
//                    adminOverride = true;
//                }
//            }
//
//            if (!adminOverride) {
//                mv.addObject(WebDefines.DEFAULT_VIEW_ATTRIBUTE_NAME, "sites/wiki/blocked");
//                return mv;
//            }
//        }

        final List<Attachment> websiteLinks = attachmentService.getAttachments(article, attachmentGroupRepository.findByCode(AttachmentGroupType.website.name()));
        final List<Attachment> videos = attachmentService.getAttachments(article, attachmentGroupRepository.findByCode(AttachmentGroupType.video.name()));
        final List<Attachment> screenshots = attachmentService.getAttachments(article, attachmentGroupRepository.findByCode(AttachmentGroupType.image.name()));

        // View zusammenstellen
        final ArticleView view = ArticleView.of(articleRevision, mainSlug);
        view.setNodeId(article.getNodeId());
        view.setTagList(tags.stream().map(TagView::of).sorted(Comparator.comparingInt(TagView::getWeight).reversed().thenComparing(TagView::getUseCount).reversed().thenComparing(TagView::getName)).toList());
        view.setContent(renderer.render(view.getContent(), null));
        //view.setSlug(mainSlug.getName());
        view.setWebsiteLinks(websiteLinks.stream().map(AttachmentView::of).toList());
        view.setYoutubeVideos(videos.stream().map(YoutubeView::of).toList());
        view.setScreenshots(screenshots.stream().map(ScreenshotView::of).map(ssv -> objectStorageService.patchUrl(ssv)).toList());

        return view;
    }

}
