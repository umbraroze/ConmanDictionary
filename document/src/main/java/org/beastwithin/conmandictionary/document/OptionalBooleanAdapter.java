/*
 * OptionalBooleanAdapter.java: For not saving boolean attributes when they're false.
 * Conman's Dictionary, a dictionary application for conlang makers.
 * Copyright © 2006,2007,2008,2009,2010,2026  Rose Midford
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package org.beastwithin.conmandictionary.document;

import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

/**
 * This is used with @XmlJavaTypeAdapter to not saving boolean attributes
 * when they're false.
 * 
 * @author wwwwolf
 */
public class OptionalBooleanAdapter extends XmlAdapter<String, Boolean> {
    public Boolean unmarshal(String val) throws Exception {
        if(val.equalsIgnoreCase("true"))
            return true;
        if(val.equalsIgnoreCase("false"))
            return false;
        throw new JAXBException("Invalid boolean value");
    }
    public String marshal(Boolean val) throws Exception {
        if(val)
            return "true";
        else
            return null; // Falses are nulls.
    }
}
