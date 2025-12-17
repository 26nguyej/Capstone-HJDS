// TreeMap-based rewrite of the Schedule class
// Added with assistance from ChatGPT for Capstone extra credit and comparison.
import java.time.LocalDate;
import java.util.Map;
import java.util.TreeMap;

public class ScheduleMap {

    private TreeMap<LocalDate, String> map = new TreeMap<>();

    // Prevents duplicates automatically
    public boolean addDay(LocalDate date, String employees) {
        if (map.containsKey(date)) {
            System.out.println("A schedule already exists for this date.");
            return false;
        }
        map.put(date, employees);
        return true;
    }

    public boolean removeDay(LocalDate date) {
        if (!map.containsKey(date)) {
            System.out.println("Date not found.");
            return false;
        }
        map.remove(date);
        return true;
    }

    public boolean updateDay(LocalDate date, String employees) {
        if (!map.containsKey(date)) {
            System.out.println("Date not found.");
            return false;
        }
        map.put(date, employees);
        return true;
    }

    public void display() {
        if (map.isEmpty()) {
            System.out.println("Schedule is empty.");
            return;
        }
        for (Map.Entry<LocalDate, String> entry : map.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    // Added with ChatGPT assistance:
    // Returns all dates where an employee is scheduled
    public void searchEmployee(String name) {
        boolean found = false;
        for (Map.Entry<LocalDate, String> entry : map.entrySet()) {
            if (entry.getValue().toLowerCase().contains(name.toLowerCase())) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
                found = true;
            }
        }
        if (!found) {
            System.out.println("Employee not found in schedule.");
        }
    }

    // ChatGPT-assisted getter for GUI access
    public TreeMap<LocalDate, String> getMap() {
        return map;
    }


}
