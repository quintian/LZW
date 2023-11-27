package kunt;

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
        int index=Math.abs(s.hashCode())%127;
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
