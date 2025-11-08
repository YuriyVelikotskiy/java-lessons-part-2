public class App {
    public static void main(String[] args) {
        MyHashMap<Integer, String> myHashMap = new MyHashMap<>(2,0.75f);

        System.out.println("HashMap должна быть пустой!");
        System.out.println("HashMap пуста? Ответ: " + myHashMap.isEmpty());
        System.out.println("Сколько пар ключ-значение в HashMap? Ответ: " + myHashMap.size());
        System.out.println("_____________________________________________");

        myHashMap.put(1, "String1");
        myHashMap.put(2, "String2");
        myHashMap.put(3, "String3");
        myHashMap.put(4, "String4");
        myHashMap.put(5, "String5");
        myHashMap.put(6, "String6");
        myHashMap.put(6, "String7"); //должно поменяться значение с ключом 6
        System.out.println("В HashMap должно быть 6 пар ключ-значение");
        System.out.println("HashMap пуста? Ответ: " + myHashMap.isEmpty());
        System.out.println("Сколько пар ключ-значение в HashMap? Ответ: " + myHashMap.size());
        System.out.println("_____________________________________________");

        myHashMap.remove(1);
        myHashMap.remove(2);
        myHashMap.remove(3);
        System.out.println("HashMap должно быть 3 пар ключ-значение!");
        System.out.println("HashMap пуста? Ответ: " + myHashMap.isEmpty());
        System.out.println("Сколько пар ключ-значение в HashMap? Ответ: " + myHashMap.size());
        System.out.println("_____________________________________________");

        System.out.println(myHashMap.get(1));
        System.out.println(myHashMap.get(2));
        System.out.println(myHashMap.get(3));
        System.out.println(myHashMap.get(4));
        System.out.println(myHashMap.get(5));
        System.out.println(myHashMap.get(6));
    }
}
