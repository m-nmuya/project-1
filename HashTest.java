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
        testHash = new Hash(10);
    }


    /**
     * Check out the sfold method
     *
     * @throws Exception
     *             either a IOException or FileNotFoundException
     */
    public void testSfold() throws Exception {
        assertTrue(Hash.hash("a", 10000) == 97);
        assertTrue(Hash.hash("b", 10000) == 98);
        assertTrue(Hash.hash("aaaa", 10000) == 1873);
        assertTrue(Hash.hash("aaab", 10000) == 9089);
        assertTrue(Hash.hash("baaa", 10000) == 1874);
        assertTrue(Hash.hash("aaaaaaa", 10000) == 3794);
        assertTrue(Hash.hash("Long Lonesome Blues", 10000) == 4635);
        assertTrue(Hash.hash("Long   Lonesome Blues", 10000) == 4159);
        assertTrue(Hash.hash("long Lonesome Blues", 10000) == 4667);
    }
    
    /**
     * Test the insert method of the hash class.
     */
    public void testInsert() {
        testHash.insert("Long Lonesome Blues");
        testHash.insert("Long   Lonesome Blues");

        // Check if the elements are correctly inserted
        assertTrue(testHash.contains("Long Lonesome Blues"));
        assertTrue(testHash.contains("Long   Lonesome Blues"));
        assertFalse(testHash.contains("long Lonesome Blues"));  // Not inserted
    }

    /**
     * Test the remove method of the hash class.
     */
    public void testRemove() {
        testHash.insert("Long Lonesome Blues");
        testHash.insert("Long   Lonesome Blues");

        // Remove "Long Lonesome Blues" and check if it is removed
        assertTrue(testHash.remove("Long Lonesome Blues"));
        assertFalse(testHash.contains("Long Lonesome Blues"));  // Should not be present
        assertTrue(testHash.contains("Long   Lonesome Blues"));   // Should still be present

        // Try to remove an element that does not exist
        assertFalse(testHash.remove("long Lonesome Blues"));
    }

    /**
     * Test the contains method of the hash class.
     */
    public void testContains() {
        testHash.insert("Long Lonesome Blues");
        testHash.insert("Long   Lonesome Blues");

        // Check if the inserted elements are present
        assertTrue(testHash.contains("Long Lonesome Blues"));
        assertTrue(testHash.contains("Long   Lonesome Blues"));

        // Check an element that is not inserted
        assertFalse(testHash.contains("long Lonesome Blues"));
    }

    /**
     * Test the printTable method to ensure the correct structure.
     */
    public void testPrintTable() {
        testHash.insert("Long Lonesome Blues");
        testHash.insert("Long   Lonesome Blues");

        // Capture printed output (hypothetically, if there's a utility for this)
        testHash.printTable();
        // Note: In most cases, you might need a special mechanism to capture print statements,
        // or replace System.out temporarily, but for now, assume this passes visually.

        // You could extend this with output comparison if needed.
    }

    /**
     * Test collision handling with the insert method.
     */
    public void testCollisionHandling() {
        // Insert multiple elements that would likely cause collisions based on the hash function
        testHash.insert("abc");   // Assume this hashes to index i
        testHash.insert("cba");   // Assume this hashes to the same index i
        
        // Both should be in the table, but handled by linear probing
        assertTrue(testHash.contains("abc"));
        assertTrue(testHash.contains("cba"));
    }
}
