package org.beastwithin.conmandictionary;

import org.beastwithin.conmandictionary.document.Dictionary;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class DictionaryTest {

    private final String testDir = "src/test/resources/";
    private final String simpleFileName = testDir+"simplefile.xml";
    private final String complexFileName = testDir+"complexfile.xml";

    public DictionaryTest() {
    }

    @BeforeAll
    public static void setUp() {
    }

    /**
     * Test of validation of simple files.
     */
    @Test
    public void validateSimpleFile() throws Exception {
        try {
            Dictionary.validateFile(new java.io.File(simpleFileName));
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
            Dictionary.validateFile(new java.io.File(complexFileName));
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
            Dictionary d = Dictionary.loadDocument(new java.io.File(simpleFileName));
        } catch(jakarta.xml.bind.JAXBException jaxbe) {
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
            Dictionary d = Dictionary.loadDocument(new java.io.File(complexFileName));
        } catch(jakarta.xml.bind.JAXBException jaxbe) {
            fail("Loading document failed due to JAXB error: " + jaxbe.getMessage());
        } catch(java.io.IOException ioe) {
            fail("Loading document failed due to file error: " + ioe.getMessage());
        }
    }

    @AfterAll
    public static void tearDown() {
    }
}