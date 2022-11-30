package com.example.practice_collection.list;

public class JavaArrayList<E> implements JavaList<E> {
    private static final int DEFAULT_CAPACITY = 10;
    private static final Object[] EMPTY_ELEMENTS = {};
    private Object[] elements;
    private int size;

    public JavaArrayList() {
        this.elements = EMPTY_ELEMENTS;
        this.size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    /**
     * List 에 데이터 하나 추가 O(n)
     * List 내부에 있는 배열 (elements) 의 길이를 초과하면 새로운 배열을 선언하여 데이터를 복사
     * 확장 후 배열은 기존 배열 길이의 1.5 배만큼 증가
     *
     * 배열을 확장하지 않는다면 O(1) 이지만 배열을 확장하는 경우 O(n) 이기 때문에
     * Worst Case 기준으로 시간 복잡도는 O(n)
     */
    @Override
    public void add(E e) {
        if (size == elements.length) {
            growElements(size / 2);
        }

        this.elements[size] = e;
        this.size += 1;
    }

    /**
     * List 의 데이터 하나 조회 O(1)
     * index 범위에 맞지 않으면 IndexOutOfBoundsException
     */
    @Override
    public E get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        return (E) elements[index];
    }

    /**
     * 파라미터 o 가 List 에 포함되면 true, 아니면 false
     * 배열을 일일히 돌면서 확인해야 하기 때문에 시간복잡도는 O(n)
     */
    @Override
    public boolean contains(E e) {
        return indexOfElement(e) > -1;
    }

    /**
     * List 에 존재하는 데이터 하나 삭제 O(n)
     *
     * 중간에 있는 데이터를 삭제한다면 배열 특성상 뒤의 데이터를 모두 땡겨줘야 함
     * 만약 0 번째 데이터를 삭제한다면 1 ~ size 만큼의 데이터를 전부 0 ~ size-1 으로 옮겨줘야 함
     *
     * 가장 마지막의 데이터 (size - 1 번째) 를 삭제한다면 O(1) 이지만
     * Worst Case 기준으로 시간복잡도는 O(n)
     */
    @Override
    public void remove(E e) {
        int targetIndex = indexOfElement(e);

        if (targetIndex == -1) {
            return;
        }

        if (targetIndex != size - 1) {
            Object[] newElements = new Object[size];

            for (int i = 0; i < targetIndex; i++) {
                newElements[i] = elements[i];
            }

            for (int i = targetIndex; i < size - 1; i++) {
                newElements[i] = elements[i + 1];
            }

            this.elements = newElements;
        }

        elements[size - 1] = null;
        size -= 1;
    }

    /**
     * 다른 List 를 받아 전부 추가하는 메서드
     *
     * 항상 1.5 배의 길이로 늘어나는 add 와 다르게 다른 list 의 크기만큼 데이터를 늘려줘야 함
     * 예를 들어 기존 배열이 10 인 경우 다음 길이는 capacity 는 15 여야 하지만
     * list 의 길이가 20 이면 30 이 되어야 하기 때문에 별도의 newSize 만큼 배열의 길이를 늘려줌
     */
    @Override
    public void addAll(JavaList<E> list) {
        if (list.size() == 0) {
            return;
        }

        int newSize = size + list.size();
        growElements(newSize);

        for (int i = size; i < newSize; i++) {
            this.elements[i] = list.get(i - size);
        }

        this.size = newSize;
    }

    /**
     * capacity 만큼 배열의 길이를 늘려주는 메서드
     * 다만, 예외적으로 배열의 데이터가 0 인 경우에는 DEFAULT_CAPACITY (10) 만큼의 공간을 할당
     */
    private void growElements(int capacity) {
        if (size == 0) {
            this.elements = copyOf(elements, Math.max(capacity, DEFAULT_CAPACITY));
            return;
        }

        int oldCapacity = elements.length;
        int newCapacity = oldCapacity + capacity;
        this.elements = copyOf(elements, newCapacity);
    }

    private Object[] copyOf(Object[] objects, int newLength) {
        Object[] newObjects = new Object[newLength];

        for (int i = 0; i < objects.length && i < newLength; i++) {
            newObjects[i] = objects[i];
        }

        return newObjects;
    }

    /**
     * 데이터의 index 위치를 찾는 메서드
     * null 값 자체가 데이터로 들어오는 경우도 있기 때문에 별도의 처리를 해줌
     */
    private int indexOfElement(E e) {
        for (int i = 0; i < size; i++) {
            if (e == null) {
                if (elements[i] == null) {
                    return i;
                }

                continue;
            }

            if (e.equals(elements[i])) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public E[] toArray() {
        return null;
    }
}
