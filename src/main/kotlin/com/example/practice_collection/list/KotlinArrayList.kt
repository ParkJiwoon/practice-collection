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

    override fun add(e: E) {
        if (size == elements.size) {
            growElements(size / 2)
        }

        this.elements[size] = e;
        this.size += 1
    }

    override fun get(index: Int): E {
        if (index < 0 || index >= size) {
            throw IndexOutOfBoundsException()
        }

        return elements[index] as E
    }

    override fun remove(e: E) {
        val targetIndex = indexOfElement(e)

        if (targetIndex == -1) {
            return
        }

        (targetIndex until (size - 1)).forEach { elements[it] = elements[it + 1] }

        this.elements[size - 1] = null
        this.size -= 1
    }

    override fun contains(e: E): Boolean {
        return indexOfElement(e) > -1
    }

    override fun addAll(list: JavaList<E>) {
        if (list.size() == 0) {
            return
        }

        val newSize = size + list.size()
        growElements(newSize)

        for (i in size until newSize) {
            elements[i] = list[i - size]
        }

        size = newSize
    }

    private fun growElements(capacity: Int) {
        if (size == 0) {
            this.elements = copyOf(elements, max(capacity, DEFAULT_CAPACITY))
            return
        }

        val oldCapacity = elements.size
        val newCapacity = oldCapacity + capacity
        this.elements = copyOf(elements, newCapacity)
    }

    private fun copyOf(array: Array<Any?>, newSize: Int): Array<Any?> {
        val newArray = arrayOfNulls<Any>(newSize)

        val size = min(array.size, newArray.size)
        (0 until size).forEach { newArray[it] = array[it] }

        return newArray
    }

    private fun indexOfElement(e: E?): Int {
        for (i in 0 until size) {
            if (e == null) {
                if (elements[i] == null) {
                    return i
                }

                continue
            }

            if (e == elements[i]) {
                return i
            }
        }

        return -1
    }

    override fun toArray(): Array<E> {
        TODO("Not yet implemented")
    }
}

