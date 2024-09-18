/**
 * Node class
 *
 * @author Maanasa Ramakrishnan (maanasar)
           Marie Muya (mariem26)
 * @version 2024.09.20
 */

 public class Node<D>
    {

        // The data element stored in the node.
        public D data;

        // The next node in the sequence.
        public Node<D> next;

        /**
         * Creates a new node with the given data
         *
         * @param d
         *            the data to put inside the node
         */
        public Node(D d)
        {
            data = d;
        }
               
        /**
         * Sets the node after this node
         *
         * @param n
         *            the node after this one
         */
        public void set(Node<D> n)
        {
            next = n;
        }

        /**
         * Gets the data in the node
         *
         * @return the data in the node
         */
        public D get()
        {
            return data;
        }
    }
