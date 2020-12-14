package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<T> {
        private T item;
        private Node<T> previous;
        private Node<T> next;

        public Node(Node<T> previous, T item, Node<T> next) {
            this.previous = previous;
            this.item = item;
            this.next = next;
        }
    }

    @Override
    public boolean add(T value) {
        addToTail(value);
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            addToTail(value);
        } else {
            addToHead(value, findNode(index));
        }
    }

    @Override
    public boolean addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
        return true;
    }

    @Override
    public T get(int index) {
        return findNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> newValue = findNode(index);
        T oldValue = newValue.item;
        newValue.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> deleted = findNode(index);
        T removedValue = deleted.item;
        erase(deleted);
        return removedValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> element = head;
        for (int i = 0; i < size; i++) {
            if (element.item == object || (element.item != null && (element.item).equals(object))) {
                erase(element);
                return true;
            }
            element = element.next;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private Node<T> findNode(int index) {
        checkedIndex(index);
        Node<T> wanted;
        if (index < size / 2) {
            wanted = head;
            for (int i = 0; i < index; i++) {
                wanted = wanted.next;
            }
        } else {
            wanted = tail;
            for (int i1 = size - 1; i1 > index; i1--) {
                wanted = wanted.previous;
            }
        }
        return wanted;
    }

    private void addToTail(T value) {
        Node<T> element = new Node<>(tail, value, null);
        if (tail == null) {
            head = element;
        } else {
            tail.next = element;
        }
        tail = element;
        size++;
    }

    private void addToHead(T value, Node<T> wantedNode) {
        Node<T> beforeNeeded = wantedNode.previous;
        Node<T> addedNode = new Node<>(beforeNeeded, value, wantedNode);
        wantedNode.previous = addedNode;
        if (beforeNeeded == null) {
            head = addedNode;
        } else {
            beforeNeeded.next = addedNode;
        }
        size++;
    }

    private void checkedIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Uncorrected index: index out of size");
        }
    }

    private void erase(Node<T> wanted) {
        Node<T> left = wanted.previous;
        Node<T> right = wanted.next;
        if (left == null) {
            head = right;
        } else {
            left.next = right;
            wanted.previous = null;
        }
        if (right == null) {
            tail = left;
        } else {
            right.previous = left;
            wanted.next = null;
        }
        wanted.item = null;
        size--;
    }
}
