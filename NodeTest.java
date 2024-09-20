import student.TestCase;

/**
 * This class was designed to test the DoubleLinkedList 
 *
 * @author Maanasa Ramakrishnan (maanasar)
           Marie Muya (mariem26)
 * @version 2024.09.20
 */

public class NodeTest extends TestCase {

    private Node node1;
    private Node node2;
    private Node node3;

    /**
     * Set up the test environment.
     */
    public void setUp() {
        node1 = new Node("Long Lonesome Blues");
        node2 = new Node("Ma Rainey's Black Bottom");
        node3 = new Node("Jelly Roll Blues");
    }

    /**
     * Test the constructor and data field.
     */
    public void testConstructor() {
        assertEquals("Long Lonesome Blues", node1.data);
        assertNull(node1.next);
        assertNull(node1.prev);

        assertEquals("Ma Rainey's Black Bottom", node2.data);
        assertNull(node2.next);
        assertNull(node2.prev);
    }

    /**
     * Test setting the next node.
     */
    public void testSetNext() {
        node1.next = node2;
        assertEquals(node2, node1.next);
    }

    /**
     * Test setting the previous node.
     */
    public void testSetPrev() {
        node2.prev = node1;
        assertEquals(node1, node2.prev);
    }

    /**
     * Test circular connection between nodes.
     */
    public void testCircularConnections() {
        node1.next = node2;
        node2.prev = node1;

        assertEquals(node2, node1.next);
        assertEquals(node1, node2.prev);
    }
    
    /**
     * Test the setData and getData methods.
     */
    public void testSetAndGetData() {
        // Test getData
        assertEquals("Long Lonesome Blues", node1.getData());
        assertEquals("Ma Rainey's Black Bottom", node2.getData());

        // Test setData
        node1.setData("St. Louis Blues");
        assertEquals("St. Louis Blues", node1.getData());

        node2.setData("Worried Man Blues");
        assertEquals("Worried Man Blues", node2.getData());
    }

    /**
     * Test setting next and prev to null explicitly.
     */
    public void testSetNextAndPrevToNull() {
        // Initially, next and prev are null
        assertNull(node1.getNext());
        assertNull(node1.getPrev());

        // Set next and prev to other nodes
        node1.setNext(node2);
        node1.setPrev(node3);

        assertEquals(node2, node1.getNext());
        assertEquals(node3, node1.getPrev());

        // Set next and prev back to null
        node1.setNext(null);
        node1.setPrev(null);

        assertNull(node1.getNext());
        assertNull(node1.getPrev());
    }

    /**
     * Test multiple nodes linked together.
     */
    public void testMultipleNodeLinks() {
        // Link nodes together
        node1.setNext(node2);
        node2.setNext(node3);
        node2.setPrev(node1);
        node3.setPrev(node2);

        // Test forward linking
        assertEquals(node2, node1.getNext());
        assertEquals(node3, node2.getNext());

        // Test backward linking
        assertEquals(node1, node2.getPrev());
        assertEquals(node2, node3.getPrev());
    }
}
