/**
 * Hash class
 *
 * @author Maanasa Ramakrishnan (maanasar)
 *         Marie Muya (mariem26)
 * @version 2024.09.20
*/

public class Hash {
	private int size; // Size of the hash table
	private int numberOfRecords; // Number of records currently in the hash table
	private Record[] allRecords; // Array to store the records
	private int capacity; // the total capacity of the hash table

	public Hash(int initialSize) {
		this.size = initialSize;
		this.numberOfRecords = 0;
		this.allRecords = new Record[size]; // Initialize the hash table
		this.capacity = initialSize;
	}

	// Hash function (simple modulo operation)
	public int hash(String key) {
		return Math.abs(key.hashCode()) % size;
	}

	public static int h(String s, int length) {
		int intLength = s.length() / 4;
		long sum = 0;
		for (int j = 0; j < intLength; j++) {
			char[] c = s.substring(j * 4, (j * 4) + 4).toCharArray();
			long mult = 1;
			for (int k = 0; k < c.length; k++) {
				sum += c[k] * mult;
				mult *= 256;
			}
		}
		char[] c = s.substring(intLength * 4).toCharArray();
		long mult = 1;
		for (int k = 0; k < c.length; k++) {
			sum += c[k] * mult;
			mult *= 256;
		}

		return (int) (Math.abs(sum) % length);
	}

	public void insert(String key, Node node) {
	    int index = h(key, capacity);
	    int i = 0;
	    int firstTombstoneIndex = -1;  // Track the first available tombstone
	    
	    // Linear probing to find the correct index or tombstone
	    while (allRecords[(index + i) % capacity] != null) {
	        Record currentRecord = allRecords[(index + i) % capacity];
	        
	        if (currentRecord.isTombstone() && firstTombstoneIndex == -1) {
	            // Mark the first tombstone we find, but keep looking for the key
	            firstTombstoneIndex = (index + i) % capacity;
	        } else if (currentRecord.getKey().equals(key)) {
	            // If the key matches, update the node
	            currentRecord.setNode(node);
	            return;
	        }
	        i++;
	    }
	    // If we found a tombstone, insert at that position, otherwise insert at empty slot
	    if (firstTombstoneIndex != -1) {
	        allRecords[firstTombstoneIndex] = new Record(key, node);
	    } else {
	        allRecords[(index + i) % capacity] = new Record(key, node);
	    }
	    size++;
	}
   
	
	// Find a record by its key
	public Record find(String key) {
		int index = hash(key);
		// Linear probing to find the record
		while (allRecords[index] != null) {
			if (key.equals(allRecords[index].getKey())) {
				return allRecords[index]; // Return the found record
			}
			index = (index + 1) % size;
		}
		// If not found, return null
		return null;
	}

	public void remove(String key) {
	    int index = h(key, capacity);
	    int i = 0;

	    // Linear probing to find the record with the matching key
	    while (allRecords[(index + i) % capacity] != null) {
	        Record currentRecord = allRecords[(index + i) % capacity];

	        // If we find the key and it's not a tombstone
	        if (!currentRecord.isTombstone() && currentRecord.getKey().equals(key)) {
	            // Mark the record as a tombstone to logically remove it
	            currentRecord.markAsTombstone();
	            size--; // Decrease the size of active elements in the hash table
	            return;
	        }
	        i++;
	    }

	    // If the key is not found, print a message (optional)
	    System.out.println("Key '" + key + "' not found.");
	}
	

	// resize method to increase capacity
	private void resize() {
		int newCapacity = capacity * 2;
		Record[] oldRecords = allRecords;
		allRecords = new Record[newCapacity];
		capacity = newCapacity;
		size = 0;

		for (Record record : oldRecords) {
			if (record != null) {
				insert(record.getKey(), record.getNode());
			}
		}
	}

	// Print the hash table
	public void print() {
		for (int i = 0; i < size; i++) {
			if (allRecords[i] == null || allRecords[i].getKey() == null) {
				System.out.println(i + ": TOMBSTONE");
			} else if (allRecords[i] != null && allRecords[i].getKey().equals("TOMBSTONE")) {
				System.out.println(i + ": |TOMBSTONE|");

			} else {
				System.out.println(i + ": " + allRecords[i].getKey() + " -> " + allRecords[i].getNode());
			}
		}
	}
}
