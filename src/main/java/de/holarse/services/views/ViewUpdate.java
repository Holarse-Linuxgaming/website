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

/**
 * Beinhaltet die Notification nach dem Bearbeiten eines Node
 * @author Comrad
 */
public class ViewUpdate {

    private final String followUrl;
    private final String message;
    private final UpdateState state;
   
    public ViewUpdate(String followUrl, String message) {
        this(followUrl, message, UpdateState.SUCCESS);
    }
    
    public ViewUpdate(String followUrl, String message, UpdateState state) {
        this.followUrl = followUrl;
        this.message = message;
        this.state = state;
    }
    
    public String getFollowUrl() {
        return followUrl;
    }

    public String getMessage() {
        return message;
    }

    public UpdateState getState() {
        return state;
    }
}
