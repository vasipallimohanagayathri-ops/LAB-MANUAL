import java.util.*;
class Box<T> {
    private T item;

    public void set(T item) {
        this.item = item;
    }

    public T get() {
        return item;
    }

    public void showType() {
        System.out.println("Type of stored item : " + item.getClass().getName());
    }
}
class Pair<K, V> {
    private K key;
    private V value;

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public void display() {
        System.out.println(key + " = " + value);
    }
}

public class GenericDemo {
    public static <T extends Comparable<T>> T findMax(T[] array) {
        if (array == null || array.length == 0) {
            return null;
        }
        T max = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i].compareTo(max) > 0) {
                max = array[i];
            }
        }
        return max;
    }

    public static void main(String[] args) {
     
        Box<Integer> intBox = new Box<>();
        intBox.set(100);
        System.out.println("Integer Box Value : " + intBox.get());
        intBox.showType();
        Box<String> strBox = new Box<>();
        strBox.set("Hello Generics");
        System.out.println("String Box Value : " + strBox.get());
        strBox.showType();
        System.out.println("---- Key-Value Pairs ----");
        Pair<String, Integer> pair1 = new Pair<>("Rahul", 88);
        pair1.display();

        Pair<Integer, String> pair2 = new Pair<>(101, "CSE");
        pair2.display();
        Integer[] intArray = {45, 89, 12, 67, 23};
        String[] strArray = {"Rahul", "Sneha", "Kiran", "Divya"};
        Double[] doubleArray = {78.5, 92.3, 85.0, 64.2};

        System.out.println("Maximum Number : " + findMax(intArray));
        System.out.println("Maximum (Alphabetical) : " + findMax(strArray));
        System.out.println("Maximum Marks : " + findMax(doubleArray));
    }
}
