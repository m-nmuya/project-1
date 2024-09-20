import student.TestCase;

/**
 * This class was designed to test the DoubleLinkedList 
 *
 * @author Maanasa Ramakrishnan (maanasar)
           Marie Muya (mariem26)
 * @version 2024.09.20
 */

public class DoubleLinkedListTest extends TestCase {
    private DoubleLinkedList list;

    /**
     * Set up the tests.
     */
    public void setUp() {
        list = new DoubleLinkedList();
    }

    /**
     * Test the constructor and initial state of the list.
     */
    public void testConstructor() {
        assertNull(list.getHead());
        assertEquals(0, list.getSize());
        assertTrue(list.isEmpty());
    }

    /**
     * Test adding elements to the list.
     */
    public void testAdd() {
        list.add("Long   Lonesome Blues");
        assertEquals("Long   Lonesome Blues", list.getHead().data);
        assertEquals(1, list.getSize());

        list.add("Long Lonesome Blues");
        assertEquals("Long   Lonesome Blues", list.getHead().data); // Head remains the same
        assertEquals(2, list.getSize());
    }

    /**
     * Test removing an element from the list.
     */
    public void testRemove() {
        list.add("Long   Lonesome Blues");
        list.add("Long Lonesome Blues");
        list.add("Ma Rainey's Black Bottom");

        // Remove middle element
        list.remove("Long Lonesome Blues");
        assertFalse(list.contains("Long Lonesome Blues"));
        assertEquals(2, list.getSize());

        // Remove head element
        list.remove("Long   Lonesome Blues");
        assertEquals("Ma Rainey's Black Bottom", list.getHead().data);
        assertEquals(1, list.getSize());

        // Remove tail element
        list.remove("Ma Rainey's Black Bottom");
        assertNull(list.getHead());
        assertTrue(list.isEmpty());
    }

    /**
     * Test contains method to check if an element is in the list.
     */
    public void testContains() {
        list.add("Long   Lonesome Blues");
        list.add("Long Lonesome Blues");

        assertTrue(list.contains("Long   Lonesome Blues"));
        assertTrue(list.contains("Long Lonesome Blues"));
        assertFalse(list.contains("Ma Rainey's Black Bottom"));
    }

    /**
     * Test the printList method to ensure it prints the correct values.
     */
    public void testPrintList() {
        list.add("Long   Lonesome Blues");
        list.add("Long Lonesome Blues");

        list.printList();
        String actualOutput = systemOut().getHistory();
        String expectedOutput = "Long   Lonesome Blues Long Lonesome Blues \n";
        assertEquals(expectedOutput, actualOutput);
    }

    /**
     * Test replacing an element in the list.
     */
    public void testReplace() {
        list.add("Long   Lonesome Blues");
        list.add("Long Lonesome Blues");

        // Replace Long   Lonesome Blues with TOMBSTONE
        assertTrue(list.replace("Long   Lonesome Blues", "TOMBSTONE"));
        assertEquals("TOMBSTONE", list.getHead().data);

        // Try replacing a non-existing element
        assertFalse(list.replace("Ma Rainey's Black Bottom", "TOMBSTONE"));
    }

    /**
     * Test isEmpty method.
     */
    public void testIsEmpty() {
        assertTrue(list.isEmpty());
        list.add("Long   Lonesome Blues");
        assertFalse(list.isEmpty());
    }

    /**
     * Test the getHead method.
     */
    public void testGetHead() {
        assertNull(list.getHead());
        list.add("Long   Lonesome Blues");
        assertNotNull(list.getHead());
        assertEquals("Long   Lonesome Blues", list.getHead().data);
    }

    /**
     * Test the getSize method.
     */
    public void testGetSize() {
        assertEquals(0, list.getSize());
        list.add("Long   Lonesome Blues");
        assertEquals(1, list.getSize());
    }
}
