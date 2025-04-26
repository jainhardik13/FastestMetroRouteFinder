public class MyList {
    private MyNode head;

    public MyList() {
        head = null;
    }

    public void add(Object data) {
        MyNode newNode = new MyNode(data);
        if (head == null) {
            head = newNode;
        } else {
            MyNode current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
    }

    public MyNode getHead() {
        return head;
    }

    public void setHead(MyNode node) {
        this.head = node;
    }

    public boolean isEmpty() {
        return head == null;
    }
}
