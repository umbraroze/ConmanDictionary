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
    /// Main menu listener.
    private MainMenuListener mainMenuListener;
    
    private class MainMenuListener implements ActionListener {

        private MainWindow mainWindow = null;

        public MainMenuListener(MainWindow mainWindow) {
            this.mainWindow = mainWindow;
        }

        private void doSave() {
            try {
                mainWindow.getModel().saveDocument();
            } catch (IOException ioe) {
                JOptionPane.showMessageDialog(
                        mainWindow.getFrame(),
                        "File error while saving file:\n" +
                        ioe.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                ioe.printStackTrace();
            } catch (JAXBException jaxbe) {
                JOptionPane.showMessageDialog(
                        mainWindow.getFrame(),
                        "XML error while saving file:\n" +
                        jaxbe.getMessage() +
                        "\nFurther details printed at console.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                jaxbe.printStackTrace();
            }
            mainWindow.changesHaveBeenSaved();
            //setAppTitle(mainWindow.getModel().getCurrentFile());
        }

        private void saveAs() {
            final JFileChooser fc = new JFileChooser();
            int ret = fc.showSaveDialog(mainWindow.getFrame());
            if (ret != JFileChooser.APPROVE_OPTION) {
                return;
            }
            mainWindow.getModel().setCurrentFile(fc.getSelectedFile());
            doSave();
        }

        private void newDocument() {
            if (isUnsaved()) {
                int resp = JOptionPane.showConfirmDialog(
                        mainWindow.getFrame(),
                        "There are unsaved changes.\nReally clear everything?",
                        "Really clear everything?",
                        JOptionPane.YES_NO_OPTION);
                if (resp != 0) {
                    return;
                }
            }
            Dictionary newDocument = new Dictionary();
            mainWindow.setModel(newDocument);
            //mainWindow.setAppTitle(null);
        }

        public void actionPerformed(ActionEvent e) {
            String c = e.getActionCommand();
            if (c.compareTo("file-quit")==0) {
                mainWindow.quit();
            } else if (c.compareTo("file-new")==0) {
                newDocument();
            } else if (c.compareTo("file-open")==0) {
                if (isUnsaved()) {
                    int resp = JOptionPane.showConfirmDialog(
                            mainWindow.getFrame(),
                            "There are unsaved changes.\nReally open another file?",
                            "Really open another file?",
                            JOptionPane.YES_NO_OPTION);
                    if (resp != 0) {
                        return;
                    }
                }
                final JFileChooser fc = new JFileChooser();
                int ret = fc.showOpenDialog(mainWindow.getFrame());
                if (ret != JFileChooser.APPROVE_OPTION) {
                    return;
                }
                mainWindow.openDocument(fc.getSelectedFile());
            } else if (c.compareTo("file-save")==0) {
                // What's the file?
                if (mainWindow.getModel().getCurrentFile() == null) {
                    saveAs();
                } else {
                    doSave();
                }
            } else if (c.compareTo("file-save-as")==0) {
                saveAs();
            } else if (c.compareTo("file-export-dictd")==0) {
                final JFileChooser fc = new JFileChooser();
                int ret = fc.showSaveDialog(mainWindow.getFrame());
                if (ret != JFileChooser.APPROVE_OPTION) {
                    return;
                }
                mainWindow.getModel().exportAsDictd(fc.getSelectedFile().getPath());
            } else if (c.compareTo("research-notepad")==0) {
                mainWindow.getNotePad().setVisible(true);
            } else if (c.compareTo("settings-languagenames")==0) {
                mainWindow.getLanguageNameDialog().open();
            } else if (c.compareTo("help-about")==0) {
                mainWindow.showAboutBox();
            } else {
                System.err.println("WARNING: Unhandled menu item " + c + ".");
            }
        }
    }
    
    
    private boolean isUnsaved() {
        return getModel().isUnsavedChanges();
    }

    public LanguageNameDialog getLanguageNameDialog() {
        return languageNameDialog;
    }

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

    public void openDocument(File f) {
        try {
            Dictionary.validateFile(f);
        } catch (IOException ioe) {
            JOptionPane.showMessageDialog(
                    this.getFrame(),
                    "Unable to open the file " + f + ".\n" + ioe.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            ioe.printStackTrace();
            return;
        } catch (SAXException saxe) {
            JOptionPane.showMessageDialog(
                    this.getFrame(),
                    "The file format for file " + f + " is invalid.\n" + saxe.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            saxe.printStackTrace();
            return;
        }
        try {
            Dictionary loadedDictionary = Dictionary.loadDocument(f);
            this.setModel(loadedDictionary);
        } catch (JAXBException jaxbe) {
            JOptionPane.showMessageDialog(
                    this.getFrame(),
                    "XML error while loading file:\n" +
                    jaxbe.getMessage() +
                    "\nFurther details printed at console.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            jaxbe.printStackTrace();
        } catch (IOException ioe) {
            JOptionPane.showMessageDialog(
                    this.getFrame(),
                    "Error reading file:\n" +
                    ioe.getMessage() +
                    "\nFurther details printed at console.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            ioe.printStackTrace();
        }
        //this.setAppTitle(f);
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
        this.leftLanguagePanel.getEntryList().setModified(false);
        this.leftLanguagePanel.getEntryList().setLastModificationReason("Saved");
        this.rightLanguagePanel.getEntryList().setModified(false);
        this.rightLanguagePanel.getEntryList().setLastModificationReason("Saved");
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
        rightLanguagePanel.setEntryList(newModel.getDefinitions().get(1));
        notePad.setModel(newModel.getNotePadDocument());
    // FIXME: Other associations go here!
    }

    public Dictionary getModel() {
        return model;
    }

    public MainWindow(SingleFrameApplication app) {
        super(app);

        initComponents();
        
        mainMenuListener = new MainMenuListener(this);
        initComponents();
        languageNameDialog = new LanguageNameDialog(this);
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
        fileExportDictdMenuItem = new javax.swing.JMenuItem();
        fileQuitSeparator = new javax.swing.JSeparator();
        fileQuitMenuItem = new javax.swing.JMenuItem();
        researchMenu = new javax.swing.JMenu();
        researchNotepadMenuItem = new javax.swing.JMenuItem();
        settingsMenu = new javax.swing.JMenu();
        settingsNamesMenuItem = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        helpAboutMenuItem = new javax.swing.JMenuItem();

        mainPanel.setName("mainPanel"); // NOI18N

        leftLanguagePanel.setName("leftLanguagePanel"); // NOI18N

        rightLanguagePanel.setName("rightLanguagePanel"); // NOI18N

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 611, Short.MAX_VALUE)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addComponent(leftLanguagePanel, javax.swing.GroupLayout.DEFAULT_SIZE, 303, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rightLanguagePanel, javax.swing.GroupLayout.DEFAULT_SIZE, 302, Short.MAX_VALUE))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 476, Short.MAX_VALUE)
            .addComponent(leftLanguagePanel, javax.swing.GroupLayout.DEFAULT_SIZE, 476, Short.MAX_VALUE)
            .addComponent(rightLanguagePanel, javax.swing.GroupLayout.DEFAULT_SIZE, 476, Short.MAX_VALUE)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 425, Short.MAX_VALUE)
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

        fileNewMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        fileNewMenuItem.setMnemonic('n');
        fileNewMenuItem.setText(resourceMap.getString("fileNewMenuItem.text")); // NOI18N
        fileNewMenuItem.setActionCommand(resourceMap.getString("fileNewMenuItem.actionCommand")); // NOI18N
        fileNewMenuItem.setName("fileNewMenuItem"); // NOI18N
        fileNewMenuItem.addActionListener(mainMenuListener);
        fileMenu.add(fileNewMenuItem);

        fileOpenMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        fileOpenMenuItem.setMnemonic('o');
        fileOpenMenuItem.setText(resourceMap.getString("fileOpenMenuItem.text")); // NOI18N
        fileOpenMenuItem.setActionCommand(resourceMap.getString("fileOpenMenuItem.actionCommand")); // NOI18N
        fileOpenMenuItem.setName("fileOpenMenuItem"); // NOI18N
        fileOpenMenuItem.addActionListener(mainMenuListener);
        fileMenu.add(fileOpenMenuItem);

        fileSaveMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        fileSaveMenuItem.setMnemonic('s');
        fileSaveMenuItem.setText(resourceMap.getString("fileSaveMenuItem.text")); // NOI18N
        fileSaveMenuItem.setActionCommand(resourceMap.getString("fileSaveMenuItem.actionCommand")); // NOI18N
        fileSaveMenuItem.setName("fileSaveMenuItem"); // NOI18N
        fileSaveMenuItem.addActionListener(mainMenuListener);
        fileMenu.add(fileSaveMenuItem);

        fileSaveAsMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        fileSaveAsMenuItem.setMnemonic('a');
        fileSaveAsMenuItem.setText(resourceMap.getString("fileSaveAsMenuItem.text")); // NOI18N
        fileSaveAsMenuItem.setActionCommand(resourceMap.getString("fileSaveAsMenuItem.actionCommand")); // NOI18N
        fileSaveAsMenuItem.setName("fileSaveAsMenuItem"); // NOI18N
        fileSaveAsMenuItem.addActionListener(mainMenuListener);
        fileMenu.add(fileSaveAsMenuItem);

        fileExportDictdMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_MASK));
        fileExportDictdMenuItem.setMnemonic('e');
        fileExportDictdMenuItem.setText(resourceMap.getString("fileExportDictdMenuItem.text")); // NOI18N
        fileExportDictdMenuItem.setActionCommand(resourceMap.getString("fileExportDictdMenuItem.actionCommand")); // NOI18N
        fileExportDictdMenuItem.setName("fileExportDictdMenuItem"); // NOI18N
        fileExportDictdMenuItem.addActionListener(mainMenuListener);
        fileMenu.add(fileExportDictdMenuItem);

        fileQuitSeparator.setName("fileQuitSeparator"); // NOI18N
        fileMenu.add(fileQuitSeparator);

        fileQuitMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        fileQuitMenuItem.setMnemonic('q');
        fileQuitMenuItem.setText(resourceMap.getString("fileQuitMenuItem.text")); // NOI18N
        fileQuitMenuItem.setActionCommand(resourceMap.getString("fileQuitMenuItem.actionCommand")); // NOI18N
        fileQuitMenuItem.setName("fileQuitMenuItem"); // NOI18N
        fileQuitMenuItem.addActionListener(mainMenuListener);
        fileMenu.add(fileQuitMenuItem);

        menuBar.add(fileMenu);

        researchMenu.setMnemonic('r');
        researchMenu.setText(resourceMap.getString("researchMenu.text")); // NOI18N
        researchMenu.setName("researchMenu"); // NOI18N

        researchNotepadMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        researchNotepadMenuItem.setMnemonic('n');
        researchNotepadMenuItem.setText(resourceMap.getString("researchNotepadMenuItem.text")); // NOI18N
        researchNotepadMenuItem.setActionCommand(resourceMap.getString("researchNotepadMenuItem.actionCommand")); // NOI18N
        researchNotepadMenuItem.setName("researchNotepadMenuItem"); // NOI18N
        researchNotepadMenuItem.addActionListener(mainMenuListener);
        researchMenu.add(researchNotepadMenuItem);

        menuBar.add(researchMenu);

        settingsMenu.setMnemonic('s');
        settingsMenu.setText(resourceMap.getString("settingsMenu.text")); // NOI18N
        settingsMenu.setName("settingsMenu"); // NOI18N

        settingsNamesMenuItem.setMnemonic('l');
        settingsNamesMenuItem.setText(resourceMap.getString("settingsNamesMenuItem.text")); // NOI18N
        settingsNamesMenuItem.setActionCommand(resourceMap.getString("settingsNamesMenuItem.actionCommand")); // NOI18N
        settingsNamesMenuItem.setName("settingsNamesMenuItem"); // NOI18N
        settingsNamesMenuItem.addActionListener(mainMenuListener);
        settingsMenu.add(settingsNamesMenuItem);

        menuBar.add(settingsMenu);

        helpMenu.setMnemonic('h');
        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        helpAboutMenuItem.setMnemonic('a');
        helpAboutMenuItem.setText(resourceMap.getString("helpAboutMenuItem.text")); // NOI18N
        helpAboutMenuItem.setActionCommand(resourceMap.getString("helpAboutMenuItem.actionCommand")); // NOI18N
        helpAboutMenuItem.setName("helpAboutMenuItem"); // NOI18N
        helpAboutMenuItem.addActionListener(mainMenuListener);
        helpMenu.add(helpAboutMenuItem);

        menuBar.add(helpMenu);

        setComponent(mainPanel);
        setMenuBar(menuBar);
        setStatusBar(statusPanel);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem fileExportDictdMenuItem;
    private javax.swing.JMenu fileMenu;
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
    private org.beastwithin.conmandictionary.LanguagePanel rightLanguagePanel;
    private javax.swing.JMenu settingsMenu;
    private javax.swing.JMenuItem settingsNamesMenuItem;
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
}
