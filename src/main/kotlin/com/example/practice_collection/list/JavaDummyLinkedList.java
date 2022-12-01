package com.example.practice_collection.list;

import org.jetbrains.annotations.Nullable;

public class JavaDummyLinkedList<E> implements JavaList<E> {

    /**
     * 기존 LinkedList 와 다르게 first, last 에 dummy node 를 넣어두는 LinkedList
     *
     * 장점
     * 1. first, last 는 항상 더미 노드로 존재하기 때문에 맨앞, 맨뒤 데이터 변경 시 first, last 를 관리할 필요가 없음
     * 2. 맨앞, 맨뒤 데이터 추가, 삭제 시 중간에 삭제하는 것과 동일하게 처리할 수 있음
     */

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
    private final Node<E> first;
    private final Node<E> last;

    public JavaDummyLinkedList() {
        this.size = 0;
        this.first = new Node<>(null, null, null);
        this.last = new Node<>(null, null, null);

        first.next = last;
        last.prev = first;
    }

    @Override
    public int size() {
        return size;
    }

    /**
     * List 의 맨 뒤에 데이터 추가 O(1)
     *
     * LinkedList 의 특성상 맨앞에도 O(1) 의 속도로 추가할 수 있다
     * 중간에 추가하려면 탐색 시간 때문에 O(n)
     */
    @Override
    public void add(E e) {
        Node<E> newNode = new Node<>(e, last.prev, last);

        last.prev.next = newNode;
        last.prev = newNode;

        size += 1;
    }

    /**
     * 특정 index 의 데이터를 가져온다 O(n)
     * index 의 범위를 벗어나면 IndexOutOfBoundsException
     * 찾는 시간을 조금이라도 줄이기 위해 size 와 비교해서 앞에서부터 찾거나 뒤에서부터 찾음
     */
    @Override
    public E get(int index) {
        validateIndexRange(index);

        if (index < (size / 2)) {
            return findNodeFromFirst(index);
        }

        return findNodeFromLast(index);
    }

    private void validateIndexRange(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
    }

    private E findNodeFromFirst(int index) {
        Node<E> node = first.next;

        for (int i = 0; i < index; i++) {
            node = node.next;
        }

        return node.item;
    }

    private E findNodeFromLast(int index) {
        Node<E> node = last.prev;

        for (int i = size - 1; i > index; i--) {
            node = node.prev;
        }

        return node.item;
    }

    /**
     * 파라미터가 List 에 포함되면 true, 아니면 false
     * 배열을 일일히 돌면서 확인해야 하기 때문에 시간복잡도는 O(n)
     */
    @Override
    public boolean contains(E e) {
        return findNode(e) != null;
    }

    /**
     * List 에 존재하는 데이터 하나 삭제 O(n)
     *
     * LinkedList 의 특성상 배열처럼 삭제 후 데이터를 밀어주는 작업은 없지만
     * 데이터를 찾는데 걸리는 시간이 존재하기 때문에 O(n) 의 시간복잡도를 가짐
     *
     * pop(), poll() 처럼 맨앞이나 맨뒷값을 제거한다는 사실을 알고있다면 O(1)
     */
    @Override
    public void remove(E e) {
        Node<E> node = findNode(e);

        if (node != null) {
            unlink(node);
        }
    }

    @Nullable
    private Node<E> findNode(E e) {
        if (e == null) {
            return findNodeOfNullItem();
        }

        return findNodeOfItem(e);
    }

    @Nullable
    private Node<E> findNodeOfNullItem() {
        Node<E> node = first.next;

        for (int i = 0; i < size; i++) {
            if (node.item == null) {
                return node;
            }

            node = node.next;
        }

        return null;
    }

    @Nullable
    private Node<E> findNodeOfItem(E e) {
        Node<E> node = first.next;

        for (int i = 0; i < size; i++) {
            if (e.equals(node.item)) {
                return node;
            }

            node = node.next;
        }

        return null;
    }

    private void unlink(Node<E> node) {
        assert node != null;

        node.prev.next = node.next;
        node.next.prev = node.prev;

        node.next = null;
        node.prev = null;
        node.item = null;
        node = null;
        size -= 1;
    }

    /**
     * ArrayList 와 다르게 capacity 의 관리같은거 할 필요 없이 맨 뒤에 주르륵 추가 가능
     */
    @Override
    public void addAll(JavaList<E> list) {
        E[] array = list.toArray();

        for (E e : array) {
            add(e);
        }
    }

    @Override
    public E[] toArray() {
        Object[] result = new Object[size];
        Node<E> node = first.next;

        for (int i = 0; i < size; i++) {
            result[i] = node.item;
            node = node.next;
        }

        return (E[]) result;
    }
}
