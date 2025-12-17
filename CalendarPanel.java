/*
 * CalendarPanel
 * -------------
 * A Swing JPanel that displays a monthly calendar view for the employee schedule.
 *
 * This panel:
 * - Shows the current month in a grid-based calendar layout
 * - Highlights days that have scheduled employees
 * - Highlights the current day with a special border
 * - Allows month navigation (previous / next)
 * - Displays employee details for a selected day
 *
 * NOTES:
 * - This panel is designed to work with the Schedule class backend
 * - Existing inline comments have been preserved exactly as written
 * - Only BLOCK COMMENTS have been added for documentation
 * - No code, logic, or structure has been changed
 */

import javax.swing.*;              /* Swing UI components */
import javax.swing.border.*;       /* Borders for styling */
import java.awt.*;                 /* Layouts, colors, fonts */
import java.awt.event.*;           /* Event handling */
import java.time.*;                /* Date/time utilities */
import java.time.format.DateTimeFormatter; /* Month formatting */
import java.util.*;                /* Utility classes */

/*
 * CalendarPanel class
 * -------------------
 * Custom JPanel that renders a monthly calendar view
 * and integrates with the Schedule data structure.
 */
public class CalendarPanel extends JPanel {

    /* Reference to the shared schedule backend */
    private Schedule schedule;

    /* Currently displayed month (always set to day 1) */
    private LocalDate currentMonth;

    /* UI components */
    private JLabel monthLabel;      /* Displays current month/year */
    private JPanel calendarGrid;    /* Holds day headers and day buttons */
    private JTextArea dayDetails;   /* Displays selected day details */

    /* ---------- COLOR THEME (DARK MODE) ---------- */
    private final Color bgColor = new Color(34, 34, 34);
    private final Color dayColor = new Color(50, 50, 50);
    private final Color textColor = new Color(230, 230, 230); // Light text for readability
    private final Color highlightColor = new Color(70, 130, 180);
    private final Color todayBorderColor = Color.YELLOW; // ⭐ TODAY BORDER

    /*
     * Constructor
     * Initializes the calendar panel and displays the current month
     *
     * @param schedule The Schedule object used to retrieve employee data
     */
    public CalendarPanel(Schedule schedule) {
        this.schedule = schedule;
        this.currentMonth = LocalDate.now().withDayOfMonth(1);
        setLayout(new BorderLayout(10, 10));
        setBackground(bgColor);
        createCalendarUI();
        refreshCalendar();
    }

    /*
     * Builds the static UI components of the calendar panel
     * - Header with navigation buttons
     * - Calendar grid
     * - Day detail display area
     */
    private void createCalendarUI() {
        // Header panel with month and navigation
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(bgColor);

        JButton prevBtn = new JButton("<");
        prevBtn.setBackground(dayColor);
        prevBtn.setForeground(textColor); // ✅ Make arrow text readable
        prevBtn.setFocusPainted(false);
        prevBtn.addActionListener(e -> {
            currentMonth = currentMonth.minusMonths(1);
            refreshCalendar();
        });

        JButton nextBtn = new JButton(">");
        nextBtn.setBackground(dayColor);
        nextBtn.setForeground(textColor); // ✅ Make arrow text readable
        nextBtn.setFocusPainted(false);
        nextBtn.addActionListener(e -> {
            currentMonth = currentMonth.plusMonths(1);
            refreshCalendar();
        });

        monthLabel = new JLabel("", SwingConstants.CENTER);
        monthLabel.setForeground(textColor); // ✅ Light text for month label
        monthLabel.setFont(new Font("Open Sans", Font.BOLD, 16));

        header.add(prevBtn, BorderLayout.WEST);
        header.add(monthLabel, BorderLayout.CENTER);
        header.add(nextBtn, BorderLayout.EAST);

        add(header, BorderLayout.NORTH);

        // Calendar grid
        calendarGrid = new JPanel(new GridLayout(0, 7, 5, 5));
        calendarGrid.setBackground(bgColor);
        add(calendarGrid, BorderLayout.CENTER);

        // Day details
        dayDetails = new JTextArea();
        dayDetails.setEditable(false);
        dayDetails.setBackground(dayColor);
        dayDetails.setForeground(textColor);
        dayDetails.setFont(new Font("Open Sans", Font.PLAIN, 14));
        dayDetails.setLineWrap(true);
        dayDetails.setWrapStyleWord(true);
        dayDetails.setBorder(new EmptyBorder(10, 10, 10, 10));
        add(new JScrollPane(dayDetails), BorderLayout.SOUTH);
    }

    /*
     * Refreshes the calendar display
     * - Updates the month label
     * - Rebuilds the grid of day buttons
     * - Highlights scheduled days and the current day
     */
    private void refreshCalendar() {
        calendarGrid.removeAll();

        monthLabel.setText(currentMonth.format(DateTimeFormatter.ofPattern("MMMM yyyy")));

        // Weekday headers
        String[] days = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        for (String d : days) {
            JLabel lbl = new JLabel(d, SwingConstants.CENTER);
            lbl.setForeground(textColor);
            lbl.setFont(new Font("Open Sans", Font.BOLD, 12));
            calendarGrid.add(lbl);
        }

        int firstDay = currentMonth.getDayOfWeek().getValue() % 7;
        int daysInMonth = currentMonth.lengthOfMonth();
        LocalDate today = LocalDate.now();

        // Empty cells before the first day of the month
        for (int i = 0; i < firstDay; i++) {
            calendarGrid.add(new JLabel(""));
        }

        // Day buttons
        for (int day = 1; day <= daysInMonth; day++) {
            LocalDate date = currentMonth.withDayOfMonth(day);

            JButton dayBtn = new JButton(String.valueOf(day));
            dayBtn.setFocusPainted(false);
            dayBtn.setBackground(dayColor);
            dayBtn.setForeground(Color.WHITE); // ✅ Day numbers are white
            dayBtn.setFont(new Font("Open Sans", Font.PLAIN, 14));

            // Highlight scheduled days
            String employees = schedule.getEmployees(date.toString());
            if (employees != null && !employees.isEmpty()) {
                dayBtn.setBackground(highlightColor);
            }

            // ⭐ Highlight TODAY
            if (date.equals(today)) {
                dayBtn.setBorder(new LineBorder(todayBorderColor, 2));
            } else {
                dayBtn.setBorder(new EmptyBorder(2, 2, 2, 2));
            }

            dayBtn.addActionListener(e -> displayDayDetails(date));
            calendarGrid.add(dayBtn);
        }

        revalidate();
        repaint();
    }

    /*
     * Displays employee information for a selected date
     *
     * @param date The date selected on the calendar
     */
    private void displayDayDetails(LocalDate date) {
        String employees = schedule.getEmployees(date.toString());
        if (employees == null || employees.isEmpty()) {
            dayDetails.setText(date + ": No employees scheduled.");
        } else {
            dayDetails.setText(date + ": " + employees);
        }
    }
}
