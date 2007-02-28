/*  Entry.java: Represents a single entry in a dictionary.
 * 
 *  Conman's Dictionary, a dictionary application for conlang makers.
 *  Copyright (C) 2006  Urpo Lankinen
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301 USA
 */

package org.beastwithin.conmandictionary;

import java.io.*;

/**
 * An entry in the dictionary.
 * 
 * @author wwwwolf
 */
public class Entry implements Comparable<Entry>, Serializable {
	public static final long serialVersionUID = 1; 
	
	private String term = "";
	private String definition = "";
	
	public Entry() {
		this.term = "";
		this.definition = "";
	}
	public Entry(String term, String definition) {
		this.term = term;
		this.definition = definition;
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
	
	/**
	 * The string representation of the term, used in listbox etc,
	 * is "foo: bar baz quux..." with some truncation.
	 */
	public String toString() {
		String def = this.definition;
		if(def.length() > 30)
			def = this.definition.substring(0,29) +  "...";
		return this.term + ": " + def;
	}
		
	public int compareTo(Entry x) {
		return (this.getTerm().compareTo(x.getTerm()));
	}
}
