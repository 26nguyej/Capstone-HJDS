import java.util.*;

public class CollisionResolutionDemo {

    // Custom hash function that forces collisions

    static int customHash(String key) {
        switch (key) {
            case "Alice": return 2;
            case "Bob": return 2;
            case "Charlie": return 4;
            case "Diana": return 4;
            case "Eve": return 6;
            default: return 0;
        }
    }

    // Demonstration of chaining 
    static void demonstrateChaining(String[] keys, int tableSize) {
        System.out.println("=== Chaining ===");
        List<String>[] table = new LinkedList[tableSize];
        for (int i = 0; i < table.length; i++) {
            table[i] = new LinkedList<>();
        }

        for (String key : keys) {
            int index = customHash(key);
            table[index].add(key);
        }

        for (int i = 0; i < table.length; i++) {
            System.out.println("Index " + i + ": " + table[i]);
        }
    }

    // Demonstration of linear probing
    static void demonstrateLinearProbing(String[] keys, int tableSize) {
        System.out.println("\n=== Linear Probing ===");
        String[] table = new String[tableSize]
    }
}