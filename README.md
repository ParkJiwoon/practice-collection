## 소개

Java, Kotlin 에서 Collection 을 직접 구현하기 위해 연습하는 코드입니다.

Spring Boot 와 최대한 비슷한 환경을 만들기 위해 일부러 Spring Boot 프로젝트로 만들었습니다.

## 환경

- Java 17
- Kotlin
- Spring Boot 3.0.0

## 방법

1. `git fork`
2. fork 된 레포지토리를 `git clone`
3. 테스트 코드가 통과하게 코드 작성

## 코드 구현

Java 에서 제공하는 공식 Collection 과 다른 부분이 있을 수 있지만 메서드 네이밍 등은 최대한 맞추었습니다.

- ArrayList
  - [Java](https://github.com/ParkJiwoon/practice-collection/blob/implemented/src/main/kotlin/com/example/practice_collection/list/JavaArrayList.java)
  - [Kotlin](https://github.com/ParkJiwoon/practice-collection/blob/implemented/src/main/kotlin/com/example/practice_collection/list/KotlinArrayList.kt)
- LinkedList
  - [Java](https://github.com/ParkJiwoon/practice-collection/blob/implemented/src/main/kotlin/com/example/practice_collection/list/JavaLinkedList.java): Dummy Node 사용하지 않는 Java 공식과 비슷한 구현
  - [Java](https://github.com/ParkJiwoon/practice-collection/blob/implemented/src/main/kotlin/com/example/practice_collection/list/JavaDummyLinkedList.java): `first`, `last` 에 Dummy Node 넣어서 구현
  - [Kotlin](https://github.com/ParkJiwoon/practice-collection/blob/implemented/src/main/kotlin/com/example/practice_collection/list/KotlinLinkedList.kt): `first`, `last` 에 Dummy Node 넣어서 구현
