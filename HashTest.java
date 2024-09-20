/**
 * Hash class
 *
 * @author Maanasa Ramakrishnan (maanasar)
 *         Marie Muya (mariem26)
 * @version 2024.09.20
*/

public class Hash {
    private static final String EMPTY = null; // Represents an empty slot
    private static final String TOMBSTONE = "TOMBSTONE"; // Special value for tombstones
    private int initHashSize;
    private String[] table;
    private int size;

    public Hash(int initialSize) {
        this.size = initialSize;
        table = new String[initialSize];
    }

    public static int hash(String s, int length) {
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
    
    public int hash(String key) {
        int hash = 10;//initHashSize; // Initial hash value
        for (int i = 0; i < key.length(); i++) {
            hash = hash * 31 + key.charAt(i); // Multiply by 31 and add the character value
        }
        // Handle negative hash values without using Math.abs
        if (hash < 0) {
            hash = -hash; // Convert negative value to positive
        }
        return hash % size; // Return the hash within the bounds of the table size
    }

    
    // Insert a key into the hash table
    public void insert(String key) {
        int index = hash(key);
        while (table[index] != null && !table[index].equals(TOMBSTONE) && !table[index].equals(key)) {
            index = (index + 1) % size; // Linear probing
        }
        table[index] = key;
    }

    // Remove a key from the hash table
    public boolean remove(String key) {
        int index = hash(key);
        while (table[index] != null) {
            if (table[index].equals(key)) {
                table[index] = TOMBSTONE; // Mark as tombstone
                System.out.println(index + ":" + TOMBSTONE);
                return true;
            }
            index = (index + 1) % size; // Linear probing
        }
        return false;
    }

    // Check if a key is in the hash table
    public boolean contains(String key) {
        int index = hash(key);
        while (table[index] != null) {
            if (table[index].equals(key)) {
                return true;
            }
            index = (index + 1) % size; // Linear probing
        }
        return false;
    }

    public void printTable() {
        for (int i = 0; i < table.length; i++) {
            if (table[i] == null) {
                //System.out.println(i + ": EMPTY");
            } else if (table[i].equals(TOMBSTONE)) {
                System.out.println(i + ": TOMBSTONE");
            } else {
                System.out.println(i + ": " + table[i]);
            }
        }
    }


