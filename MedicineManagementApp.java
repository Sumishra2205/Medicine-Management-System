import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class MedicineManagementApp {
    private final ArrayList<Patient> patients = new ArrayList<>();
    private final ArrayList<Medicine> medicines = new ArrayList<>();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MedicineManagementApp::new);
    }

    public MedicineManagementApp() {
        createGUI();
    }

    private void createGUI() {
        JFrame frame = new JFrame("Medicine Management");
        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Patients", createPatientPanel());
        tabbedPane.addTab("Medicines", createMedicinePanel());
        tabbedPane.addTab("Final Report", createReportPanel());

        frame.add(tabbedPane);
        frame.setSize(500, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private JPanel createPatientPanel() {
        JPanel patientPanel = new JPanel();
        patientPanel.setLayout(new BorderLayout());
        patientPanel.setBackground(new Color(220, 240, 255));

        JPanel inputPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        JTextField nameField = new JTextField();
        JTextField ageField = new JTextField();
        JTextField diseaseField = new JTextField();
        JTextField doctorField = new JTextField();
        String[] genders = {"Male", "Female", "Other"};
        JComboBox<String> genderComboBox = new JComboBox<>(genders);
        JButton addPatientButton = new JButton("Add Patient");
        JTextArea patientArea = new JTextArea(10, 30);
        patientArea.setEditable(false);
        patientArea.setBackground(Color.WHITE);
        patientArea.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        addPatientButton.addActionListener(e -> addPatient(nameField, ageField, diseaseField, genderComboBox, doctorField, patientArea));

        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Age:"));
        inputPanel.add(ageField);
        inputPanel.add(new JLabel("Disease:"));
        inputPanel.add(diseaseField);
        inputPanel.add(new JLabel("Gender:"));
        inputPanel.add(genderComboBox);
        inputPanel.add(new JLabel("Doctor Name:"));
        inputPanel.add(doctorField);
        inputPanel.add(addPatientButton);

        patientPanel.add(inputPanel, BorderLayout.NORTH);
        patientPanel.add(new JScrollPane(patientArea), BorderLayout.CENTER);

        return patientPanel;
    }

    private void addPatient(JTextField nameField, JTextField ageField, JTextField diseaseField, JComboBox<String> genderComboBox, JTextField doctorField, JTextArea patientArea) {
        String name = nameField.getText();
        String ageText = ageField.getText();
        String disease = diseaseField.getText();
        String gender = (String) genderComboBox.getSelectedItem();
        String doctorName = doctorField.getText();

        try {
            int age = Integer.parseInt(ageText);
            if (age <= 0) throw new NumberFormatException();
            patients.add(new Patient(name, age, disease, gender, doctorName));
            updatePatientArea(patientArea);
            clearFields(nameField, ageField, diseaseField, doctorField);
            JOptionPane.showMessageDialog(null, "Patient added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid age! Please enter a positive number.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updatePatientArea(JTextArea patientArea) {
        patientArea.setText("");
        for (Patient patient : patients) {
            patientArea.append(patient.toString() + "\n");
        }
    }

    private JPanel createMedicinePanel() {
        JPanel medicinePanel = new JPanel();
        medicinePanel.setLayout(new BorderLayout());
        medicinePanel.setBackground(new Color(255, 240, 220));

        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        JTextField medicineField = new JTextField();
        JTextField stockField = new JTextField();
        JTextField diseaseField = new JTextField();
        JButton addMedicineButton = new JButton("Add Medicine");
        JTextArea medicineArea = new JTextArea(10, 30);
        medicineArea.setEditable(false);
        medicineArea.setBackground(Color.WHITE);
        medicineArea.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        addMedicineButton.addActionListener(e -> addMedicine(medicineField, stockField, diseaseField, medicineArea));

        inputPanel.add(new JLabel("Disease:"));
        inputPanel.add(diseaseField);
        inputPanel.add(new JLabel("Medicine Name:"));
        inputPanel.add(medicineField);
        inputPanel.add(new JLabel("Quantity:"));
        inputPanel.add(stockField);
        inputPanel.add(addMedicineButton);

        medicinePanel.add(inputPanel, BorderLayout.NORTH);
        medicinePanel.add(new JScrollPane(medicineArea), BorderLayout.CENTER);

        return medicinePanel;
    }

    private void addMedicine(JTextField medicineField, JTextField stockField, JTextField diseaseField, JTextArea medicineArea) {
        String name = medicineField.getText();
        String stockText = stockField.getText();
        String disease = diseaseField.getText();

        try {
            int stock = Integer.parseInt(stockText);
            if (stock < 0) throw new NumberFormatException();
            medicines.add(new Medicine(name, stock, disease));
            updateMedicineArea(medicineArea);
            clearFields(medicineField, stockField, diseaseField);
            JOptionPane.showMessageDialog(null, "Medicine added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid stock! Please enter a non-negative number.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateMedicineArea(JTextArea medicineArea) {
        medicineArea.setText("");
        for (Medicine medicine : medicines) {
            medicineArea.append(medicine.toString() + "\n");
        }
    }

    private JPanel createReportPanel() {
        JPanel reportPanel = new JPanel();
        reportPanel.setLayout(new BorderLayout());
        reportPanel.setBackground(new Color(240, 255, 220));

        JTextArea reportArea = new JTextArea();
        reportArea.setEditable(false);
        reportArea.setBackground(new Color(255, 255, 255));
        reportArea.setFont(new Font("Arial", Font.PLAIN, 14));
        reportArea.setForeground(new Color(0, 0, 0));
        reportArea.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        reportArea.setText(generateReport());

        JButton refreshButton = new JButton("Refresh Report");
        refreshButton.addActionListener(e -> reportArea.setText(generateReport()));

        reportPanel.add(new JScrollPane(reportArea), BorderLayout.CENTER);
        reportPanel.add(refreshButton, BorderLayout.SOUTH);

        return reportPanel;
    }

    private String generateReport() {
        StringBuilder report = new StringBuilder();
        int patientCount = 1;
        for (Patient patient : patients) {
            report.append("===============================================\n");
            report.append("Patient " + patientCount++ + " Details\n");
            report.append("===============================================\n");
            report.append("Name: " + patient.name + "\n");
            report.append("Age: " + patient.age + "\n");
            report.append("Disease: " + patient.disease + "\n");
            report.append("Gender: " + patient.gender + "\n");
            report.append("Doctor: " + patient.doctorName + "\n");
            report.append("-----------------------------------------------\n");
            report.append("Medicines prescribed:\n");
            boolean medicinesFound = false;
            for (Medicine medicine : medicines) {
                if (medicine.disease.equalsIgnoreCase(patient.disease)) {
                    report.append("- " + medicine.name + " | Quantity: " + medicine.stock + "\n");
                    medicinesFound = true;
                }
            }
            if (!medicinesFound) {
                report.append("No medicines available for this disease.\n");
            }
            report.append("\n");
        }
        report.append("===============================================\n");
        return report.toString();
    }

    private void clearFields(JTextField... fields) {
        for (JTextField field : fields) {
            field.setText("");
        }
    }

    class Patient {
        String name;
        int age;
        String disease;
        String gender;
        String doctorName;

        Patient(String name, int age, String disease, String gender, String doctorName) {
            this.name = name;
            this.age = age;
            this.disease = disease;
            this.gender = gender;
            this.doctorName = doctorName;
        }

        @Override
        public String toString() {
            return name + " | Age: " + age + " | Disease: " + disease + " | Gender: " + gender + " | Doctor: " + doctorName;
        }
    }

    class Medicine {
        String name;
        int stock;
        String disease;

        Medicine(String name, int stock, String disease) {
            this.name = name;
            this.stock = stock;
            this.disease = disease;
        }

        @Override
        public String toString() {
            return name + " | Quantity: " + stock;
        }
    }
}