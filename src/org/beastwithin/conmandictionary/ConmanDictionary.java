/*  ConmanDictionary.java: Main program.
 * 
 *  Conman's Dictionary, a dictionary application for conlang makers.
 *  Copyright (C) 2006,2007  Urpo Lankinen
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


import java.io.File;
import java.net.URL;

import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JOptionPane;

/**
 * Main class of the program.
 * 
 * @author wwwwolf
 */
public class ConmanDictionary {
	///Application name. This will appear in window titles etc.
	public final static String APP_NAME = "Conman's Dictionary";
	///Application version.
	public final static String APP_VERSION = "version 0.9";

	/// Application icon image
	private static Image appIcon = null; 
	
	/**
	 * The main program thread. Fired up by the Swing runner utility.
	 */
	private static class MainThread implements Runnable {
		String[] args;
		public MainThread(String args[]) {
			super();
			this.args = args;
		}
		public void run() {
			ConmanDictionary.loadResources();
			dictionary = new Dictionary(); 
			mainWin = new MainWindow();
			mainWin.setModel(dictionary);
			mainWin.setVisible(true);
			if(args.length == 1) {
				mainWin.openDocument(new File(args[0]));
			}
		}
	}	
	
	/**
	 * Utility method to load all resources the application needs.
	 */
	private static void loadResources() {
		final String iconFileName = "resources/graphics/conmandictionary.png";
		// Set icon.
		URL iconURL = ClassLoader.getSystemClassLoader().getResource(iconFileName);
		if(iconURL != null) {
			appIcon = Toolkit.getDefaultToolkit().getImage(iconURL);
		}
	}
	/**
	 * Get the application icon resource.
	 */
	public static Image getAppIcon() {
		return appIcon;
	}
	
	/**
	 * Main window.
	 */
	private static MainWindow mainWin = null;
	
	/**
	 * Dictionary document currently edited.
	 */
	private static Dictionary dictionary = null;
	public static Dictionary getDictionary() {
		return dictionary;
	}

	/**
	 * The main program for the application.
	 * 
	 * @param args Program arguments.
	 */
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new MainThread(args));
	}
	
	/**
	 * Used to get the main window that the application uses.
	 * 
	 * @return the application main window object.
	 */
	public static MainWindow getMainWindow() {
		return mainWin;
	}
	
	
	/**
	 * Shows "About this application" dialog.
	 */
	public static void showAboutDialog() {
		String appAboutString =
			APP_NAME + "\n" +
			APP_VERSION +
			"\nCopyright \u00a9 Urpo Lankinen 2006,2007\n\n" +
			APP_NAME + " comes with ABSOLUTELY NO WARRANTY.\n\n" +
			"This is free software, and you are welcome to redistribute it\n"+
			"under the terms of GNU General Public Licence as published by\n"+
			"the Free Software Foundation; either version 3 of the License, or\n"+
			"(at your option) any later version.\n" +
			"Please see COPYING file for more details.\n\n";

		JOptionPane.showMessageDialog(
				mainWin,
				appAboutString,
				"About " + APP_NAME,
				JOptionPane.INFORMATION_MESSAGE
			);
	}
}
