/**
 * Controller class
 *
 * @author Maanasa Ramakrishnan (maanasar) Marie Muya (mariem26)
 * @version 2024.09.20
 */
public class Controller {
	// Graph instance to maintain relationships between artists and songs
	private final Graph graph;

	// Arrays to store linked lists of artists and songs, representing the hash
	// table
	private DoubleLinkedList[] artistList;
	private DoubleLinkedList[] songList;

	// Hash functions for artists and songs
	private final Hash artistHash;
	private final Hash songHash;

	// Counters for the number of artists and songs in the system
	private int artistCount;
	private int songCount;

	// A special marker for tombstone (soft deletion)
	private static final String TOMBSTONE = "TOMBSTONE";

	// Constructor to initialize the Controller with a graph and an initial size for
	// hash tables
	public Controller(Graph graph, int initialSize) {
		this.graph = graph;
		this.artistHash = new Hash(initialSize);
		this.songHash = new Hash(initialSize);

		// Initialize artist and song lists (hash tables) with linked lists
		this.artistList = new DoubleLinkedList[initialSize];
		this.songList = new DoubleLinkedList[initialSize];
		for (int i = 0; i < initialSize; i++) {
			artistList[i] = new DoubleLinkedList();
			songList[i] = new DoubleLinkedList();
		}

		// Initialize the artist and song counters
		this.artistCount = 0;
		this.songCount = 0;
	}

	// Method to print all songs in the database with their hash table index
	public void printSongs() {
		for (int i = songList.length - 1; i >= 0; i--) {
			Node current = songList[i].getHead();
			while (current != null) {
				if (!current.data.equals(TOMBSTONE)) {
					System.out.println(i + ": |" + current.data + "|");
				} else if (current.data.equals(TOMBSTONE)) {
					System.out.println(i + ": " + current.data);
				}
				current = current.next;
			}
		}
		System.out.println("total songs: " + songCount);
	}

	// Method to print all artists in the database with their hash table index
	public void printArtists() {
		for (int i = artistList.length - 1; i >= 0; i--) {
			Node current = artistList[i].getHead();
			while (current != null) {
				if (!current.data.equals(TOMBSTONE)) {
					System.out.println(i + ": |" + current.data + "|");
				} else if (current.data.equals(TOMBSTONE)) {
					System.out.println(i + ": " + current.data);
				}
				current = current.next;
			}
		}
		System.out.println("total artists: " + artistCount);
	}

	// Method to print the entire graph (artist-song relationships)
	public void printGraph() {
		graph.printGraph();
	}

	// Helper method to double the size of the song hash table when needed
	private void doubleTableSize(DoubleLinkedList[] list) {
		int newSize = list.length * 2; // Calculate the new size of the hash table
		DoubleLinkedList[] newList = new DoubleLinkedList[newSize]; // Create a new hash table
		// Initialize the new hash table
		for (int i = 0; i < newSize; i++) {
			newList[i] = new DoubleLinkedList();
		}
		// Rehash all elements from the old hash table into the new one
		for (int i = 0; i < list.length; i++) {
			Node current = list[i].getHead();
			while (current != null) {
				if (!current.data.equals(TOMBSTONE)) {
					int newIndex = Hash.h(current.data, newSize); // Recalculate the hash index
					newList[newIndex].add(current.data); // Add to the new list
				}
				current = current.next;
			}
		}
	}

	// Method to remove a song from the database
	public void removeSong(String song) {
		int songIndex = songHash.hash(song);

		// If the song exists in the database, mark it as TOMBSTONE
		Node current = songList[songIndex].getHead();
		while (current != null) {
			if (current.data.equals(song)) {
				current.data = TOMBSTONE; // Mark as tombstone
				songCount--;
				graph.removeSong(song); // Remove the song from the graph
				//return;
			}
			current = current.next;
		}
		System.out.println("|" + song + "| does not exist in the Song database.");
	}

	// Method to remove an artist from the database
	public void removeArtist(String artist) {
		int artistIndex = artistHash.hash(artist);

		// If the artist exists in the database, mark it as TOMBSTONE
		Node current = artistList[artistIndex].getHead();
		while (current != null) {
			if (current.data.equals(artist)) {
				current.data = TOMBSTONE; // Mark as tombstone
				artistCount--;
				graph.removeArtist(artist); // Remove the artist from the graph
				// artistList[artistIndex].add(TOMBSTONE);
				System.out.println("|" + artist + "| is removed from the Artist database.");
				return;
			}
			current = current.next;
		}
		System.out.println("|" + artist + "| does not exist in the Artist database.");
	}

	public void insert(String artist, String song) {
		// Calculate the hash index for the artist and song
		int artistIndex = artistHash.hash(artist);
		int songIndex = songHash.hash(song);

		// Check if the artist exists at the given index
		boolean artistExists = false;
		Node artistCurrent = artistList[artistIndex].getHead();
		while (artistCurrent != null) {
			if (artistCurrent.data.equals(artist)) {
				artistExists = true;
				break;
			}
			artistCurrent = artistCurrent.next;
		}

		// Check if the song exists at the given index
		boolean songExists = false;
		Node songCurrent = songList[songIndex].getHead();
		while (songCurrent != null) {
			if (songCurrent.data.equals(song)) {
				songExists = true;
				break;
			}
			songCurrent = songCurrent.next;
		}

		// Check if the artist-song relationship already exists in the graph
		boolean relationshipExists = graph.hasRelationship(artist, song);

		if (relationshipExists) {
			System.out.println("|" + artist + "<SEP>" + song + "| duplicates a record already in the database.");
			return; // If the relationship exists, return early to prevent further processing
		}
		if (!artistExists && songExists) {
            System.out.println("|" + artist + "| is already in the Artist database.");
        }
		if (!artistExists) {
			// Add the artist to the database if it doesn't exist
			artistList[artistIndex].add(artist);
			artistCount++;
			System.out.println("|" + artist + "| is added to the Artist database.");
		}

		if (!songExists) {
			// Check if there's a TOMBSTONE entry at the given index
			if (songList[songIndex].getHead() != null && songList[songIndex].getHead().data.equals(TOMBSTONE)) {
				// Replace TOMBSTONE entry with the new song
				songList[songIndex].getHead().data = song;
				System.out.println("|" + song + "| replaces a TOMBSTONE entry in the Song database.");
			} else {
				// Add the song to the database if it doesn't exist
				songList[songIndex].add(song);
				songCount++;
				System.out.println("|" + song + "| is added to the Song database.");
			}
		}

		// If the number of songs exceeds the hash table size, double the size
		if (songCount >= songList.length/2) {
			System.out.println("Song hash table size doubled.");
			doubleTableSize(songList);
		}

		// If the number of artists exceeds the hash table size, double the size
		if (artistCount > artistList.length) {
			doubleTableSize(artistList);
		}

		// Add a relationship between the artist and song in the graph
		graph.addRelationship(artist, song);
	}

}
