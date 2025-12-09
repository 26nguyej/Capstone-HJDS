import java.util.ArrayList;
import java.util.Random;

// Simulates dispatch system
public class DispatchSystem {
    public static void main(String[] args) {
        MinHeap dispatchQueue = new MinHeap();

        // Sample calls
        dispatchQueue.insert(new Call("Cat stuck in tree", 5));
        dispatchQueue.insert(new Call("Car accident", 2));
        dispatchQueue.insert(new Call("House fire", 1));
        dispatchQueue.insert(new Call("Stolen bike", 5));
        dispatchQueue.insert(new Call("Heart attack", 1));

        dispatchQueue.printHeap();

        // Processing calls
        System.out.println("Dispatching in order of priority:");
        while (!dispatchQueue.isEmpty()) {
            System.out.println(dispatchQueue.remove());
        }
    }
}

// Represents an emergency call
class Call {
    String description;
    int priority; // Lower number = higher priority

    public Call(String description, int priority) {
        this.description = description;
        this.priority = priority;
    }

    public String toString() {
        return "Call: " + description + " | Priority: " + priority;
    }
}

// Simple MinHeap for Calls
class MinHeap {
    private ArrayList<Call> heap;

    public MinHeap() {
        heap = new ArrayList<>();
    }

    public void insert(Call call) {
        heap.add(call);
        bubbleUp(heap.size() - 1);
    }

    public Call remove() {
        if (heap.size() == 0) return null;
        Call root = heap.get(0);
        Call last = heap.remove(heap.size() - 1);
        if (heap.size() > 0) {
            heap.set(0, last);
            bubbleDown(0);
        }
        return root;
    }

    public boolean isEmpty() {
        return heap.isEmpty();
    }

    private void bubbleUp(int index) {
        while (index > 0) {
            int parent = (index - 1) / 2;
            if (heap.get(index).priority <= heap.get(parent).priority) {
                swap(index, parent);
                index = parent;
            } else {
                break;
            }
        }
    }

    private void bubbleDown(int index) {
        int left, right, smallest;
        while (true) {
            left = 2 * index + 1;
            right = 2 * index + 2;
            smallest = index;

            if (left < heap.size() && heap.get(left).priority < heap.get(smallest).priority) {
                smallest = left;
            }
            if (right < heap.size() && heap.get(right).priority < heap.get(smallest).priority) {
                smallest = right;
            }

            if (smallest != index) {
                swap(index, smallest);
                index = smallest;
            } else {
                break;
            }
        }
    }

    private void swap(int i, int j) {
        Call temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }

    public void printHeap() {
        System.out.println("=== Current Emergency Call Queue ===");
        for (int i = 0; i < heap.size(); i++) {
            System.out.println("[" + i + "] " + heap.get(i).description + " (Priority: " + heap.get(i).priority + ")");
        }
        System.out.println("====================================\n");
    }

    public void reprioritize(String description, int newPriority) {
        for (int i = 0; i < heap.size(); i++) {
            if (heap.get(i).description.equals(description)) {
                heap.get(i).priority = newPriority;

                // Fix heap after changing priority
                bubbleUp(i);
                bubbleDown(i);
                return;
            }
        }
        System.out.println("Call not found: " + description);
    }
}

