import java.util.PriorityQueue;

public class PriorityScheduling { // Main class
    public static void main(String[] args) { // main method to add the tasks
        PriorityQueue<Task> eventQueue = new PriorityQueue<>();
        eventQueue.add(new Task("Health Boost", 3));
        eventQueue.add(new Task("Shield Potion", 1));
        eventQueue.add(new Task("Reload", 2));
        
        while (!eventQueue.isEmpty()) {
            System.out.println("Executing: " + eventQueue.poll());
        }
    }
}

class Task implements Comparable<Task> { // Class for the Task
    String name;
    int priority;

    public Task(String name, int priority) { // constructor for the Task
        this.name = name;
        this.priority = priority;
    }

    public int compareTo(Task other) { // compare method for the priority of two tasks
        return this.priority - other.priority;
    }

    public String toString() { // Returns a String for priority level
        return name + " (Priority: " + priority + ")";
    }
}
