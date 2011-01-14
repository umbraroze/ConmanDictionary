
package org.beastwithin.conmandictionary;

import java.util.HashMap;
import javax.swing.text.html.*;
import org.beastwithin.conmandictionary.WordClass;
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
        StringBuffer stats = new StringBuffer();

        // FIXME: DOES NO HTML ESCAPING RIGHT NOW. IT SHOULD.

        // Count headwords.
        stats.append("<h1>Headwords</h1>");
        stats.append("<table>");
        for(short i = 0; i <= 1; i++) {
            stats.append("<tr><td>");
            stats.append(dictionary.getDefinitions().get(i).getLanguage());
            stats.append(":</td><td>");
            stats.append(dictionary.getDefinitions().get(i).size());
            stats.append("</td></tr>");
        }
        stats.append("</table>");

        // Count wordclass usage.
        stats.append("<h1>Wordclass use</h1>");
        for(short lang = 0; lang <= 1; lang++) {
            // Heading for word list
            stats.append("<h2>");
            stats.append(dictionary.getDefinitions().get(lang).getLanguage());
            stats.append("</h2>");
            // Build a hash with word classes as keys and counts as values.
            HashMap<WordClass,Integer> results = new HashMap<WordClass,Integer>();
            EntryList dl = dictionary.getDefinitions().get(lang);
            for(int n = 0; n < dl.size(); n++) {
                WordClass w = dl.get(n).getWordClass();
                Integer c = results.get(w);
                if(c == null)
                    c = 0;
                c++;
                results.put(w, c);
            }
            // Present results.
            stats.append("<table>");
            for(WordClass w : results.keySet()) {
                stats.append("<tr><td>");
                stats.append(w.getName());
                stats.append(":</td><td>");
                stats.append(results.get(w));
                stats.append("</td></tr>");
            }
            stats.append("</table>");
        }

        statisticsView.setText(stats.toString());
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
        closeButton.setText(resourceMap.getString("closeButton.text")); // NOI18N
        closeButton.setName("closeButton"); // NOI18N

        statisticsScroll.setName("statisticsScroll"); // NOI18N

        statisticsView.setContentType(resourceMap.getString("statisticsView.contentType")); // NOI18N
        statisticsView.setName("statisticsView"); // NOI18N
        statisticsScroll.setViewportView(statisticsView);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, closeButton)
                    .add(statisticsScroll, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .add(statisticsScroll, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(closeButton)
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
