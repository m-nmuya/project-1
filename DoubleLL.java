/**
 * DoubleLinkedList class
 *
 * @author Maanasa Ramakrishnan (maanasar)
 *         Marie Muya (mariem26)
 * @version 2024.09.20
*/

public class DoubleLinkedList {
    private Node head;
    private Node tail;
    private int size;
    
    public DoubleLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }   
    
    // Method to add a new element to the linked list
    public void add(String data) {
        Node newNode = new Node(data);
        if (head == null) {
            head = tail = newNode;
            size += 1;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
            size += 1;
        }
    }

    // Method to check if the list contains an element
    public boolean contains(String data) {
        Node current = head;
        while (current != null) {
            if (current.data.equals(data)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    // Method to remove an element from the list
    public void remove(String data) {
        Node current = head;
        while (current != null) {
            if (current.data.equals(data)) {
                if (current == head) {
                    head = current.next;
                    if (head != null) head.prev = null;
                } else if (current == tail) {
                    tail = current.prev;
                    if (tail != null) tail.next = null;
                } else {
                    current.prev.next = current.next;
                    current.next.prev = current.prev;
                }
                size -= 1;
                break;
            }
            current = current.next;
        }
    }

    public void printList() {
        Node current = head;
        while (current != null) {
            System.out.print(current.data + " ");
            current = current.next;
        }
        System.out.println();
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public Node getHead() {
        return head;
    }

    public int getSize() {
        return size;
    }
    
    public boolean replace(String oldData, String newData) {
        Node current = head;
        while (current != null) {
            if (current.data.equals(oldData)) {
                current.data = newData; 
                return true;
            }
            current = current.next;
        }
        return false;
    }   
}
