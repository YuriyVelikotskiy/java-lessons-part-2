/**
 * Самописная HashMap.
 * <p>Существует 3 конструктора:
 * <p> - создание с дефолтными параметрами размера (16) и процента заполненности (0.75).
 * <p> - создание с указанием размера и с дефолтным процентом заполненности (0.75).
 * <p> - создание с указанием размера и процента заполненности.
 *
 * @param <K> тип данных ключей
 * @param <V> тип данных значений
 */
public class MyHashMap<K, V> {
    private Node<K, V>[] nodes;
    private int hashMapSize;
    private final float loadFactor;

    private static final int DEFAULT_SIZE = 16;

    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    /**
     * Конструктор пустой HashMap требующий размер и процент заполненности.
     *
     * @param initialSize       размер HashMap
     * @param initialLoadFactor процент заполненности HashMap
     * @throws IllegalArgumentException если размер HashMap меньше 1 элемента и процент заполненности HashMap не попадает в диапазон от 0 до 1
     */
    @SuppressWarnings("unchecked")
    public MyHashMap(int initialSize, float initialLoadFactor) {
        if (initialSize <= 0) {
            throw new IllegalArgumentException("The size must be greater than 0");
        }
        if (initialLoadFactor <= 0 || initialLoadFactor > 1) {
            throw new IllegalArgumentException("The load factor must be greater than 0 and less than 1");
        }
        this.loadFactor = initialLoadFactor;
        this.hashMapSize = 0;
        this.nodes = new Node[initialSize];
    }

    /**
     * Конструктор пустой HashMap требующий размер с процентом заполненности (0.75).
     *
     * @param initialSize размер HashMap
     * @throws IllegalArgumentException если размер HashMap меньше 1 элемента
     */
    public MyHashMap(int initialSize) {
        this(initialSize, DEFAULT_LOAD_FACTOR);
    }

    /**
     * Конструктор пустой HashMap с размером (16) и процентом заполненности (0.75).
     *
     * @throws IllegalArgumentException если размер HashMap меньше 1 элемента
     */
    public MyHashMap() {
        this(DEFAULT_SIZE, DEFAULT_LOAD_FACTOR);
    }

    /**
     * Метод вычисления индекса элемента в массиве
     */
    private int getIndex(int hash) {
        return Math.abs(hash % (nodes.length - 1));
    }

    /**
     * Метод добавления элементов в HashMap
     *
     * @param key   ключ
     * @param value хранимое значение
     */
    public void put(K key, V value) {
        Node<K, V> newNode = new Node<>(key, value);
        int hash = newNode.getHash();
        int index = getIndex(hash);
        Node<K, V> head = nodes[index];
        Node<K, V> curr = head;
        while (curr != null) {
            if (curr.getKey().equals(key)) {
                curr.setData(newNode.getData());
                return;
            }
            curr = curr.getNextNode();
        }
        newNode.setNextNode(head);
        nodes[index] = newNode;
        hashMapSize++;
        if (hashMapSize > nodes.length * loadFactor) {
            rehash();
        }
    }

    /**
     * Метод изменения размера HashMap и пересчета индексов
     */
    @SuppressWarnings("unchecked")
    private void rehash() {
        Node<K, V>[] oldNodes = nodes;
        nodes = new Node[nodes.length * 2];
        hashMapSize = 0;
        for (Node<K, V> head : oldNodes) {
            while (head != null) {
                put(head.getKey(), head.getData());
                head = head.getNextNode();
            }
        }
    }
    /**
     * Метод изменения выдающий значение по ключу
     * @param key ключ
     * @return значение по ключу, если такой ключ не найден, то возвращает null
     */
    public V get(K key) {
        int index = getIndex(key.hashCode());
        Node<K, V> head = nodes[index];
        while (head != null){
            if (head.getKey().equals(key)){
                return head.getData();
            }
            head = head.getNextNode();
        }
        return null;
    }

    /**
     * Метод удаления по ключу
     * @param key ключ
     */
    public void remove(K key) {
        int index = getIndex(key.hashCode());
        Node<K, V> head = nodes[index];
        Node<K, V> pre = null;
        while (head != null) {
            if (head.getKey().equals(key)) {
                if (pre == null) {
                    nodes[index] = head.getNextNode();
                } else {
                    pre.setNextNode(head.getNextNode());
                }
                hashMapSize -= 1;
                return;
            }
            pre = head;
            head = head.getNextNode();
        }
    }

    /**
     * Метод возвращает количество пар ключ-значение
     * @return количество пар ключ-значение
     */
    public int size() {
        return hashMapSize;
    }

    /**
     * Метод возвращает true, если HashMap не содержит пар ключ-значение
     * @return true, если HashMap не содержит пар ключ-значение
     */
    public boolean isEmpty() {
        return hashMapSize == 0;
    }
}
