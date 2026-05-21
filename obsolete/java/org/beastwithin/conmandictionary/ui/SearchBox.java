/*  SearchBox.java: Search box control.
 *
 *  Conman's Dictionary, a dictionary application for conlang makers.
 *  Copyright (C) 2006,2016 Urpo Lankinen
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.beastwithin.conmandictionary.ui;

import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.io.*;
import java.util.*;

public class SearchBox extends HBox {
    private static final String FXML_FILE = "SearchBox.fxml";

    private List<SearchBoxListener> searchBoxListeners;

    @FXML private TextField search;
    @FXML private Button clearButton;

    public SearchBox() {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(FXML_FILE));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        searchBoxListeners = Collections.synchronizedList(new ArrayList<SearchBoxListener>());
    }

    @FXML
    protected void clearSearch() {
        search.clear();
        notifySearchBoxCleared();
    }
    @FXML
    protected void searchKeyTyped() {
        if (search.getText().length() == 0) {
            notifySearchBoxCleared();
        } else {
            notifySearchBoxChanged();
        }
    }

    private void notifySearchBoxChanged() {
        String t = search.getText();
        for (SearchBoxListener s : searchBoxListeners) {
            s.searchBoxContentsChanged(t);
        }
    }
    private void notifySearchBoxCleared() {
        for (SearchBoxListener s : searchBoxListeners) {
            s.searchBoxCleared();
        }
    }
    public void addSearchBoxListener(SearchBoxListener newListener) {
        searchBoxListeners.add(newListener);
    }
}
