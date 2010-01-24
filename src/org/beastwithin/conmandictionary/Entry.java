/*  Entry.java: Represents a single entry in a dictionary.
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

import java.io.*;
import java.util.regex.*;
import javax.xml.bind.annotation.*;

/**
 * An entry in the dictionary.
 * 
 * @author wwwwolf
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "term",
    "definition"
})
@XmlRootElement(name="entry")
public class Entry implements Comparable<Entry>, Serializable {
	public static final long serialVersionUID = 1; 
	private static final int maxTruncatedStringLength = 30;  
	
	@XmlElement(required=true)
	protected String term = "";
	@XmlElement(required=true)
	protected String definition = "";
        @XmlAttribute(required=false)
        protected boolean flagged = false;
	
	public Entry() {
		this.term = "";
		this.definition = "";
                this.flagged = false;
	}
	public Entry(String term, String definition) {
		this.term = term;
		this.definition = definition;
                this.flagged = false;
	}
	public Entry(String term, String definition, boolean flagged) {
		this.term = term;
		this.definition = definition;
                this.flagged = flagged;
	}

	public String getDefinition() {
		return definition;
	}

	public void setDefinition(String definition) {
		this.definition = definition;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}
	
        public boolean getFlagged() {
		return flagged;
	}

	public void setFlagged(boolean flagged) {
		this.flagged = flagged;
	}
	/**
	 * The string representation of the term, used in listbox etc,
	 * is "foo: bar baz quux..." with some truncation.
	 */
        @Override
	public String toString() {
		String def = this.definition;
		if(def.length() > maxTruncatedStringLength)
			def = this.definition.substring(0,maxTruncatedStringLength-1) +  "...";
		return this.term + ": " + def + (flagged ? " (F)" : "");
	}
	/**
	 * Returns the dictionary entry as plain text (suitable for dictd
	 * string builder tool). Headwords are in a line of their own,
	 * the rest is indented with one TAB character. Entries separated
	 * with a single empty line (which is included after the entry.
	 * 
	 * @return the string reporesentation
	 */
	public String toDictString() {		
		StringBuffer s = new StringBuffer();
		s.append(term);
		s.append('\n');
		s.append("");
		s.append(Pattern.compile("^").matcher(definition).replaceAll("\t"));	
		s.append('\n');
		return s.toString();
	}
		
	public int compareTo(Entry x) {
		return (this.getTerm().compareTo(x.getTerm()));
	}
}
