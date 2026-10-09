package org.beastwithin.conmandictionary

import jakarta.xml.bind.JAXBException
import org.beastwithin.conmandictionary.document.Dictionary
import org.junit.jupiter.api.*
import java.io.File
import java.io.IOException

class MergeTest {
    private val testDir = "src/test/resources/"
    private val complexFileName = testDir + "complexfile.xml"
    private val complexFile2Name = testDir + "complexfile2.xml"
    private val mergeResultFileName = testDir + "complexfiles_mergedbyhand.xml"

    @Test
    fun mergeFile() {
        var dict1: Dictionary? = null
        var dict2: Dictionary? = null

        // Load up the first file.
        try {
            dict1 = Dictionary.loadDocument(File(complexFileName))
        } catch (sxe: JAXBException) {
            fail("Loading document failed due to JAXB error: ${sxe.message}")
        } catch (ioe: IOException) {
            fail("Loading document failed due to file error: ${ioe.message}")
        }
        if (dict1 == null) fail("Some odd error when loading dictionary document?")

        // Merge entries from second file.
        try {
            dict1.mergeEntriesFrom(File(complexFile2Name))
        } catch (sxe: JAXBException) {
            fail("Merging document failed due to JAXB error: ${sxe.message}")
        } catch (ioe: IOException) {
            fail("Merging document failed due to file error: ${ioe.message}")
        }

        // Load up a third file that has been merged by hand.
        try {
            dict2 = Dictionary.loadDocument(File(mergeResultFileName))
        } catch (sxe: JAXBException) {
            fail("Loading second document failed due to JAXB error: ${sxe.message}")
        } catch (ioe: IOException) {
            fail("Loading second document failed due to file error: ${ioe.message}")
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
        if (!dict1.equals(dict2)) {
            fail("The merged documents differ.")
        }
    }
}