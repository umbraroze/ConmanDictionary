package org.beastwithin.conmandictionary

import jakarta.xml.bind.JAXBException
import org.beastwithin.conmandictionary.document.Dictionary
import org.beastwithin.conmandictionary.document.Entry
import org.beastwithin.conmandictionary.document.WordClass
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import java.io.File
import java.io.IOException

class DynamicCreationTest {
    companion object {
        var tempFile: File? = null

        @JvmStatic
        @BeforeAll
        fun createTempFile() {
            try {
                tempFile = File.createTempFile("wordclasstest.", ".xml")
                println("Created temporary file: " + tempFile!!.absolutePath)
            } catch (ioe: IOException) {
                fail("Internal error: Couldn't create temp file.")
            }
        }

        /**
         * Create a file dynamically.
         */
        @JvmStatic
        @BeforeAll
        fun createFile() {
            val d = Dictionary()
            val n = WordClass("Noun", "n")
            val v = WordClass("Verb", "v")
            val m = WordClass("Mystery", "m", "A very mysterious word class.")
            d.getWordClasses().add(n)
            d.getWordClasses().add(v)
            d.getWordClasses().add(m)
            val e1 = Entry("foo", "A person who knows nothing.", false, n)
            val e2 = Entry("pity", "Activity which foos (q.v.) end up receiving", false, v)
            val e3 = Entry("bar", "An epic weapon of ultimate smackdown", false, n)
            val e4 = Entry("zplepb", "This isn't supposed to be on the list, or something!")
            val e5 = Entry("grrlubub", "Your guess is as good or mine, even if it's documented", false, m)
            d.getDefinitions().get(0).add(e1)
            d.getDefinitions().get(0).add(e2)
            d.getDefinitions().get(0).add(e3)
            d.getDefinitions().get(1).add(e4)
            d.getDefinitions().get(1).add(e5)
            try {
                d.save(tempFile)
            } catch (ioe: IOException) {
                fail("Saving file failed due to file error: ${ioe.message}")
            } catch (jaxbe: JAXBException) {
                fail("Saving file failed due to JAXB error: ${jaxbe.message}")
            }
        }

        @JvmStatic
        @AfterAll
        fun deleteTempFile() {
            tempFile!!.delete()
        }
    }

    /**
     * Test unmarshalling the file that we just created programmatically.
     */
    @Test
    fun loadDynamicallyCreatedFile() {
        try {
            val d = Dictionary.loadDocument(tempFile)
        } catch (jaxbe: JAXBException) {
            fail("Loading document failed due to JAXB error: ${jaxbe.message}")
        } catch (ioe: IOException) {
            fail("Loading document failed due to file error: ${ioe.message}")
        }
    }

    /**
     * Test unmarshalling the file that we just created programmatically, and
     * check that the contents match with the ones we created.
     */
    @Test
    fun compareDynamicallyCreatedFile() {
        var d: Dictionary? = null
        try {
            d = Dictionary.loadDocument(tempFile)
        } catch (jaxbe: JAXBException) {
            fail("Loading document failed due to JAXB error: ${jaxbe.message}")
        } catch (ioe: IOException) {
            fail("Loading document failed due to file error: ${ioe.message}")
        }
        assertEquals(3, d!!.getWordClasses().size)
        val n = d.getWordClasses()[0]
        val v = d.getWordClasses()[1]
        val m = d.getWordClasses()[2]
        assertEquals("Noun", n.getName())
        assertEquals("Verb", v.getName())
        assertEquals("Mystery", m.getName())
        assertEquals("n", n.getAbbreviation())
        assertEquals("v", v.getAbbreviation())
        assertEquals("m", m.getAbbreviation())
        assertNull(n.getDescription())
        assertNull(v.getDescription())
        assertEquals("A very mysterious word class.", m.getDescription())
        assertEquals(2, d.getDefinitions().size)
        val el1 = d.getDefinitions()[0]
        val el2 = d.getDefinitions()[1]
        assertEquals(3, el1.size())
        assertEquals(2, el2.size())
        val e1 = el1[0]
        val e2 = el1[1]
        val e3 = el1[2]
        val e4 = el2[0]
        val e5 = el2[1]
        assertEquals("foo", e1.getTerm())
        assertEquals("A person who knows nothing.", e1.getDefinition())
        assertFalse(e1.isFlagged)
        assertEquals(e1.getWordClass(), n)
        assertEquals("pity", e2.getTerm())
        assertEquals("Activity which foos (q.v.) end up receiving", e2.getDefinition())
        assertFalse(e2.isFlagged)
        assertEquals(e2.getWordClass(), v)
        assertEquals("bar", e3.getTerm())
        assertEquals("An epic weapon of ultimate smackdown", e3.getDefinition())
        assertFalse(e3.isFlagged)
        assertEquals(e3.getWordClass(), n)
        assertEquals("zplepb", e4.getTerm())
        assertEquals("This isn't supposed to be on the list, or something!", e4.getDefinition())
        assertFalse(e4.isFlagged)
        assertNull(e4.getWordClass())
        assertEquals("grrlubub", e5.getTerm())
        assertEquals("Your guess is as good or mine, even if it's documented", e5.getDefinition())
        assertFalse(e5.isFlagged)
        assertEquals(e5.getWordClass(), m)
    }
}