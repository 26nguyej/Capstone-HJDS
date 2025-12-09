import java.util.ArrayList;

public class MinHeap {
    private ArrayList<Integer> heap;

    public MinHeap() {
        heap = new ArrayList<>();
    }

    public void insert(int value) {
        heap.add(value);
        int index = heap.size() - 1;
        while (index > 0 && heap.get(index) < heap.get((index - 1) / 2 )) {
            int parent = (index - 1) / 2;
            int temp = heap.get(index);
            heap.set(index, heap.get(parent));
            heap.set(parent, temp);
            index = parent;
        }
    }

    public void deletion(int value) {
        heap.remove(value);
        int index = 0;
        while (index < heap.size() - 1 && heap.get(index) > heap.get((index - 1) / 2)) {
            int parent 
        }
    }

    public void printHeap() {
        System.out.println(heap);
    }

    public static void main(String[] args) {
        MinHeap minHeap = new MinHeap();
        minHeap.insert(20);
        minHeap.insert(15);
        minHeap.insert(30);
        minHeap.insert(10);
        minHeap.insert(25);
        minHeap.insert(5);
        minHeap.insert(35);
        minHeap.insert(12);
        minHeap.printHeap();
    }
}