/**
 * Hash class
 *
 * @author Maanasa Ramakrishnan (maanasar)
 *         Marie Muya (mariem26)
 * @version 2024.09.20
*/

public class Hash {
    private int numberOfRecords; // Number of records currently in the hash table
    private Record[] allRecords; // Array to store the records
    private int capacity; // the total capacity of the hash table

    public Hash(int initialSize) {
        this.numberOfRecords = 0;
        this.capacity = initialSize;
        this.allRecords = new Record[capacity]; // Initialize the hash table
    }

    // Hash function (simple modulo operation)
    public int hash(String key) {
        return Math.abs(key.hashCode()) % capacity;
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
       if ((numberOfRecords / capacity) >= 0.5) {
           resize();
           System.out.println("Song hash table size doubled.");
       }
       int index = h(key, capacity);
       int i = 0;
       while (allRecords[(index + i * i) % capacity] != null) {
           Record currentRecord = allRecords[(index + i * i) % capacity];
           if (currentRecord.getKey().equals(key)) {
               currentRecord.setNode(node);
               return;
           }
           i++; 
       }
       allRecords[(index + i * i) % capacity] = new Record(key, node);
       numberOfRecords++;
   }
   
    // Find a record by its key
    public Record find(String key) {
        int index = h(key, capacity);
        int startIndex = index;
        // Linear probing to find the record
        while (allRecords[index] != null) {
            if (key.equals(allRecords[index].getKey())) {
                return allRecords[index]; // Return the found record
            }
            index = (index + 1) % capacity;
            
            if (index == startIndex) {
                break; // Avoid infinite loop if key isn't found
            }
        }
        // If not found, return null
        return null;
    }

    // Remove a record by its key
    public boolean remove(String key) {
        int index = hash(key);
        int startIndex = index;

        // Linear probing to find the record
        while (allRecords[index] != null) {
            if (allRecords[index].getKey() != null && key.equals(allRecords[index].getKey())) {
                allRecords[index].setKey(null); // Mark the slot as empty
                allRecords[index].setNode(null);
                numberOfRecords--;
                return true; // Successfully removed
            }
            index = (index + 1) % capacity;
            
            if (index == startIndex) {
                break; // Avoid infinite loop if key isn't found
            }
        }
        return false; // Key not found
    }

    // resize method to increase capacity
    private void resize() {
        int newCapacity = capacity * 2;
        Record[] oldRecords = allRecords;
        allRecords = new Record[newCapacity];
        capacity = newCapacity;

        for (Record record : oldRecords) {
            if (record != null) {
                insert(record.getKey(), record.getNode());
            }
        }
    }

    // Print the hash table
    public void print() {
        for (int i = 0; i < capacity; i++) {
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
