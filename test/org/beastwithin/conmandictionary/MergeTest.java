
package org.beastwithin.conmandictionary;

import java.io.*;
import javax.xml.bind.*;
import org.junit.*;
import static org.junit.Assert.*;

public class MergeTest {

    public MergeTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }


    @Before
    public void setUp() {
    }

    @Test
    @Ignore
    public void mergeFile() {
        Dictionary d = null;
        try {
            d = Dictionary.loadDocument(new File("test/org/beastwithin/conmandictionary/complexfile.xml"));
        } catch(JAXBException sxe) {
            fail("Loading document failed due to JAXB error: " + sxe.getMessage());
        } catch(java.io.IOException ioe) {
            fail("Loading document failed due to file error: " + ioe.getMessage());
        }
        if(d == null)
            fail("Some odd error when loading dictionary document?");
        try {
            d.mergeEntriesFrom(new File("test/org/beastwithin/conmandictionary/complexfile2.xml"));
        } catch(JAXBException sxe) {
            fail("Merging document failed due to JAXB error: " + sxe.getMessage());
        } catch(java.io.IOException ioe) {
            fail("Merging document failed due to file error: " + ioe.getMessage());
        }
        // TODO: Compare the results.
        /*
        try {
            d.save("/tmp/mergeresults.xml");
        } catch (JAXBException sxe) {
            fail("Saving document failed due to JAXB error: " + sxe.getMessage());
        } catch (java.io.IOException ioe) {
            fail("Saving document failed due to file error: " + ioe.getMessage());
        }
        */
    }

    @After
    public void tearDown() {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

}