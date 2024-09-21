import student.TestCase;

/**
 * This class was designed to test the DoubleLinkedList 
 *
 * @author Maanasa Ramakrishnan (maanasar)
           Marie Muya (mariem26)
 * @version 2024.09.20
 */

public class GraphTest extends TestCase {
    
    private Graph graph;

    /**
     * Set up the test environment with a new graph instance.
     */
    public void setUp() {
        graph = new Graph(); // Initialize the graph
    }

    /**
     * Test the addition of nodes by indirectly testing through relationships.
     */
    public void testAddNodeViaRelationship() {
        // Add relationships, which should internally add nodes
        graph.addRelationship("Blind Lemon Jefferson", "Long Lonesome Blues");
        
        // Check if the relationship is correctly established
        assertTrue(graph.hasRelationship("Blind Lemon Jefferson", "Long Lonesome Blues"));
    }

    /**
     * Test the addition of relationships between nodes.
     */
    public void testAddRelationship() {
        graph.addRelationship("Blind Lemon Jefferson", "Long Lonesome Blues");
        graph.addRelationship("Ma Rainey", "Fixin' To Die Blues");

        // Verify both relationships exist
        assertTrue(graph.hasRelationship("Blind Lemon Jefferson", "Long Lonesome Blues"));
        assertTrue(graph.hasRelationship("Ma Rainey", "Fixin' To Die Blues"));
    }

    /**
     * Test removing a song from the graph.
     */
    public void testRemoveSong() {
        graph.addRelationship("Blind Lemon Jefferson", "Long Lonesome Blues");
        graph.addRelationship("Ma Rainey", "Ma Rainey's Black Bottom");

        graph.removeSong("PartyRockAnthem");
        graph.removeSong("Long Lonesome Blues");

        // Ensure the song has been removed and no relationship exists
        assertFalse(graph.hasRelationship("Blind Lemon Jefferson", "Long Lonesome Blues"));
    }

    /**
     * Test removing an artist from the graph.
     */
    public void testRemoveArtist() {
        graph.addRelationship("Blind Lemon Jefferson", "Long Lonesome Blues");
        graph.addRelationship("Ma Rainey", "Ma Rainey's Black Bottom");

        graph.removeArtist("Clairo");
        graph.removeArtist("Blind Lemon Jefferson");

        // Ensure the artist has been removed and no relationship exists
        assertFalse(graph.hasRelationship("Blind Lemon Jefferson", "Long Lonesome Blues"));
    }

    /**
     * Test printing the graph and connected components.
     */
    public void testPrintGraph() {
        graph.addRelationship("Blind Lemon Jefferson", "Long Lonesome Blues");
        graph.addRelationship("Ma Rainey", "Ma Rainey's Black Bottom");

        // Capture the output of the printGraph method
        graph.printGraph();
        String actualOutput = systemOut().getHistory();

        // Expected output based on two relationships
        assertTrue(actualOutput.contains("There are 2 connected components"));
        assertTrue(actualOutput.contains("The largest connected component has 2 elements"));
    }

    /**
     * Test resizing the graph arrays.
     */
    public void testResize() {
        // Add more nodes to trigger resizing
        for (int i = 0; i < 15; i++) {
            graph.addNode("Node " + i);
        }

        // Verify that the graph still functions after resizing (check relationships)
        graph.addRelationship("Node 1", "Node 2");
        assertTrue(graph.hasRelationship("Node 1", "Node 2"));
    }

    /**
     * Test if there is a relationship between an artist and a song.
     */
    public void testHasRelationship() {
        graph.addRelationship("Blind Lemon Jefferson", "Long Lonesome Blues");
        assertTrue(graph.hasRelationship("Blind Lemon Jefferson", "Long Lonesome Blues"));
        assertFalse(graph.hasRelationship("Ma Rainey", "Long Lonesome Blues")); // No relationship exists
    }
    
}
