
package org.beastwithin.conmandictionary;

import org.junit.*;
import static org.junit.Assert.*;

public class EntryTest {
    public EntryTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    /**
     * Tests entry comparisons using equals().
     */
    @Test
    public void entryComparison() throws Exception {
        WordClass w = new WordClass("Class","c","A redundant word class");
        Entry a = new Entry("test", "A rigorous whatchamabangit", false, w);
        Entry b = new Entry("taste", "What is not found in this project", false, null);
        Entry c = new Entry("test", "A rigorous whatchamabangit", false, w);
        assertTrue(a.equals(a)); // Same object.
        assertFalse(a.equals(b)); // Separate objects, different values.
        assertTrue(a.equals(c)); // Separate objects, identical values.
    }
    /**
     * Tests entry list comparisons using equals().
     */
    @Test
    public void entryListComparison() throws Exception {
        WordClass w = new WordClass("Class","c","A redundant word class");
        WordClass x = new WordClass("Another class","c2","Even more redundant word class");
        Entry a = new Entry("test", "A rigorous whatchamabangit", false, w);
        Entry b = new Entry("taste", "What is not found in this project", false, null);
        Entry c = new Entry("taser", "What is probably needed here", false, null);
        Entry d = new Entry("tasher", "A taser that is certified kosher", true, w);
        Entry e = new Entry("тасс", "Some Russian organisation or something", false, x);
        EntryList l1 = new EntryList();
        l1.add(a);
        l1.add(b);
        EntryList l2 = new EntryList();
        l2.add(a);
        l2.add(b);
        assertTrue(l1.equals(l2));
        l1.add(c);
        assertFalse(l1.equals(l2)); // Inequality of size if anything...
        l2.add(d);
        assertFalse(l1.equals(l2)); // Same size, but different contents
        l1.add(e);
        l2.add(e);
        assertFalse(l1.equals(l2)); // Now this is just silly.
        assertTrue(l1.equals(l1)); // But this should be pretty normal...
        assertTrue(l2.equals(l2)); // ...just like this!
    }

    @After
    public void tearDown() {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }


    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}

}