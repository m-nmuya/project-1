/**
 * Record class
 *
 * @author Maanasa Ramakrishnan (maanasar)
           Marie Muya (mariem26)
 * @version 2024.09.20
 */

public class Record {
    // ~ Fields ................................................................
    private String key;
    private Node node;
    
    // ~ Constructor ...........................................................
    public Record(String key, Node node) {
        this.key = key;
        this.node = node;
    }
    
    // ~ Public Methods ........................................................
    public String getKey() {
        return key;
    }
    
    public Node getNode() {
        return node;
    }
    
    public void setKey(String key) {
        this.key = key;
    }
    
    public void setNode(Node node) {
        this.node = node;
    }
}
