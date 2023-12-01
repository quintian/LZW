package kunt;

/**
 *  @Name: Quinn Tian
 *  @Course: 95771 Data Structures and Algorithms
 *  @Assignment: Project 5
 */

/**
 * This class is equivalent of HashMap to implement the dictionary for LZW
 * compression, and stores the pair of String-Integer value in Data field of each node.
 * It contains an initializer, put(), get(), and containsKey() methods, just like the HashMap.
 */
public class MyTable {

    private SinglyLinkedList[] array;
    private static final int SIZE = 127;

    public MyTable() {
        array = new SinglyLinkedList[SIZE];
        for (int i = 0; i < SIZE; i++) {
            array[i] = new SinglyLinkedList();
        }
    }


    public void put(String s, int code){
        Data data=new Data(s, code);
        ObjectNode node=new ObjectNode(data, null);
        int index=Math.abs(s.hashCode())%127;
        array[index].addAtEndNode(node);
    }

    /**
     * @precondition this.containsKey(String s) == true
     * @param s
     * @return
     */
    public int get(String s){
        int index=Math.abs(s.hashCode())%127;
        SinglyLinkedList list=array[index];

        list.reset();
        while (list.hasNext()){
            ObjectNode node=(ObjectNode) list.next();
            Data data=(Data)node.getData();
            if (data.key.equals(s)){
                return data.code;
            }
        }
       return -1;

    }
    public boolean containsKey(String s){
        int index=Math.abs(s.hashCode())%127; //index of the array of linked list
        SinglyLinkedList list=array[index];

        list.reset();
        while (list.hasNext()){
            ObjectNode node=(ObjectNode) list.next();
            Data data=(Data)node.getData();
            if (data.key.equals(s)){
                return true;
            }
        }
        return false;

    }
}
