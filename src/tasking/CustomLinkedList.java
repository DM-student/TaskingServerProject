package tasking;

public class CustomLinkedList<T> {
    public class Node<O>
    {

        public O item;
        private Node<O> nextNode;
        private CustomLinkedList owner;
        private Node<O> lastNode;

        public Node<O> getNextNode() {
            return nextNode;
        }

        public Node<O> getLastNode() {
            return lastNode;
        }
        public CustomLinkedList<T> getOwner() { return owner; }
    }


    private Node<T> firstNode;
    private Node<T> lastNode;
    private int size;

    public Node<T> getFirstNode()
    {
        return firstNode;
    }

    public Node<T> getLastNode()
    {
        return lastNode;
    }

    public int getSize()
    {
        return size;
    }

    public Node<T> add(T target)
    {
        Node<T> newNode = new Node<>();
        newNode.item = target;
        newNode.lastNode = lastNode;
        newNode.owner = this;

        if(lastNode != null)
        {
            lastNode.nextNode = newNode;
        }
        lastNode = newNode;
        if(firstNode == null)
        {
            firstNode = newNode;
        }
        size++;
        return newNode;
    }

    public void remove(Node target)
    {
        if(target.owner != this) { return; }
        if(target == firstNode)
        {
            firstNode = target.nextNode;
        }
        if(target == lastNode)
        {
            lastNode = target.lastNode;
        }

        if(target.lastNode != null) {target.lastNode.nextNode = target.nextNode; }
        if(target.nextNode != null) {target.nextNode.lastNode = target.lastNode; }
        size--;
    }

    public CustomLinkedList()
    {
        size = 0;
    }
}
