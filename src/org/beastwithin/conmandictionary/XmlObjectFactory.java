

package org.beastwithin.conmandictionary;

import javax.xml.bind.*;
import javax.xml.bind.annotation.*;
import javax.xml.namespace.*;


@XmlRegistry
public class XmlObjectFactory {

    private final static QName _Notepad_QNAME = new QName("", "notePad");

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

    @XmlElementDecl(namespace = "", name = "notePad")
    public JAXBElement<String> createNotePad(String value) {
        return new JAXBElement<String>(_Notepad_QNAME, String.class, null, value);
    }

}
