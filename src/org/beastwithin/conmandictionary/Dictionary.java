/*  Dictionary.java: Class that represents the dictionary data.
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

import java.util.*;
import java.io.*;
import javax.xml.*;
import javax.xml.validation.*;
import javax.xml.transform.stream.*;
import org.xml.sax.*;
import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "", propOrder = {
    "notePad",
    "definitions"
})
@XmlRootElement(name = "dictionarydatabase")
public class Dictionary {
	private static final String schemaResourceFile = "dictionary.xsd";
	
	private class NotePadDocument {
		protected String text;
		public NotePadDocument() {
			this.text = "";
		}
		public NotePadDocument(String s) {
			this.text = s;
		}
		public void setText(String s) {
			this.text = s;
		}
		public String getText() {
			return text;
		}
	}
	
	@XmlTransient
    private NotePadDocument notePad;
    
    @XmlElement(name="definitions", required=true)
    protected List<EntryList> definitions;
      
    public Dictionary() {
    	this.notePad = new NotePadDocument();
    	this.definitions = Collections.synchronizedList(new ArrayList<EntryList>());
    }

    @XmlElement(name="notepad",
    		required=false,
    		type=String.class)
    public void setNotePad(String n) {
    	this.notePad = new NotePadDocument(n);
    }
    public String getNotePad() {
    	return this.notePad.getText();
    }
    public List<EntryList> getDefinitions() {
        if (definitions == null) {
            definitions = Collections.synchronizedList(new ArrayList<EntryList>());
        }
        return this.definitions;
    }
    public String toString() {
    	StringBuffer s = new StringBuffer();
    	s.append("Notepad:\n"+notePad.getText()+"\n");
    	s.append("\n\nList 1 ("+this.definitions.get(0).size()+"):"+this.definitions.get(0).toString());
    	s.append("\n\nList 2 ("+this.definitions.get(1).size()+"):"+this.definitions.get(1).toString());
    	
    	return s.toString();
    }
    
    public static void validateFile(File f) throws SAXException, IOException {
    	SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
    	// Try to get a resource.
    	InputStream schema = ClassLoader.getSystemClassLoader().getResourceAsStream(schemaResourceFile);
    	// So, resource wasn't found - maybe it's in a file?
    	File schemaFile = new File(schemaResourceFile);
    	if(!schemaFile.exists())
    		throw new IOException("The schema file " + schemaResourceFile + ", used to\n"+
    				"validate the file contents, can't be found.");
    	if(!schemaFile.canRead())
    		throw new IOException("The schema file " + schemaResourceFile + ", used to\n"+
    				"validate the file contents, exists but can't be read.");
    	if(schema == null)
    		schema = new FileInputStream(schemaResourceFile);
    	// No? Cannot validate without a schema...
    	if(schema == null)
    		throw new IOException("Can't find the schema file " + schemaResourceFile);    	
    	Schema s = sf.newSchema(new StreamSource(schema));
    	Validator v = s.newValidator();
    	v.validate(new StreamSource(new FileInputStream(f)));
    }
}
