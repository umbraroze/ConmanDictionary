// $Id: Entry.java 3 2006-09-17 13:19:05Z wwwwolf $

package org.beastwithin.conmandictionary;

import org.w3c.dom.*;

/**
 * Describes an entry in the dictionary.
 * 
 * @author wwwwolf
 */
public class Entry implements Comparable<Entry> {
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
	
	public DocumentFragment toXmlElement() {
		DocumentFragment doc = XmlHelper.createXmlDocumentFragment();
		Element root = XmlHelper.createXmlElement("entry");
		doc.appendChild(root);
		
		Element term = XmlHelper.createXmlElement("term");
		term.setTextContent(this.term);
		Element definition = XmlHelper.createXmlElement("definition");
		definition.setTextContent(this.definition);
		
		root.appendChild(term);
		root.appendChild(definition);
		doc.appendChild(root);
		
		return doc;
	}
	
	public int compareTo(Entry x) {
		return (this.getTerm().compareTo(x.getTerm()));
	}
}
