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

import org.beastwithin.conmandictionary.document.*;
import org.beastwithin.conmandictionary.ui.*;
import java.io.File;

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


    /**
     * @param args the command line arguments
     */
    public static void mainFromMainWindowNewStuff(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainWindow2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainWindow2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainWindow2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainWindow2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainWindow2().setVisible(true);
            }
        });
    }

}
