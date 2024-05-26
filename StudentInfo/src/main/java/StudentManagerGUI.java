import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

/**
 * The main GUI class for managing student rosters.
 * Provides functionality to add, edit, delete, view, and sort classes and students.
 */
public class StudentManagerGUI extends JFrame {
    private JComboBox<String> classComboBox, sortComboBox; // Combo boxes for selecting class and sorting option
    private JButton addClassButton, editClassButton, deleteClassButton; // Buttons for adding, editing, and deleting classes
    private JTable studentTable; // Table for displaying students
    private DefaultTableModel tableModel; // Table model for student data
    private Map<String, ArrayList<Student>> classMap; // Map of class names to students
    private int classCounter = 101; // Counter for generating default class names
    private static final String CSV_FILE_PATH = "class_roster.csv"; // Path to the CSV file for saving class roster

    /**
     * Constructor to initialize the GUI components and load the class roster.
     */
    public StudentManagerGUI() {
        setTitle("Student Manager"); // Set window title
        setSize(800, 600); // Set window size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Exit application on close
        setLayout(new BorderLayout()); // Use border layout for main panel

        // Set up table model and table for displaying students
        tableModel = new DefaultTableModel(new Object[]{"Roll Number", "Name", "Address"}, 0); // Column headers
        studentTable = new JTable(tableModel); // Table with default table model
        studentTable.setAutoCreateRowSorter(true); // Enable row sorting
        JScrollPane scrollPane = new JScrollPane(studentTable); // Scroll pane for table
        add(scrollPane, BorderLayout.CENTER); // Add scroll pane to main panel

        // Set column widths
        TableColumnModel columnModel = studentTable.getColumnModel(); // Get column model
        columnModel.getColumn(0).setPreferredWidth(100);  // Roll Number
        columnModel.getColumn(1).setPreferredWidth(200);  // Name
        columnModel.getColumn(2).setPreferredWidth(400);  // Address

        classMap = new HashMap<>(); // Initialize class map

        // Top panel with buttons and combo boxes
        JPanel topPanel = new JPanel(); // Panel for buttons and combo boxes
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); // Use flow layout for top panel

        classComboBox = new JComboBox<>(); // Combo box for selecting classes
        classComboBox.addActionListener(e -> displayClass((String) classComboBox.getSelectedItem())); // Display selected class
        topPanel.add(classComboBox); // Add class combo box to top panel

        addClassButton = new JButton("Add A Class"); // Button to add a new class
        addClassButton.addActionListener(this::addClass); // Add action listener for adding a class
        addClassButton.setToolTipText("Add a new class"); // Set tooltip for button
        topPanel.add(addClassButton); // Add add class button to top panel

        editClassButton = new JButton("Edit Class"); // Button to edit the selected class
        editClassButton.addActionListener(this::editClass); // Add action listener for editing a class
        editClassButton.setToolTipText("Edit the selected class"); // Set tooltip for button
        topPanel.add(editClassButton); // Add edit class button to top panel

        deleteClassButton = new JButton("Delete Class"); // Button to delete the selected class
        deleteClassButton.addActionListener(this::deleteClass); // Add action listener for deleting a class
        deleteClassButton.setToolTipText("Delete the selected class"); // Set tooltip for button
        topPanel.add(deleteClassButton); // Add delete class button to top panel

        String[] sortingOptions = {"Sort by Name", "Sort by Roll Number"}; // Sorting options for students
        sortComboBox = new JComboBox<>(sortingOptions); // Combo box for sorting options
        sortComboBox.addActionListener(this::sortClass); // Add action listener for sorting students
        sortComboBox.setToolTipText("Sort the students by name or roll number"); // Set tooltip for combo box
        topPanel.add(sortComboBox); // Add sort combo box to top panel

        add(topPanel, BorderLayout.NORTH); // Add top panel to main panel

        createMenuBar(); // Create menu bar with load and save options

        // Initialize with 10 students if not already in CSV
        loadClassRoster(CSV_FILE_PATH); // Load class roster from CSV file
        if (!checkInitialStudentsExist()) { // Check if initial students exist
            initializeStudents(); // Initialize with 10 students
            saveClassRoster(CSV_FILE_PATH); // Save class roster to CSV file
        }

        if (!classMap.isEmpty()) { // Display first class if available
            displayClass(classMap.keySet().iterator().next()); // Display first class
        }

        // Ensure save on exit works correctly
        addWindowListener(new WindowAdapter() { // Add window listener for window closing event
            @Override
            public void windowClosing(WindowEvent e) { // Override window closing method
                saveClassRoster(CSV_FILE_PATH); // Save class roster to CSV file
            }
        });
    }

    /**
     * Initializes the application with 10 students.
     */
    private void initializeStudents() { 
        String initialClass = "Programming I"; // Initial class name
        ArrayList<Student> initialStudents = new ArrayList<>(); // Initial students list
        initialStudents.add(new Student(1, "John Doe", "123 Main St"));
        initialStudents.add(new Student(2, "Jane Smith", "456 Oak St"));
        initialStudents.add(new Student(3, "Alice Johnson", "789 Pine St"));
        initialStudents.add(new Student(4, "Bob Brown", "321 Maple St"));
        initialStudents.add(new Student(5, "Charlie Davis", "654 Elm St"));
        initialStudents.add(new Student(6, "Diana Evans", "987 Cedar St"));
        initialStudents.add(new Student(7, "Frank Green", "111 Spruce St"));
        initialStudents.add(new Student(8, "Grace Harris", "222 Birch St"));
        initialStudents.add(new Student(9, "Henry Lee", "333 Aspen St"));
        initialStudents.add(new Student(10, "Ivy Miller", "444 Walnut St"));

        classMap.put(initialClass, initialStudents); // Add initial students to class map
        classComboBox.addItem(initialClass); // Add initial class to combo box
    }

    /**
     * Creates the menu bar with load and save options.
     */
    private void createMenuBar() { // Create menu bar with load and save options
        JMenuBar menuBar = new JMenuBar(); // Menu bar for the application

        JMenu fileMenu = new JMenu("File"); // File menu
        JMenuItem loadMenuItem = new JMenuItem("Load"); // Load menu item
        loadMenuItem.addActionListener(e -> chooseFile(true)); // Add action listener for loading a file
        fileMenu.add(loadMenuItem); // Add load menu item to file menu

        JMenuItem saveMenuItem = new JMenuItem("Save"); // Save menu item
        saveMenuItem.addActionListener(e -> chooseFile(false)); // Add action listener for saving a file
        fileMenu.add(saveMenuItem); // Add save menu item to file menu

        menuBar.add(fileMenu); // Add file menu to menu bar
        setJMenuBar(menuBar); // Set menu bar for the application
    }

    /**
     * Opens a file chooser for loading or saving the class roster.
     * @param isLoad True if loading a file, false if saving a file.
     */
    private void chooseFile(boolean isLoad) { // Open file chooser for loading or saving class roster
        JFileChooser fileChooser = new JFileChooser(); // File chooser for selecting files
        int result = isLoad ? fileChooser.showOpenDialog(this) : fileChooser.showSaveDialog(this); // Show file chooser dialog
        if (result == JFileChooser.APPROVE_OPTION) { // Check if file selection is approved
            File file = fileChooser.getSelectedFile(); // Get selected file
            if (isLoad) { // Load file if specified
                loadClassRoster(file.getAbsolutePath()); // Load class roster from file
            } else {
                saveClassRoster(file.getAbsolutePath()); // Save class roster to file
            }
        }
    }

    /**
     * Adds a new class and allows the user to add students to it.
     * @param e ActionEvent triggered by clicking the "Add A Class" button.
     */
    private void addClass(ActionEvent e) { // Add a new class and students
        String className = JOptionPane.showInputDialog(this, "Enter the name of the class:"); // Get class name from user
        if (className == null || className.trim().isEmpty()) { // Check if class name is empty
            className = "Class " + (++classCounter); // Generate default class name
        }
        ArrayList<Student> newClass = new ArrayList<>(); // Create new class with empty student list
        final String finalClassName = className; // Final class name for lambda expression
        classMap.put(finalClassName, newClass); // Add new class to class map
        classComboBox.addItem(finalClassName); // Add new class to combo box

        // Create a modal dialog to add students
        JDialog dialog = new JDialog(this, "Add Students", true); // Dialog for adding students
        dialog.setLayout(new BorderLayout()); // Use border layout for dialog

        JPanel fieldsPanel = new JPanel(new GridLayout(0, 2)); // Panel for student fields
        JTextField nameField = new JTextField(); // Text field for student name
        JTextField addressField = new JTextField(); // Text field for student address
        fieldsPanel.add(new JLabel("Name:")); // Add name label to fields panel
        fieldsPanel.add(nameField); // Add name field to fields panel
        fieldsPanel.add(new JLabel("Address:")); // Add address label to fields panel
        fieldsPanel.add(addressField); // Add address field to fields panel
        dialog.add(fieldsPanel, BorderLayout.CENTER); // Add fields panel to dialog

        JPanel buttonPanel = new JPanel(); // Panel for buttons
        JButton addButton = new JButton("Add Student"); // Button to add a student
        JButton doneButton = new JButton("Done"); // Button to finish adding students
        JButton cancelButton = new JButton("Cancel"); // Button to cancel adding students
        buttonPanel.add(addButton); // Add add button to button panel
        buttonPanel.add(doneButton); // Add done button to button panel
        buttonPanel.add(cancelButton); // Add cancel button to button panel
        dialog.add(buttonPanel, BorderLayout.SOUTH); // Add button panel to dialog

        addButton.addActionListener(ev -> { // Add action listener for add button
            String name = nameField.getText().trim(); // Get student name from text field
            String address = addressField.getText().trim(); // Get student address from text field
            if (!name.isEmpty() && !address.isEmpty()) {  // Check if name and address are not empty
                newClass.add(new Student(newClass.size() + 1, name, address)); // Add student to class
                tableModel.addRow(new Object[]{newClass.size(), name, address}); // Add student to table
                nameField.setText(""); // Clear name field
                addressField.setText(""); // Clear address field
            } else {
                JOptionPane.showMessageDialog(dialog, "Both name and address are required.", "Error", JOptionPane.ERROR_MESSAGE); // Show error message
            }
        });

        doneButton.addActionListener(ev -> { // Add action listener for done button
            displayClass(finalClassName); // Display the new class
            dialog.dispose(); // Close the dialog
        });

        cancelButton.addActionListener(ev -> { // Add action listener for cancel button
            if (JOptionPane.showConfirmDialog(dialog, "Are you sure you want to cancel?", "Confirm Cancel", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) { // Confirm cancel
                classMap.remove(finalClassName); // Remove new class
                classComboBox.removeItem(finalClassName); // Remove new class from combo box
                dialog.dispose(); // Close the dialog
            }
        });

        dialog.pack(); // Pack dialog components
        dialog.setLocationRelativeTo(this); // Set dialog location relative to main window
        dialog.setVisible(true); // Show dialog
    }

    /**
     * Displays the students in the selected class in the table.
     * @param className The name of the class to display.
     */
    private void displayClass(String className) { // Display students for the selected class
        if (className != null && classMap.containsKey(className)) { // Check if class exists
            ArrayList<Student> students = classMap.get(className); // Get students for the selected class
            tableModel.setRowCount(0); // Clear existing data
            for (Student student : students) { // Add students to table
                tableModel.addRow(new Object[]{student.getRollno(), student.getName(), student.getAddress()}); // Add student to table
            }
        }
    }

    /**
     * Deletes the selected class after user confirmation.
     * @param e ActionEvent triggered by clicking the "Delete Class" button.
     */
    private void deleteClass(ActionEvent e) { // Delete the selected class
        String selectedClass = (String) classComboBox.getSelectedItem(); // Get selected class name
        if (selectedClass != null && JOptionPane.showConfirmDialog(this, "Are you sure you want to delete " + selectedClass + "?", "Confirm Deletion", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) { // Confirm deletion
            classMap.remove(selectedClass); // Remove selected class
            classComboBox.removeItem(selectedClass); // Remove selected class from combo box
            tableModel.setRowCount(0); // Clear table
        }
    }

    /**
     * Edits the details of the selected class, allowing the user to modify student details and add new students.
     * @param e ActionEvent triggered by clicking the "Edit Class" button.
     */
    private void editClass(ActionEvent e) { // Edit the selected class
        String selectedClass = (String) classComboBox.getSelectedItem(); // Get selected class name
        if (selectedClass != null) { // Check if class exists
            ArrayList<Student> students = classMap.get(selectedClass); // Get students for the selected class

            // Create a modal dialog to edit class name and students
            JDialog dialog = new JDialog(this, "Edit Class", true); // Dialog for editing class
            dialog.setLayout(new BorderLayout()); // Use border layout for dialog

            JPanel fieldsPanel = new JPanel(new GridLayout(0, 2)); // Panel for class and student fields
            JTextField classNameField = new JTextField(selectedClass); // Text field for class name
            fieldsPanel.add(new JLabel("Class Name:")); // Add class name label to fields panel
            fieldsPanel.add(classNameField); // Add class name field to fields panel

            ArrayList<JTextField> nameFields = new ArrayList<>(); // List of text fields for student names
            ArrayList<JTextField> addressFields = new ArrayList<>(); // List of text fields for student addresses

            for (Student student : students) { // Add student fields to panel
                JTextField nameField = new JTextField(student.getName()); // Text field for student name
                JTextField addressField = new JTextField(student.getAddress()); // Text field for student address
                fieldsPanel.add(new JLabel("Name:")); // Add name label to fields panel
                fieldsPanel.add(nameField); // Add name field to fields panel
                fieldsPanel.add(new JLabel("Address:")); // Add address label to fields panel
                fieldsPanel.add(addressField); // Add address field to fields panel
                nameFields.add(nameField); // Add name field to list
                addressFields.add(addressField); // Add address field to list
            }

            dialog.add(fieldsPanel, BorderLayout.CENTER); // Add fields panel to dialog

            JPanel buttonPanel = new JPanel(); // Panel for buttons
            JButton saveButton = new JButton("Save"); // Button to save changes
            JButton addStudentButton = new JButton("Add Student"); // Button to add a student
            JButton cancelButton = new JButton("Cancel"); // Button to cancel changes
            buttonPanel.add(saveButton); // Add save button to button panel
            buttonPanel.add(addStudentButton); // Add add student button to button panel
            buttonPanel.add(cancelButton); // Add cancel button to button panel
            dialog.add(buttonPanel, BorderLayout.SOUTH); // Add button panel to dialog

            saveButton.addActionListener(ev -> { // Add action listener for save button
                String newClassName = classNameField.getText().trim(); // Get new class name
                if (newClassName.isEmpty()) { // Check if class name is empty
                    JOptionPane.showMessageDialog(dialog, "Class name cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE); // Show error message
                    return;
                }
                if (!newClassName.equals(selectedClass) && classMap.containsKey(newClassName)) { // Check if class name already exists
                    JOptionPane.showMessageDialog(dialog, "Class name already exists.", "Error", JOptionPane.ERROR_MESSAGE); // Show error message
                    return;
                }

                for (int i = 0; i < students.size(); i++) { // Update student details
                    students.get(i).setName(nameFields.get(i).getText().trim()); // Update student name
                    students.get(i).setAddress(addressFields.get(i).getText().trim()); // Update student address
                }

                if (!newClassName.equals(selectedClass)) { // Update class name
                    classMap.put(newClassName, students); // Update class map
                    classMap.remove(selectedClass); // Remove old class name
                    classComboBox.removeItem(selectedClass); // Remove old class name from combo box
                    classComboBox.addItem(newClassName); // Add new class name to combo box
                    classComboBox.setSelectedItem(newClassName); // Select new class name
                }

                displayClass(newClassName); // Display updated class
                dialog.dispose(); // Close the dialog
            });

            addStudentButton.addActionListener(ev -> { // Add action listener for add student button
                JTextField nameField = new JTextField(); // Text field for student name
                JTextField addressField = new JTextField(); // Text field for student address
                fieldsPanel.add(new JLabel("Name:")); // Add name label to fields panel
                fieldsPanel.add(nameField); // Add name field to fields panel
                fieldsPanel.add(new JLabel("Address:")); // Add address label to fields panel
                fieldsPanel.add(addressField); // Add address field to fields panel
                nameFields.add(nameField); // Add name field to list
                addressFields.add(addressField); // Add address field to list
                students.add(new Student(students.size() + 1, "", "")); // Add new student to list
                dialog.pack(); // Pack dialog components
            });

            cancelButton.addActionListener(ev -> { // Add action listener for cancel button
                if (JOptionPane.showConfirmDialog(dialog, "Are you sure you want to cancel?", "Confirm Cancel", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) { // Confirm cancel
                    dialog.dispose(); // Close the dialog
                }
            });

            dialog.pack(); // Pack dialog components 
            dialog.setLocationRelativeTo(this); // Set dialog location relative to main window
            dialog.setVisible(true); // Show dialog
        }
    }

    /**
     * Sorts the students in the selected class based on the selected sorting option.
     * @param e ActionEvent triggered by changing the sort option in the combo box.
     */
    private void sortClass(ActionEvent e) { // Sort students in the selected class
        String selectedClass = (String) classComboBox.getSelectedItem(); // Get selected class name
        if (selectedClass != null) { // Check if class exists 
            ArrayList<Student> students = classMap.get(selectedClass); // Get students for the selected class
            String sortType = (String) sortComboBox.getSelectedItem(); // Get selected sorting option
            if (sortType.equals("Sort by Name")) { // Sort students by name
                SelectionSort.sort(students, new NameComparator()); // Sort students by name
            } else if (sortType.equals("Sort by Roll Number")) { // Sort students by roll number
                SelectionSort.sort(students, new RollnoComparator()); // Sort students by roll number
            }
            displayClass(selectedClass); // Display sorted class
        }
    }

    /**
     * Loads the class roster from a CSV file.
     * @param filePath The path to the CSV file.
     */
    private void loadClassRoster(String filePath) { // Load class roster from CSV file
        classMap.clear(); // Clear existing class map
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) { // Read file using buffered reader
            String line; // Line read from file
            while ((line = br.readLine()) != null) { // Read each line from file
                String[] parts = line.split(","); // Split line by comma
                if (parts.length < 4) { // Check if line has all required fields
                    System.err.println("Skipping invalid line: " + line); // Log error message
                    continue;
                }
                try {
                    String className = parts[0].trim(); // Get class name
                    int rollno = Integer.parseInt(parts[1].trim()); // Get student roll number
                    String name = parts[2].trim(); // Get student name
                    String address = parts[3].trim(); // Get student address
                    classMap.computeIfAbsent(className, k -> new ArrayList<>()).add(new Student(rollno, name, address)); // Add student to class map
                } catch (NumberFormatException e) { // Catch number format exception
                    System.err.println("Error parsing line: " + line + " - " + e.getMessage()); // Log error message
                }
            }
            classComboBox.setModel(new DefaultComboBoxModel<>(classMap.keySet().toArray(new String[0]))); // Set class combo box model
            System.out.println("Loaded classes: " + classMap.keySet()); // Log loaded classes
        } catch (IOException e) { // Catch IO exception
            JOptionPane.showMessageDialog(this, "Failed to load class roster: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); // Show error message
        }
    }

    /**
     * Saves the class roster to a CSV file.
     * @param filePath The path to the CSV file.
     */
    private void saveClassRoster(String filePath) { // Save class roster to CSV file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) { // Write file using buffered writer
            for (Map.Entry<String, ArrayList<Student>> entry : classMap.entrySet()) { // Iterate over class map entries
                String className = entry.getKey(); // Get class name
                for (Student student : entry.getValue()) { // Iterate over students in class
                    bw.write(className + "," + student.getRollno() + "," + student.getName() + "," + student.getAddress()); // Write class and student details
                    bw.newLine(); // Write new line
                }
            }
            JOptionPane.showMessageDialog(this, "Class roster saved to " + filePath, "Success", JOptionPane.INFORMATION_MESSAGE); // Show success message
        } catch (IOException e) { // Catch IO exception 
            JOptionPane.showMessageDialog(this, "Failed to save class roster: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); // Show error message
        }
    }

    /**
     * Checks if the initial students already exist in the loaded class roster.
     * @return True if the initial students exist, false otherwise.
     */
    private boolean checkInitialStudentsExist() { // Check if initial students exist
        if (classMap.containsKey("Programming I")) { // Check if class exists
            ArrayList<Student> initialStudents = classMap.get("Programming I"); // Get students for class
            return initialStudents.size() >= 10 && initialStudents.get(0).getName().equals("John Doe"); // Check if initial students exist
        }
        return false; // Return false if class does not exist
    }

    /**
     * Main method to run the Student Manager GUI application.
     * @param args Command line arguments.
     */
    public static void main(String[] args) { // Main method to run the Student Manager GUI application
        SwingUtilities.invokeLater(() -> new StudentManagerGUI().setVisible(true)); // Create and show the GUI
    }
}
