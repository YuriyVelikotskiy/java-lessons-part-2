/**
 * Класс ячейка для {@link MyHashMap  MyHashMap}
 * <p>Методы:
 * <p>{@link #setNextNode setNextNode()} - устанавливает ссылку на следующий элемент с одинаковым индексом
 * <p>{@link #setData setData()} - изменяет данные хранящиеся под данным ключом
 * <p>{@link #getKey()}, {@link #getData()}, {@link #getHash()}, {@link #getNextNode()} - стандартные геттеры
 *
 * @param <K> тип данных ключей
 * @param <V> тип данных значений
 */
public class Node<K, V> {
    private final K key;
    private V data;
    private final int hash;
    private Node<K, V> nextNode;

    public Node(K key, V data) {
        this.key = key;
        this.data = data;
        this.hash = key.hashCode();
        this.nextNode = null;
    }

    public void setNextNode(Node<K, V> nextNode) {
        this.nextNode = nextNode;
    }

    public void setData(V data) {
        this.data = data;
    }

    public K getKey() {
        return key;
    }

    public V getData() {
        return data;
    }

    public int getHash() {
        return hash;
    }

    public Node<K, V> getNextNode() {
        return nextNode;
    }
}
