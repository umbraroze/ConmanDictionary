package org.beastwithin.conmandictionary

import org.beastwithin.conmandictionary.document.Entry
import org.beastwithin.conmandictionary.document.EntryList
import org.beastwithin.conmandictionary.document.WordClass
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*

class EntryTest {
    /**
     * Tests entry comparisons using equals().
     */
    @Test
    fun entryComparison() {
        val w = WordClass("Class", "c", "A redundant word class")
        val a = Entry("test", "A rigorous whatchamabangit", false, w)
        val b = Entry("taste", "What is not found in this project", false, null)
        val c = Entry("test", "A rigorous whatchamabangit", false, w)
        assertTrue(a.equals(a)) // Same object.
        assertFalse(a.equals(b)) // Separate objects, different values.
        assertTrue(a.equals(c)) // Separate objects, identical values.
    }

    /**
     * Tests entry list comparisons using equals().
     */
    @Test
    fun entryListComparison() {
        val w = WordClass("Class", "c", "A redundant word class")
        val x = WordClass("Another class", "c2", "Even more redundant word class")
        val a = Entry("test", "A rigorous whatchamabangit", false, w)
        val b = Entry("taste", "What is not found in this project", false, null)
        val c = Entry("taser", "What is probably needed here", false, null)
        val d = Entry("tasher", "A taser that is certified kosher", true, w)
        val e = Entry("тасс", "Some Russian organisation or something", false, x)
        val l1 = EntryList()
        l1.add(a)
        l1.add(b)
        val l2 = EntryList()
        l2.add(a)
        l2.add(b)
        assertTrue(l1.equals(l2))
        l1.add(c)
        assertFalse(l1.equals(l2)) // Inequality of size if anything...
        l2.add(d)
        assertFalse(l1.equals(l2)) // Same size, but different contents
        l1.add(e)
        l2.add(e)
        assertFalse(l1.equals(l2)) // Now this is just silly.
        assertTrue(l1.equals(l1)) // But this should be pretty normal...
        assertTrue(l2.equals(l2)) // ...just like this!
    }
}