
package org.beastwithin.conmandictionary;

import javax.swing.*;
import javax.swing.text.*;
import javax.swing.text.html.*;
import org.jdesktop.application.Action;

/**
 *
 * @author wwwwolf
 */
public class StatisticsWindow extends javax.swing.JDialog {

    private HTMLDocument statisticsText;

    /** Creates new form StatisticsWindow */
    public StatisticsWindow() {
        statisticsText = new HTMLDocument();
        initComponents();
    }

    public void updateStatisticsOn(Dictionary dictionary) {
        Element root = statisticsText.getRootElements()[0];
        try {
            statisticsText.insertAfterStart(root, "<p>Hello <em>world.</em></p>");
        } catch (BadLocationException ble) {
            ble.printStackTrace();
            JOptionPane.showMessageDialog(
                    this,
                    "Text generation error when generating statistics:\n" +
                    ble.getMessage() +
                    "\nFurther details printed at console.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        } catch (java.io.IOException ioe) {
            ioe.printStackTrace();
            JOptionPane.showMessageDialog(
                    this,
                    "File error when generating statistics:\n" +
                    ioe.getMessage() +
                    "\nFurther details printed at console.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }
    }

    @Action
    public void close() {
        dispose();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        closeButton = new javax.swing.JButton();
        statisticsScroll = new javax.swing.JScrollPane();
        statisticsView = new javax.swing.JEditorPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(org.beastwithin.conmandictionary.ConmanDictionary.class).getContext().getResourceMap(StatisticsWindow.class);
        setTitle(resourceMap.getString("StatisticsWindow.title")); // NOI18N
        setModal(true);
        setName("StatisticsWindow"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(org.beastwithin.conmandictionary.ConmanDictionary.class).getContext().getActionMap(StatisticsWindow.class, this);
        closeButton.setAction(actionMap.get("close")); // NOI18N
        closeButton.setMnemonic('c');
        closeButton.setText(resourceMap.getString("closeButton.text")); // NOI18N
        closeButton.setName("closeButton"); // NOI18N

        statisticsScroll.setName("statisticsScroll"); // NOI18N

        statisticsView.setContentType(resourceMap.getString("statisticsView.contentType")); // NOI18N
        statisticsView.setEditable(false);
        statisticsView.setName("statisticsView"); // NOI18N
        statisticsScroll.setViewportView(statisticsView);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(closeButton, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(statisticsScroll, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(statisticsScroll, javax.swing.GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(closeButton)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton closeButton;
    private javax.swing.JScrollPane statisticsScroll;
    private javax.swing.JEditorPane statisticsView;
    // End of variables declaration//GEN-END:variables

}
