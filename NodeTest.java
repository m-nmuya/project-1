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

    /**
     * Set up the test environment.
     */
    public void setUp() {
        node1 = new Node("Long Lonesome Blues");
        node2 = new Node("Ma Rainey's Black Bottom");
    }

    /**
     * Test the constructor and data field.
     */
    public void testConstructor() {
        assertEquals("Song A", node1.data);
        assertNull(node1.next);
        assertNull(node1.prev);

        assertEquals("Song B", node2.data);
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
}
