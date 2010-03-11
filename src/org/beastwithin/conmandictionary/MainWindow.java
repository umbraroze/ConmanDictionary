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

package org.beastwithin.conmandictionary;

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
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.xml.bind.JAXBException;
import org.xml.sax.SAXException;

/**
 * The application's main frame.
 */
public class MainWindow extends FrameView {

        
    private Dictionary model;
    /// Notepad.
    private NotePad notePad;
    /// The language selection window.
    private LanguageNameDialog languageNameDialog;    
        
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
                    "There are unsaved changes.\nReally clear everything?",
                    "Really clear everything?",
                    JOptionPane.YES_NO_OPTION);
            if (resp != 0) {
                return;
            }
        }
        Dictionary newDocument = new Dictionary();
        leftLanguagePanel.clearEntries();
        rightLanguagePanel.clearEntries();
        setModel(newDocument);
        //mainWindow.setAppTitle(null);
    }

    @Action
    public void quit() {
        if (isUnsaved()) {
            int resp = JOptionPane.showConfirmDialog(this.getFrame(),
                    "There are unsaved changes.\nReally quit?",
                    "Really quit?",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);
            if (resp != 0) {
                return;
            }
        }
        System.exit(0);
    }

    @Action
    public void mergeInto() {
        final JFileChooser fc = new JFileChooser();
        int ret = fc.showDialog(this.getFrame(),
                "Choose file to merge entries from");
        if (ret != JFileChooser.APPROVE_OPTION) {
            return;
        }
        try {
            this.getModel().mergeEntriesFrom(fc.getSelectedFile());
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
    }

    @Action
    public void open() {
        if (isUnsaved()) {
            int resp = JOptionPane.showConfirmDialog(
                    this.getFrame(),
                    "There are unsaved changes.\nReally open another file?",
                    "Really open another file?",
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
                    "Unable to open the file " + f + ".\n" + ioe.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        } catch (SAXException saxe) {
            saxe.printStackTrace();
            JOptionPane.showMessageDialog(
                    this.getFrame(),
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
                    this.getFrame(),
                    "XML error while loading file:\n" +
                    jaxbe.getMessage() +
                    "\nFurther details printed at console.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (IOException ioe) {
            ioe.printStackTrace();
            JOptionPane.showMessageDialog(
                    this.getFrame(),
                    "Error reading file:\n" +
                    ioe.getMessage() +
                    "\nFurther details printed at console.",
                    "Error",
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
        this.getModel().setCurrentFile(fc.getSelectedFile());
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
        rightLanguagePanel.setEntryList(newModel.getDefinitions().get(1));
        rightLanguagePanel.setWordClasses(newModel.getWordClasses());
        notePad.setModel(newModel.getNotePadDocument());
    // FIXME: Other associations go here!
    }

    public Dictionary getModel() {
        return model;
    }

    public MainWindow(SingleFrameApplication app) {
        super(app);

        initComponents();
        
        languageNameDialog = new LanguageNameDialog(this);
        wordClassEditor = null; // Will be instantiated upon opening
        notePad = new NotePad();
        
        // The rest is from the app template.

        // status bar initialization - message timeout, idle icon and busy animation, etc
        ResourceMap resourceMap = getResourceMap();
        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
        messageTimer = new Timer(messageTimeout, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                statusMessageLabel.setText("");
            }
        });
        messageTimer.setRepeats(false);
        int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
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
        statusAnimationLabel.setIcon(idleIcon);
        progressBar.setVisible(false);

        // connecting action tasks to status bar via TaskMonitor
        TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
        taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                String propertyName = evt.getPropertyName();
                if ("started".equals(propertyName)) {
                    if (!busyIconTimer.isRunning()) {
                        statusAnimationLabel.setIcon(busyIcons[0]);
                        busyIconIndex = 0;
                        busyIconTimer.start();
                    }
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(true);
                } else if ("done".equals(propertyName)) {
                    busyIconTimer.stop();
                    statusAnimationLabel.setIcon(idleIcon);
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
    public void notifyWordClassChanges() {
        leftLanguagePanel.wordClassesChanged();
        rightLanguagePanel.wordClassesChanged();
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
        leftLanguagePanel = new org.beastwithin.conmandictionary.LanguagePanel();
        rightLanguagePanel = new org.beastwithin.conmandictionary.LanguagePanel();
        statusPanel = new javax.swing.JPanel();
        javax.swing.JSeparator statusPanelSeparator = new javax.swing.JSeparator();
        statusMessageLabel = new javax.swing.JLabel();
        statusAnimationLabel = new javax.swing.JLabel();
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
            .addComponent(leftLanguagePanel, javax.swing.GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE)
            .addComponent(rightLanguagePanel, javax.swing.GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE)
        );

        statusPanel.setName("statusPanel"); // NOI18N

        statusPanelSeparator.setName("statusPanelSeparator"); // NOI18N

        statusMessageLabel.setName("statusMessageLabel"); // NOI18N

        statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

        progressBar.setName("progressBar"); // NOI18N

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statusPanelSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 611, Short.MAX_VALUE)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(statusMessageLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 427, Short.MAX_VALUE)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusAnimationLabel)
                .addContainerGap())
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addComponent(statusPanelSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(statusMessageLabel)
                    .addComponent(statusAnimationLabel)
                    .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3))
        );

        menuBar.setName("menuBar"); // NOI18N

        fileMenu.setMnemonic('f');
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(org.beastwithin.conmandictionary.ConmanDictionary.class).getContext().getResourceMap(MainWindow.class);
        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(org.beastwithin.conmandictionary.ConmanDictionary.class).getContext().getActionMap(MainWindow.class, this);
        fileNewMenuItem.setAction(actionMap.get("newDocument")); // NOI18N
        fileNewMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        fileNewMenuItem.setMnemonic('n');
        fileNewMenuItem.setText(resourceMap.getString("fileNewMenuItem.text")); // NOI18N
        fileNewMenuItem.setName("fileNewMenuItem"); // NOI18N
        fileMenu.add(fileNewMenuItem);

        fileOpenMenuItem.setAction(actionMap.get("open")); // NOI18N
        fileOpenMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        fileOpenMenuItem.setMnemonic('o');
        fileOpenMenuItem.setText(resourceMap.getString("fileOpenMenuItem.text")); // NOI18N
        fileOpenMenuItem.setName("fileOpenMenuItem"); // NOI18N
        fileMenu.add(fileOpenMenuItem);

        fileSaveMenuItem.setAction(actionMap.get("save")); // NOI18N
        fileSaveMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        fileSaveMenuItem.setMnemonic('s');
        fileSaveMenuItem.setText(resourceMap.getString("fileSaveMenuItem.text")); // NOI18N
        fileSaveMenuItem.setName("fileSaveMenuItem"); // NOI18N
        fileMenu.add(fileSaveMenuItem);

        fileSaveAsMenuItem.setAction(actionMap.get("saveAs")); // NOI18N
        fileSaveAsMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        fileSaveAsMenuItem.setMnemonic('a');
        fileSaveAsMenuItem.setText(resourceMap.getString("fileSaveAsMenuItem.text")); // NOI18N
        fileSaveAsMenuItem.setName("fileSaveAsMenuItem"); // NOI18N
        fileMenu.add(fileSaveAsMenuItem);

        fileMergeMenuItem.setAction(actionMap.get("mergeInto")); // NOI18N
        fileMergeMenuItem.setMnemonic('m');
        fileMergeMenuItem.setText(resourceMap.getString("fileMergeMenuItem.text")); // NOI18N
        fileMergeMenuItem.setName("fileMergeMenuItem"); // NOI18N
        fileMenu.add(fileMergeMenuItem);

        fileExportDictdMenuItem.setAction(actionMap.get("exportAsDictd")); // NOI18N
        fileExportDictdMenuItem.setMnemonic('e');
        fileExportDictdMenuItem.setText(resourceMap.getString("fileExportDictdMenuItem.text")); // NOI18N
        fileExportDictdMenuItem.setName("fileExportDictdMenuItem"); // NOI18N
        fileMenu.add(fileExportDictdMenuItem);

        fileQuitSeparator.setName("fileQuitSeparator"); // NOI18N
        fileMenu.add(fileQuitSeparator);

        fileQuitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        fileQuitMenuItem.setText(resourceMap.getString("fileQuitMenuItem.text")); // NOI18N
        fileQuitMenuItem.setToolTipText(resourceMap.getString("fileQuitMenuItem.toolTipText")); // NOI18N
        fileQuitMenuItem.setName("fileQuitMenuItem"); // NOI18N
        fileMenu.add(fileQuitMenuItem);

        menuBar.add(fileMenu);

        researchMenu.setMnemonic('r');
        researchMenu.setText(resourceMap.getString("researchMenu.text")); // NOI18N
        researchMenu.setName("researchMenu"); // NOI18N

        researchNotepadMenuItem.setAction(actionMap.get("showNotepad")); // NOI18N
        researchNotepadMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        researchNotepadMenuItem.setMnemonic('n');
        researchNotepadMenuItem.setText(resourceMap.getString("researchNotepadMenuItem.text")); // NOI18N
        researchNotepadMenuItem.setName("researchNotepadMenuItem"); // NOI18N
        researchMenu.add(researchNotepadMenuItem);

        researchStatisticsMenuItem.setMnemonic('s');
        researchStatisticsMenuItem.setText(resourceMap.getString("researchStatisticsMenuItem.text")); // NOI18N
        researchStatisticsMenuItem.setEnabled(false);
        researchStatisticsMenuItem.setName("researchStatisticsMenuItem"); // NOI18N
        researchMenu.add(researchStatisticsMenuItem);

        menuBar.add(researchMenu);

        settingsMenu.setMnemonic('s');
        settingsMenu.setText(resourceMap.getString("settingsMenu.text")); // NOI18N
        settingsMenu.setName("settingsMenu"); // NOI18N

        settingsSaveFlaggedMenuItem.setMnemonic('f');
        settingsSaveFlaggedMenuItem.setSelected(true);
        settingsSaveFlaggedMenuItem.setText(resourceMap.getString("settingsSaveFlaggedMenuItem.text")); // NOI18N
        settingsSaveFlaggedMenuItem.setActionCommand(resourceMap.getString("settingsSaveFlaggedMenuItem.actionCommand")); // NOI18N
        settingsSaveFlaggedMenuItem.setEnabled(false);
        settingsSaveFlaggedMenuItem.setName("settingsSaveFlaggedMenuItem"); // NOI18N
        settingsMenu.add(settingsSaveFlaggedMenuItem);

        settingsNamesMenuItem.setAction(actionMap.get("showLanguageNameDialog")); // NOI18N
        settingsNamesMenuItem.setMnemonic('l');
        settingsNamesMenuItem.setText(resourceMap.getString("settingsNamesMenuItem.text")); // NOI18N
        settingsNamesMenuItem.setName("settingsNamesMenuItem"); // NOI18N
        settingsMenu.add(settingsNamesMenuItem);

        settingsWordClassMenuItem.setAction(actionMap.get("showWordClassEditor")); // NOI18N
        settingsWordClassMenuItem.setMnemonic('w');
        settingsWordClassMenuItem.setText(resourceMap.getString("settingsWordClassMenuItem.text")); // NOI18N
        settingsWordClassMenuItem.setToolTipText(resourceMap.getString("settingsWordClassMenuItem.toolTipText")); // NOI18N
        settingsWordClassMenuItem.setName("settingsWordClassMenuItem"); // NOI18N
        settingsMenu.add(settingsWordClassMenuItem);

        settingsCategoriesMenuItem.setMnemonic('c');
        settingsCategoriesMenuItem.setText(resourceMap.getString("settingsCategoriesMenuItem.text")); // NOI18N
        settingsCategoriesMenuItem.setEnabled(false);
        settingsCategoriesMenuItem.setName("settingsCategoriesMenuItem"); // NOI18N
        settingsMenu.add(settingsCategoriesMenuItem);

        menuBar.add(settingsMenu);

        helpMenu.setMnemonic('h');
        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        helpAboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        helpAboutMenuItem.setText(resourceMap.getString("helpAboutMenuItem.text")); // NOI18N
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
    private org.beastwithin.conmandictionary.LanguagePanel leftLanguagePanel;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JMenu researchMenu;
    private javax.swing.JMenuItem researchNotepadMenuItem;
    private javax.swing.JMenuItem researchStatisticsMenuItem;
    private org.beastwithin.conmandictionary.LanguagePanel rightLanguagePanel;
    private javax.swing.JMenuItem settingsCategoriesMenuItem;
    private javax.swing.JMenu settingsMenu;
    private javax.swing.JMenuItem settingsNamesMenuItem;
    private javax.swing.JCheckBoxMenuItem settingsSaveFlaggedMenuItem;
    private javax.swing.JMenuItem settingsWordClassMenuItem;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    // End of variables declaration//GEN-END:variables

    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    private int busyIconIndex = 0;

    private JDialog aboutBox;
    private WordClassEditor wordClassEditor;
}
