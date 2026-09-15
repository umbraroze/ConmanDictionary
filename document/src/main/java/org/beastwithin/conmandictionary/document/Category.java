/*
 * WordClass.java: Bean for word classes.
 * Conman's Dictionary, a dictionary application for conlang makers.
 * Copyright © 2006,2007,2008,2009,2010,2026  Rose Midford
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package org.beastwithin.conmandictionary.document;

import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.*;

/**
 * Bean for word classes.
 * @author wwwwolf
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "", propOrder = {
    "description"
})
@XmlRootElement(name="category")
public class Category implements Comparable {

    protected String name;
    protected String description;

    @XmlAttribute(required = false)
    @XmlJavaTypeAdapter(OptionalBooleanAdapter.class)
    protected Boolean flagged = false;

    public Category() {
        name = "";
        description = null;
        flagged = false;
    }
    public Category(String name, String description) {
        this.name = name;
        this.description = (description.equals("") ? null : description);
        this.flagged = false;
    }
    public Category(String name, String description, boolean flagged) {
        this.name = name;
        this.description = (description.equals("") ? null : description);
        this.flagged = flagged;
    }
    
    /**
     * Are the two word classes identical? Identical means they have the
     * same name. (Not named equal() because this is dumb.)
     * 
     * @param x Word class to compare against.
     * @return Whether the two word classes have the same name.
     */
    public boolean sharesIdentifierWith(Category x) {
        return name.equals(x.name);
    }
    
    @XmlAttribute(required=true) @XmlID
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @XmlValue
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        // Description is automagically null'd, because that reduces
        // the amount of XML.
        this.description = (description.equals("") ? null : description);
    }

    public boolean isFlagged() {
        return flagged;
    }

    public void setFlagged(boolean flagged) {
        this.flagged = flagged;
    }

    /**
     * Compare category to another. The categories are compared by name; all other
     * attributes are ignored.
     *
     * @param o Object to compare to.
     * @return Comparison result.
     */
    public int compareTo(Object o) {
        Category w = (Category) o;
        return name.compareTo(w.name);
    }

    public static boolean categoryListsFunctionallyEqual(java.util.List<Category> x, java.util.List<Category> y) {
        if(x == null && y == null)
            return true;
        if((x == null && y != null) || (x != null && y == null))
            return false;
        if(x.size() != y.size())
            return false;
        for(int n = 0; n < x.size(); n++) {
            if(!x.get(n).sharesIdentifierWith(y.get(n)))
                return false;
        }
        return true;
    }
}
