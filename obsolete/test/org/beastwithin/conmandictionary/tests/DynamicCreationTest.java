package org.beastwithin.conmandictionary.tests;

import org.beastwithin.conmandictionary.document.Entry;
import org.beastwithin.conmandictionary.document.Dictionary;
import org.beastwithin.conmandictionary.document.EntryList;
import org.beastwithin.conmandictionary.document.WordClass;
import java.io.*;
import javax.xml.bind.JAXBException;
import org.junit.*;
import static org.junit.Assert.*;

public class DynamicCreationTest {
    static File tempFile = null;

    public DynamicCreationTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        try {
            tempFile = File.createTempFile("wordclasstest.", ".xml");
            System.out.println("Created temporary file: " + tempFile.getAbsolutePath());
        } catch(IOException ioe) {
            fail("Internal error: Couldn't create temp file.");
        }
    }

    @Before
    public void setUp() {
    }

    /**
     * Create a file dynamically.
     */
    @Before
    public void createFile() throws Exception {
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
            fail("Saving file failed due to JAXB error: " + jaxbe.toString());
        }
    }

    /**
     * Test unmarshalling the file that we just created programmatically.
     */
    @Test
    public void loadDynamicallyCreatedFile() throws Exception {
        try {
            Dictionary d = Dictionary.loadDocument(tempFile);
        } catch(javax.xml.bind.JAXBException jaxbe) {
            fail("Loading document failed due to JAXB error: " + jaxbe.toString());
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
        } catch(javax.xml.bind.JAXBException jaxbe) {
            fail("Loading document failed due to JAXB error: " + jaxbe.toString());
        } catch(java.io.IOException ioe) {
            fail("Loading document failed due to file error: " + ioe.getMessage());
        }
        assertTrue(d.getWordClasses().size() == 3);
        WordClass n = d.getWordClasses().get(0);
        WordClass v = d.getWordClasses().get(1);
        WordClass m = d.getWordClasses().get(2);
        assertTrue(n.getName().equals("Noun"));
        assertTrue(v.getName().equals("Verb"));
        assertTrue(m.getName().equals("Mystery"));
        assertTrue(n.getAbbreviation().equals("n"));
        assertTrue(v.getAbbreviation().equals("v"));
        assertTrue(m.getAbbreviation().equals("m"));
        assertTrue(n.getDescription() == null);
        assertTrue(v.getDescription() == null);
        assertTrue(m.getDescription().equals("A very mysterious word class."));
        assertTrue(d.getDefinitions().size() == 2);
        EntryList el1 = d.getDefinitions().get(0);
        EntryList el2 = d.getDefinitions().get(1);
        assertTrue(el1.size() == 3);
        assertTrue(el2.size() == 2);
        Entry e1 = el1.get(0);
        Entry e2 = el1.get(1);
        Entry e3 = el1.get(2);
        Entry e4 = el2.get(0);
        Entry e5 = el2.get(1);
        assertTrue(e1.getTerm().equals("foo"));
        assertTrue(e1.getDefinition().equals("A person who knows nothing."));
        assertTrue(e1.isFlagged() == false);
        assertTrue(e1.getWordClass().equals(n));
        assertTrue(e2.getTerm().equals("pity"));
        assertTrue(e2.getDefinition().equals("Activity which foos (q.v.) end up receiving"));
        assertTrue(e2.isFlagged() == false);
        assertTrue(e2.getWordClass().equals(v));
        assertTrue(e3.getTerm().equals("bar"));
        assertTrue(e3.getDefinition().equals("An epic weapon of ultimate smackdown"));
        assertTrue(e3.isFlagged() == false);
        assertTrue(e3.getWordClass().equals(n));
        assertTrue(e4.getTerm().equals("zplepb"));
        assertTrue(e4.getDefinition().equals("This isn't supposed to be on the list, or something!"));
        assertTrue(e4.isFlagged() == false);
        assertTrue(e4.getWordClass() == null);
        assertTrue(e5.getTerm().equals("grrlubub"));
        assertTrue(e5.getDefinition().equals("Your guess is as good or mine, even if it's documented"));
        assertTrue(e5.isFlagged() == false);
        assertTrue(e5.getWordClass().equals(m));
    }

    @After
    public void tearDown() {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        tempFile.delete();
    }
}