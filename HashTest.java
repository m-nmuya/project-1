import student.TestCase;

/**
 * @author Maanasa Ramakrishnan (maanasar)
           Marie Muya (mariem26)
 * @version 2024.09.20
 */
public class HashTest extends TestCase {
    
    private Hash testHash;
    
    /**
     * Sets up the tests that follow. In general, used for initialization
     */
    public void setUp() {
        // create a test instance of hash
        testHash = new Hash(2);
    }


    /**
     * Check out the sfold method
     *
     * @throws Exception
     *             either a IOException or FileNotFoundException
     */
    public void testSfold() throws Exception {
        assertTrue(Hash.h("a", 10000) == 97);
        assertTrue(Hash.h("b", 10000) == 98);
        assertTrue(Hash.h("aaaa", 10000) == 1873);
        assertTrue(Hash.h("aaab", 10000) == 9089);
        assertTrue(Hash.h("baaa", 10000) == 1874);
        assertTrue(Hash.h("aaaaaaa", 10000) == 3794);
        assertTrue(Hash.h("Long Lonesome Blues", 10000) == 4635);
        assertTrue(Hash.h("Long   Lonesome Blues", 10000) == 4159);
        assertTrue(Hash.h("long Lonesome Blues", 10000) == 4667);
    }
    
    /**
     * Test the insert method and verify that elements can be found afterward.
     */
    public void testInsertAndFind() {
        Node node1 = new Node("Hit Different");
        Node node2 = new Node("Casual");

        testHash.insert("The Neptunes", node1);
        testHash.insert("Chappell Roan", node2);
        testHash.print();

        // Verify that inserted elements can be found
        assertNotNull(testHash.find("The Neptunes"));
        assertNotNull(testHash.find("Chappell Roan"));
        
        assertEquals("Hit Different", testHash.find("The Neptunes").getNode().getData());
        assertEquals("Casual", testHash.find("Chappell Roan").getNode().getData());
    }

    /**
     * Test removing elements and ensure they can no longer be found.
     */
    public void testRemove() {
        Node node1 = new Node("Juna");
        Node node2 = new Node("Juno");

        testHash.insert("Clairo", node1);
        testHash.insert("Sabrina Carpenter", node2);
        
        // Remove elements
        testHash.print();
        assertTrue(testHash.remove("Clairo"));
        assertFalse(testHash.remove("SZA"));
        testHash.print();
        
        // Verify that removed elements can no longer be found
        assertNull(testHash.find("Clairo"));
        assertNotNull(testHash.find("Sabrina Carpenter")); 
    }

    /**
     * Test the resize functionality when the table reaches capacity.
     */
    public void testResize() {
        Hash hash2 = new Hash(2);  // Small initial size to trigger resizing

        // Insert enough elements to trigger resize
        hash2.insert("Clairo", new Node("JukeBox"));
        hash2.insert("SZA", new Node("PartyRockAnthem"));
        hash2.print();
        
        // This should trigger a resize
        hash2.insert("Tyler", new Node("Juno"));
        hash2.print();
        
        // Check that the table resized and all elements are still present
        assertNotNull(hash2.find("Clairo"));
        assertNotNull(hash2.find("SZA"));
        assertNotNull(hash2.find("Tyler"));
    }
    
    public void testPrintTombstone() {
        Hash hash3 = new Hash(2); // Create a new hash table with size 10

        // Insert an element
        Node node1 = new Node("Hit Different");
        Node node2 = new Node(null);
        hash3.insert("The Neptunes", node1);
        hash3.insert("TOMBSTONE", node2);
        hash3.print();

        // Remove the element to create a tombstone
        hash3.remove("The Neptunes");

        // Capture the print output (using System output capture)
        hash3.print();
        assertNotNull(hash3.find("TOMBSTONE"));

    }
    
    public void testInsertDuplicateKey() {
        // Insert an element with a key
        Node node1 = new Node("Hit Different");
        testHash.insert("The Neptunes", node1);
        testHash.print();

        // Insert another element with the same key but different data
        Node node2 = new Node("Casual");
        testHash.insert("The Neptunes", node2);
        testHash.print();

        // Check that the node associated with "The Neptunes" has been updated
        assertEquals("Casual", testHash.find("The Neptunes").getNode().getData());
    }
}
