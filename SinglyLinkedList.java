package kunt;

/**
 *  @Name: Quinn Tian
 *  @Course: 95771 Data Structures and Algorithms
 *  @Assignment: Project 5
 */
public class SinglyLinkedList {

//Class invariants:
//1. The head pointer is null or points to the first element on the list.
// 2. The tail pointer is null or points to the last node on the list.
// 3. An integer countNodes is maintained to keep count of the number of nodes added to the list.
// This provided an O(1) count to the caller.
// 4. The cursor is null or points to the node in the list as designated

    ObjectNode head;
    ObjectNode tail;
    int countNodes;
    private ObjectNode cursor;
    /**
     * Initialize a list with null head, null tail, 0 countNodes, and null cursor
     * @precondition
     * count >= 0;
     * @postcondition
     *   This linked list contains the specified head and tail, and the total number of nodes is count.
     *   @bigtheta   Θ(1)

     **/
    public SinglyLinkedList( ){
        this.head = null;
        this.tail = null;
        this.countNodes = 0;
        this.cursor = null;
    }

    /**
     * Modification method to add a node containing the Object c to the end of the linked list.
     * @param c
     *   the data to place in the new node
     * @postcondition
     *   A new node has been created and placed after the tail and become the new tail.
     *   The data for the new node is c.
     * @exception OutOfMemoryError
     *   Indicates that there is insufficient memory for a new
     *   ObjectNode.
     *   @bigtheta   Θ(1)
     **/
    public void addAtEndNode(Object c){
        ObjectNode node = new ObjectNode(c, null);
        if (head == null && tail == null) {
            head = node;
            tail = head;
        }
        else {
            tail.setLink(node);
            tail=tail.getLink();

        }
        countNodes++;
    }
    /**
     * Modification method to add a node containing the Object c to the front of the linked list.
     * @param c
     *   the data to place in the new node
     * @postcondition
     *   A new node has been created and placed after the tail and become the new tail.
     *   The data for the new node is c.
     * @exception OutOfMemoryError
     *   Indicates that there is insufficient memory for a new
     *   ObjectNode.
     *   @bigtheta   Θ(1)
     **/
    public void addAtFrontNode(Object c){
        ObjectNode node = new ObjectNode(c, null);

        if (head !=null ) {
            ObjectNode oldHead = head;
            node.setLink(oldHead);
            head = node;
        }
        else {
            head = node;
            head.setLink(tail);
        }
        countNodes += 1;
    }
    /**
     * Get the number of nodes in a singly linked list
     * @postcondition
     *   what returns equals to the countNodes of the list
     *
     *  @bigtheta   Θ(n)
     **/
    public int	countNodes(){
        return ObjectNode.listLength(head);

    }
    /**
     * Get the data of the tail node in a singly linked list
     * @precondition the list is not null
     * @postcondition
     *   what returns equals to the countNodes of the list
     *
     *  @bigtheta   Θ(1)
     **/
    public Object getLast(){
        if (tail == null) return null;
        //cursor=tail;
        return tail.getData();
    }
    /**
     * Find a node at a specified position in a linked list.
     * @param i
     *   index of the node in list, starting at 0
     * @precondition
     *   i>=0
     * @return
     *   The return value is a reference to the node at the specified index in
     *   the list. (The head node is position 0.) If there is no such position
     *   (because the list is too short), then the null reference is returned.
     *  @bigtheta   Θ(n)
     **/
    public Object getObjectAt(int i){
        // listPosition() use index from 1, so i+1 for this function
        return ObjectNode.listPosition(head, i+1);
    }

    /**
     * Find if the current node has the next node
     * @return
     *   return true if yes, false if not
     *  @bigtheta   Θ(1)
     **/
    public boolean hasNext(){
        if (cursor == null) return false;
        return true;
    }
    /**
     * return the Object pointed to by the iterator
     * and increment the iterator to the next node in the list.
     * @return
     *   return the next node data, or null
     *  @bigtheta   Θ(1)
     **/
    public Object next(){
        // cursor = head;
        if (cursor != null) {
            ObjectNode current=cursor;
            cursor = cursor.getLink();
            return current.getData();
        }
        return null;
    }
    /**
     * Reset the iterator to the beginning of the list
     * That is, set a reference to the head of the list.
     *
     *  @bigtheta   Θ(1)
     **/
    public void reset(){
        cursor=head;
    }

    /**
     *  Return a Java String containing all data on the list or null
     *  if the list is empty.
     * @return
     *  The returned String is the concatenation of all data on the list or null
     *  if the list is empty.
     *
     *  @bigtheta   Θ(n)
     **/
    public String toString(){
        return head.toString();
    }

    /**
     * test driver, print the results as required
     */
    public static void main(String[] args) {
        //a. make a linked list of 26 letters
        SinglyLinkedList list = new SinglyLinkedList( );

        char c = 'a';
        while (c <= 'z'){
            list.addAtEndNode(c);
            c++;
        }

        System.out.println("After adding 26 letters, this list has "+list.countNodes+" nodes");
        System.out.println("The head node data is "+ list.head.getData());
        System.out.println("The tail node data is "+ list.tail.toString());
        ObjectNode node27= new ObjectNode('!', null);
        // test addEndNode()
        list.addAtEndNode(node27);

        System.out.println("\nAfter adding '!' at end, list has "+list.countNodes+" nodes");
        // test countNodes()
        System.out.println("Count nodes, list has "+list.countNodes()+" nodes");
        // test getLast()
        System.out.println("The tail data is "+list.getLast()+"\n");

        ObjectNode node0= new ObjectNode('0', null);
        // test addFrontNode()
        list.addAtFrontNode(node0);
        System.out.println("After adding '0' at front, list has "+list.countNodes+" nodes");
        System.out.println("The head node data is "+ list.head.getData()+"\n");
        // test.getObject()
        System.out.println("Object at 4 is "+list.getObjectAt(4)+"\n");

        // test iterator methods
        System.out.println("The below is testing the iterator methods: ");
        list.reset();
        while(list.hasNext()) {
            System.out.print(list.next());
        }
        //test toString()
        System.out.println("\nTesting list.toString(): \n"+list.toString());
    }
}
