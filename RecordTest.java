import student.TestCase;

/**
 * This class was designed to test the DoubleLinkedList 
 *
 * @author Maanasa Ramakrishnan (maanasar)
           Marie Muya (mariem26)
 * @version 2024.09.20
 */

public class RecordTest extends TestCase {
    
    private Record testRecord;
    private Node testNode;

    /**
     * Set up the test environment.
     */
    public void setUp() {
        testNode = new Node("Long Lonesome Blues");
        testRecord = new Record("Blind Lemon Jefferson", testNode);
    }

    /**
     * Test the constructor and getter methods.
     */
    public void testConstructorAndGetters() {
        assertEquals("Blind Lemon Jefferson", testRecord.getKey());
        assertEquals(testNode, testRecord.getNode());
    }

    /**
     * Test the setKey method.
     */
    public void testSetKey() {
        testRecord.setKey("Ma Rainey");
        assertEquals("Ma Rainey", testRecord.getKey());
    }

    /**
     * Test the setNode method.
     */
    public void testSetNode() {
        Node newNode = new Node("Ma Rainey's Black Bottom");
        testRecord.setNode(newNode);
        assertEquals(newNode, testRecord.getNode());
    }

    /**
     * Test setting and getting node data.
     */
    public void testNodeData() {
        Node newNode = new Node("Cross Road Blues");
        testRecord.setNode(newNode);
        assertEquals("Cross Road Blues", testRecord.getNode().getData());

        // Change the data and verify
        newNode.setData("Hellhound on My Trail");
        assertEquals("Hellhound on My Trail", testRecord.getNode().getData());
    }

    /**
     * Test node chaining with next and prev.
     */
    public void testNodeNextAndPrev() {
        Node firstNode = new Node("Blind Willie McTell");
        Node secondNode = new Node("Lead Belly");
        
        firstNode.setNext(secondNode);  
        secondNode.setPrev(firstNode);  
        
        assertEquals(secondNode, firstNode.getNext());  
        assertEquals(firstNode, secondNode.getPrev());  
    }

    /**
     * Test setting a null node.
     */
    public void testSetNullNode() {
        testRecord.setNode(null);
        assertNull(testRecord.getNode());  
    }
}
