/**
 * Graph class
 *
 * @author Maanasa Ramakrishnan (maanasar)
 *         Marie Muya (mariem26)
 * @version 2024.09.20
*/
import java.util.Arrays;

public class Graph {
	private String[] nodes; // Array to store node names (artists or songs)
	private boolean[][] adjacencyList; // 2D array representing the adjacency list (connections between nodes)
	private int nodeCount; // Current number of nodes in the graph
	private static final int INITIAL_SIZE = 10; // Initial size of the arrays
	private DoubleLinkedList[] adjacentList; 
	
	// Constructor to initialize the graph with a default size
	public Graph() {
		nodes = new String[INITIAL_SIZE]; // Initialize the nodes array with a default size
		adjacencyList = new boolean[INITIAL_SIZE][INITIAL_SIZE]; // Initialize the adjacency matrix
		nodeCount = 0; // Start with 0 nodes
	}

	// Method to add a new node (artist or song) to the graph
	public void addNode(String node) {
		if (nodeCount >= nodes.length) { // Check if resizing is needed
			resize(); // If the array is full, double its size
		}
		nodes[nodeCount] = node; // Add the node to the nodes array
		nodeCount++; // Increment the node count
	}

	// Method to add a relationship (edge) between two nodes
	public void addRelationship(String node1, String node2) {
		int index1 = getNodeIndex(node1); // Find the index of node1
		int index2 = getNodeIndex(node2); // Find the index of node2

		// If node1 doesn't exist, add it
		if (index1 == -1) {
			addNode(node1);
			index1 = getNodeIndex(node1); // Get the new index of node1
		}

		// If node2 doesn't exist, add it
		if (index2 == -1) {
			addNode(node2);
			index2 = getNodeIndex(node2); // Get the new index of node2
		}

		// Add the relationship between node1 and node2 in the adjacency matrix
		adjacencyList[index1][index2] = true;
		adjacencyList[index2][index1] = true; // Since it's an undirected graph, make it bidirectional
	}

	// Method to remove a song (node) from the graph
	public void removeSong(String song) {
		int songIndex = getNodeIndex(song); // Get the index of the song
		if (songIndex != -1) { // If the song exists
			// Remove all edges (connections) related to the song
			for (int i = 0; i < nodeCount; i++) {
				adjacencyList[songIndex][i] = false; // Remove row
				adjacencyList[i][songIndex] = false; // Remove column
			}
			// Mark the song as removed by setting the node to null
			nodes[songIndex] = null;
		}
	}

	// Method to remove an artist (node) from the graph
	public void removeArtist(String artist) {
		int artistIndex = getNodeIndex(artist); // Get the index of the artist
		if (artistIndex != -1) { // If the artist exists
			// Remove all edges (connections) related to the artist
			for (int i = 0; i < nodeCount; i++) {
				adjacencyList[artistIndex][i] = false; // Remove row
				adjacencyList[i][artistIndex] = false; // Remove column
			}
			// Mark the artist as removed by setting the node to null
			nodes[artistIndex] = null;
		}
	}

	// Method to print the graph's connected components
	public void printGraph() {
		boolean[] visited = new boolean[nodeCount]; // Keep track of visited nodes
		int components = 0; // Track the number of connected components
		int largestComponentSize = 0; // Track the size of the largest connected component

		// Iterate over all nodes in the graph
		for (int i = 0; i < nodeCount; i++) {
			if (nodes[i] != null && !visited[i]) { // If the node exists and hasn't been visited
				int componentSize = search(i, visited); // Perform BFS to find the size of the component
				components++; // Increment the component count
				if (componentSize > largestComponentSize) {
					largestComponentSize = componentSize; // Update the largest component size
				}
			}
		}
		// Output the results
		System.out.println("There are " + components + " connected components");
		System.out.println("The largest connected component has " + largestComponentSize + " elements");
	}

	// search to explore the graph and count the size of a component using DFS
	private int search(int nodeIndex, boolean[] visited) {
	    visited[nodeIndex] = true; // Mark the node as visited
	    int count = 1; // Initialize component size counter with current node

	    // Check all adjacent nodes
	    for (int i = 0; i < nodeCount; i++) {
	        if (adjacencyList[nodeIndex][i] && !visited[i]) { // If an adjacent node exists and is not visited
	            count += search(i, visited); // Recursively visit the adjacent node
	        }
	    }
	    return count; // Return the size of the component
	}

	// Helper method to get the index of a node in the nodes array
	private int getNodeIndex(String node) {
		for (int i = 0; i < nodeCount; i++) {
			if (node.equals(nodes[i])) { // If the node is found
				return i;
			}
		}
		return -1; // Return -1 if the node is not found
	}

	// Method to resize the arrays when the graph grows beyond its current capacity
	private void resize() {
		int newSize = nodes.length * 2; // Double the size of the arrays
		nodes = Arrays.copyOf(nodes, newSize); // Resize the nodes array
		boolean[][] newList = new boolean[newSize][newSize]; // Create a new larger adjacency matrix
		// Copy the existing adjacency matrix to the new matrix
		for (int i = 0; i < adjacencyList.length; i++) {
			System.arraycopy(adjacencyList[i], 0, newList[i], 0, adjacencyList[i].length);
		}
		adjacencyList = newList; // Replace the old adjacency matrix with the new one
	}

	public boolean hasRelationship(String artist, String song) {
	    // Find the index of the artist
	    int artistIndex = getNodeIndex(artist);
	    // Find the index of the song
	    int songIndex = getNodeIndex(song);

	    // Check if both the artist and song exist in the graph
	    if (artistIndex == -1 || songIndex == -1) {
	        return false; // If either doesn't exist, return false
	    }
	    // Return true if there is a relationship (edge) between the artist and the song
	    return adjacencyList[artistIndex][songIndex];
	}
}
