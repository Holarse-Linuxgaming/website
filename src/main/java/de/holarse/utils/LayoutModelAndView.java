/*
 * Copyright (C) 2023 comrad
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
package de.holarse.utils;

import de.holarse.web.defines.WebDefines;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author comrad
 */
public class LayoutModelAndView extends ModelAndView {
    
    private final String layout;
    private final String page;
    
    public LayoutModelAndView(final String layout, final String page) {
        this.layout = layout;
        this.page = page;
        
        super.setViewName(layout);
        super.addObject(WebDefines.DEFAULT_VIEW_ATTRIBUTE_NAME, page);                    
    }
    
    @Override
    public String getViewName() {
        return super.getViewName();
    }
    
}
