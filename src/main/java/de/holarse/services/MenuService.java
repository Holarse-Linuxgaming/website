package de.holarse.services;

import de.holarse.backend.db.Menu;
import de.holarse.backend.db.repositories.MenuRepository;
import de.holarse.backend.views.MainMenuView;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MenuService {
    
    @Autowired
    MenuRepository menuRepository;
    
    public List<MainMenuView> buildMainMenu() {
        final List<MainMenuView> items = new ArrayList<>();
        
        for(final Menu menu : menuRepository.findMainMenu()) {
            final List<Menu> children = menuRepository.findChildren(menu);
            final List<MainMenuView> childrenViews;
            if (children != null && !children.isEmpty()) {
                childrenViews = children.stream().map(m -> new MainMenuView(m.getLabel(), m.getUrl())).collect(Collectors.toList());
            } else {
                childrenViews = null;
            }
            
            final MainMenuView view = new MainMenuView(menu.getLabel(), menu.getUrl(), childrenViews);
            items.add(view);
        }
        
        return items;
    }
    
}
