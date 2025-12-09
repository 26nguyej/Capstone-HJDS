import java.util.PriorityQueue; // Imports Priority Queue package to help construct Heaps

public class HeapExample {
    // Main method
    public static void main(String[] args) {
        // Min Heap (4 values and output)
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        minHeap.add(10);
        minHeap.add(40);
        minHeap.add(20);
        minHeap.add(30);
        minHeap.add(5);
        minHeap.add(60);
        minHeap.add(15);
        System.out.println("Min Heap: " + minHeap);

        // Max Heap (4 values and output)
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> b - a);
        maxHeap.add(10);
        maxHeap.add(40);
        maxHeap.add(20);
        maxHeap.add(30);
        maxHeap.add(5);
        maxHeap.add(60);
        maxHeap.add(15);
        System.out.println("Max Heap: " + maxHeap);
    }
}