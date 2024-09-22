/**
* Record class
*
* @author Maanasa Ramakrishnan (maanasar)
*         Marie Muya (mariem26)
* @version 2024.09.20
*/

public class Record {
   private String key;
   private Node node;
   private boolean isTombstone; // To mark deleted entries
   
   public Record(String key, Node node) {
       this.key = key;
       this.node = node;
   }

   public String getKey() {
       return key;
   }

   public void setKey(String key) {
       this.key = key;
   }

   public Node getNode() {
       return node;
   }

   public void setNode(Node node) {
       this.node = node;
   }
   public boolean isTombstone() {
       return isTombstone;
   }

   public void markAsTombstone() {
       this.isTombstone = true;
   }
}
