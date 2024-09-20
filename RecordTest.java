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
    private DoubleLinkedList.Node testNode;

    /**
     * Set up the test environment.
     */
    public void setUp() {
        testNode = new DoubleLinkedList.Node("Long Lonesome Blues");
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
        DoubleLinkedList.Node newNode = new DoubleLinkedList.Node("Ma Rainey's Black Bottom");
        testRecord.setNode(newNode);
        assertEquals(newNode, testRecord.getNode());
    }
}
