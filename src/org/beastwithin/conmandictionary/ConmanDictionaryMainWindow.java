// $Id: ConmanDictionaryMainWindow.java 2 2006-09-17 12:33:48Z wwwwolf $

package org.beastwithin.conmandictionary;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ConmanDictionaryMainWindow extends JFrame {
	private class MainWindowMenuListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String c = e.getActionCommand();
			if(c == "file-quit") {
				ConmanDictionary.quit();
			} else if(c == "file-new") {
				ConmanDictionary.newDictionary();
			} else if(c == "file-open") {
				ConmanDictionary.openDictionary();
			} else if(c == "file-save") {
				ConmanDictionary.saveDictionary();
			} else if(c == "file-save-as") {
				ConmanDictionary.saveDictionaryAs();
			} else if(c == "help-about") {
				ConmanDictionary.showAboutDialog();
			}
		}
	}
		
	static final long serialVersionUID = 1; 
	
	private LanguagePanel leftLanguagePanel;
	private LanguagePanel rightLanguagePanel;

	private void constructMenuBar() {
		JMenuItem mi;
		MainWindowMenuListener ml = new MainWindowMenuListener();
		
		JMenuBar mb = new JMenuBar();
		
		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);
		mi = new JMenuItem("New",KeyEvent.VK_N);
		mi.setActionCommand("file-new");
		mi.setAccelerator(KeyStroke.getKeyStroke("ctrl N"));
		mi.addActionListener(ml);
		fileMenu.add(mi);
		mi = new JMenuItem("Open...",KeyEvent.VK_O);
		mi.setActionCommand("file-open");
		mi.setAccelerator(KeyStroke.getKeyStroke("ctrl O"));
		mi.addActionListener(ml);
		fileMenu.add(mi);
		mi = new JMenuItem("Save",KeyEvent.VK_S);
		mi.setActionCommand("file-save");
		mi.setAccelerator(KeyStroke.getKeyStroke("ctrl S"));
		mi.addActionListener(ml);
		fileMenu.add(mi);
		mi = new JMenuItem("Save as...",KeyEvent.VK_A);
		mi.setActionCommand("file-save-as");
		mi.setAccelerator(KeyStroke.getKeyStroke("shift ctrl S"));
		mi.addActionListener(ml);
		fileMenu.add(mi);
		fileMenu.addSeparator();
		mi = new JMenuItem("Quit",KeyEvent.VK_Q);
		mi.setActionCommand("file-quit");
		mi.setAccelerator(KeyStroke.getKeyStroke("ctrl Q"));
		mi.addActionListener(ml);
		fileMenu.add(mi);
		mb.add(fileMenu);
		JMenu helpMenu = new JMenu("Help");
		helpMenu.setMnemonic(KeyEvent.VK_H);
		mi = new JMenuItem("About...",KeyEvent.VK_A);
		mi.setActionCommand("help-about");
		mi.addActionListener(ml);
		helpMenu.add(mi);
		mb.add(helpMenu);
		
		this.setJMenuBar(mb);
	}

	private void constructContents() {
		JPanel mainWinContents = new JPanel(new FlowLayout(FlowLayout.CENTER)); 
		
		leftLanguagePanel = new LanguagePanel("Lang1 to Lang2");
		rightLanguagePanel = new LanguagePanel("Lang2 to Lang1");
		
		mainWinContents.add(leftLanguagePanel);
		mainWinContents.add(new JSeparator(JSeparator.VERTICAL));
		mainWinContents.add(rightLanguagePanel);

		this.add(mainWinContents);
		this.pack();
	}

	public ConmanDictionaryMainWindow() {
		super();

		this.setTitle(ConmanDictionary.APP_NAME);
		// We want to quit when this one's closed.
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		constructMenuBar();
		constructContents();
	}

	public LanguagePanel getLeftLanguagePanel() {
		return leftLanguagePanel;
	}

	public LanguagePanel getRightLanguagePanel() {
		return rightLanguagePanel;
	}
}