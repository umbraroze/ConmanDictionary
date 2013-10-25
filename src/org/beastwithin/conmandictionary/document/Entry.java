/*  Entry.java: Represents a single entry in a dictionary.
 * 
 *  Conman's Dictionary, a dictionary application for conlang makers.
 *  Copyright (C) 2006,2007,2008,2009,2010  Urpo Lankinen
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
package org.beastwithin.conmandictionary.document;

import org.beastwithin.conmandictionary.ui.OptionalBooleanAdapter;
import java.io.*;
import java.util.regex.*;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.*;

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
@XmlRootElement(name = "entry")
public class Entry implements Comparable<Entry>, Serializable {
    @XmlTransient
    public static final long serialVersionUID = 1;
    @XmlTransient
    private static final int maxTruncatedStringLength = 30;
    
    @XmlElement(required = true)
    protected String term = "";
    @XmlElement(required = true)
    protected String definition = "";
    @XmlAttribute(required = false)
    @XmlJavaTypeAdapter(OptionalBooleanAdapter.class)
    protected Boolean flagged = false;
    @XmlAttribute(required = false, name = "class")
    @XmlIDREF
    protected WordClass wordClass;
    @XmlAttribute(required = false, name = "category")
    @XmlIDREF
    protected Category category;

    public Entry() {
        this.term = "";
        this.definition = "";
        this.flagged = false;
        this.wordClass = null;
        this.category = null;
    }

    public Entry(String term, String definition) {
        this.term = term;
        this.definition = definition;
        this.flagged = false;
        this.wordClass = null;
        this.category = null;
    }

    public Entry(String term, String definition, boolean flagged) {
        this.term = term;
        this.definition = definition;
        this.flagged = flagged;
        this.wordClass = null;
        this.category = null;
    }

    public Entry(String term, String definition, boolean flagged, WordClass wordClass) {
        this.term = term;
        this.definition = definition;
        this.flagged = flagged;
        this.wordClass = wordClass;
        this.category = null;
    }

    public Entry(String term, String definition, boolean flagged, WordClass wordClass, Category category) {
        this.term = term;
        this.definition = definition;
        this.flagged = flagged;
        this.wordClass = wordClass;
        this.category = category;
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

    public boolean isFlagged() {
        return flagged;
    }
    public void setFlagged(boolean flagged) {
        this.flagged = flagged;
    }

    public WordClass getWordClass() {
        return wordClass;
    }

    public void setWordClass(WordClass wordClass) {
        this.wordClass = wordClass;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    /**
     * The string representation of the term, used in listbox etc,
     * is "foo: (w.cl.abbr.) bar baz quux..." with some truncation.
     */
    @Override
    public String toString() {
        String def = (wordClass == null ? ""
                : wordClass.getParentheticalAbbreviation() + " ")
                + this.definition;
        if (def.length() > maxTruncatedStringLength) {
            def = def.substring(0, maxTruncatedStringLength - 1) + "...";
        }
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
        // Definition should have a parenthetical word class
        // at the beginning, if any
        String d = (wordClass != null
                ? wordClass.getParentheticalAbbreviation() + " "
                : "")
                + definition;
        StringBuilder s = new StringBuilder();
        s.append(term);
        s.append('\n');
        s.append("");
        
        // Indent lines with tab characters.
        String sr;
        sr = Pattern.compile("^").matcher(d).replaceAll("\t");
        sr = Pattern.compile("\n").matcher(sr).replaceAll("\n\t");
        s.append(sr);
        
        s.append('\n');
        return s.toString();
    }

    public int compareTo(Entry x) {
        return (this.getTerm().compareTo(x.getTerm()));
    }

    public boolean equals(Entry x) {
        // Use object equality test first.
        if(this.equals((Object)x))
            return true;
        // Okay, then they MAY just be equal otherwise. Fuck.
        // You know what this means, right? Fuckton of null value juggling.
        // Yes, I know this code sucks. I emphatically do know that indeed.
        // Please don't post this to thedailywtf.com. At least there's a
        // jUnit test for this...

        // Check term.
        if (term == null) {
            if (x.term != null) {
                //System.err.println("Term fail 1");
                return false;
            }
        } else { // term != null
            if (x.term == null) {
                //System.err.println("Term fail 2");
                return false;
            }
            if (!term.equals(x.term)) {
                //System.err.println("Term fail 3");
                return false;
            }
        }
        // Check definition.
        if (definition == null) {
            if (x.definition != null) {
                //System.err.println("Definition fail 1");
                return false;
            }
        } else { // definition != null
            if (x.definition == null) {
                //System.err.println("Definition fail 2");
                return false;
            }
            if (!definition.equals(x.definition)) {
                //System.err.println("Definition fail 3");
                return false;
            }
        }
        // Check flagged.
        if (flagged == null) {
            if (x.flagged != null) {
                //System.err.println("Flagged fail 1");
                return false;
            }
        } else { // flagged != null
            if (x.flagged == null) {
                //System.err.println("Flagged fail 2");
                return false;
            }
            if (!flagged.equals(x.flagged)) {
                //System.err.println("Flagged fail 3");
                return false;
            }
        }
        // Check wordClass.
        if (wordClass == null) {
            if (x.wordClass != null) {
                //System.err.println("Wordclass fail 1");
                return false;
            }
        } else { // wordClass != null
            if (x.wordClass == null) {
                //System.err.println("Wordclass fail 2");
                return false;
            }
            // COULD BE PROBLEMATIC???
            if (!wordClass.sharesIdentifierWith(x.wordClass)) {
                //System.err.println("Wordclass fail 3");
                return false;
            }
        }
        // Check Category.
        if (category == null) {
            if (x.category != null) {
                //System.err.println("Category fail 1");
                return false;
            }
        } else { // category != null
            if (x.category == null) {
                //System.err.println("Category fail 2");
                return false;
            }
            // COULD BE PROBLEMATIC???
            if (!category.sharesIdentifierWith(x.category)) {
                //System.err.println("Category fail 3");
                return false;
            }
        }
        return true;

        /*
         * I'd use the following, but the null values ruin my day.
         */
        /*
        if (term.equals(x.term)
                && definition.equals(x.definition)
                && flagged.equals(x.flagged)
                && wordClass.equals(x.wordClass)
                && category.equals(x.category)) {
            return true;
        }
        return false;
         */
    }
}
