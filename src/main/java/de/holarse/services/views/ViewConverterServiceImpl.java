/*
 * Copyright (C) 2019 Comrad
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.holarse.services.views;

import de.holarse.backend.db.Article;
import de.holarse.backend.db.Attachment;
import de.holarse.backend.db.types.AttachmentGroup;
import de.holarse.backend.views.ArticleView;
import de.holarse.renderer.Renderer;
import de.holarse.web.articles.ArticleController;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ViewConverterServiceImpl implements ViewConverter {
    
    Logger logger = LoggerFactory.getLogger(ViewConverterServiceImpl.class);
    
    @Qualifier("htmlRenderer")
    @Autowired
    Renderer renderer;    
    
    @Override
    public ArticleView convert(final Article article) {
        return convert(article, new ArticleView(), ConverterOptions.WITH_RENDERER);
    }
    
    @Override
    public ArticleView convert(final Article article, final ArticleView view, final ConverterOptions options) {
        if (view == null) {
            throw new IllegalArgumentException("ArticleView was not prepared");
        }
        
        final Map<AttachmentGroup, List<Attachment>> attachmentGroups = article.getAttachments()
                .stream()
                .filter(a -> StringUtils.isNotBlank(a.getAttachmentData()))
                .collect(Collectors.groupingBy(a -> a.getAttachmentGroup()));

        logger.debug("Content: {}", article.getContent());

        view.setNodeId(article.getNodeId());
        view.setMainTitle(article.getTitle());
        view.setAlternativeTitle1(article.getAlternativeTitle1());
        view.setAlternativeTitle2(article.getAlternativeTitle2());
        view.setAlternativeTitle3(article.getAlternativeTitle3());
        switch (options) {
            case WITHOUT_RENDERER:
                view.setContent(article.getContent());
                break;
            case WITH_RENDERER:
                view.setContent( renderer.render(article.getContent() ));
                break;
            default:
                throw new IllegalArgumentException("Unbehandelte ConverterOption " + options);
        }
        
        view.getTags().addAll(article.getTags().stream().map(t -> t.getName()).collect(Collectors.toList()));
        view.getAttachments().putAll(attachmentGroups);
        view.getComments().addAll(article.getComments());    
        
        return view;
    }
    
}
