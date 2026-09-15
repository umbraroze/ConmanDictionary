/*
 * XmlObjectFactory.java: Dunno what this does, JAXB just created this file one day.
 * Conman's Dictionary, a dictionary application for conlang makers.
 * Copyright © 2006,2007,2008,2009,2010,2026  Rose Midford
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package org.beastwithin.conmandictionary.document;

import jakarta.xml.bind.*;
import jakarta.xml.bind.annotation.*;
import javax.xml.namespace.*;

@XmlRegistry
public class XmlObjectFactory {
    public XmlObjectFactory() {
    }

    public Entry createEntry() {
        return new Entry();
    }

    public EntryList createEntryList() {
        return new EntryList();
    }

    public Dictionary createDictionary() {
        return new Dictionary();
    }

    @XmlElementDecl(namespace = "", name = "notepad")
    public JAXBElement<String> createNotePad(String value) {
        return new JAXBElement<String>(new QName("", "notepad"), String.class, null, value);
    }

}
