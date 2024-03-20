package Forms;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

public class EmployeeHRSDataBase extends javax.swing.JFrame {
    private JTable table;
    private DefaultTableModel model;
    private JTextField searchField;
    private Object[][] originalData;

    public EmployeeHRSDataBase() {
        super("Manage Employee Information");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1920, 700);
        

        JPanel mainPanel = new JPanel(null);
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        mainPanel.setBackground(new Color(51,51,51));
        setContentPane(mainPanel);

        model = new DefaultTableModel();
        table = new JTable(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        
       // Iterate over each column
for (int columnIndex = 0; columnIndex < table.getColumnCount(); columnIndex++) {
    TableColumn column = table.getColumnModel().getColumn(columnIndex);
    int preferredWidth = 0;
    
    // Iterate over each row in the column to find the maximum preferred width
    for (int rowIndex = 0; rowIndex < table.getRowCount(); rowIndex++) {
        TableCellRenderer cellRenderer = table.getCellRenderer(rowIndex, columnIndex);
        Component cellComponent = table.prepareRenderer(cellRenderer, rowIndex, columnIndex);
        preferredWidth = Math.max(preferredWidth, cellComponent.getPreferredSize().width);
    }
            
            // Set the preferred width for the column
            column.setPreferredWidth(preferredWidth + table.getIntercellSpacing().width);
        }
        
        table.getTableHeader().setFont(new Font("Open Sans", Font.BOLD, 14));
        table.setFont(new Font("Open Sans", Font.PLAIN, 14));
        table.setRowHeight(25);
        

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(200, 50, 1660, 540);
        mainPanel.add(scrollPane);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        searchField = new JTextField(60);
        JButton searchButton = new JButton("SEARCH");
        searchButton.setBackground(new java.awt.Color(255, 153, 0));
        searchButton.setBorderPainted(false);
        searchButton.setFont(new java.awt.Font("Open Sans", 0, 14)); // NOI18N
        searchButton.setForeground(new java.awt.Color(255, 255, 255));
        searchButton.setPreferredSize(new java.awt.Dimension(100, 28));
        topPanel.add(searchField);
        topPanel.add(searchButton);
        topPanel.setBounds(200,10, 840, 50);
        topPanel.setBackground(new Color(51,51,51));
        mainPanel.add(topPanel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(0, 1, 0, 12));
        buttonPanel.setBounds(30, 50, 160, 200);
        buttonPanel.setBackground(new Color(51,51,51));
        
        JButton addEmployeeButton = new JButton("ADD EMPLOYEE");
        addEmployeeButton.setBackground(new java.awt.Color(255, 153, 0));
        addEmployeeButton.setBorderPainted(false);
        addEmployeeButton.setFont(new java.awt.Font("Open Sans", 0, 14)); // NOI18N
        addEmployeeButton.setForeground(new java.awt.Color(255, 255, 255));
        addEmployeeButton.setPreferredSize(new java.awt.Dimension(160, 28));
        addEmployeeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addEmployee();
            }
        });
        buttonPanel.add(addEmployeeButton);

        JButton deleteEmployeeButton = new JButton("DELETE EMPLOYEE");
        deleteEmployeeButton.setBackground(new java.awt.Color(255, 153, 0));
        deleteEmployeeButton.setBorderPainted(false);
        deleteEmployeeButton.setFont(new java.awt.Font("Open Sans", 0, 14)); // NOI18N
        deleteEmployeeButton.setForeground(new java.awt.Color(255, 255, 255));
        deleteEmployeeButton.setPreferredSize(new java.awt.Dimension(160, 28));
        deleteEmployeeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteSelectedEmployee();
            }
        });
        buttonPanel.add(deleteEmployeeButton);

        JButton updateEmployeeButton = new JButton("UPDATE EMPLOYEE");
        updateEmployeeButton.setBackground(new java.awt.Color(255, 153, 0));
        updateEmployeeButton.setBorderPainted(false);
        updateEmployeeButton.setFont(new java.awt.Font("Open Sans", 0, 14)); // NOI18N
        updateEmployeeButton.setForeground(new java.awt.Color(255, 255, 255));
        updateEmployeeButton.setPreferredSize(new java.awt.Dimension(160, 28));
        updateEmployeeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateSelectedEmployee();
            }
        });
        buttonPanel.add(updateEmployeeButton);

        JButton viewEmployeeButton = new JButton("VIEW EMPLOYEE");
        viewEmployeeButton.setBackground(new java.awt.Color(255, 153, 0));
        viewEmployeeButton.setBorderPainted(false);
        viewEmployeeButton.setFont(new java.awt.Font("Open Sans", 0, 14)); // NOI18N
        viewEmployeeButton.setForeground(new java.awt.Color(255, 255, 255));
        viewEmployeeButton.setPreferredSize(new java.awt.Dimension(160, 28));
        viewEmployeeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewSelectedEmployee();
            }
        });
        buttonPanel.add(viewEmployeeButton);

        JButton importCSVButton = new JButton("IMPORT CSV");
        importCSVButton.setBackground(new java.awt.Color(255, 153, 0));
        importCSVButton.setBorderPainted(false);
        importCSVButton.setFont(new java.awt.Font("Open Sans", 0, 14)); // NOI18N
        importCSVButton.setForeground(new java.awt.Color(255, 255, 255));
        importCSVButton.setPreferredSize(new java.awt.Dimension(160, 28));
        importCSVButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                importCSV();
            }
        });
        buttonPanel.add(importCSVButton);

        mainPanel.add(buttonPanel);

        displayCSVData("/Files/EmployeeData.csv"); // Display once run

        setVisible(true);
        
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchEmployee();
            }
        });
    }
    
    public void updateDataTable() {
        model.setRowCount(0);
        displayCSVData("/Files/EmployeeData.csv");
    }

    private void displayCSVData(String csvFile) {
        model.setColumnIdentifiers(new String[]{"Employee #", "Last Name", "First Name", "Birthday", "Address",
            "Phone Number", "SSS #", "Philhealth #", "TIN #", "Pag-ibig #", "Status", "Position",
            "Immediate Supervisor"});

    try (InputStream inputStream = getClass().getResourceAsStream(csvFile);
         BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {

        String line;
        List<Object[]> rows = new ArrayList<>();
        // Skip the first line (header)
        
        while ((line = br.readLine()) != null) {
            // Skip empty lines
            if (line.trim().isEmpty()) {
                continue;
            }
            String[] data = line.split(";");
            Object[] rowData = Arrays.copyOf(data, Math.min(data.length, 13));
            rows.add(rowData);
        }
        
        for (Object[] rowData : rows) {
            model.addRow(rowData);
        }
        
        originalData = new Object[rows.size()][];
        for (int i = 0; i < rows.size(); i++) {
            originalData[i] = rows.get(i);
        }
        
    } catch (IOException e) {
        e.printStackTrace();
    }

    // Center align cells
    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
    centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
    for (int i = 0; i < table.getColumnCount(); i++) {
        table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
    }

    // Enable row selection
    table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    // Add mouse listener to handle row selection for deletion and updating
    table.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1 && e.getClickCount() == 2) {
                updateSelectedEmployee();
            }
        }
    });
    }




    private void addEmployee() {
        JTextField[] fields = new JTextField[13];
        JPanel panel = new JPanel(new GridLayout(13, 2, 5, 5));
        panel.setPreferredSize(new Dimension(400, 400));
        String[] labels = {"Employee #", "Last Name", "First Name", "Birthday", "Address",
                "Phone Number", "SSS #", "Philhealth #", "TIN #", "Pag-ibig #", "Status", "Position",
                "Immediate Supervisor"};
        for (int i = 0; i < labels.length; i++) {
            panel.add(new JLabel(labels[i]));
            fields[i] = new JTextField();
            panel.add(fields[i]);
        }
        int result = JOptionPane.showConfirmDialog(null, panel, "Add Employee", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            StringBuilder newEmployee = new StringBuilder();
            for (int i = 0; i < fields.length; i++) {
                newEmployee.append(fields[i].getText());
                if (i < fields.length - 1) {
                    newEmployee.append(";");
                }
            }
            try (FileWriter writer = new FileWriter("src/Files/EmployeeData.csv", true)) {
                writer.write(newEmployee.toString() + "\n");
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            model.addRow(newEmployee.toString().split(";"));
        }
    }

    private void deleteSelectedEmployee() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int response = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this employee?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            
            model.removeRow(selectedRow);
            updateCSVFile();
        }
    }
    }

    private void updateSelectedEmployee() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            JTextField[] fields = new JTextField[13];
            JPanel panel = new JPanel(new GridLayout(13, 2, 5, 5));
            panel.setPreferredSize(new Dimension(400, 600));
            String[] labels = {"Employee #", "Last Name", "First Name", "Birthday", "Address",
                    "Phone Number", "SSS #", "Philhealth #", "TIN #", "Pag-ibig #", "Status", "Position",
                    "Immediate Supervisor"};
            for (int i = 0; i < labels.length; i++) {
                panel.add(new JLabel(labels[i]));
                fields[i] = new JTextField(model.getValueAt(selectedRow, i).toString());
                panel.add(fields[i]);
            }
            int result = JOptionPane.showConfirmDialog(null, panel, "Update Employee", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                StringBuilder updatedEmployee = new StringBuilder();
                for (int i = 0; i < fields.length; i++) {
                    updatedEmployee.append(fields[i].getText());
                    if (i < fields.length - 1) {
                        updatedEmployee.append(";");
                    }
                }
                for (int i = 0; i < model.getColumnCount(); i++) {
                    model.setValueAt(fields[i].getText(), selectedRow, i);
                }
                updateCSVFile();
            }
        }
    }
    
    
    
    private void updateCSVFile() {
    try (FileWriter writer = new FileWriter("src/Files/EmployeeData.csv")) {
        for (int i = 0; i < model.getRowCount(); i++) {
            for (int j = 0; j < 13; j++) { // Iterate only over the first 19 columns
                Object newValue = model.getValueAt(i, j);
                writer.write(newValue.toString());
                if (j < 12) { // Check if it's not the last column
                    writer.write(";");
                }
            }
            // Append remaining columns from the original data
            for (int j = 13; j < originalData[i].length; j++) {
                Object originalValue = originalData[i][j];
                writer.write(";" + originalValue.toString());
            }
            writer.write("\n");
        }
        writer.flush();
    } catch (IOException e) {
        e.printStackTrace();
    }
}

    private void searchEmployee() {
        String searchTerm = searchField.getText().trim().toLowerCase();
        if (searchTerm.isEmpty()) {
            // If search term is empty, reset the table to display all data
            resetTable();
            return;
        }

        DefaultTableModel filteredModel = new DefaultTableModel();

        // Add column names to the filtered model
        for (int i = 0; i < model.getColumnCount(); i++) {
            filteredModel.addColumn(model.getColumnName(i));
        }

        for (int i = 0; i < model.getRowCount(); i++) {
            boolean found = false;
            for (int j = 0; j < model.getColumnCount(); j++) {
                String cellValue = model.getValueAt(i, j).toString().toLowerCase();
                if (cellValue.contains(searchTerm)) {
                    found = true;
                    break; // Break the inner loop to move to the next row
                }
            }
            if (found) {
                filteredModel.addRow(model.getDataVector().elementAt(i));
            }
        }

        table.setModel(filteredModel);
    }



    private void viewSelectedEmployee() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            StringBuilder details = new StringBuilder();
            for (int i = 0; i < model.getColumnCount(); i++) {
                details.append(model.getColumnName(i)).append(": ").append(model.getValueAt(selectedRow, i)).append("\n");
            }
           showCustomMessage(details.toString(), "Employee Details");
        }
    }
    private void resetTable() {
        table.setModel(model);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(EmployeeHRSDataBase::new);
    }

    private void importCSV() {
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Select CSV File to Import");
    fileChooser.setFileFilter(new FileNameExtensionFilter("CSV files", "csv"));

    int userSelection = fileChooser.showOpenDialog(this);
    if (userSelection == JFileChooser.APPROVE_OPTION) {
        File fileToImport = fileChooser.getSelectedFile();
        String filePath = fileToImport.getAbsolutePath();

        try {
            // Read the new CSV data
            List<String> newRows = readCSV(filePath);

            // Read existing CSV data
            List<String> existingRows = readCSV("src/Files/EmployeeData.csv");
            
            

            // Prepare a set of unique keys (combining 1st and 2nd column)
            Set<String> existingKeys = new HashSet<>();
            for (String row : existingRows) {
                String[] columns = row.split(";");
                if (columns.length >= 2) {
                    String key = (columns[0].trim() + ";" + columns[1].trim()).toLowerCase(); // Combine 1st and 2nd column and convert to lowercase
                    existingKeys.add(key);
                }
            }

            // Filter out duplicates based on the 1st and 2nd column
            List<String> uniqueRows = new ArrayList<>();
            for (String newRow : newRows) {
                String[] columns = newRow.split(";");
                if (columns.length >= 2) {
                    String key = (columns[0].trim() + ";" + columns[1].trim()).toLowerCase(); // Combine 1st and 2nd column and convert to lowercase
                    if (!existingKeys.contains(key)) {
                        uniqueRows.add(newRow);
                        existingKeys.add(key); // Add the key to prevent duplicates in the future
                    }
                }
            }

            // Determine the number of duplicates
            int numOfDuplicates = newRows.size() - uniqueRows.size();

            // Ask for confirmation
            int confirmOption = JOptionPane.showConfirmDialog(this, "Add " + uniqueRows.size() + " rows to the table?" +
                            (numOfDuplicates > 0 ? "\n" + numOfDuplicates + " duplicates found and will not be copied." : ""),
                    "Confirmation", JOptionPane.YES_NO_OPTION);
            if (confirmOption == JOptionPane.YES_OPTION) {
                // Append unique rows to existing data
                if (!uniqueRows.isEmpty()) {
                    // Append unique rows to the CSV file
                    try (FileWriter writer = new FileWriter("src/Files/EmployeeData.csv", true);
                         BufferedWriter bufferedWriter = new BufferedWriter(writer);
                         PrintWriter out = new PrintWriter(bufferedWriter)) {

                        // Move to next line if there are existing rows
                        if (!existingRows.isEmpty() || !uniqueRows.isEmpty()) {
                            out.println();
                        }

                        // Write each row
                        for (String row : uniqueRows) {
                            out.println(row);
                        }

                        // Display success message
                        JOptionPane.showMessageDialog(this, "Added " + uniqueRows.size() + " row/s successfully.");

                        // Refresh the table to reflect the changes
                        updateDataTable();
                        model.fireTableDataChanged();
                    } catch (IOException e) {
                        JOptionPane.showMessageDialog(this, "Error appending data: " + e.getMessage());
                        e.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "No new data to import.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Import canceled by user.");
            }
            
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error importing data: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
    
    

private List<String> readCSV(String filePath) throws IOException {
    List<String> rows = new ArrayList<>();
    BufferedReader csvReader = new BufferedReader(new FileReader(filePath));
    String row;
    while ((row = csvReader.readLine()) != null) {
        rows.add(row);
    }
    csvReader.close();
    return rows;
}
    
    private void showCustomMessage(String message, String title) {
    JDialog dialog = new JDialog(this, title, true);
    JTextArea textArea = new JTextArea(message);
    textArea.setEditable(false);
    JScrollPane scrollPane = new JScrollPane(textArea);
    dialog.add(scrollPane);
    dialog.setSize(600, 400); // Set the size as per your requirement
    dialog.setLocationRelativeTo(this);
    dialog.setVisible(true);
}

}