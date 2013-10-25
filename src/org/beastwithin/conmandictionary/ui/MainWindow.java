/*  MainWindow.java: main window class.
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

package org.beastwithin.conmandictionary.ui;

import org.beastwithin.conmandictionary.*;
import org.beastwithin.conmandictionary.document.*;

import java.io.*;
import javax.swing.*;
import javax.xml.bind.JAXBException;
import org.xml.sax.SAXException;

/**
 * The application's main frame.
 */
public class MainWindow extends javax.swing.JFrame {
    private static java.util.ResourceBundle uiMessages;
    private final String UIMESSAGES_RESOURCEBUNDLE = "org/beastwithin/conmandictionary/ui/UIMessages";

    /**
     * Creates the main window.
     */
    public MainWindow() {
        super();

        initComponents();
        
        languageNameDialog = new LanguageNameDialog(this);
        wordClassEditor = null; // Will be instantiated upon opening
        categoryEditor = null; // ditto
        notePad = new NotePad();        
        
        uiMessages = java.util.ResourceBundle.getBundle(UIMESSAGES_RESOURCEBUNDLE);
    }

    private Dictionary model;
    /// Notepad.
    private NotePad notePad;
    /// The language selection window.
    private LanguageNameDialog languageNameDialog;
    // About window.
    private JDialog aboutBox;
    // Word class editor.
    private WordClassEditor wordClassEditor;
    // Word category editor
    private CategoryEditor categoryEditor;
    // Statistics window
    private StatisticsWindow statisticsWindow;

    
    private boolean isUnsaved() {
        return getModel().isUnsavedChanges();
    }

    public LanguageNameDialog getLanguageNameDialog() {
        return languageNameDialog;
    }

    public void newDocument() {        
        if (isUnsaved()) {
            int resp = JOptionPane.showConfirmDialog(
                    this,
                    uiMessages.getString("Dialog.unsavedChanges")+"\n"+
                    uiMessages.getString("Dialog.confirmNewDocument"),
                    uiMessages.getString("Dialog.confirmNewDocument.title"),
                    JOptionPane.YES_NO_OPTION);
            if (resp != JOptionPane.YES_OPTION) {
                return;
            }
        }
        // Make a new dictionary.
        Dictionary newDocument = new Dictionary();
        // Mop up our entry lists.
        leftLanguagePanel.clearEntries();
        rightLanguagePanel.clearEntries();
        // Set new document model
        setModel(newDocument);
        //mainWindow.setAppTitle(null);
    }

    public void quit() {
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle(UIMESSAGES_RESOURCEBUNDLE);
        
        if (isUnsaved()) {
            int resp = JOptionPane.showConfirmDialog(this,
                    uiMessages.getString("Dialog.unsavedChanges")+"\n"+
                    uiMessages.getString("Dialog.confirmQuit"),
                    uiMessages.getString("Dialog.confirmQuit.title"),
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);
            if (resp != JOptionPane.YES_OPTION) {
                return;
            }
        }
        System.exit(0);
    }

    public void mergeInto() {
        final JFileChooser fc = new JFileChooser();
        int ret = fc.showDialog(this,
                uiMessages.getString("Dialog.merge.title"));
        if (ret != JFileChooser.APPROVE_OPTION) {
            return;
        }
        try {
            this.getModel().mergeEntriesFrom(fc.getSelectedFile());
        } catch (IOException ioe) {
            ioe.printStackTrace();
            JOptionPane.showMessageDialog(
                    this,
                    uiMessages.getString("Dialog.saveError")+":\n"+
                    ioe.getMessage(),
                    uiMessages.getString("Dialog.saveError.title"),
                    JOptionPane.ERROR_MESSAGE);
        } catch (JAXBException jaxbe) {
            jaxbe.printStackTrace();
            JOptionPane.showMessageDialog(
                    this,
                    uiMessages.getString("Dialog.xmlSaveError")+":\n"+
                            jaxbe.getMessage()+"\n"+
                            uiMessages.getString("Dialog.checkdebug"),
                    uiMessages.getString("Dialog.xmlSaveError.title"),
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void open() {
        if (isUnsaved()) {
            int resp = JOptionPane.showConfirmDialog(
                    this,
                    uiMessages.getString("Dialog.unsavedChanges")+"\n"+
                    uiMessages.getString("Dialog.confirmOpenDocument"),
                    uiMessages.getString("Dialog.confirmOpenDocument.title"),
                    JOptionPane.YES_NO_OPTION);
            if (resp != 0) {
                return;
            }
        }
        final JFileChooser fc = new JFileChooser();
        int ret = fc.showOpenDialog(this);
        if (ret != JFileChooser.APPROVE_OPTION) {
            return;
        }
        openDocument(fc.getSelectedFile());
    }

    public void openDocument(File f) {
        try {
            Dictionary.validateFile(f);
        } catch (IOException ioe) {
            ioe.printStackTrace();
            JOptionPane.showMessageDialog(
                    this,
                    uiMessages.getString("Dialog.openError")+
                            " "+f+":\n"+ioe.getMessage(),
                    uiMessages.getString("Dialog.openError.title"),
                    JOptionPane.ERROR_MESSAGE);
            return;
        } catch (SAXException saxe) {
            saxe.printStackTrace();
            JOptionPane.showMessageDialog(
                    this,
                    "The file format for file " + f + " is invalid.\n" + saxe.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            Dictionary loadedDictionary = Dictionary.loadDocument(f);
            this.setModel(loadedDictionary);
        } catch (JAXBException jaxbe) {
            jaxbe.printStackTrace();
            JOptionPane.showMessageDialog(
                    this,
                    "XML error while loading file:\n" +
                    jaxbe.getMessage() +
                    "\nFurther details printed at console.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (IOException ioe) {
            ioe.printStackTrace();
            JOptionPane.showMessageDialog(
                    this,
                    "Error reading file:\n" +
                    ioe.getMessage() +
                    "\nFurther details printed at console.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        //this.setAppTitle(f);
    }

    public void save() {
        // What's the file?
        if (this.getModel().getCurrentFile() == null) {
            saveAs();
        } else {
            doSave();
        }
    }

    public void saveAs() {
        final JFileChooser fc = new JFileChooser();
        int ret = fc.showSaveDialog(this);
        if (ret != JFileChooser.APPROVE_OPTION) {
            return;
        }
        File targetFile = fc.getSelectedFile();
        if(targetFile.exists()) {
            int confirm =
                    JOptionPane.showConfirmDialog(this,
                    targetFile.getAbsolutePath() + " exists. Overwrite?",
                    "Overwrite?",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);
            if(confirm == JOptionPane.NO_OPTION)
                return;
        }
        this.getModel().setCurrentFile(targetFile);
        doSave();
    }
    private void doSave() {
        try {
            this.getModel().saveDocument();
        } catch (IOException ioe) {
            ioe.printStackTrace();
            JOptionPane.showMessageDialog(
                    this,
                    "File error while saving file:\n" +
                    ioe.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (JAXBException jaxbe) {
            jaxbe.printStackTrace();
            JOptionPane.showMessageDialog(
                    this,
                    "XML error while saving file:\n" +
                    jaxbe.getMessage() +
                    "\nFurther details printed at console.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        changesHaveBeenSaved();
        //setAppTitle(mainWindow.getModel().getCurrentFile());
    }

    public void exportAsDictd() {
        final JFileChooser fc = new JFileChooser();
        int ret = fc.showSaveDialog(this);
        if (ret != JFileChooser.APPROVE_OPTION) {
            return;
        }
        this.getModel().exportAsDictd(fc.getSelectedFile().getPath());
    }

    public void showStatisticsWindow() {
        statisticsWindow = new StatisticsWindow();
        statisticsWindow.updateStatisticsOn(model);
        statisticsWindow.setVisible(true);
    }
    
    /**
     * Get the left-side language panel.
     * @return the left-side language panel.
     */
    public LanguagePanel getLeftLanguagePanel() {
        return leftLanguagePanel;
    }

    /**
     * Get the right-side language panel.
     * @return the right-side language panel.
     */
    public LanguagePanel getRightLanguagePanel() {
        return rightLanguagePanel;
    }

    public void changesHaveBeenSaved() {
        leftLanguagePanel.getEntryList().setModified(false);
        leftLanguagePanel.getEntryList().setLastModificationReason("Saved");
        rightLanguagePanel.getEntryList().setModified(false);
        rightLanguagePanel.getEntryList().setLastModificationReason("Saved");
        model.setWordClassesModified(false);
        model.setCategoriesModified(false);
    }

    public NotePad getNotePad() {
        return notePad;
    }

    /**
     * Will associate this window (and all subwindows etc) with a Dictionary document.
     * @param newModel
     */
    public void setModel(Dictionary newModel) {
        model = newModel;
        leftLanguagePanel.setEntryList(newModel.getDefinitions().get(0));
        leftLanguagePanel.setWordClasses(newModel.getWordClasses());
        leftLanguagePanel.setCategories(newModel.getCategories());
        rightLanguagePanel.setEntryList(newModel.getDefinitions().get(1));
        rightLanguagePanel.setWordClasses(newModel.getWordClasses());
        rightLanguagePanel.setCategories(newModel.getCategories());
        notePad.setModel(newModel.getNotePadDocument());
        if(wordClassEditor != null)
            wordClassEditor.setModel(newModel);
        if(categoryEditor != null)
            categoryEditor.setModel(newModel);
        // FIXME: Other associations go here!
    }

    public Dictionary getModel() {
        return model;
    }

    public void showAboutBox() {
        /*
        if (aboutBox == null) {
            JFrame mainFrame = ConmanDictionary.getApplication().getMainFrame();
            aboutBox = new AboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        ConmanDictionary.getApplication().show(aboutBox);
        */
    }

    public void showWordClassEditor() {
        /*
        if (wordClassEditor == null) {            
            wordClassEditor = new WordClassEditor(this,true,model);
            wordClassEditor.setLocationRelativeTo(this);
        }
        ConmanDictionary.getApplication().show(wordClassEditor);
        */
    }

    public void showCategoryEditor() {
        /*
        if (categoryEditor == null) {
            categoryEditor = new CategoryEditor(this,true,model);
            categoryEditor.setLocationRelativeTo(this);
        }
        ConmanDictionary.getApplication().show(categoryEditor);
        */
    }

    public void notifyWordClassChanges() {
        leftLanguagePanel.wordClassesChanged();
        rightLanguagePanel.wordClassesChanged();
    }

    public void notifyCategoryChanges() {
        leftLanguagePanel.categoriesChanged();
        rightLanguagePanel.categoriesChanged();
    }

    public void showNotepad() {
        //ConmanDictionary.getApplication().show(notePad);
    }

    public void showLanguageNameDialog() {
        //ConmanDictionary.getApplication().show(languageNameDialog);
    }

    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        leftLanguagePanel = new org.beastwithin.conmandictionary.ui.LanguagePanel();
        mainWindowSeparator = new javax.swing.JSeparator();
        rightLanguagePanel = new org.beastwithin.conmandictionary.ui.LanguagePanel();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        fileNewMenuItem = new javax.swing.JMenuItem();
        fileOpenMenuItem = new javax.swing.JMenuItem();
        fileSaveMenuItem = new javax.swing.JMenuItem();
        fileSaveAsMenuItem = new javax.swing.JMenuItem();
        fileMergeMenuItem = new javax.swing.JMenuItem();
        fileExportDictdMenuItem = new javax.swing.JMenuItem();
        fileQuitSeparator = new javax.swing.JPopupMenu.Separator();
        fileQuitMenuItem = new javax.swing.JMenuItem();
        researchMenu = new javax.swing.JMenu();
        researchNotepadMenuItem = new javax.swing.JMenuItem();
        researchStatisticsMenuItem = new javax.swing.JMenuItem();
        settingsMenu = new javax.swing.JMenu();
        settingsSaveFlaggedMenuItem = new javax.swing.JCheckBoxMenuItem();
        settingsLanguageNamesMenuItem = new javax.swing.JMenuItem();
        settingsWordClassesMenuItem = new javax.swing.JMenuItem();
        settingsCategoriesMenuItem = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        aboutMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        mainWindowSeparator.setOrientation(javax.swing.SwingConstants.VERTICAL);

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/beastwithin/conmandictionary/ui/UIMessages"); // NOI18N
        fileMenu.setText(bundle.getString("Menu.file")); // NOI18N

        fileNewMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        fileNewMenuItem.setText(bundle.getString("Menu.file.new")); // NOI18N
        fileMenu.add(fileNewMenuItem);

        fileOpenMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        fileOpenMenuItem.setText(bundle.getString("Menu.file.open")); // NOI18N
        fileMenu.add(fileOpenMenuItem);

        fileSaveMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        fileSaveMenuItem.setText(bundle.getString("Menu.file.save")); // NOI18N
        fileMenu.add(fileSaveMenuItem);

        fileSaveAsMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        fileSaveAsMenuItem.setText(bundle.getString("Menu.file.saveas")); // NOI18N
        fileMenu.add(fileSaveAsMenuItem);

        fileMergeMenuItem.setText(bundle.getString("Menu.file.merge")); // NOI18N
        fileMenu.add(fileMergeMenuItem);

        fileExportDictdMenuItem.setText(bundle.getString("Menu.file.export")); // NOI18N
        fileMenu.add(fileExportDictdMenuItem);
        fileMenu.add(fileQuitSeparator);

        fileQuitMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        fileQuitMenuItem.setText(bundle.getString("Menu.file.quit")); // NOI18N
        fileMenu.add(fileQuitMenuItem);

        menuBar.add(fileMenu);

        researchMenu.setText(bundle.getString("Menu.research")); // NOI18N

        researchNotepadMenuItem.setText(bundle.getString("Menu.research.notepad")); // NOI18N
        researchMenu.add(researchNotepadMenuItem);

        researchStatisticsMenuItem.setText(bundle.getString("Menu.research.statistics")); // NOI18N
        researchMenu.add(researchStatisticsMenuItem);

        menuBar.add(researchMenu);

        settingsMenu.setText(bundle.getString("Menu.settings")); // NOI18N

        settingsSaveFlaggedMenuItem.setSelected(true);
        settingsSaveFlaggedMenuItem.setText(bundle.getString("Menu.settings.saveflagged")); // NOI18N
        settingsSaveFlaggedMenuItem.setEnabled(false);
        settingsMenu.add(settingsSaveFlaggedMenuItem);

        settingsLanguageNamesMenuItem.setText(bundle.getString("Menu.settings.languagenames")); // NOI18N
        settingsMenu.add(settingsLanguageNamesMenuItem);

        settingsWordClassesMenuItem.setText(bundle.getString("Menu.settings.wordclasses")); // NOI18N
        settingsMenu.add(settingsWordClassesMenuItem);

        settingsCategoriesMenuItem.setText(bundle.getString("Menu.settings.categories")); // NOI18N
        settingsMenu.add(settingsCategoriesMenuItem);

        menuBar.add(settingsMenu);

        helpMenu.setText(bundle.getString("Menu.help")); // NOI18N

        aboutMenuItem.setText(bundle.getString("Menu.help.about")); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(leftLanguagePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mainWindowSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rightLanguagePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(leftLanguagePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                .addComponent(mainWindowSeparator, javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(rightLanguagePanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem aboutMenuItem;
    private javax.swing.JMenuItem fileExportDictdMenuItem;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenuItem fileMergeMenuItem;
    private javax.swing.JMenuItem fileNewMenuItem;
    private javax.swing.JMenuItem fileOpenMenuItem;
    private javax.swing.JMenuItem fileQuitMenuItem;
    private javax.swing.JPopupMenu.Separator fileQuitSeparator;
    private javax.swing.JMenuItem fileSaveAsMenuItem;
    private javax.swing.JMenuItem fileSaveMenuItem;
    private javax.swing.JMenu helpMenu;
    private org.beastwithin.conmandictionary.ui.LanguagePanel leftLanguagePanel;
    private javax.swing.JSeparator mainWindowSeparator;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu researchMenu;
    private javax.swing.JMenuItem researchNotepadMenuItem;
    private javax.swing.JMenuItem researchStatisticsMenuItem;
    private org.beastwithin.conmandictionary.ui.LanguagePanel rightLanguagePanel;
    private javax.swing.JMenuItem settingsCategoriesMenuItem;
    private javax.swing.JMenuItem settingsLanguageNamesMenuItem;
    private javax.swing.JMenu settingsMenu;
    private javax.swing.JCheckBoxMenuItem settingsSaveFlaggedMenuItem;
    private javax.swing.JMenuItem settingsWordClassesMenuItem;
    // End of variables declaration//GEN-END:variables

}
