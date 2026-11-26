package model;

/**
 * Nodo de una lista doblemente ligada.
 * Guarda un dato (info) y los apuntadores al nodo anterior y al siguiente
 * Como lo trabajabamos con el profe solo que con genericos.
 */

public class Node<T> {
    private T info;
    private Node<T> prev;
    private Node<T> next;

    public Node(T info) {
        this.prev = null;
        this.next = null;
        this.info = info;
    }

    public T getInfo() { return info; }
    public Node<T> getPrev() {
        return prev;
    }
    public Node<T> getNext() {
        return next;
    }

    public void setPrev(Node<T> prev) {
        this.prev = prev;
    }
    public void setNext(Node<T> next) {
        this.next = next;
    }
}
