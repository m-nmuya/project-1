import student.TestCase;

/**
 * This class was designed to test the DoubleLinkedList 
 *
 * @author Maanasa Ramakrishnan (maanasar)
           Marie Muya (mariem26)
 * @version 2024.09.20
 */

public class ControllerTest extends TestCase {
    private Controller controller;
    private Graph graph; // Assume Graph is a mockable class
    
    /**
     * Set up the test environment.
     */
    public void setUp() {
        graph = new Graph();
        controller = new Controller(graph, 10); // Initialize with a small hash table size
    }

    /**
     * Test inserting an artist and a song.
     */
    public void testInsertArtistAndSong() {
        controller.insert("Blind Lemon Jefferson", "Long Lonesome Blues");

        String actualOutput = systemOut().getHistory();

        String expectedOutput = "|Blind Lemon Jefferson| is added to the Artist database.\n" +
                                "|Long Lonesome Blues| is added to the Song database.\n";

        assertFuzzyEquals(expectedOutput, actualOutput);
    }

    /**
     * Test printing artists.
     */
    public void testPrintArtists() {
        controller.insert("Blind Lemon Jefferson", "Long Lonesome Blues");
        controller.insert("Ma Rainey", "Ma Rainey's Black Bottom");

        controller.printArtists();

        String actualOutput = systemOut().getHistory();

        String expectedOutput = "0: |Blind Lemon Jefferson|\n" +
                                "7: |Ma Rainey|\n" +
                                "total artists: 2\n";

        assertFuzzyEquals(expectedOutput, actualOutput);
    }

    /**
     * Test printing songs.
     */
    public void testPrintSongs() {
        controller.insert("Ma Rainey", "Fixin' To Die Blues");
        controller.insert("Blind Lemon Jefferson", "Long Lonesome Blues");

        controller.printSongs();

        String actualOutput = systemOut().getHistory();

        String expectedOutput = "1: |Fixin' To Die Blues|\n" +
                                "15: |Long Lonesome Blues|\n" +
                                "total songs: 2\n";

        assertFuzzyEquals(expectedOutput, actualOutput);
    }

    /**
     * Test doubling the size of the hash table.
     */
    public void testDoubleTableSize() {
        controller.insert("Blind Lemon Jefferson", "Long Lonesome Blues");
        controller.insert("Ma Rainey", "Mississippi Boweavil Blues");
        // Add more entries to trigger table doubling
        controller.insert("Ma Rainey", "Fixin' To Die Blues");

    }

    /**
     * Test printing the graph.
     */
    public void testPrintGraph() {
        controller.insert("Blind Lemon Jefferson", "Long Lonesome Blues");
        controller.insert("Ma Rainey", "Fixin' To Die Blues");

        controller.printGraph();

        String actualOutput = systemOut().getHistory();
        // Assuming the graph prints connected components
        String expectedOutput = "There are 1 connected components\n" +
                                "The largest connected component has 2 elements\n";

        assertFuzzyEquals(expectedOutput, actualOutput);
    }

}
