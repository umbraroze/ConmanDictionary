package org.beastwithin.conmandictionary;

import org.beastwithin.conmandictionary.document.Entry;
import org.beastwithin.conmandictionary.document.Dictionary;
import org.beastwithin.conmandictionary.document.EntryList;
import org.beastwithin.conmandictionary.document.WordClass;
import java.io.*;
import jakarta.xml.bind.JAXBException;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class DynamicCreationTest {
    static File tempFile = null;

    public DynamicCreationTest() {
    }

    @BeforeAll
    public static void createTempFile() throws Exception {
        try {
            tempFile = File.createTempFile("wordclasstest.", ".xml");
            System.out.println("Created temporary file: " + tempFile.getAbsolutePath());
        } catch(IOException ioe) {
            fail("Internal error: Couldn't create temp file.");
        }
    }

    /**
     * Create a file dynamically.
     */
    @BeforeAll
    public static void createFile() throws Exception {
        Dictionary d = new Dictionary();
        WordClass n = new WordClass("Noun","n");
        WordClass v = new WordClass("Verb","v");
        WordClass m = new WordClass("Mystery","m","A very mysterious word class.");
        d.getWordClasses().add(n);
        d.getWordClasses().add(v);
        d.getWordClasses().add(m);
        Entry e1 = new Entry("foo","A person who knows nothing.",false,n);
        Entry e2 = new Entry("pity","Activity which foos (q.v.) end up receiving",false,v);
        Entry e3 = new Entry("bar","An epic weapon of ultimate smackdown",false,n);
        Entry e4 = new Entry("zplepb","This isn't supposed to be on the list, or something!");
        Entry e5 = new Entry("grrlubub","Your guess is as good or mine, even if it's documented",false,m);
        d.getDefinitions().get(0).add(e1);
        d.getDefinitions().get(0).add(e2);
        d.getDefinitions().get(0).add(e3);
        d.getDefinitions().get(1).add(e4);
        d.getDefinitions().get(1).add(e5);
        try {
            d.save(tempFile);
        } catch(IOException ioe) {
            fail("Saving file failed due to file error: " + ioe.getMessage());
        } catch(JAXBException jaxbe) {
            fail("Saving file failed due to JAXB error: " + jaxbe);
        }
    }

    /**
     * Test unmarshalling the file that we just created programmatically.
     */
    @Test
    public void loadDynamicallyCreatedFile() throws Exception {
        try {
            Dictionary d = Dictionary.loadDocument(tempFile);
        } catch(jakarta.xml.bind.JAXBException jaxbe) {
            fail("Loading document failed due to JAXB error: " + jaxbe);
        } catch(java.io.IOException ioe) {
            fail("Loading document failed due to file error: " + ioe.getMessage());
        }
    }

    /**
     * Test unmarshalling the file that we just created programmatically, and
     * check that the contents match with the ones we created.
     */
    @Test
    public void compareDynamicallyCreatedFile() throws Exception {
        Dictionary d = null;
        try {
            d = Dictionary.loadDocument(tempFile);
        } catch(jakarta.xml.bind.JAXBException jaxbe) {
            fail("Loading document failed due to JAXB error: " + jaxbe);
        } catch(java.io.IOException ioe) {
            fail("Loading document failed due to file error: " + ioe.getMessage());
        }
        assertEquals(3, d.getWordClasses().size());
        WordClass n = d.getWordClasses().get(0);
        WordClass v = d.getWordClasses().get(1);
        WordClass m = d.getWordClasses().get(2);
        assertEquals("Noun", n.getName());
        assertEquals("Verb", v.getName());
        assertEquals("Mystery", m.getName());
        assertEquals("n", n.getAbbreviation());
        assertEquals("v", v.getAbbreviation());
        assertEquals("m", m.getAbbreviation());
        assertNull(n.getDescription());
        assertNull(v.getDescription());
        assertEquals("A very mysterious word class.", m.getDescription());
        assertEquals(2, d.getDefinitions().size());
        EntryList el1 = d.getDefinitions().get(0);
        EntryList el2 = d.getDefinitions().get(1);
        assertEquals(3, el1.size());
        assertEquals(2, el2.size());
        Entry e1 = el1.get(0);
        Entry e2 = el1.get(1);
        Entry e3 = el1.get(2);
        Entry e4 = el2.get(0);
        Entry e5 = el2.get(1);
        assertEquals("foo", e1.getTerm());
        assertEquals("A person who knows nothing.", e1.getDefinition());
        assertFalse(e1.isFlagged());
        assertEquals(e1.getWordClass(), n);
        assertEquals("pity", e2.getTerm());
        assertEquals("Activity which foos (q.v.) end up receiving", e2.getDefinition());
        assertFalse(e2.isFlagged());
        assertEquals(e2.getWordClass(), v);
        assertEquals("bar", e3.getTerm());
        assertEquals("An epic weapon of ultimate smackdown", e3.getDefinition());
        assertFalse(e3.isFlagged());
        assertEquals(e3.getWordClass(), n);
        assertEquals("zplepb", e4.getTerm());
        assertEquals("This isn't supposed to be on the list, or something!", e4.getDefinition());
        assertFalse(e4.isFlagged());
        assertNull(e4.getWordClass());
        assertEquals("grrlubub", e5.getTerm());
        assertEquals("Your guess is as good or mine, even if it's documented", e5.getDefinition());
        assertFalse(e5.isFlagged());
        assertEquals(e5.getWordClass(), m);
    }

    @AfterAll
    public static void deleteTempFile() {
        tempFile.delete();
    }
}