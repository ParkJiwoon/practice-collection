package com.example.practice_collection.list

class KotlinLinkedList<E>(
    private var size: Int = 0,
    private val first: Node<E> = Node(),
    private val last: Node<E> = Node()
) : JavaList<E> {

    /**
     * 기존 LinkedList 와 다르게 first, last 에 dummy node 를 넣어두는 LinkedList
     *
     * 장점
     * 1. first, last 는 항상 더미 노드로 존재하기 때문에 맨앞, 맨뒤 데이터 변경 시 first, last 를 관리할 필요가 없음
     * 2. 맨앞, 맨뒤 데이터 추가, 삭제 시 중간에 삭제하는 것과 동일하게 처리할 수 있음
     */

    init {
        first.next = last
        last.prev = first
    }

    companion object {
        class Node<E>(
            val item: E? = null,
            var prev: Node<E>? = null,
            var next: Node<E>? = null
        )
    }

    override fun size(): Int {
        return size
    }

    /**
     * List 의 맨 뒤에 데이터 추가 O(1)
     *
     * LinkedList 의 특성상 맨앞에도 O(1) 의 속도로 추가할 수 있다
     * 중간에 추가하려면 탐색 시간 때문에 O(n)
     */
    override fun add(e: E) {
        val newNode = Node(e, last.prev, last)

        last.prev!!.next = newNode
        last.prev = newNode

        size += 1
    }

    /**
     * 특정 index 의 데이터를 가져온다 O(n)
     * index 의 범위를 벗어나면 IndexOutOfBoundsException
     * 찾는 시간을 조금이라도 줄이기 위해 size 와 비교해서 앞에서부터 찾거나 뒤에서부터 찾음
     */
    override fun get(index: Int): E {
        validateIndexRange(index)

        if (index < (size / 2)) {
            return findNodeFromFirst(index)
        }

        return findNodeFromLast(index)
    }

    private fun validateIndexRange(index: Int) {
        if (index < 0 || index >= size) {
            throw IndexOutOfBoundsException()
        }
    }

    private fun findNodeFromFirst(index: Int): E {
        var node: Node<E> = first.next!!

        repeat (index) { node = node.next!! }

        return node.item!!
    }

    private fun findNodeFromLast(index: Int): E {
        var node: Node<E> = last.prev!!

        ((size - 1) downTo (index + 1)).forEach {
            node = node.prev!!
        }

        return node.item!!
    }

    /**
     * List 에 존재하는 데이터 하나 삭제 O(n)
     *
     * LinkedList 의 특성상 배열처럼 삭제 후 데이터를 밀어주는 작업은 없지만
     * 데이터를 찾는데 걸리는 시간이 존재하기 때문에 O(n) 의 시간복잡도를 가짐
     *
     * pop(), poll() 처럼 맨앞이나 맨뒷값을 제거한다는 사실을 알고있다면 O(1)
     */
    override fun remove(e: E) {
        findNode(e)?.let { unlink(it) }
    }

    /**
     * 파라미터가 List 에 포함되면 true, 아니면 false
     * 배열을 일일히 돌면서 확인해야 하기 때문에 시간복잡도는 O(n)
     */
    override fun contains(e: E): Boolean {
        return findNode(e) != null
    }

    private fun findNode(e: E?): Node<E>? {
        e ?: return findNodeOfNullItem()
        return findNodeOfItem(e)
    }

    private fun findNodeOfNullItem(): Node<E>? {
        var node: Node<E> = first.next!!

        (0 until size).forEach { _ ->
            if (node.item == null) {
                return node
            }

            node = node.next!!
        }

        return null
    }

    private fun findNodeOfItem(e: E): Node<E>? {
        var node: Node<E> = first.next!!

        (0 until size).forEach { _ ->
            if (e == node.item) {
                return node
            }

            node = node.next!!
        }

        return null
    }

    private fun unlink(node: Node<E>) {
        node.prev!!.next = node.next
        node.next!!.prev = node.prev

        node.next = null
        node.prev = null
        size -= 1
    }

    /**
     * ArrayList 와 다르게 capacity 의 관리같은거 할 필요 없이 맨 뒤에 주르륵 추가 가능
     */
    override fun addAll(list: JavaList<E>) {
        list.toArray().forEach { add(it) }
    }

    override fun toArray(): Array<E> {
        val result: Array<Any?> = arrayOfNulls(size)
        var node: Node<E> = first.next!!

        (0 until size).forEach {
            result[it] = node.item
            node = node.next!!
        }

        return result as Array<E>
    }
}
