import student.TestCase;

/**
 * This class was designed to test the DoubleLinkedList 
 *
 * @author Maanasa Ramakrishnan (maanasar)
           Marie Muya (mariem26)
 * @version 2024.09.20
 */

public class CommandProcessorTest extends TestCase {

    private CommandProcessor commandProcessor;
    private Controller controller; // Use a real Controller instance
    private Graph graph;

    /**
     * Set up the test environment with a real controller.
     */
    public void setUp() {
        graph = new Graph();  // Assuming a Graph class is available
        controller = new Controller(graph, 10); // Initialize the controller with a small hash table size
        commandProcessor = new CommandProcessor(controller); // Inject the real Controller
    }

    /**
     * Test a valid insert command.
     */
    public void testInsertCommand() {
        String command = "insert Blind Lemon Jefferson<SEP>Long Lonesome Blues";

        // Process the command
        commandProcessor.processCommand(command);

        // Capture the output
        String actualOutput = systemOut().getHistory();

        // Check expected output (based on Controller's insertion logic)
        String expectedOutput = "|Blind Lemon Jefferson| is added to the Artist database.\n" +
                                "|Long Lonesome Blues| is added to the Song database.\n";
        assertEquals(expectedOutput, actualOutput);
    }

    /**
     * Test an invalid insert command format.
     */
    public void testInvalidInsertCommandFormat() {
        String command = "insert Blind Lemon Jefferson Long Lonesome Blues";

        // Process the command
        commandProcessor.processCommand(command);
        String actualOutput = systemOut().getHistory();

        // Check the output for the invalid command format message
        assertTrue(actualOutput.contains("Invalid insert command format"));
    }

    /**
     * Test a valid remove song command.
     */
    public void testRemoveSongCommand() {
        // First, insert the song to remove later
        String command = "insert Blind Lemon Jefferson<SEP>Long Lonesome Blues";

        // Process the command
        commandProcessor.processCommand(command);

        command = "remove song Long Lonesome Blues";

        // Process the command
        commandProcessor.processCommand(command);

        // Capture the output
        String actualOutput = systemOut().getHistory();

        // Check expected output after song removal
        String expectedOutput = "|Long Lonesome Blues| is removed from the Song database.\n";
        assertFalse(actualOutput.contains(expectedOutput));
    }

    /**
     * Test a valid remove artist command.
     */
    public void testRemoveArtistCommand() {
        // First, insert the artist to remove later
        String command = "insert Blind Lemon Jefferson<SEP>Long Lonesome Blues";

        // Process the command
        commandProcessor.processCommand(command);

        command = "remove artist Blind Lemon Jefferson";

        // Process the command
        commandProcessor.processCommand(command);

        // Capture the output
        String actualOutput = systemOut().getHistory();

        // Check expected output after artist removal
        String expectedOutput = "|Blind Lemon Jefferson| is removed from the Artist database.\n";
        assertTrue(actualOutput.contains(expectedOutput));
    }

    /**
     * Test an invalid remove command format.
     */
    public void testInvalidRemoveCommandFormat() {
        String command = "remove something Long Lonesome Blues";

        // Process the command
        commandProcessor.processCommand(command);
        String actualOutput = systemOut().getHistory();

        // Check the output for the invalid remove command format message
        assertTrue(actualOutput.contains("Invalid remove command format"));
    }

    /**
     * Test a valid print songs command.
     */
    public void testPrintSongsCommand() {
        // Insert a few songs
        controller.insert("Blind Lemon Jefferson", "Long Lonesome Blues");
        controller.insert("Ma Rainey", "Ma Rainey's Black Bottom");

        String command = "print song";

        // Capture the output
        commandProcessor.processCommand(command);
        String actualOutput = systemOut().getHistory();

        // Expected output for printing songs
        assertTrue(actualOutput.contains("Long Lonesome Blues"));
        assertTrue(actualOutput.contains("Ma Rainey's Black Bottom"));
    }

    /**
     * Test a valid print artists command.
     */
    public void testPrintArtistsCommand() {
        // Insert a few artists
        controller.insert("Blind Lemon Jefferson", "Long Lonesome Blues");
        controller.insert("Ma Rainey", "Ma Rainey's Black Bottom");

        String command = "print artist";

        // Capture the output
        commandProcessor.processCommand(command);
        String actualOutput = systemOut().getHistory();

        // Expected output for printing artists
        assertTrue(actualOutput.contains("Blind Lemon Jefferson"));
        assertTrue(actualOutput.contains("Ma Rainey"));
    }

    /**
     * Test an unknown command.
     */
    public void testUnknownCommand() {
        String command = "delete artist Blind Lemon Jefferson";

        // Process the command
        commandProcessor.processCommand(command);
        String actualOutput = systemOut().getHistory();

        // Check for unknown command output
        assertTrue(actualOutput.contains("Unknown command"));
    }
    
    /**
     * Test invalid command format (command with less than 2 parts).
     */
    public void testInvalidCommandFormat() {
        String command = "insert"; // Missing the second part

        // Capture the output
        commandProcessor.processCommand(command);
        String actualOutput = systemOut().getHistory();

        // Check for the invalid command format message
        assertTrue(actualOutput.contains("Invalid command format: insert"));
    }

    /**
     * Test invalid remove command format.
     */
    public void testInvalidRemove2() {
        String command = "remove"; // Missing the item to remove

        // Capture the output
        commandProcessor.processCommand(command);
        String actualOutput = systemOut().getHistory();

        // Check for the invalid remove command format message
        assertTrue(actualOutput.contains("Invalid command format: remove"));
    }

    /**
     * Test valid remove song command.
     */
    public void testRemoveSong2() {
        // Insert a song to be removed later
        controller.insert("Blind Lemon Jefferson", "Long Lonesome Blues");

        String command = "remove song Long Lonesome Blues";

        // Capture the output
        commandProcessor.processCommand(command);
        String actualOutput = systemOut().getHistory();

        // Check that the song was removed
        assertTrue(actualOutput.contains("Long Lonesome Blues"));
    }

    /**
     * Test invalid print command format.
     */
    public void testInvalidPrintCommandFormat() {
        String command = "print"; // Missing what to print (artist or song)

        // Capture the output
        commandProcessor.processCommand(command);
        String actualOutput = systemOut().getHistory();

        // Check for the invalid print command format message
        assertTrue(actualOutput.contains("Invalid command format: print"));
        
     // Test with an invalid print command (missing "song", "artist", or "graph")
        command = "print playlist";

        // Process the command
        commandProcessor.processCommand(command);

        // Capture the output
        actualOutput = systemOut().getHistory();

        // Check for the invalid print command format message
        assertTrue(actualOutput.contains("Invalid print command format: print"));
    }

    /**
     * Test valid print graph command.
     */
    public void testPrintGraphCommand() {
        // Insert data into the graph
        controller.insert("Blind Lemon Jefferson", "Long Lonesome Blues");
        controller.insert("Ma Rainey", "Ma Rainey's Black Bottom");

        String command = "print graph";

        // Capture the output
        commandProcessor.processCommand(command);
        String actualOutput = systemOut().getHistory();

        // Check that the graph was printed
        assertTrue(actualOutput.contains("There are 2 connected components"));
    }
}
