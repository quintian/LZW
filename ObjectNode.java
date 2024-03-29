
package kunt;

/**
 *  @Name: Quinn Tian
 *  @Course: 95771 Data Structures and Algorithms
 *  @Assignment: Project 5
 */
public class ObjectNode {


    // Invariant of the ObjectNode class:
    //   1. The node's Object data is in the instance variable data.
    //   2. For the final node of a list, the link part is null.
    //      Otherwise, the link part is a reference to the
    //      next node of the list.
    private Object data;
    private ObjectNode link;


    /**
     * Initialize a node with a specified initial data and link to the next
     * node. Note that the initialLink may be the null reference,
     * which indicates that the new node has nothing after it.
     * @param initialData
     *   the initial data of this new node
     * @param initialLink
     *   a reference to the node after this new node--this reference may be null
     *   to indicate that there is no node after this new node.
     *   @precondition initialData !=null
     * @postcondition
     *   This node contains the specified data and link to the next node.
     * @bigtheta   Θ(1)
     **/
    public ObjectNode(Object initialData, ObjectNode initialLink)
    {
        data = initialData;
        link = initialLink;
    }


    /**
     * Modification method to add a new node after this node.
     * @param item
     *   the data to place in the new node
     * @precondition item !=null
     * @postcondition
     *   A new node has been created and placed after this node.
     *   The data for the new node is item. Any other nodes
     *   that used to be after this node are now after the new node.
     * @exception OutOfMemoryError
     *   Indicates that there is insufficient memory for a new
     *   ObjectNode.
     *   @bigtheta   Θ(1)
     **/
    public void addNodeAfter(Object item)
    {
        link = new ObjectNode(item, link);
    }


    /**
     * Accessor method to get the data from this node.
     * @precondition ObjectNode instance is not null
     * @return
     *   the data from this node
     *  @bigtheta   Θ(1)
     **/
    public Object getData( )
    {
        return data;
    }


    /**
     * Accessor method to get a reference to the next node after this node.
     *  @precondition ObjectNode instance is not null
     * @return
     *   a reference to the node after this node (or the null reference if there
     *   is nothing after this node)
     *   @bigtheta   Θ(1)
     **/
    public ObjectNode getLink( )
    {
        return link;
    }


    /**
     * Copy a list.
     * @param source
     *   the head of a linked list that will be copied (which may be
     *   an empty list in where source is null)
     * @return
     *   The method has made a copy of the linked list starting at
     *   source. The return value is the head reference for the
     *   copy.
     * @exception OutOfMemoryError
     *   Indicates that there is insufficient memory for the new list.
     * @precondition source is null or an ObjectNode
     * @bigtheta   Θ(n)
     **/
    public static ObjectNode listCopy(ObjectNode source)
    {
        ObjectNode copyHead;
        ObjectNode copyTail;

        // Handle the special case of the empty list.
        if (source == null)
            return null;

        // Make the first node for the newly created list.
        copyHead = new ObjectNode(source.data, null);
        copyTail = copyHead;

        // Make the rest of the nodes for the newly created list.
        while (source.link != null)
        {
            source = source.link;
            copyTail.addNodeAfter(source.data);
            copyTail = copyTail.link;
        }

        // Return the head reference for the new list.
        return copyHead;
    }

    /**
     * Copy a list with recursive method
     * @param source
     *   the head of a linked list that will be copied (which may be
     *   an empty list in where source is null)
     * @return
     *   The method has made a copy of the linked list starting at
     *   source. The return value is the head reference for the
     *   copy.
     * @exception OutOfMemoryError
     *   Indicates that there is insufficient memory for the new list.
     *   @bigtheta   Θ(n)
     **/
    public static ObjectNode listCopy_rec(ObjectNode source){
        ObjectNode copyHead; //the head node in the copied list
        //base case:
        // Handle the special case of the empty list.
        if (source == null)
            return null;

        // Make the first node for the newly created list.
        copyHead = new ObjectNode(source.data, null);

        //recursive case:
        if (source.link!=null) copyHead.link = listCopy_rec(source.link);
        return copyHead;

    }


    /**
     * Copy a list, returning both a head and tail reference for the copy.
     * @param source
     *   the head of a linked list that will be copied (which may be
     *   an empty list in where source is null)
     * @return
     *   The method has made a copy of the linked list starting at
     *   source.  The return value is an
     *   array where the [0] element is a head reference for the copy and the [1]
     *   element is a tail reference for the copy.
     * @exception OutOfMemoryError
     *   Indicates that there is insufficient memory for the new list.
     *   @bigtheta   Θ(n)
     **/
    public static ObjectNode[ ] listCopyWithTail(ObjectNode source)
    {
        ObjectNode copyHead;
        ObjectNode copyTail;
        ObjectNode[ ] answer = new ObjectNode[2];

        // Handle the special case of the empty list.
        if (source == null)
            return answer; // The answer has two null references .

        // Make the first node for the newly created list.
        copyHead = new ObjectNode(source.data, null);
        copyTail = copyHead;

        // Make the rest of the nodes for the newly created list.
        while (source.link != null)
        {
            source = source.link;
            copyTail.addNodeAfter(source.data);
            copyTail = copyTail.link;
        }

        // Return the head and tail references.
        answer[0] = copyHead;
        answer[1] = copyTail;
        return answer;
    }


    /**
     * Compute the number of nodes in a linked list.
     * @param head
     *   the head reference for a linked list (which may be an empty list
     *   with a null head)
     * @return
     *   the number of nodes in the list with the given head
     * @note
     *   A wrong answer occurs for lists longer than Int.MAX_VALUE.
     * @bigtheta   Θ(n)
     **/
    public static int listLength(ObjectNode head)
    {
        ObjectNode cursor;
        int answer;

        answer = 0;
        for (cursor = head; cursor != null; cursor = cursor.link)
            answer++;

        return answer;
    }

    /**
     * Compute the number of nodes in a linked list with recursive method
     * @param head
     *   the head reference for a linked list (which may be an empty list
     *   with a null head)
     * @return
     *   the number of nodes in the list with the given head
     * @note
     *   A wrong answer occurs for lists longer than Int.MAX_VALUE.
     *  @bigtheta   Θ(n)
     **/
    public static int	listLength_rec(ObjectNode head){
        //ObjectNode cursor;
        int answer;

        answer = 0;
        // cursor = head;
        if (head!=null){
            answer = 1 + listLength_rec(head.link);
        }
        return answer;
    }


    /**
     * Copy part of a list, providing a head and tail reference for the new copy.
     * @precondition
     *   start and end are non-null references to nodes
     *   on the same linked list,
     *   with the start node at or before the end node.
     * @return
     *   The method has made a copy of the part of a linked list, from the
     *   specified start node to the specified end node. The return value is an
     *   array where the [0] component is a head reference for the copy and the
     *   [1] component is a tail reference for the copy.
     * @param start
     *   first node to copy
     * @param end
     *   final node to copy
     * @exception IllegalArgumentException
     *   Indicates that start and end are not references
     *   to nodes on the same list.
     * @exception NullPointerException
     *   Indicates that start is null.
     * @exception OutOfMemoryError
     *   Indicates that there is insufficient memory for the new list.
     * @precondition the start node must be ahead of end or the same as end node
     *  @bigtheta   Θ(n)
     **/
    public static ObjectNode[ ] listPart(ObjectNode start, ObjectNode end)
    {
        ObjectNode copyHead;
        ObjectNode copyTail;
        ObjectNode cursor;
        ObjectNode[ ] answer = new ObjectNode[2];

        // Make the first node for the newly created list. Notice that this will
        // cause a NullPointerException if start is null.
        copyHead = new ObjectNode(start.data, null);
        copyTail = copyHead;
        cursor = start;

        // Make the rest of the nodes for the newly created list.
        while (cursor != end)
        {
            cursor = cursor.link;
            if (cursor == null)
                throw new IllegalArgumentException
                        ("end node was not found on the list");
            copyTail.addNodeAfter(cursor.data);
            copyTail = copyTail.link;
        }

        // Return the head and tail references
        answer[0] = copyHead;
        answer[1] = copyTail;
        return answer;
    }


    /**
     * Find a node at a specified position in a linked list.
     * @param head
     *   the head reference for a linked list (which may be an empty list in
     *   which case the head is null)
     * @param position
     *   a node number
     * @precondition
     *   position &gt; 0.
     * @return
     *   The return value is a reference to the node at the specified position in
     *   the list. (The head node is position 1, the next node is position 2, and
     *   so on.) If there is no such position (because the list is too short),
     *   then the null reference is returned.
     * @exception IllegalArgumentException
     *   Indicates that position is not positive.
     * @precondition head != null, and position>0
     *  @bigtheta   Θ(n)
     **/
    public static ObjectNode listPosition(ObjectNode head, int position)
    {
        ObjectNode cursor;
        int i;

        if (position <= 0)
            throw new IllegalArgumentException("position is not positive");

        cursor = head;
        for (i = 1; (i < position) && (cursor != null); i++)
            cursor = cursor.link;

        return cursor;
    }


    /**
     * Search for a particular piece of data in a linked list.
     * @param head
     *   the head reference for a linked list (which may be an empty list in
     *   which case the head is null)
     * @param target
     *   a piece of data to search for
     * @return
     *   The return value is a reference to the first node that contains the
     *   specified target. If there is no such node, the null reference is
     *   returned.
     * @precondition target!=null
     *  @bigtheta   Θ(n)
     **/
    public static ObjectNode listSearch(ObjectNode head, Object target)
    {
        ObjectNode cursor;

        for (cursor = head; cursor != null; cursor = cursor.link)
            if (target == cursor.data)
                return cursor;

        return null;
    }
    /**
     * Display the data in every third Node as one String.
     * @param head
     *   the head reference for a linked list (which may be an empty list in
     *   which case the head is null)
     * @return
     *   The return value is a reference to the first node that contains the
     *   specified target. If there is no such node, the null reference is
     *   returned.
     *
     *  @bigtheta   Θ(n)
     **/
    public static void  displayEveryThird(ObjectNode head){
        if (head==null) System.out.println("The list is empty");
        int i = 0; //index of the node
        ObjectNode cursor;

        for (cursor = head; cursor != null; cursor = cursor.link){
            i++;
            if (i%3==0){
                System.out.print(cursor.getData());
            }
        }
        System.out.println();
    }


    /**
     * Modification method to remove the node after this node.
     * @precondition
     *   This node must not be the tail node of the list.
     * @postcondition
     *   The node after this node has been removed from the linked list.
     *   If there were further nodes after that one, they are still
     *   present on the list.
     * @exception NullPointerException
     *   Indicates that this was the tail node of the list, so there is nothing
     *   after it to remove.
     *  @bigtheta   Θ(1)
     **/
    public void removeNodeAfter( )
    {
        link = link.link;
    }


    /**
     * Modification method to set the data in this node.
     * @param newData
     *   the new data to place in this node
     * @postcondition
     *   The data of this node has been set to newData.
     *  @bigtheta   Θ(1)
     **/
    public void setData(Object newData)
    {
        data = newData;
    }


    /**
     * Modification method to set the link to the next node after this node.
     * @param newLink
     *   a reference to the node that should appear after this node in the linked
     *   list (or the null reference if there is no node after this node)
     * @postcondition
     *   The link to the node after this node has been set to newLink.
     *   Any other node (that used to be in this link) is no longer connected to
     *   this node.
     *   @bigtheta   Θ(1)
     **/
    public void setLink(ObjectNode newLink)
    {
        link = newLink;
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
        if (data==null) return null;
        StringBuilder sb = new StringBuilder();

        sb.append(data+" ");
        ObjectNode cursor;
        for (cursor = link; cursor != null; cursor = cursor.link){
            sb.append(cursor.getData().toString()+" ");
        }
        return sb.toString();

    }

    /**
     *
     * @param args
     * test driver, print the results as required
     */

    public static void main(String[] args) {
        //a. make a linked list of 26 letters
        ObjectNode ob = new ObjectNode('a',   null);
        char c = 'a';
        ObjectNode cursor = ob;
        while (c <'z'){
            c++;
            ObjectNode temp = new ObjectNode(c, null);
            cursor.setLink(temp);
            cursor=cursor.getLink();
        }
        //b. call toString()
        System.out.println(ob.toString());
        //c. call displayEveryThird()
        ObjectNode.displayEveryThird(ob);
        //d. print the size of the list
        System.out.println("Number of nodes = " + ObjectNode.listLength(ob));
        System.out.println("Number of nodes = " + ObjectNode.listLength_rec(ob));
        //e. Make a copy of the list into a new list, use listCopy(),
        //  with the front node of the new list being pointed to by an ObjectNode named k.
        ObjectNode k = ObjectNode.listCopy(ob);
        //f. On the ObjectNode named k, call its toString() method.
        System.out.println(k.toString());
        //g. Print the size of the list k twice.
        System.out.println("Number of nodes in k = " + ObjectNode.listLength(k));
        System.out.println("Number of nodes in k = " + ObjectNode.listLength_rec(k));
        //h. Make a copy of the list into a new list, use listCopy_rec()
        ObjectNode k2 = ObjectNode.listCopy_rec(ob);
        //i. On the ObjectNode named k, call its toString() method.
        System.out.println(k2.toString());
        //j. Print the size of the list k twice.
        System.out.println("Number of nodes in k2 = " + ObjectNode.listLength(k2));
        System.out.println("Number of nodes in k2 = " + ObjectNode.listLength_rec(k2));

    }
}

