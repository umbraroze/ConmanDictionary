package org.beastwithin.conmandictionary

import org.junit.jupiter.api.Assertions.fail
import org.junit.jupiter.api.Test

import org.beastwithin.conmandictionary.document.Dictionary;
import java.io.File
import java.io.IOException
import org.xml.sax.SAXException

class ValidateTests {
    @Test
    fun validateDocument() {
        try {
            val file = File("src/test/resources/simplefile.xml")
            Dictionary.validateFile(file)
        } catch (iae: IllegalArgumentException) {
            fail("Error file finding simplefile: ${iae.message}")
        } catch (saxe: SAXException) {
            fail("Error while parsing simplefile: ${saxe.message}")
        } catch (ioe: IOException) {
            fail("Error while loading simplefile: ${ioe.message}")
        }
    }
}