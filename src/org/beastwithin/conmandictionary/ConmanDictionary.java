/*  ConmanDictionary.java: Main program.
 * 
 *  Conman's Dictionary, a dictionary application for conlang makers.
 *  Copyright (C) 2006,2007,2008  Urpo Lankinen
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

package org.beastwithin.conmandictionary;

import org.beastwithin.conmandictionary.document.Dictionary;
import org.beastwithin.conmandictionary.ui.MainWindow;
import java.io.File;
import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

/**
 * The main class of the application.
 */
public class ConmanDictionary extends SingleFrameApplication {
    /**
     * Main window.
     */
    private static MainWindow mainWindow = null;
    public static MainWindow getMainWindow() {
        return mainWindow;
    }
    /**
     * Dictionary document currently edited.
     */
    private static Dictionary dictionary = null;

    public static Dictionary getDictionary() {
        return dictionary;
    }

    /**
     * At startup create and show the main frame of the application.
     */
    @Override protected void startup() {
        dictionary = new Dictionary();
        mainWindow = new MainWindow(this);
        mainWindow.setModel(dictionary);
        show(mainWindow);
    }

    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     */
    @Override protected void configureWindow(java.awt.Window root) {
    }

    /**
     * A convenient static getter for the application instance.
     * @return the instance of ConmanDictionary
     */
    public static ConmanDictionary getApplication() {
        return Application.getInstance(ConmanDictionary.class);
    }

    /**
     * Main method launching the application.
     */
    public static void main(String[] args) {
        launch(ConmanDictionary.class, args);
        if (args.length == 1) {
            mainWindow.openDocument(new File(args[0]));
        }
    }
}
