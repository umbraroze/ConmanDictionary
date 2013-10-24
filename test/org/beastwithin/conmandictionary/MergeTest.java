
package org.beastwithin.conmandictionary;

import java.io.*;
import javax.xml.bind.*;
import org.junit.*;
import static org.junit.Assert.*;

public class MergeTest {
    
    private final String complexFileName = "target/test-classes/complexfile.xml";
    private final String complexFile2Name = "target/test-classes/complexfile2.xml";
    private final String mergeResultFileName = "target/test-classes/complexfiles_mergedbyhand.xml";
    

    public MergeTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }


    @Before
    public void setUp() {
    }

    @Test
    public void mergeFile() {
        Dictionary dict1 = null;
        Dictionary dict2 = null;

        // Load up the first file.
        try {
            dict1 = Dictionary.loadDocument(new File(complexFileName));
        } catch(JAXBException sxe) {
            fail("Loading document failed due to JAXB error: " + sxe.getMessage());
        } catch(java.io.IOException ioe) {
            fail("Loading document failed due to file error: " + ioe.getMessage());
        }
        if(dict1 == null)
            fail("Some odd error when loading dictionary document?");

        // Merge entries from second file.
        try {
            dict1.mergeEntriesFrom(new File(complexFile2Name));
        } catch(JAXBException sxe) {
            fail("Merging document failed due to JAXB error: " + sxe.getMessage());
        } catch(java.io.IOException ioe) {
            fail("Merging document failed due to file error: " + ioe.getMessage());
        }

        // Load up a third file that has been merged by hand.
        try {
            dict2 = Dictionary.loadDocument(new File(mergeResultFileName));
        } catch(JAXBException sxe) {
            fail("Loading second document failed due to JAXB error: " + sxe.getMessage());
        } catch(java.io.IOException ioe) {
            fail("Loading second document failed due to file error: " + ioe.getMessage());
        }

        // Save the files to do comparison by hand.
        /*
        try {
            dict1.save(File.createTempFile("merged_by_machine.",".xml"));
            dict2.save(File.createTempFile("merged_by_hand.",".xml"));
        } catch(JAXBException sxe) {
            fail("Saving documents failed due to JAXB error: " + sxe.getMessage());
        } catch(java.io.IOException ioe) {
            fail("Saving documents failed due to file error: " + ioe.getMessage());
        }
        */

        // Now for the interesting part.
        if(!dict1.equals(dict2)) {
            fail("The merged documents differ.");
        }
    }

    @After
    public void tearDown() {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

}