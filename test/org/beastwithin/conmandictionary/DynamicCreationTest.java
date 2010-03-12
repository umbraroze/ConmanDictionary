/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.beastwithin.conmandictionary;

import java.io.*;
import javax.xml.bind.JAXBException;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author wwwwolf
 */
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
     * Test of validation of simple files.
     */
    @Test
    public void createFile() throws Exception {
        Dictionary d = new Dictionary();
        WordClass n = new WordClass("Noun","n.");
        WordClass v = new WordClass("Verb","v.");
        WordClass m = new WordClass("Mystery","m.","A very mysterious word class.");
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
        } catch(JAXBException jaxe) {
            fail("Saving file failed due to JAXB error: " + jaxe.getMessage());
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
            fail("Loading document failed due to JAXB error: " + jaxbe.getMessage());
        } catch(java.io.IOException ioe) {
            fail("Loading document failed due to file error: " + ioe.getMessage());
        }
    }

    @After
    public void tearDown() {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        tempFile.delete();
    }
}