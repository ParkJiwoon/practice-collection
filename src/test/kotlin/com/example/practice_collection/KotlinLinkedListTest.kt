package com.example.practice_collection

import com.example.practice_collection.list.KotlinArrayList
import com.example.practice_collection.list.KotlinLinkedList
import com.example.practice_collection.list.JavaList
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class KotlinLinkedListTest {

    @Test
    @DisplayName("KotlinLinkedList add 테스트")
    fun testArrayList() {
        val list: JavaList<String> = KotlinLinkedList()
        list.add("add 1")
        list.add("add 2")
        assertThat(list.size()).isEqualTo(2)
    }

    @Test
    @DisplayName("KotlinLinkedList get 테스트")
    fun testGet() {
        val list: JavaList<String> = KotlinLinkedList()
        list.add("get sample")

        // success
        assertThat(list[0]).isEqualTo("get sample")

        // 범위에 없는 index 를 넣으면 Exception
        assertThatExceptionOfType(IndexOutOfBoundsException::class.java)
            .isThrownBy { list[1] }
    }

    @Test
    @DisplayName("KotlinLinkedList contains 테스트")
    fun testContains() {
        val list: JavaList<String?> = KotlinLinkedList()
        list.add("contains sample")

        // 있는 값을 넣으면 true
        assertThat(list.contains("contains sample")).isTrue

        // 없는 값을 넣으면 false
        assertThat(list.contains("contains")).isFalse

        // null 값이 없으면 false
        assertThat(list.contains(null)).isFalse

        // null 값이 있으면 true
        list.add(null)
        assertThat(list.contains(null)).isTrue

        // null 값이 있어도 다른 데이터는 정상 동작해야함
        assertThat(list.contains("contains sample")).isTrue
        assertThat(list.contains("contains")).isFalse
    }

    @Test
    @DisplayName("KotlinLinkedList remove 테스트")
    fun testRemove() {
        val list: JavaList<String> = KotlinLinkedList()

        // 없는 요소를 넣으면 Nothing
        list.remove("hello")
        for (i in 0..9) {
            list.add("remove " + (i + 1))
        }

        // 마지막 원소 제거
        list.remove("remove 10")

        // 첫번째 원소 제거
        list.remove("remove 1")

        // 중간 원소 제거
        list.remove("remove 3")

        // 최종 결과
        assertThat(list.size()).isEqualTo(7)
        assertThat(list[0]).isEqualTo("remove 2")
        assertThat(list[1]).isEqualTo("remove 4")
        assertThat(list[list.size() - 1]).isEqualTo("remove 9")
    }

    @Test
    @DisplayName("KotlinLinkedList addAll 테스트")
    fun testAddAll() {
        val list: JavaList<String> = KotlinLinkedList()

        // 빈 리스트를 넣으면 아무것도 없음
        list.addAll(KotlinLinkedList())
        assertThat(list.size()).isEqualTo(0)

        // 빈 리스트에 다른 리스트를 넣을 수 있음
        val otherList: JavaList<String> = KotlinLinkedList()
        otherList.add("addAll 1")
        otherList.add("addAll 2")
        list.addAll(otherList)
        assertThat(list.size()).isEqualTo(2)
        assertThat(list[0]).isEqualTo("addAll 1")
        assertThat(list[1]).isEqualTo("addAll 2")

        // 리스트가 존재할 때 다른 리스트를 넣을 수 있음
        list.addAll(otherList)
        assertThat(list.size()).isEqualTo(4)
        assertThat(list[0]).isEqualTo("addAll 1")
        assertThat(list[1]).isEqualTo("addAll 2")
        assertThat(list[2]).isEqualTo("addAll 1")
        assertThat(list[3]).isEqualTo("addAll 2")

        // JavaList 의 구현체라면 KotlinArrayList 도 가능하다
        val arrayList: JavaList<String> = KotlinArrayList()
        arrayList.add("arrayList 1")
        arrayList.add("arrayList 2")
        arrayList.add("arrayList 3")
        list.addAll(arrayList)

        assertThat(list.size()).isEqualTo(7)
        assertThat(list[0]).isEqualTo("addAll 1")
        assertThat(list[1]).isEqualTo("addAll 2")
        assertThat(list[2]).isEqualTo("addAll 1")
        assertThat(list[3]).isEqualTo("addAll 2")
        assertThat(list[4]).isEqualTo("arrayList 1")
        assertThat(list[5]).isEqualTo("arrayList 2")
        assertThat(list[6]).isEqualTo("arrayList 3")
    }
}
