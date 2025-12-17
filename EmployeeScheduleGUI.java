import javax.swing.*;
import javax.swing.border.*;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

// GUI program implemented with ChatGPT
public class EmployeeScheduleGUI {

    private Schedule schedule;

    public EmployeeScheduleGUI(Schedule schedule) {
        this.schedule = schedule;
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        // ---------- FRAME ----------
        JFrame frame = new JFrame("Employee Schedule Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(750, 500);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout(15, 15));

        // ---------- DARK MODE COLORS ----------
        Color bgColor = new Color(34, 34, 34);
        Color outputBg = new Color(50, 50, 50);
        Color textColor = new Color(230, 230, 230);
        Color spinnerTextColor = new Color(200, 200, 200);
        Color buttonColor = new Color(70, 130, 180); // Steel Blue
        frame.getContentPane().setBackground(bgColor);

        // ---------- OUTPUT AREA ----------
        JTextArea outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Open Sans", Font.PLAIN, 14));
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);
        outputArea.setBackground(outputBg);
        outputArea.setForeground(textColor);
        outputArea.setBorder(new EmptyBorder(10, 10, 10, 10));

        JScrollPane outputScrollPane = new JScrollPane(outputArea);
        outputScrollPane.setBorder(BorderFactory.createCompoundBorder(
                new EmptyBorder(10, 10, 10, 10),
                BorderFactory.createLineBorder(new Color(80, 80, 80))
        ));

        // ---------- TABBED PANE ----------
        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Schedule Output", outputScrollPane);
        tabs.addTab("Calendar", new JScrollPane(new CalendarPanel(schedule)));

        // Set tab colors for dark mode
        tabs.setForeground(textColor);
        tabs.setBackground(bgColor);

        // Custom UI for tabs
        tabs.setUI(new BasicTabbedPaneUI() {
            @Override
            protected void paintTabBackground(Graphics g, int tabPlacement,
                                              int tabIndex, int x, int y, int w, int h, boolean isSelected) {
                g.setColor(isSelected ? buttonColor : bgColor);
                g.fillRect(x, y, w, h);
            }

            @Override
            protected void paintText(Graphics g, int tabPlacement, Font font, 
                                     FontMetrics metrics, int tabIndex, String title, 
                                     Rectangle textRect, boolean isSelected) {
                g.setColor(textColor);
                g.setFont(font);
                g.drawString(title, textRect.x, textRect.y + metrics.getAscent());
            }

            @Override
            protected void paintTabArea(Graphics g, int tabPlacement, int selectedIndex) {
                g.setColor(bgColor);
                g.fillRect(0, 0, tabPane.getWidth(), tabPane.getHeight());
                super.paintTabArea(g, tabPlacement, selectedIndex);
            }
        });

        frame.add(tabs, BorderLayout.CENTER);

        // ---------- BUTTON PANEL ----------
        JPanel buttonPanel = new JPanel(new GridLayout(2, 3, 15, 15));
        buttonPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        buttonPanel.setBackground(bgColor);

        JButton addButton = createRoundedButton("Add Day", buttonColor);
        JButton removeButton = createRoundedButton("Remove Day", buttonColor);
        JButton updateButton = createRoundedButton("Update Day", buttonColor);
        JButton displayButton = createRoundedButton("Display Schedule", buttonColor);
        JButton searchButton = createRoundedButton("Search Employee", buttonColor);
        JButton exitButton = createRoundedButton("Exit", buttonColor);

        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(displayButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(exitButton);

        frame.add(buttonPanel, BorderLayout.SOUTH);

        // ---------- BUTTON ACTIONS ----------
        setupAddButton(addButton, outputArea, frame, textColor, spinnerTextColor, buttonColor, bgColor);
        setupRemoveButton(removeButton, outputArea, frame, spinnerTextColor, bgColor);
        setupUpdateButton(updateButton, outputArea, frame, textColor, spinnerTextColor, buttonColor, bgColor);
        setupDisplayButton(displayButton, outputArea);
        setupSearchButton(searchButton, outputArea);
        exitButton.addActionListener(e -> frame.dispose());

        frame.setVisible(true);
    }

    // ---------- ADD BUTTON ----------
    private void setupAddButton(JButton button, JTextArea outputArea, JFrame frame, Color textColor, Color spinnerTextColor, Color buttonColor, Color panelBg) {
        button.addActionListener(e -> {
            LocalDate today = LocalDate.now();

            JSpinner yearSpinner = new JSpinner(new SpinnerNumberModel(today.getYear(), 1900, 2100, 1));
            yearSpinner.setEditor(new JSpinner.NumberEditor(yearSpinner, "####"));
            setSpinnerColors(yearSpinner, spinnerTextColor, panelBg);

            JSpinner monthSpinner = new JSpinner(new SpinnerNumberModel(today.getMonthValue(), 1, 12, 1));
            setSpinnerColors(monthSpinner, spinnerTextColor, panelBg);

            JSpinner daySpinner = new JSpinner(new SpinnerNumberModel(today.getDayOfMonth(), 1, 31, 1));
            setSpinnerColors(daySpinner, spinnerTextColor, panelBg);

            JPanel datePanel = new JPanel();
            datePanel.setBackground(panelBg);
            datePanel.add(createLabel("Year:", textColor));
            datePanel.add(yearSpinner);
            datePanel.add(createLabel("Month:", textColor));
            datePanel.add(monthSpinner);
            datePanel.add(createLabel("Day:", textColor));
            datePanel.add(daySpinner);

            int dateResult = JOptionPane.showConfirmDialog(frame, datePanel, "Select Date", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (dateResult != JOptionPane.OK_OPTION) return;

            LocalDate date;
            try {
                date = LocalDate.of((Integer) yearSpinner.getValue(), (Integer) monthSpinner.getValue(), (Integer) daySpinner.getValue());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Invalid date selected.");
                return;
            }

            DefaultListModel<String> listModel = new DefaultListModel<>();
            JList<String> employeeJList = new JList<>(listModel);
            employeeJList.setBackground(panelBg);
            employeeJList.setForeground(textColor);
            JScrollPane empScrollPane = new JScrollPane(employeeJList);

            JButton addEmpBtn = createRoundedButton("Add Employee", buttonColor);
            addEmpBtn.addActionListener(ae -> {
                String name = JOptionPane.showInputDialog(frame, "Enter employee name:");
                if (name != null && !name.trim().isEmpty()) listModel.addElement(name.trim());
            });

            JButton removeEmpBtn = createRoundedButton("Remove Selected", buttonColor);
            removeEmpBtn.addActionListener(ae -> {
                for (String s : employeeJList.getSelectedValuesList()) listModel.removeElement(s);
            });

            JPanel empPanel = new JPanel(new BorderLayout());
            empPanel.setBackground(panelBg);
            empPanel.add(empScrollPane, BorderLayout.CENTER);

            JPanel btnPanel = new JPanel();
            btnPanel.setBackground(panelBg);
            btnPanel.add(addEmpBtn);
            btnPanel.add(removeEmpBtn);
            empPanel.add(btnPanel, BorderLayout.SOUTH);

            int empResult = JOptionPane.showConfirmDialog(frame, empPanel, "Manage Employees", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (empResult != JOptionPane.OK_OPTION) return;

            List<String> employeeList = Collections.list(listModel.elements());
            if (employeeList.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Employee list cannot be empty.");
                return;
            }

            schedule.addDay(date.toString(), String.join(", ", employeeList));
            outputArea.setText("Added day: " + date + " -> " + String.join(", ", employeeList));
        });
    }

    // ---------- REMOVE BUTTON ----------
    private void setupRemoveButton(JButton button, JTextArea outputArea, JFrame frame, Color spinnerTextColor, Color panelBg) {
        button.addActionListener(e -> {
            LocalDate today = LocalDate.now();

            JSpinner yearSpinner = new JSpinner(new SpinnerNumberModel(today.getYear(), 1900, 2100, 1));
            yearSpinner.setEditor(new JSpinner.NumberEditor(yearSpinner, "####"));
            setSpinnerColors(yearSpinner, spinnerTextColor, panelBg);

            JSpinner monthSpinner = new JSpinner(new SpinnerNumberModel(today.getMonthValue(), 1, 12, 1));
            setSpinnerColors(monthSpinner, spinnerTextColor, panelBg);

            JSpinner daySpinner = new JSpinner(new SpinnerNumberModel(today.getDayOfMonth(), 1, 31, 1));
            setSpinnerColors(daySpinner, spinnerTextColor, panelBg);

            JPanel panel = new JPanel();
            panel.setBackground(panelBg);
            panel.add(createLabel("Year:", spinnerTextColor));
            panel.add(yearSpinner);
            panel.add(createLabel("Month:", spinnerTextColor));
            panel.add(monthSpinner);
            panel.add(createLabel("Day:", spinnerTextColor));
            panel.add(daySpinner);

            int result = JOptionPane.showConfirmDialog(frame, panel, "Select date to remove", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result != JOptionPane.OK_OPTION) return;

            try {
                LocalDate date = LocalDate.of(
                        (Integer) yearSpinner.getValue(),
                        (Integer) monthSpinner.getValue(),
                        (Integer) daySpinner.getValue()
                );
                schedule.removeDay(date.toString());
                outputArea.setText("Removed day: " + date);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Invalid date selected.");
            }
        });
    }

    // ---------- UPDATE BUTTON ----------
    private void setupUpdateButton(JButton button, JTextArea outputArea, JFrame frame, Color textColor, Color spinnerTextColor, Color buttonColor, Color panelBg) {
        button.addActionListener(e -> {
            LocalDate today = LocalDate.now();

            JSpinner yearSpinner = new JSpinner(new SpinnerNumberModel(today.getYear(), 1900, 2100, 1));
            yearSpinner.setEditor(new JSpinner.NumberEditor(yearSpinner, "####"));
            setSpinnerColors(yearSpinner, spinnerTextColor, panelBg);

            JSpinner monthSpinner = new JSpinner(new SpinnerNumberModel(today.getMonthValue(), 1, 12, 1));
            setSpinnerColors(monthSpinner, spinnerTextColor, panelBg);

            JSpinner daySpinner = new JSpinner(new SpinnerNumberModel(today.getDayOfMonth(), 1, 31, 1));
            setSpinnerColors(daySpinner, spinnerTextColor, panelBg);

            JPanel datePanel = new JPanel();
            datePanel.setBackground(panelBg);
            datePanel.add(createLabel("Year:", spinnerTextColor));
            datePanel.add(yearSpinner);
            datePanel.add(createLabel("Month:", spinnerTextColor));
            datePanel.add(monthSpinner);
            datePanel.add(createLabel("Day:", spinnerTextColor));
            datePanel.add(daySpinner);

            int dateResult = JOptionPane.showConfirmDialog(frame, datePanel, "Select Date to Update", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (dateResult != JOptionPane.OK_OPTION) return;

            LocalDate date;
            try {
                date = LocalDate.of(
                        (Integer) yearSpinner.getValue(),
                        (Integer) monthSpinner.getValue(),
                        (Integer) daySpinner.getValue()
                );
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Invalid date selected.");
                return;
            }

            DefaultListModel<String> listModel = new DefaultListModel<>();
            String existing = schedule.getEmployees(date.toString());
            if (existing != null && !existing.isEmpty()) {
                for (String emp : existing.split(",\\s*")) listModel.addElement(emp);
            }

            JList<String> employeeJList = new JList<>(listModel);
            employeeJList.setBackground(panelBg);
            employeeJList.setForeground(textColor);
            JScrollPane empScrollPane = new JScrollPane(employeeJList);

            JButton addEmpBtn = createRoundedButton("Add Employee", buttonColor);
            addEmpBtn.addActionListener(ae -> {
                String name = JOptionPane.showInputDialog(frame, "Enter employee name:");
                if (name != null && !name.trim().isEmpty()) listModel.addElement(name.trim());
            });

            JButton removeEmpBtn = createRoundedButton("Remove Selected", buttonColor);
            removeEmpBtn.addActionListener(ae -> {
                for (String s : employeeJList.getSelectedValuesList()) listModel.removeElement(s);
            });

            JPanel empPanel = new JPanel(new BorderLayout());
            empPanel.setBackground(panelBg);
            empPanel.add(empScrollPane, BorderLayout.CENTER);

            JPanel btnPanel = new JPanel();
            btnPanel.setBackground(panelBg);
            btnPanel.add(addEmpBtn);
            btnPanel.add(removeEmpBtn);
            empPanel.add(btnPanel, BorderLayout.SOUTH);

            int empResult = JOptionPane.showConfirmDialog(frame, empPanel, "Update Employees", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (empResult != JOptionPane.OK_OPTION) return;

            List<String> employeeList = new ArrayList<>();
            for (int i = 0; i < listModel.getSize(); i++) employeeList.add(listModel.get(i));

            if (employeeList.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Employee list cannot be empty.");
                return;
            }

            schedule.updateDay(date.toString(), String.join(", ", employeeList));
            outputArea.setText("Updated day: " + date + " -> " + String.join(", ", employeeList));
        });
    }

    // ---------- DISPLAY BUTTON ----------
    private void setupDisplayButton(JButton button, JTextArea outputArea) {
        button.addActionListener(e -> {
            StringBuilder sb = new StringBuilder();
            DayNode current = schedule.head;

            if (current == null) {
                outputArea.setText("Schedule is empty.");
                return;
            }

            while (current != null) {
                sb.append(current.date).append(": ").append(current.employees).append("\n");
                current = current.next;
            }

            outputArea.setText(sb.toString());
        });
    }

    // ---------- SEARCH BUTTON ----------
    private void setupSearchButton(JButton button, JTextArea outputArea) {
        button.addActionListener(e -> {
            String name = JOptionPane.showInputDialog("Enter employee name to search:");
            if (name == null || name.trim().isEmpty()) return;

            StringBuilder sb = new StringBuilder();
            String searchName = name.toLowerCase();

            HashSet<String> printed = new HashSet<>();
            DayNode current = schedule.head;

            while (current != null) {
                if (current.employees.toLowerCase().contains(searchName) && printed.add(current.date)) {
                    sb.append(current.date).append(": ").append(current.employees).append("\n");
                }
                current = current.next;
            }

            outputArea.setText(sb.length() == 0 ? "Employee not found." : sb.toString());
        });
    }

    // ---------- HELPER METHODS ----------
    private JLabel createLabel(String text, Color color) {
        JLabel label = new JLabel(text);
        label.setForeground(color);
        label.setFont(new Font("Open Sans", Font.PLAIN, 14));
        return label;
    }

    private void setSpinnerColors(JSpinner spinner, Color fg, Color bg) {
        spinner.setBackground(bg);
        spinner.getEditor().getComponent(0).setBackground(bg);
        spinner.getEditor().getComponent(0).setForeground(fg);
        spinner.setForeground(fg);
        spinner.setFont(new Font("Open Sans", Font.PLAIN, 14));
    }

    // ---------- ROUNDED BUTTON ----------
    private JButton createRoundedButton(String text, Color bgColor) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(bgColor);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                super.paintComponent(g);
                g2.dispose();
            }
        };
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Open Sans", Font.BOLD, 14));
        return button;
    }
}
