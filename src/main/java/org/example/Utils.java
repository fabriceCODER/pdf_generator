package org.example;

public class Utils {
    public static <T> void print(T message) {
        System.out.println(message);
    }

    public static <T> void printArray(T[] array) {
        for (T item : array) {
            System.out.println(item);
        }
    }

    public static void printError(Throwable e) {
        System.out.println("Error occured: " + e.toString());
    }
}