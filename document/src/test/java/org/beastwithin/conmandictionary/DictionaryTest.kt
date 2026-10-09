package org.beastwithin.conmandictionary

import jakarta.xml.bind.JAXBException
import org.beastwithin.conmandictionary.document.Dictionary
import org.junit.jupiter.api.*
import org.xml.sax.SAXException
import java.io.File
import java.io.IOException

class DictionaryTest {
    private val testDir = "src/test/resources/"
    private val simpleFileName = testDir + "simplefile.xml"
    private val complexFileName = testDir + "complexfile.xml"

    /**
     * Test of validation of simple files.
     */
    @Test
    fun validateSimpleFile() {
        try {
            Dictionary.validateFile(File(simpleFileName))
        } catch (sxe: SAXException) {
            fail("Validation of a valid document failed: ${sxe.message}")
        } catch (ioe: IOException) {
            fail("Validation of a document failed due to file error: ${ioe.message}")
        }
    }

    /**
     * Test of validation of complex files.
     */
    @Test
    fun validateComplexFile() {
        try {
            Dictionary.validateFile(File(complexFileName))
        } catch (sxe: SAXException) {
            fail("Validation of a valid document failed: ${sxe.message}")
        } catch (ioe: IOException) {
            fail("Validation of a document failed due to file error: ${ioe.message}")
        }
    }

    /**
     * Test loading of simple files.
     */
    @Test
    fun loadSimpleFile() {
        try {
            val d = Dictionary.loadDocument(File(simpleFileName))
        } catch (jaxbe: JAXBException) {
            fail("Loading document failed due to JAXB error: ${jaxbe.message}")
        } catch (ioe: IOException) {
            fail("Loading document failed due to file error: ${ioe.message}")
        }
    }

    /**
     * Test loading of complex files.
     */
    @Test
    fun loadComplexFile() {
        try {
            val d = Dictionary.loadDocument(File(complexFileName))
        } catch (jaxbe: JAXBException) {
            fail("Loading document failed due to JAXB error: ${jaxbe.message}")
        } catch (ioe: IOException) {
            fail("Loading document failed due to file error: ${ioe.message}")
        }
    }
}