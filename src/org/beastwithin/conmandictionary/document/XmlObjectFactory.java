

package org.beastwithin.conmandictionary.document;

import javax.xml.bind.*;
import javax.xml.bind.annotation.*;
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
