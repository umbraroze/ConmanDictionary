
package org.beastwithin.conmandictionary;

import java.io.File;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author wwwwolf
 */
public class DictionaryTest {

    public DictionaryTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of document validation.
     */
    @Test
    public void validateFile() throws Exception {
        System.out.println("validateFile");
        try {
            Dictionary.validateFile(new File("test/org/beastwithin/conmandictionary/test.dictx"));
        } catch(org.xml.sax.SAXException sxe) {
            fail("Validation of a valid document failed: " + sxe.getMessage());
        } catch(java.io.IOException ioe) {
            fail("Validation of a document failed due to file error: " + ioe.getMessage());
        }
    }

    /**
     * Test of loadDocument method, of class Dictionary.
     */
    /*
    @Test
    public void loadDocument() throws Exception {
        System.out.println("loadDocument");
        File file = null;
        Dictionary expResult = null;
        Dictionary result = Dictionary.loadDocument(file);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }*/

    /**
     * Test of isUnsavedChanges method, of class Dictionary.
     */
    /*
    @Test
    public void isUnsavedChanges() {
        System.out.println("isUnsavedChanges");
        Dictionary instance = new Dictionary();
        boolean expResult = false;
        boolean result = instance.isUnsavedChanges();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }*/
}