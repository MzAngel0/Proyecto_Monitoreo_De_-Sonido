package controller;

import model.Node;

import java.util.ArrayList;
import java.util.List;

public class DoublyLinkedList<T> {
    protected Node<T> head;
    protected Node<T> tail;

    public DoublyLinkedList() {
        this.head = null;
        this.tail = null;
    
    }

    public boolean isEmpty() {
        return head == null;
    }

    public Node<T> getHead() {
        return head;
    }

    public Node<T> getTail() {
        return tail;
    }

    public void addFirst(T info) {
        Node<T> newNode = new Node<>(info);
        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else {
            newNode.setNext(head);
            head.setPrev(newNode);
            head = newNode;
        }
        
    }

    public void addLast(T info) {
        Node<T> newNode = new Node<>(info);
        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else {
            tail.setNext(newNode);
            newNode.setPrev(tail);
            tail = newNode;
        }
    }

    protected void removeNode(Node<T> current) {
        if (current == null) {
            return;
        }
        if (current == head && current == tail) {
            head = null;
            tail = null;
        } else if (current == head) {
            head = head.getNext();
            head.setPrev(null);
        } else if (current == tail) {
            tail = tail.getPrev();
            tail.setNext(null);
        } else {
            current.getPrev().setNext(current.getNext());
            current.getNext().setPrev(current.getPrev());
        }
    }

    public List<T> getAllForward() {
        List<T> list = new ArrayList<>();
        Node<T> current = head;
        while (current != null) {
            list.add(current.getInfo());
            current = current.getNext();
        }
        return list;
    }

    public List<T> getAllBackward() {
        List<T> list = new ArrayList<>();
        Node<T> current = tail;
        while (current != null) {
            list.add(current.getInfo());
            current = current.getPrev();
        }
        return list;
    }
}