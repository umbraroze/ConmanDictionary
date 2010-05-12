/*  Dictionary.java: Class that represents the dictionary data.
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

import java.util.*;
import java.io.*;

import javax.xml.*;
import javax.xml.validation.*;
import javax.xml.transform.stream.*;
import org.xml.sax.*;
import javax.xml.bind.*;
import javax.xml.bind.annotation.*;
import javax.swing.*;
import javax.swing.text.*;

@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "", propOrder = { "notePad", "wordClasses", "definitions" })
@XmlRootElement(name = "dictionarydatabase")
public class Dictionary {
    @XmlTransient
    private static final String schemaResourceFile = "org/beastwithin/conmandictionary/resources/dictionary.xsd";
    @XmlTransient
    private File currentFile = null;
    // This is XmlTransient because of a name clash.
    // We don't want to serialize the PlainDocument, just the String that
    // is accessed through the accessors.
    @XmlTransient
    private PlainDocument notePad;
    
    @XmlElement(name = "definitions", required = true)
    protected List<EntryList> definitions;

    @XmlElementWrapper(name = "wordclasses", required = false)
    @XmlElement(name = "class", required = false)
    protected List<WordClass> wordClasses;
    @XmlTransient
    private boolean wordClassesModified = false;
    
    public Dictionary() {
        wordClasses = Collections.synchronizedList(new ArrayList<WordClass>());
        notePad = new PlainDocument();
        definitions = Collections.synchronizedList(new ArrayList<EntryList>());
        definitions.add(new EntryList("Language 1"));
        definitions.add(new EntryList("Language 2"));
    }

    @XmlElement(name = "notepad", required = false, type = String.class)
    public void setNotePad(String n) {
        if (notePad == null) {
            notePad = new PlainDocument();
        }
        try {
            int oldLength = notePad.getLength();
            notePad.replace(0, oldLength, n, null);
        } catch (BadLocationException ble) {
            JOptionPane.showMessageDialog(
                    ConmanDictionary.getApplication().getMainFrame(),
                    "Error changing the text on the notepad:\n" +
                    ble.getMessage() +
                    "\nFurther details printed at console.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            ble.printStackTrace();
        }
    }

    public String getNotePad() {
        try {
            return notePad.getText(0, notePad.getLength());
        } catch (BadLocationException ble) {
            JOptionPane.showMessageDialog(
                    ConmanDictionary.getApplication().getMainFrame(),
                    "Error getting text from the notepad:\n" +
                    ble.getMessage() +
                    "\nFurther details printed at console.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            ble.printStackTrace();
            return "";
        }
    }

    @XmlTransient
    public PlainDocument getNotePadDocument() {
        return notePad;
    }

    public List<EntryList> getDefinitions() {
        if (definitions == null) {
            definitions = Collections.synchronizedList(new ArrayList<EntryList>());
        }
        return this.definitions;
    }

    @Override
    public String toString() {
        StringBuffer s = new StringBuffer();
        s.append("Notepad:\n" + getNotePad() + "\n");
        s.append("\n\nList 1 (" + this.definitions.get(0).size() + "):" + this.definitions.get(0).toString());
        s.append("\n\nList 2 (" + this.definitions.get(1).size() + "):" + this.definitions.get(1).toString());

        return s.toString();
    }

    public static void validateFile(File f) throws SAXException, IOException {
        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        // Try to get a resource from the jar file.
        InputStream schema = ClassLoader.getSystemClassLoader().getResourceAsStream(schemaResourceFile);
        if (schema == null) {
            // If resource wasn't found in the jar, maybe it's in a file?
            File schemaFile = new File(schemaResourceFile);
            if (!schemaFile.exists()) {
                throw new IOException("The schema file " + schemaResourceFile + ", used to\n" +
                        "validate the file contents, can't be found.");
            }
            if (!schemaFile.canRead()) {
                throw new IOException("The schema file " + schemaResourceFile + ", used to\n" +
                        "validate the file contents, exists but can't be read.");
            }
            schema = new FileInputStream(schemaResourceFile);
        }
        // If we still can't find it, we just give up.
        // Cannot validate without a schema...
        if (schema == null) {
            throw new IOException("Can't find the schema file " + schemaResourceFile);
        }
        Schema s = sf.newSchema(new StreamSource(schema));
        javax.xml.validation.Validator v = s.newValidator();
        v.validate(new StreamSource(new FileInputStream(f)));
    }

    public void setCurrentFile(File f) {
        currentFile = f;
    }
    public void setCurrentFile(String fn) {
        currentFile = new File(fn);
    }

    public File getCurrentFile() {
        return currentFile;
    }
    
    public void save(File f) throws JAXBException, IOException {
        setCurrentFile(f);
        saveDocument();
    }
    public void save(String fn) throws JAXBException, IOException {
        setCurrentFile(fn);
        saveDocument();
    }
    public void saveDocument() throws JAXBException, IOException {
        JAXBContext jc = JAXBContext.newInstance(this.getClass());
        Marshaller m = jc.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, new Boolean(true));
        FileWriter f = new FileWriter(currentFile);
        m.marshal(this, f);
    }

    public static Dictionary loadDocument(File file) throws JAXBException, IOException {
        JAXBContext jc = JAXBContext.newInstance(Dictionary.class);
        Unmarshaller um = jc.createUnmarshaller();
        Dictionary r = (Dictionary) um.unmarshal(file);
        r.setCurrentFile(file);
        // Clear modified flags after unmarshalling
        for(EntryList l : r.getDefinitions()) {
            l.setModified(false);
        }
        return r;
    }

    /**
     * Merge dictionary with another dictionary.
     *
     * Word lists are copied to respective word lists (first to first, second
     * to second) and sorted afterwards. Word list titles are ignored.
     *
     * Word classes and categories are copied from the other file IF they're
     * functionally different from what we have in this document
     * (i.e. they have different identifiers), otherwise the equivalent
     * objects in current documents are used.
     *
     * Notepads are concatenated, separated by a newline.
     *
     * @param file The file to be merged into this file.
     * @throws JAXBException
     * @throws IOException
     */
    public void mergeEntriesFrom(File file) throws JAXBException, IOException {
        // Load up the document.
        Dictionary source = Dictionary.loadDocument(file);
        
        // If the word classes in the source lists match any
        // word classes we have, we use our word classes instead.
        // We append the new-found word classes to our list of word
        // classes.
        for (short listNo = 0; listNo <= 1; listNo++) {
            EntryList sourceDefs = source.definitions.get(listNo);
            for (Entry e : sourceDefs.getEntries()) {
                if(e.getWordClass() != null) { // No wordclass? Don't bother.
                    boolean found = false;
                    for (WordClass ourWordClass : wordClasses) {
                        WordClass theirWordClass = e.getWordClass();
                        if (theirWordClass.sharesIdentifierWith(ourWordClass)) {
                            // We have this one, so let's use ours!
                            e.setWordClass(ourWordClass);
                            found = true;
                            break;
                        }
                    }
                    if(found == false)
                        wordClasses.add(e.getWordClass());
                }
                // Append this entry to our word list.
                definitions.get(listNo).add(e);
            }
            // Once everything is added, sort this list.
            definitions.get(listNo).sort();
        }

        // Merge notepads. The notepad texts are separated by \n.
        try {
            if(source.notePad.getLength() > 0) { // Only merge if there actually IS a notepad
                notePad.insertString(notePad.getLength(),
                        "\n" + source.notePad.getText(0, source.notePad.getLength()),
                        null);
            }
        } catch (javax.swing.text.BadLocationException ble) {
            // No, it's not really a JAXB exception, but we're processinating XML data, right?
            throw new JAXBException("Couldn't merge notepads: "+ble.getMessage());
        }
    }


    public boolean equals(Dictionary x) {
        // Use object equality test first.
        if(this.equals((Object)x))
            return true;
        // That done, we're looking at some other criteria.
        boolean wordClassesSame = false;
        boolean notePadsSame = false;
        boolean entryListsSame = false;

        // Compare notepad texts.
        try {
            String npText = this.notePad.getText(0, this.notePad.getLength());
            String np2Text = x.notePad.getText(0, x.notePad.getLength());
            if (npText.equals(np2Text)) {
                notePadsSame = true;
            }
        } catch (javax.swing.text.BadLocationException ble) {
            notePadsSame = false;
        }
        // Compare word classes.
        wordClassesSame = WordClass.wordClassListsFunctionallyEqual(wordClasses, x.wordClasses);

        EntryList a1 = definitions.get(0);
        EntryList a2 = definitions.get(1);
        EntryList b1 = x.definitions.get(0);
        EntryList b2 = x.definitions.get(1);

        // Compare word lists.
        if(a1.equals(b1) && a2.equals(b2))
            entryListsSame = true;

        /*
        System.err.println("a1 = b1: "+a1.equals(b1));
        System.err.println("a2 = b2: "+a2.equals(b2));
        System.err.println("wordClassesSame: "+wordClassesSame);
        System.err.println("notePadsSame: "+notePadsSame);
        System.err.println("entryListsSame: "+entryListsSame);
        */

        // If all these conditions are met, then we have a winner.
        if(wordClassesSame && entryListsSame && notePadsSame)
            return true;
        // Otherwise, we have failed to prove they're equivalent.
        return false;
    }

    public boolean isUnsavedChanges() {
        boolean leftModified = definitions.get(0).isModified();
        //String leftReason = definitions.get(0).getLastModificationReason();
        boolean rightModified = definitions.get(1).isModified();
        //String rightReason = definitions.get(1).getLastModificationReason();
        //System.err.printf("Left modified: %s, %s\n",leftModified,leftReason);
        //System.err.printf("Right modified: %s, %s\n",rightModified,rightReason);
        if (leftModified || rightModified || wordClassesModified) {
            return true;
        }
        return false;
    }
    public boolean isWordClassesModified() {
        return wordClassesModified;
    }
    public void setWordClassesModified(boolean wordClassesModified) {
        this.wordClassesModified = wordClassesModified;
    }
    

    public void exportAsDictd(String fileNameBase) {
        LanguagePanel lp = ConmanDictionary.getMainWindow().getLeftLanguagePanel();
        LanguagePanel rp = ConmanDictionary.getMainWindow().getRightLanguagePanel();
        String lFileName, rFileName;
        if (lp.getLanguage().compareTo(rp.getLanguage()) == 0) {
            // The names are same, so let's come up with something...
            lFileName = "left";
            rFileName = "right";
        } else {
            lFileName = lp.getLanguage();
            rFileName = rp.getLanguage();
        }
        File leftFile = new File(fileNameBase + "." + lFileName + ".txt");
        File rightFile = new File(fileNameBase + "." + rFileName + ".txt");
        try {
            PrintWriter lf = new PrintWriter(new BufferedWriter(new FileWriter(leftFile)));
            PrintWriter rf = new PrintWriter(new BufferedWriter(new FileWriter(rightFile)));

            for (Object o : lp.getEntryList().toArray()) {
                Entry e = (Entry) o;
                lf.println(e.toDictString());
            }
            lf.close();
            for (Object o : rp.getEntryList().toArray()) {
                Entry e = (Entry) o;
                rf.println(e.toDictString());
            }
            rf.close();
        } catch (IOException ioe) {
            JOptionPane.showMessageDialog(ConmanDictionary.getApplication().getMainFrame(),
                    "File error occurred when exporting the file:\n" +
                    ioe.getMessage(),
                    "Error exporting the file.", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public List<WordClass> getWordClasses() {
        return wordClasses;
    }

    public void setWordClasses(List<WordClass> wordClasses) {
        this.wordClasses = wordClasses;
    }
}
