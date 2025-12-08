# EmployeeScheduleMulti

A Console-Based Java Employee Scheduling System

EmployeeScheduleMulti is a lightweight Java console application that manages a chronologically sorted employee work schedule. It uses a custom singly linked list to store workdays, allowing users to add, update, remove, and view entries through a simple interactive menu.

## Features

* Add a workday (automatically sorted by date)

* Remove a workday

* Update employees assigned to a date

* Display full schedule in chronological order

* Interactive text menu (CLI)

* Educational: demonstrates linked lists, date handling, and console I/O in Java

## Tech Stack

* Java 17 or higher

* Custom singly linked list

* java.time.LocalDate for date handling

* Scanner for user input

## Project Structure
EmployeeScheduleMulti/
│
├── EmployeeScheduleMulti.java   # Main program and menu
├── Schedule.java                # Linked list implementation (add, remove, update)
└── DayNode.java                 # Node representing each scheduled date


(If you keep all classes in one file, the above is the logical structure.)

## How It Works
### Add a Day

* Prompts for a date in yyyy-mm-dd format.

* Prompts for a comma-separated list of employee names.

* Inserts the new day in the linked list at the correct chronological position.

### Remove a Day

* Prompts for a date.

* Removes the corresponding node from the linked list if it exists.

### Update a Day

* Prompts for a date.

* Prompts for a new comma-separated list of employees.

* Updates the matching entry’s employees.

### Display Schedule

* Prints all schedule entries in chronological order.

Example output:

2025-10-06: Jacob, Daniel
2025-10-07: Keller
2025-10-08: Matthew, Jacob

### Exit

* Ends the interactive program.

## Running the Program
### Compile
javac EmployeeScheduleMulti.java

### Run
java EmployeeScheduleMulti


When running you will see the interactive menu:

Choose an option:
1. Add a day
2. Remove a day
3. Update day
4. Display schedule
5. Exit
Enter choice:

## Example Interaction
Please enter the date you wish to add (yyyy-mm-dd): 2025-10-10  
Please enter the employees (separated by commas): Sarah, Tim  
Added 2025-10-10 with employees: Sarah, Tim  

Employee Schedule:  
2025-10-06: Jacob, Daniel  
2025-10-07: Keller  
2025-10-08: Matthew, Jacob  
2025-10-10: Sarah, Tim  

## Possible Future Improvements

* Add input validation for date format and employee names

* Prevent duplicate dates

* Add search by employee name or date range

* Save/load schedule to/from a file (CSV or JSON)

* Replace the linked list with TreeMap<LocalDate, String> for simpler code and faster lookups

* Add a GUI (JavaFX or Swing)
