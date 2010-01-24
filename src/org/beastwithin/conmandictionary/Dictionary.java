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
@XmlType(name = "", propOrder = { "notePad", "definitions" })
@XmlRootElement(name = "dictionarydatabase")
public class Dictionary {
    @XmlTransient
    private static final String schemaResourceFile = "org/beastwithin/conmandictionary/resources/dictionary.xsd";
    @XmlTransient
    private File currentFile = null;
    @XmlTransient
    private PlainDocument notePad;
    @XmlElement(name = "definitions", required = true)
    protected List<EntryList> definitions;

    public Dictionary() {
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

    public File getCurrentFile() {
        return currentFile;
    }
    
    public void saveDocument() throws JAXBException, IOException {
        JAXBContext jc = JAXBContext.newInstance(this.getClass());
        Marshaller m = jc.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, new Boolean(true));
        FileWriter f = new FileWriter(currentFile);
        m.marshal(this, f);
    }
    
    public void saveDocumentUnflagged() throws JAXBException, IOException {
        // General battle plan: Create a clone of the document in memory.
        // Prune flagged entries.
        // Stow that in a file.
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
    
    public void mergeEntriesFrom(File file) throws JAXBException, IOException {
        Dictionary source = Dictionary.loadDocument(file);
        definitions.get(0).add(source.definitions.get(0));
        definitions.get(0).sort();
        definitions.get(1).add(source.definitions.get(1));
        definitions.get(1).sort();
    }

    public boolean isUnsavedChanges() {
        boolean leftModified = definitions.get(0).isModified();
        //String leftReason = definitions.get(0).getLastModificationReason();
        boolean rightModified = definitions.get(1).isModified();
        //String rightReason = definitions.get(1).getLastModificationReason();
        //System.err.printf("Left modified: %s, %s\n",leftModified,leftReason);
        //System.err.printf("Right modified: %s, %s\n",rightModified,rightReason);
        if (leftModified || rightModified) {
            return true;
        }
        return false;
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
}
