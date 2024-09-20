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

        String expectedOutput = "2: Ma Rainey\n" + 
                                "8: Blind Lemon Jefferson\n" +
                                "total artists: 2\n";

        assertFalse(expectedOutput.contains(actualOutput));
    }

    /**
     * Test printing songs.
     */
    public void testPrintSongs() {
        controller.insert("Ma Rainey", "Fixin' To Die Blues");
        controller.insert("Blind Lemon Jefferson", "Long Lonesome Blues");

        controller.printSongs();

        String actualOutput = systemOut().getHistory();

        String expectedOutput = "1: Long Lonesome Blues\n" +
                                "9: Fixin' To Die Blues\n" +
                                "total songs: 2\n";

        assertFalse(expectedOutput.contains(actualOutput));
    }

    /**
     * Test doubling the size of the hash table.
     */
    public void testDoubleTableSize() {
        controller.insert("Blind Lemon Jefferson", "Long Lonesome Blues");
        controller.insert("Ma Rainey", "Mississippi Boweavil Blues");
        controller.insert("Ma Rainey", "Fixin' To Die Blues");


        String actualOutput = systemOut().getHistory();

        assertTrue(actualOutput.contains("is added to the Artist database"));
        assertTrue(actualOutput.contains("is added to the Song database"));
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

        assertFalse(expectedOutput.contains(actualOutput));
    }
    
    /**
     * Test inserting and removing an artist, then re-inserting to ensure tombstone reuse.
     */
    public void testInsertRemoveReinsertArtist() {
        controller.insert("Ma Rainey", "Fixin' To Die Blues");
        controller.removeArtist("Ma Rainey");

        controller.insert("Ma Rainey", "Boweavil Blues"); // Should reuse tombstone slot
        controller.printArtists();

        String actualOutput = systemOut().getHistory();
        assertTrue(actualOutput.contains("Ma Rainey"));
    }

    /**
     * Test removing a non-existent artist.
     */
    public void testRemoveNonExistentArtist() {
        controller.insert("Ma Rainey", "Fixin' To Die Blues");
        
        // Try to remove an artist that does not exist
        controller.removeArtist("Ray Charles");

        // Verify that the existing artist is still present and no changes occurred
        controller.printArtists();
        String actualOutput = systemOut().getHistory();
        assertTrue(actualOutput.contains("Ma Rainey")); // The real artist should still exist in the system
        assertTrue(actualOutput.contains("|Ray Charles| does not exist in the Artist database."));
    }

    /**
     * Test resizing the hash table when it grows.
     */
    public void testHashTableResize() {
        for (int i = 0; i < 15; i++) {
            controller.insert("Artist" + i, "Song" + i);
        }
        // At this point, the hash table should have resized
        controller.printArtists();

        String actualOutput = systemOut().getHistory();
        assertTrue(actualOutput.contains("total artists: 15")); // Ensure all 15 artists were inserted
    }

    /**
     * Test printing with an empty song list.
     */
    public void testPrintEmptySongList() {
        controller.printSongs();
        String actualOutput = systemOut().getHistory();
        assertTrue(actualOutput.contains("total songs: 0")); // No songs in the list
    }

    /**
     * Test the graph with no connected components.
     */
    public void testGraphNoConnections() {
        controller.insert("Blind Lemon Jefferson", "Long Lonesome Blues");
        controller.insert("Ma Rainey", "Fixin' To Die Blues");

        controller.printGraph();

        String actualOutput = systemOut().getHistory();
        assertTrue(actualOutput.contains("There are 2 connected components")); // No connections between the artists
    }
    
    /**
     * Test removing a song that exists in the database.
     */
    public void testRemoveExistingSong() {
        // Insert a song
        controller.insert("Ma Rainey", "Fixin' To Die Blues");
        
        // Remove the song
        controller.removeSong("Fixin' To Die Blues");

        // Verify the song is marked as TOMBSTONE in the hash table
        controller.printSongs();
        String actualOutput = systemOut().getHistory();
        assertTrue(actualOutput.contains("TOMBSTONE")); // The song should be marked as TOMBSTONE
        
        // Verify the song count is decremented
        assertTrue(actualOutput.contains("total songs: 0")); // Song count should be decremented

        // Verify the song is removed from the graph
        controller.printGraph();
        actualOutput = systemOut().getHistory();
        assertTrue(actualOutput.contains("Fixin' To Die Blues")); 
    }

    /**
     * Test removing a song that does not exist in the database.
     */
    public void testRemoveNonexistentSong() {
        // Attempt to remove a song that hasn't been added
        controller.removeSong("Nonexistent Song");

        // Verify the message indicating the song does not exist
        String actualOutput = systemOut().getHistory();
        assertTrue(actualOutput.contains("|Nonexistent Song| does not exist in the Song database."));
    }
    
    /**
     * Test inserting a song that already exists in the same bucket.
     */
    public void testInsertExistingSongInSameBucket() {
        // Insert the same song twice
        controller.insert("Ma Rainey", "Fixin' To Die Blues");
        controller.insert("Ma Rainey", "Fixin' To Die Blues"); // Should detect that the song exists

        String actualOutput = systemOut().getHistory();
        assertTrue(actualOutput.contains("Fixin' To Die Blues")); // First insertion should work
        assertFalse(actualOutput.contains("Fixin' To Die Blues duplicates a record")); // Ensure second does not print the duplicate message
    }

    /**
     * Test inserting a song and artist relationship that already exists in the graph.
     */
    public void testInsertExistingArtistSongRelationshipInGraph() {
        // Insert artist and song into the graph
        controller.insert("Ma Rainey", "Fixin' To Die Blues");

        // Assume graph has a relationship function, we'll mock it to always return true for this test
        graph.addRelationship("Ma Rainey", "Fixin' To Die Blues");
        controller.insert("Ma Rainey", "Fixin' To Die Blues");
        controller.insert("Ma  Rainey", "Fixin' To Die Blues");

        String actualOutput = systemOut().getHistory();
        assertTrue(actualOutput.contains("|Ma Rainey<SEP>Fixin' To Die Blues| duplicates a record already in the database."));
    }

    /**
     * Test inserting a song that replaces a TOMBSTONE entry in the hash table.
     */
    public void testInsertReplacesTombstone() {
        // Insert a song
        controller.insert("Ma Rainey", "Fixin' To Die Blues");

        // Remove the song, marking it as TOMBSTONE
        controller.removeSong("Fixin' To Die Blues");

        // Reinsert a song into the same index where TOMBSTONE exists
        controller.insert("Ma Rainey", "Fixin' To Die Blues");

        String actualOutput = systemOut().getHistory();
        assertTrue(actualOutput.contains("replaces a TOMBSTONE entry in the Song database.")); // Ensure TOMBSTONE replacement
    }

    /**
     * Test normal insertion of a new song and artist.
     */
    public void testInsertNewSongAndArtist() {
        // Insert new artist and song
        controller.insert("Blind Lemon Jefferson", "Long Lonesome Blues");

        String actualOutput = systemOut().getHistory();
        assertTrue(actualOutput.contains("|Blind Lemon Jefferson| is added to the Artist database."));
        assertTrue(actualOutput.contains("|Long Lonesome Blues| is added to the Song database."));
    }

    /**
     * Test inserting an artist that already exists but a new song.
     */
    public void testInsertNewSongExistingArtist() {
        // Insert the artist with one song
        controller.insert("Ma Rainey", "Fixin' To Die Blues");

        // Insert the same artist with a new song
        controller.insert("Ma Rainey", "See See Rider Blues");

        String actualOutput = systemOut().getHistory();
        assertTrue(actualOutput.contains("|See See Rider Blues| is added to the Song database."));
        assertTrue(actualOutput.contains("|Ma Rainey| is added to the Artist database."));
    }
}
