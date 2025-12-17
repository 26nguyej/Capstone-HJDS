# Employee Schedule Manager

## Overview

Employee Schedule Manager is a Java-based desktop application designed to manage and visualize employee work schedules. The project was developed as part of a Capstone assignment and demonstrates both data structure proficiency and GUI design using Java Swing.

The application allows users to add, remove, update, display, and search employee schedules by date. It also includes a calendar-based visualization for improved usability. Two backend implementations are provided—a linked list and a TreeMap—for comparison and extra credit purposes.

---

## Project Goals

* Demonstrate understanding of core Java concepts
* Apply custom data structures (Linked List and TreeMap)
* Build a functional Swing-based GUI
* Practice GitHub workflow and documentation standards
* Compare performance and design tradeoffs between data structures

---

## Features

### Core Functionality

* Add a work day with one or more employees
* Remove a scheduled day
* Update employees assigned to a day
* Display the full schedule in chronological order
* Search for an employee across all scheduled days

### GUI Features

* Dark-mode themed Swing interface
* Tabbed layout with:

  * Schedule output view
  * Monthly calendar view
* Clickable calendar days with employee details
* Highlighted days with scheduled employees
* Current day visual indicator

### Data Structure Implementations

* **Linked List (`Schedule`)**

  * Custom `DayNode` implementation
  * Manual date sorting using `LocalDate`
  * Used as the primary backend for the GUI

* **TreeMap (`ScheduleMap`)**

  * Automatically sorted by `LocalDate`
  * Prevents duplicate dates by design
  * Implemented for comparison and extra credit

---

## High-Level Architecture

```
EmployeeScheduleMulti (Main Entry Point)
│
├── Schedule (Linked List Backend)
│   └── DayNode
│
├── ScheduleMap (TreeMap Backend)
│
├── EmployeeScheduleGUI (Swing GUI)
│   └── CalendarPanel (Monthly Calendar View)
│
└── Input Validation & Search Utilities
```

---

## How It Works

### Schedule Management

* Dates are stored using `yyyy-MM-dd` format
* Duplicate dates are prevented in both implementations
* Employee names are stored as comma-separated strings

### Calendar View

* Displays the current month by default
* Navigation buttons allow month-to-month traversal
* Days with scheduled employees are highlighted
* Clicking a day displays employee details below the calendar

---

## Setup Instructions

### Requirements

* Java JDK 17+ (recommended)
* IDE such as IntelliJ IDEA, Eclipse, or VS Code

### Running the Application

1. Clone the repository:

   ```bash
   git clone https://github.com/your-username/employee-schedule-manager.git
   ```
2. Open the project in your Java IDE
3. Ensure all `.java` files are in the same package (default package)
4. Run the `EmployeeScheduleMulti` class

The GUI will launch automatically.

---

## Usage

### GUI Mode (Primary)

* Use the buttons to add, remove, update, display, or search schedule entries
* Switch to the **Calendar** tab to view schedules visually

### Console Mode (Optional)

* The `chooseFunction()` method in `EmployeeScheduleMulti` allows console-based interaction
* Useful for testing or demonstrating non-GUI logic

---

## GitHub Workflow & Development Process

This repository reflects an iterative development process:

* Incremental feature additions (add/remove/update/search)
* Refactoring for input validation and sorting
* GUI integration after core logic completion
* Extra credit TreeMap implementation added later for comparison
* Documentation pass added after functionality stabilized

### Commit History

* Commits are descriptive and scoped (e.g., "Add TreeMap-based ScheduleMap", "Integrate CalendarPanel into GUI")
* Demonstrates progressive enhancement rather than a single final commit

---

## Comparison: Linked List vs TreeMap

| Aspect               | Linked List (`Schedule`) | TreeMap (`ScheduleMap`) |
| -------------------- | ------------------------ | ----------------------- |
| Ordering             | Manual sorting           | Automatic sorting       |
| Duplicate Prevention | Explicit check           | Built-in                |
| Complexity           | More control             | Cleaner implementation  |
| Educational Value    | High (custom DS)         | High (Java Collections) |

---

## Known Limitations

* Employee names stored as strings rather than objects
* No persistent storage (data resets on restart)
* GUI currently uses linked list backend only

---

## Future Improvements

* Persist schedules to file or database
* Integrate `ScheduleMap` directly into GUI
* Add per-employee objects and shift times
* Improve calendar interactivity (edit-on-click)

---

## Academic Context

This project was completed as part of a Capstone course assignment. Some components were developed with assistance from ChatGPT, particularly:

* Documentation
* TreeMap-based comparison implementation
* Calendar UI scaffolding

All logic was reviewed and integrated by the student.

---

## Author

**Jacob Nguyen**

Computer Science Capstone Project

---

## License

This project is intended for educational use. No commercial license is applied.
