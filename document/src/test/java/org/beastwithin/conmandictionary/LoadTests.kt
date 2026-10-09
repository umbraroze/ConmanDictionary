package org.beastwithin.conmandictionary

import org.junit.jupiter.api.Assertions.fail
import org.junit.jupiter.api.Test

import org.beastwithin.conmandictionary.document.Dictionary;
import java.io.File
import java.io.IOException
import jakarta.xml.bind.JAXBException

class LoadTests {
    @Test
    fun loadFileSuccessfully() {
        try {
            val file = File("src/test/resources/simplefile.xml")
            Dictionary.loadDocument(file)
        } catch (iae: IllegalArgumentException) {
            fail("Error file finding simplefile: ${iae.message}")
        } catch (jaxbe: JAXBException) {
            fail("Error while parsing simplefile: ${jaxbe.message}")
        } catch (ioe: IOException) {
            fail("Error while loading simplefile: ${ioe.message}")
        }
    }
}