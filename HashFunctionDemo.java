public class HashFunctionDemo { // public class that wraps the program
    public static int customHash(String key, int tableSize) { // static method that creates a hash table and returns final hash value
        int hash = 0;

        for (char c : key.toCharArray()) {
            hash += c;
        }
        return hash % tableSize;
    }

    public static void main(String[] args) { // Main method to create Hashes for three words
        System.out.println("Hash for 'cat': " + customHash("cat", 7));
        System.out.println("Hash for 'act': " + customHash("act", 7));
        System.out.println("Hash for 'dog': " + customHash("dog", 7));
    }
} 