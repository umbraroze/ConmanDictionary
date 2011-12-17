
package org.beastwithin.conmandictionary;

import org.junit.*;
import static org.junit.Assert.*;

public class DictionaryTest {

    public DictionaryTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    /**
     * Test of validation of simple files.
     */
    @Test
    public void validateSimpleFile() throws Exception {
        try {
            Dictionary.validateFile(new java.io.File("test/org/beastwithin/conmandictionary/simplefile.xml"));
        } catch(org.xml.sax.SAXException sxe) {
            fail("Validation of a valid document failed: " + sxe.getMessage());
        } catch(java.io.IOException ioe) {
            fail("Validation of a document failed due to file error: " + ioe.getMessage());
        }
    }

    /**
     * Test of validation of complex files.
     */
    @Test
    public void validateComplexFile() throws Exception {
        try {
            Dictionary.validateFile(new java.io.File("test/org/beastwithin/conmandictionary/complexfile.xml"));
        } catch(org.xml.sax.SAXException sxe) {
            fail("Validation of a valid document failed: " + sxe.getMessage());
        } catch(java.io.IOException ioe) {
            fail("Validation of a document failed due to file error: " + ioe.getMessage());
        }
    }

    /**
     * Test loading of simple files.
     */
    @Test
    public void loadSimpleFile() throws Exception {
        try {
            Dictionary d = Dictionary.loadDocument(new java.io.File("test/org/beastwithin/conmandictionary/simplefile.xml"));
        } catch(javax.xml.bind.JAXBException jaxbe) {
            fail("Loading document failed due to JAXB error: " + jaxbe.getMessage());
        } catch(java.io.IOException ioe) {
            fail("Loading document failed due to file error: " + ioe.getMessage());
        }
    }

    /**
     * Test loading of complex files.
     */
    @Test
    public void loadComplexFile() throws Exception {
        try {
            Dictionary d = Dictionary.loadDocument(new java.io.File("test/org/beastwithin/conmandictionary/complexfile.xml"));
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
    }
}