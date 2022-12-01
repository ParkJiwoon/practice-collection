package com.example.practice_collection.list

import kotlin.math.max
import kotlin.math.min

class KotlinArrayList<E>(
    private var elements: Array<Any?> = emptyArray(),
    private var size: Int = 0
) : JavaList<E> {

    companion object {
        private const val DEFAULT_CAPACITY: Int = 10
    }

    override fun size(): Int {
        return this.size
    }


    /**
     * List 에 데이터 하나 추가 O(n)
     * List 내부에 있는 배열 (elements) 의 길이를 초과하면 새로운 배열을 선언하여 데이터를 복사
     * 확장 후 배열은 기존 배열 길이의 1.5 배만큼 증가
     *
     * 배열을 확장하지 않는다면 O(1) 이지만 배열을 확장하는 경우 O(n) 이기 때문에
     * Worst Case 기준으로 시간 복잡도는 O(n)
     */
    override fun add(e: E) {
        if (size == elements.size) {
            growElements(size / 2)
        }

        this.elements[size] = e;
        this.size += 1
    }


    /**
     * List 의 데이터 하나 조회 O(1)
     * index 범위에 맞지 않으면 IndexOutOfBoundsException
     */
    override fun get(index: Int): E {
        if (index < 0 || index >= size) {
            throw IndexOutOfBoundsException()
        }

        return elements[index] as E
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
    override fun remove(e: E) {
        val targetIndex = indexOf(e)

        if (targetIndex == -1) {
            return
        }

        (targetIndex until (size - 1)).forEach { elements[it] = elements[it + 1] }

        this.elements[size - 1] = null
        this.size -= 1
    }

    /**
     * 파라미터 o 가 List 에 포함되면 true, 아니면 false
     * 배열을 일일히 돌면서 확인해야 하기 때문에 시간복잡도는 O(n)
     */
    override fun contains(e: E): Boolean {
        return indexOf(e) > -1
    }


    /**
     * 다른 List 를 받아 전부 추가하는 메서드
     *
     * 항상 1.5 배의 길이로 늘어나는 add 와 다르게 다른 list 의 크기만큼 데이터를 늘려줘야 함
     * 예를 들어 기존 배열이 10 인 경우 다음 길이는 capacity 는 15 여야 하지만
     * list 의 길이가 20 이면 30 이 되어야 하기 때문에 별도의 newSize 만큼 배열의 길이를 늘려줌
     */
    override fun addAll(list: JavaList<E>) {
        val newSize = size + list.size()
        growElements(newSize)

        val array = list.toArray()

        for (i in size until newSize) {
            elements[i] = array[i - size]
        }

        this.size = newSize
    }


    /**
     * capacity 만큼 배열의 길이를 늘려주는 메서드
     * 배열의 데이터가 0 인 경우에는 DEFAULT_CAPACITY (10) 만큼의 공간을 할당
     * 배열의 데이터가 0 이 아닌 경우 1.5 배와 capacity 중 더 큰 값으로 늘려주기
     */
    private fun growElements(capacity: Int) {
        val newCapacity = findCapacity(capacity)
        this.elements = copyOf(elements, newCapacity)
    }

    private fun findCapacity(capacity: Int): Int {
        if (size == 0) {
            return max(capacity, DEFAULT_CAPACITY)
        }

        val oldCapacity = elements.size
        val newCapacity = oldCapacity + (oldCapacity / 2)

        return max(capacity, newCapacity)
    }

    private fun copyOf(array: Array<Any?>, newSize: Int): Array<Any?> {
        val newArray = arrayOfNulls<Any>(newSize)

        val size = min(array.size, newArray.size)
        (0 until size).forEach { newArray[it] = array[it] }

        return newArray
    }


    /**
     * 데이터의 index 위치를 찾는 메서드
     * null 값 자체가 데이터로 들어오는 경우도 있기 때문에 별도의 처리를 해줌
     */
    private fun indexOf(e: E?): Int {
        return e?.let { indexOfElement(e) } ?: indexOfNullElement()
    }

    private fun indexOfNullElement(): Int {
        (0 until size).forEach {
            if (elements[it] == null) {
                return it
            }
        }

        return -1
    }

    private fun indexOfElement(e: E): Int {
        (0 until size).forEach {
            if (e == elements[it]) {
                return it
            }
        }

        return -1
    }

    override fun toArray(): Array<E> {
        return copyOf(elements, size) as Array<E>
    }
}

