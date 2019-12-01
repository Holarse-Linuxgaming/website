package de.holarse.backend.views;

import de.holarse.backend.db.User;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ViewHelper {
   
    /**
     * Wandelt einen Benutzer in sein ViewModel um
     */
    public static final Function<User, UserView> MapToViewFn = u -> {
        UserView view = new UserView();
        view.setId(u.getId());
        view.setEmail(u.getEmail());
        view.setLogin(u.getLogin());
        
        view.getRoles().addAll( u.getRoles().stream().map(r -> r.getCode()).collect(Collectors.toList()) );
        
        return view;
    };
    
    private ViewHelper() {};
    
}
