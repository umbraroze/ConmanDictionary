/*  WordClass.java: Bean for word classes.
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

package org.beastwithin.conmandictionary;

import javax.xml.bind.annotation.*;

/**
 * Bean for word classes.
 * @author wwwwolf
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "description"
})
@XmlRootElement(name="class")
public class WordClass {
    @XmlAttribute(required=true) @XmlID
    protected String name;
    @XmlAttribute(required=true)
    protected String abbreviation;
    @XmlValue
    protected String description;

    public WordClass() {
        name = "";
        abbreviation = "";
        description = null;
    }
    public WordClass(String name, String abbreviation) {
        this.name = name;
        this.abbreviation = abbreviation;
        this.description = null;
    }
    public WordClass(String name, String abbreviation, String description) {
        this.name = name;
        this.abbreviation = abbreviation;
        this.description = (description.equals("") ? null : description);
    }
    
    /**
     * Are the two word classes identical? Identical means they have the
     * same name. (Not named equal() because this is dumb.)
     * 
     * @param x Word class to compare against.
     * @return Whether the two word classes have the same name.
     */
    public boolean sharesIdentifierWith(WordClass x) {
        return name.equals(x.name);
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        // Description is automagically null'd, because that reduces
        // the amount of XML. Note, however, that it doesn't. This is
        // because JAXB fails a bit.
        this.description = (description.equals("") ? null : description);
    }
    
    /**
     * Word classes look like "Class (abbr.)" or "Class (abbr.): Description"
     * in string format.
     * 
     * @return String representation of the word class.
     */
    @Override
    public String toString() {
        return name + " (" + abbreviation + ")" +
                (description == null ? "" : ": " + description);
    }
}
