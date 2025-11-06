import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class MyHashMapTest {
    static MyHashMap<String, String> myStringStringMap;
    static MyHashMap<Integer, String> myIntegerStringMap;

    @BeforeEach
    public void cleanUpEach() {
        myStringStringMap = new MyHashMap<>();
        myIntegerStringMap = new MyHashMap<>();
    }

    @Test
    @DisplayName("Тест конструктора на невалидный Size при создании")
    void afterCreateShouldBeExceptionInvalidSize() {
        assertThrows(IllegalArgumentException.class, () -> new MyHashMap<>(0));
        assertThrows(IllegalArgumentException.class, () -> new MyHashMap<>(-1));
    }

    @Test
    @DisplayName("Тест конструктора на невалидный LoadFactor при создании")
    void afterCreateShouldBeExceptionInvalidLoadFactor() {
        assertThrows(IllegalArgumentException.class, () -> new MyHashMap<>(1, 0f));
        assertThrows(IllegalArgumentException.class, () -> new MyHashMap<>(1, 1.1f));
    }

    @Test
    @DisplayName("Тест вызова метода isEmpty() при пустой Map")
    void shouldBeEmpty() {
        assertTrue(myStringStringMap.isEmpty());
        assertTrue(myIntegerStringMap.isEmpty());
    }

    @Test
    @DisplayName("Тест вызова метода isEmpty() при заполненной Map")
    void afterAddShouldNotBeEmpty() {
        myIntegerStringMap.put(1, "1");
        myStringStringMap.put("1", "1");
        assertFalse(myStringStringMap.isEmpty());
        assertFalse(myIntegerStringMap.isEmpty());
    }

    @Test
    @DisplayName("Тест вызова метода size() при пустой Map")
    void sizeShouldBeZero() {
        assertEquals(myIntegerStringMap.size(), 0);
        assertEquals(myStringStringMap.size(), 0);
    }

    @Test
    @DisplayName("Тест вызова метода size() при заполнение")
    void sizeShouldBeSix() {
        putSixNodes();
        assertEquals(myIntegerStringMap.size(), 6);
        assertEquals(myStringStringMap.size(), 6);
    }

    @Test
    @DisplayName("Тест вызова метода size() при удалении")
    void sizeShouldBeFive() {
        putSixNodes();
        myIntegerStringMap.remove(1);
        myStringStringMap.remove("1");
        assertEquals(myIntegerStringMap.size(), 5);
        assertEquals(myStringStringMap.size(), 5);
    }

    void putSixNodes() {
        for (int i = 1; i <= 6; i++) {
            myIntegerStringMap.put(i, String.valueOf(i));
            myStringStringMap.put(String.valueOf(i), String.valueOf(i));
        }
    }
}