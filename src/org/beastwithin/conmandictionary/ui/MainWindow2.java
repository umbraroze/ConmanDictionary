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

import org.beastwithin.conmandictionary.ui.WordClassEditor;
import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.Timer;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.xml.bind.JAXBException;
import org.beastwithin.conmandictionary.ConmanDictionary;
import org.beastwithin.conmandictionary.document.Dictionary;
import org.xml.sax.SAXException;
import org.xnap.commons.i18n.I18n;
import org.xnap.commons.i18n.I18nFactory;

/**
 * The application's main frame.
 */
public class MainWindow2 extends FrameView {
    private static I18n i18n = I18nFactory.getI18n(MainWindow.class,
             "org.beastwithin.conmandictionary.Messages");

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

    @Action
    public void newDocument() {
        if (isUnsaved()) {
            int resp = JOptionPane.showConfirmDialog(
                    getFrame(),
                    i18n.tr("There are unsaved changes.\nReally clear everything?"),
                    i18n.tr("Really clear everything?"),
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

    @Action
    public void quit() {
        if (isUnsaved()) {
            int resp = JOptionPane.showConfirmDialog(this.getFrame(),
                    i18n.tr("There are unsaved changes.\nReally quit?"),
                    i18n.tr("Really quit?"),
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);
            if (resp != JOptionPane.YES_OPTION) {
                return;
            }
        }
        System.exit(0);
    }

    @Action
    public void mergeInto() {
        final JFileChooser fc = new JFileChooser();
        int ret = fc.showDialog(this.getFrame(),
                i18n.tr("Choose file to merge entries from"));
        if (ret != JFileChooser.APPROVE_OPTION) {
            return;
        }
        try {
            this.getModel().mergeEntriesFrom(fc.getSelectedFile());
        } catch (IOException ioe) {
            ioe.printStackTrace();
            JOptionPane.showMessageDialog(
                    this.getFrame(),
                    i18n.tr("File error while saving file:\n{0}",
                    ioe.getMessage()),
                    i18n.tr("Error"),
                    JOptionPane.ERROR_MESSAGE);
        } catch (JAXBException jaxbe) {
            jaxbe.printStackTrace();
            JOptionPane.showMessageDialog(
                    this.getFrame(),
                    i18n.tr("XML error while saving file:\n{0}\nFurther details printed at console.",jaxbe.getMessage()),
                    i18n.tr("Error"),
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    @Action
    public void open() {
        if (isUnsaved()) {
            int resp = JOptionPane.showConfirmDialog(
                    this.getFrame(),
                    i18n.tr("There are unsaved changes.\nReally open another file?"),
                    i18n.tr("Really open another file?"),
                    JOptionPane.YES_NO_OPTION);
            if (resp != 0) {
                return;
            }
        }
        final JFileChooser fc = new JFileChooser();
        int ret = fc.showOpenDialog(this.getFrame());
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
                    this.getFrame(),
                    i18n.tr("Unable to open the file {0}.\n{1}", f, ioe.getMessage()),
                    i18n.tr("Error"),
                    JOptionPane.ERROR_MESSAGE);
            return;
        } catch (SAXException saxe) {
            saxe.printStackTrace();
            JOptionPane.showMessageDialog(
                    this.getFrame(),
                    "The file format for file " + f + " is invalid.\n" + saxe.getMessage(),
                    i18n.tr("Error"),
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            Dictionary loadedDictionary = Dictionary.loadDocument(f);
            this.setModel(loadedDictionary);
        } catch (JAXBException jaxbe) {
            jaxbe.printStackTrace();
            JOptionPane.showMessageDialog(
                    this.getFrame(),
                    "XML error while loading file:\n" +
                    jaxbe.getMessage() +
                    "\nFurther details printed at console.",
                    i18n.tr("Error"),
                    JOptionPane.ERROR_MESSAGE);
        } catch (IOException ioe) {
            ioe.printStackTrace();
            JOptionPane.showMessageDialog(
                    this.getFrame(),
                    "Error reading file:\n" +
                    ioe.getMessage() +
                    "\nFurther details printed at console.",
                    i18n.tr("Error"),
                    JOptionPane.ERROR_MESSAGE);
        }
        //this.setAppTitle(f);
    }

    @Action
    public void save() {
        // What's the file?
        if (this.getModel().getCurrentFile() == null) {
            saveAs();
        } else {
            doSave();
        }
    }
    @Action
    public void saveAs() {
        final JFileChooser fc = new JFileChooser();
        int ret = fc.showSaveDialog(this.getFrame());
        if (ret != JFileChooser.APPROVE_OPTION) {
            return;
        }
        File targetFile = fc.getSelectedFile();
        if(targetFile.exists()) {
            int confirm =
                    JOptionPane.showConfirmDialog(this.getFrame(),
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
                    this.getFrame(),
                    "File error while saving file:\n" +
                    ioe.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (JAXBException jaxbe) {
            jaxbe.printStackTrace();
            JOptionPane.showMessageDialog(
                    this.getFrame(),
                    "XML error while saving file:\n" +
                    jaxbe.getMessage() +
                    "\nFurther details printed at console.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        changesHaveBeenSaved();
        //setAppTitle(mainWindow.getModel().getCurrentFile());
    }
    @Action
    public void exportAsDictd() {
        final JFileChooser fc = new JFileChooser();
        int ret = fc.showSaveDialog(this.getFrame());
        if (ret != JFileChooser.APPROVE_OPTION) {
            return;
        }
        this.getModel().exportAsDictd(fc.getSelectedFile().getPath());
    }

    @Action
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

    public MainWindow2(SingleFrameApplication app) {
        super(app);

        initComponents();
        
        languageNameDialog = new LanguageNameDialog(this);
        wordClassEditor = null; // Will be instantiated upon opening
        categoryEditor = null; // ditto
        notePad = new NotePad();
        
        // The rest is from the app template.
        // TODO: We don't use the animated busy icon for anything. Ought to go.

        // status bar initialization - message timeout, idle icon and busy animation, etc
        ResourceMap resourceMap = getResourceMap();
        int messageTimeout = 10;//resourceMap.getInteger("StatusBar.messageTimeout");
        messageTimer = new Timer(messageTimeout, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                statusMessageLabel.setText("");
            }
        });
        messageTimer.setRepeats(false);
        /*int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
        for (int i = 0; i < busyIcons.length; i++) {
            busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
        }
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
                statusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
            }
        });
        idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
        statusAnimationLabel.setIcon(idleIcon);*/
        progressBar.setVisible(false);

        // connecting action tasks to status bar via TaskMonitor
        TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
        taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                String propertyName = evt.getPropertyName();
                if ("started".equals(propertyName)) {
                    /*if (!busyIconTimer.isRunning()) {
                        //statusAnimationLabel.setIcon(busyIcons[0]);
                        busyIconIndex = 0;
                        busyIconTimer.start();
                    }*/
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(true);
                } else if ("done".equals(propertyName)) {
                    //busyIconTimer.stop();
                    //statusAnimationLabel.setIcon(idleIcon);
                    progressBar.setVisible(false);
                    progressBar.setValue(0);
                } else if ("message".equals(propertyName)) {
                    String text = (String)(evt.getNewValue());
                    statusMessageLabel.setText((text == null) ? "" : text);
                    messageTimer.restart();
                } else if ("progress".equals(propertyName)) {
                    int value = (Integer)(evt.getNewValue());
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(false);
                    progressBar.setValue(value);
                }
            }
        });
    }

    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = ConmanDictionary.getApplication().getMainFrame();
            aboutBox = new AboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        ConmanDictionary.getApplication().show(aboutBox);
    }
    @Action
    public void showWordClassEditor() {
        if (wordClassEditor == null) {            
            wordClassEditor = new WordClassEditor(this,true,model);
            wordClassEditor.setLocationRelativeTo(this.getFrame());
        }
        ConmanDictionary.getApplication().show(wordClassEditor);
    }
    @Action
    public void showCategoryEditor() {
        if (categoryEditor == null) {
            categoryEditor = new CategoryEditor(this,true,model);
            categoryEditor.setLocationRelativeTo(this.getFrame());
        }
        ConmanDictionary.getApplication().show(categoryEditor);
    }
    public void notifyWordClassChanges() {
        leftLanguagePanel.wordClassesChanged();
        rightLanguagePanel.wordClassesChanged();
    }
    public void notifyCategoryChanges() {
        leftLanguagePanel.categoriesChanged();
        rightLanguagePanel.categoriesChanged();
    }
    @Action
    public void showNotepad() {
        ConmanDictionary.getApplication().show(notePad);
    }
    @Action
    public void showLanguageNameDialog() {
        ConmanDictionary.getApplication().show(languageNameDialog);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        leftLanguagePanel = new org.beastwithin.conmandictionary.ui.LanguagePanel();
        rightLanguagePanel = new org.beastwithin.conmandictionary.ui.LanguagePanel();
        statusPanel = new javax.swing.JPanel();
        javax.swing.JSeparator statusPanelSeparator = new javax.swing.JSeparator();
        statusMessageLabel = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        fileNewMenuItem = new javax.swing.JMenuItem();
        fileOpenMenuItem = new javax.swing.JMenuItem();
        fileSaveMenuItem = new javax.swing.JMenuItem();
        fileSaveAsMenuItem = new javax.swing.JMenuItem();
        fileMergeMenuItem = new javax.swing.JMenuItem();
        fileExportDictdMenuItem = new javax.swing.JMenuItem();
        fileQuitSeparator = new javax.swing.JSeparator();
        fileQuitMenuItem = new javax.swing.JMenuItem();
        researchMenu = new javax.swing.JMenu();
        researchNotepadMenuItem = new javax.swing.JMenuItem();
        researchStatisticsMenuItem = new javax.swing.JMenuItem();
        settingsMenu = new javax.swing.JMenu();
        settingsSaveFlaggedMenuItem = new javax.swing.JCheckBoxMenuItem();
        settingsNamesMenuItem = new javax.swing.JMenuItem();
        settingsWordClassMenuItem = new javax.swing.JMenuItem();
        settingsCategoriesMenuItem = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        helpAboutMenuItem = new javax.swing.JMenuItem();

        mainPanel.setName("mainPanel"); // NOI18N

        leftLanguagePanel.setName("leftLanguagePanel"); // NOI18N

        rightLanguagePanel.setName("rightLanguagePanel"); // NOI18N

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addComponent(leftLanguagePanel, javax.swing.GroupLayout.DEFAULT_SIZE, 303, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rightLanguagePanel, javax.swing.GroupLayout.DEFAULT_SIZE, 302, Short.MAX_VALUE))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(leftLanguagePanel, javax.swing.GroupLayout.DEFAULT_SIZE, 486, Short.MAX_VALUE)
            .addComponent(rightLanguagePanel, javax.swing.GroupLayout.DEFAULT_SIZE, 486, Short.MAX_VALUE)
        );

        statusPanel.setName("statusPanel"); // NOI18N

        statusPanelSeparator.setName("statusPanelSeparator"); // NOI18N

        statusMessageLabel.setName("statusMessageLabel"); // NOI18N

        progressBar.setName("progressBar"); // NOI18N

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statusPanelSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 611, Short.MAX_VALUE)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(statusMessageLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 441, Short.MAX_VALUE)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addComponent(statusPanelSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(statusMessageLabel)
                    .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3))
        );

        menuBar.setName("menuBar"); // NOI18N

        fileMenu.setText(i18n.tr("File"));
        fileMenu.setName("fileMenu"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance().getContext().getActionMap(MainWindow.class, this);
        fileNewMenuItem.setAction(actionMap.get("newDocument")); // NOI18N
        fileNewMenuItem.setText(i18n.tr("New..."));
        fileNewMenuItem.setName("fileNewMenuItem"); // NOI18N
        fileMenu.add(fileNewMenuItem);

        fileOpenMenuItem.setAction(actionMap.get("open")); // NOI18N
        fileOpenMenuItem.setText(i18n.tr("Open..."));
        fileOpenMenuItem.setName("fileOpenMenuItem"); // NOI18N
        fileMenu.add(fileOpenMenuItem);

        fileSaveMenuItem.setAction(actionMap.get("save")); // NOI18N
        fileSaveMenuItem.setText(i18n.tr("Save"));
        fileSaveMenuItem.setName("fileSaveMenuItem"); // NOI18N
        fileMenu.add(fileSaveMenuItem);

        fileSaveAsMenuItem.setAction(actionMap.get("saveAs")); // NOI18N
        fileSaveAsMenuItem.setText(i18n.tr("Save as..."));
        fileSaveAsMenuItem.setName("fileSaveAsMenuItem"); // NOI18N
        fileMenu.add(fileSaveAsMenuItem);

        fileMergeMenuItem.setAction(actionMap.get("mergeInto")); // NOI18N
        fileMergeMenuItem.setText(i18n.tr("Merge with..."));
        fileMergeMenuItem.setName("fileMergeMenuItem"); // NOI18N
        fileMenu.add(fileMergeMenuItem);

        fileExportDictdMenuItem.setAction(actionMap.get("exportAsDictd")); // NOI18N
        fileExportDictdMenuItem.setText(i18n.tr("Export as plain text..."));
        fileExportDictdMenuItem.setName("fileExportDictdMenuItem"); // NOI18N
        fileMenu.add(fileExportDictdMenuItem);

        fileQuitSeparator.setName("fileQuitSeparator"); // NOI18N
        fileMenu.add(fileQuitSeparator);

        fileQuitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        fileQuitMenuItem.setText(i18n.tr("Quit"));
        fileQuitMenuItem.setToolTipText(i18n.tr("Quit the application"));
        fileQuitMenuItem.setName("fileQuitMenuItem"); // NOI18N
        fileMenu.add(fileQuitMenuItem);

        menuBar.add(fileMenu);

        researchMenu.setText(i18n.tr("Research"));
        researchMenu.setName("researchMenu"); // NOI18N

        researchNotepadMenuItem.setAction(actionMap.get("showNotepad")); // NOI18N
        researchNotepadMenuItem.setText(i18n.tr("Show notepad..."));
        researchNotepadMenuItem.setName("researchNotepadMenuItem"); // NOI18N
        researchMenu.add(researchNotepadMenuItem);

        researchStatisticsMenuItem.setAction(actionMap.get("showStatisticsWindow")); // NOI18N
        researchStatisticsMenuItem.setText(i18n.tr("Statistics"));
        researchStatisticsMenuItem.setName("researchStatisticsMenuItem"); // NOI18N
        researchMenu.add(researchStatisticsMenuItem);

        menuBar.add(researchMenu);

        settingsMenu.setText(i18n.tr("Settings"));
        settingsMenu.setName("settingsMenu"); // NOI18N

        settingsSaveFlaggedMenuItem.setSelected(true);
        settingsSaveFlaggedMenuItem.setText(i18n.tr("Save flagged entries"));
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance().getContext().getResourceMap(MainWindow.class);
        settingsSaveFlaggedMenuItem.setActionCommand(resourceMap.getString("settingsSaveFlaggedMenuItem.actionCommand")); // NOI18N
        settingsSaveFlaggedMenuItem.setEnabled(false);
        settingsSaveFlaggedMenuItem.setName("settingsSaveFlaggedMenuItem"); // NOI18N
        settingsMenu.add(settingsSaveFlaggedMenuItem);

        settingsNamesMenuItem.setAction(actionMap.get("showLanguageNameDialog")); // NOI18N
        settingsNamesMenuItem.setText(i18n.tr("Language names..."));
        settingsNamesMenuItem.setName("settingsNamesMenuItem"); // NOI18N
        settingsMenu.add(settingsNamesMenuItem);

        settingsWordClassMenuItem.setAction(actionMap.get("showWordClassEditor")); // NOI18N
        settingsWordClassMenuItem.setText(i18n.tr("Word classes..."));
        settingsWordClassMenuItem.setToolTipText(i18n.tr("Define the parts of speech."));
        settingsWordClassMenuItem.setName("settingsWordClassMenuItem"); // NOI18N
        settingsMenu.add(settingsWordClassMenuItem);

        settingsCategoriesMenuItem.setAction(actionMap.get("showCategoryEditor")); // NOI18N
        settingsCategoriesMenuItem.setText(i18n.tr("Categories..."));
        settingsCategoriesMenuItem.setName("settingsCategoriesMenuItem"); // NOI18N
        settingsMenu.add(settingsCategoriesMenuItem);

        menuBar.add(settingsMenu);

        helpMenu.setText(i18n.tr("Help"));
        helpMenu.setName("helpMenu"); // NOI18N

        helpAboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        helpAboutMenuItem.setText(i18n.tr("About..."));
        helpAboutMenuItem.setName("helpAboutMenuItem"); // NOI18N
        helpMenu.add(helpAboutMenuItem);

        menuBar.add(helpMenu);

        setComponent(mainPanel);
        setMenuBar(menuBar);
        setStatusBar(statusPanel);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem fileExportDictdMenuItem;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenuItem fileMergeMenuItem;
    private javax.swing.JMenuItem fileNewMenuItem;
    private javax.swing.JMenuItem fileOpenMenuItem;
    private javax.swing.JMenuItem fileQuitMenuItem;
    private javax.swing.JSeparator fileQuitSeparator;
    private javax.swing.JMenuItem fileSaveAsMenuItem;
    private javax.swing.JMenuItem fileSaveMenuItem;
    private javax.swing.JMenuItem helpAboutMenuItem;
    private javax.swing.JMenu helpMenu;
    private org.beastwithin.conmandictionary.ui.LanguagePanel leftLanguagePanel;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JMenu researchMenu;
    private javax.swing.JMenuItem researchNotepadMenuItem;
    private javax.swing.JMenuItem researchStatisticsMenuItem;
    private org.beastwithin.conmandictionary.ui.LanguagePanel rightLanguagePanel;
    private javax.swing.JMenuItem settingsCategoriesMenuItem;
    private javax.swing.JMenu settingsMenu;
    private javax.swing.JMenuItem settingsNamesMenuItem;
    private javax.swing.JCheckBoxMenuItem settingsSaveFlaggedMenuItem;
    private javax.swing.JMenuItem settingsWordClassMenuItem;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    // End of variables declaration//GEN-END:variables

    private final Timer messageTimer;
    //private final Timer busyIconTimer;
    //private final Icon idleIcon;
    //private final Icon[] busyIcons = new Icon[15];
    //private int busyIconIndex = 0;

}
