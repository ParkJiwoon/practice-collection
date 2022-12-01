package com.example.practice_collection.list;

public class JavaLinkedList<E> implements JavaList<E> {

    private static class Node<E> {
        E item;
        Node<E> prev;
        Node<E> next;

        public Node(E item, Node<E> prev, Node<E> next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }

    private int size;
    private Node<E> first;
    private Node<E> last;



    @Override
    public int size() {
        return size;
    }

    @Override
    public void add(E e) {
        Node<E> newNode = new Node<>(e, last, null);

        if (last == null) {
            first = newNode;
        } else {
            last.next = newNode;
        }

        last = newNode;
        size += 1;
    }

    @Override
    public E get(int index) {
        validateIndexRange(index);

        Node<E> node;

        if (index < (size / 2)) {
            node = first;

            for (int i = 0; i < index; i++) {
                node = node.next;
            }

            return node.item;
        }

        node = last;
        for (int i = size - 1; i > index; i--) {
            node = node.prev;
        }

        return node.item;
    }

    private void validateIndexRange(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public boolean contains(E e) {
        Node<E> node = first;

        while (node != null) {
            if (e == null) {
                if (node.item == null) {
                    return true;
                }
            } else if (e.equals(node.item)) {
                return true;
            }

            node = node.next;
        }

        return false;
    }

    @Override
    public void remove(E e) {
        Node<E> node = first;

        while (node != null) {
            if (e == null) {
                if (node.item == null) {
                    unlink(node);
                }
            } else if (e.equals(node.item)) {
                unlink(node);
            }

            node = node.next;
        }
    }

    private void unlink(Node<E> node) {
        assert node != null;

        // node 가 첫번째 인 경우 first 를 변경
        // 아니라면 삭제하려는 node 의 뒤의 노드에다가 앞의 노드를 연결해줌
        if (node.prev == null) {
            first = node.next;
        } else {
            node.prev.next = node.next;
        }

        // node 가 마지막인 경우 last 를 변경
        // 아니라면 삭제하려는 node 의 앞의 노드에다가 뒤의 노드를 연결해줌
        if (node.next == null) {
            last = node.prev;
        } else {
            node.next.prev = node.prev;
        }

        node.next = null;
        node.prev = null;
        node.item = null;
        size -= 1;
    }

    @Override
    public void addAll(JavaList<E> list) {
        E[] array = list.toArray();

        for (int i = 0; i < list.size(); i++) {
            add(array[i]);
        }
    }

    @Override
    public E[] toArray() {
        Object[] result = new Object[size];
        Node<E> node = first;

        for (int i = 0; i < size; i++) {
            result[i] = node.item;
            node = node.next;
        }

        return (E[]) result;
    }
}
