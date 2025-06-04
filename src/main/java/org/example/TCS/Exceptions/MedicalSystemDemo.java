package org.example.TCS.Exceptions;

import java.util.*;
import java.io.*;

// Custom Exceptions
class InvalidPatientIdException extends Exception {
    public InvalidPatientIdException(String message) {
        super(message);
    }
}

class PatientNotFoundException extends Exception {
    public PatientNotFoundException(String message) {
        super(message);
    }
}

class InvalidMedicalDataException extends Exception {
    public InvalidMedicalDataException(String message) {
        super(message);
    }
}

// Patient class
class Patient {
    private String patientId;
    private String name;
    private int age;
    private double temperature;
    private String bloodType;

    public Patient(String patientId, String name, int age,
                   double temperature, String bloodType)
            throws InvalidMedicalDataException {
        validatePatientData(patientId, name, age, temperature, bloodType);
        this.patientId = patientId;
        this.name = name;
        this.age = age;
        this.temperature = temperature;
        this.bloodType = bloodType;
    }

    private void validatePatientData(String id, String name, int age,
                                     double temp, String bloodType)
            throws InvalidMedicalDataException {
        if (id == null || id.trim().isEmpty()) {
            throw new InvalidMedicalDataException("Patient ID cannot be empty");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidMedicalDataException("Patient name cannot be empty");
        }
        if (age < 0 || age > 150) {
            throw new InvalidMedicalDataException("Invalid age: " + age);
        }
        if (temp < 90.0 || temp > 110.0) {
            throw new InvalidMedicalDataException("Invalid temperature: " + temp);
        }
        if (!isValidBloodType(bloodType)) {
            throw new InvalidMedicalDataException("Invalid blood type: " + bloodType);
        }
    }

    private boolean isValidBloodType(String bloodType) {
        String[] validTypes = {"A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"};
        return Arrays.asList(validTypes).contains(bloodType);
    }

    // Getters
    public String getPatientId() { return patientId; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public double getTemperature() { return temperature; }
    public String getBloodType() { return bloodType; }

    @Override
    public String toString() {
        return String.format("Patient[ID=%s, Name=%s, Age=%d, Temp=%.1f°F, Blood=%s]",
                patientId, name, age, temperature, bloodType);
    }
}

// Hospital Management System
class HospitalManagementSystem {
    private Map<String, Patient> patients;
    private String dataFilePath;

    public HospitalManagementSystem(String dataFilePath) {
        this.patients = new HashMap<>();
        this.dataFilePath = dataFilePath;
    }

    // Add patient with exception handling
    public void addPatient(String patientId, String name, int age,
                           double temperature, String bloodType) {
        try {
            if (patients.containsKey(patientId)) {
                throw new IllegalArgumentException("Patient ID already exists: " + patientId);
            }

            Patient patient = new Patient(patientId, name, age, temperature, bloodType);
            patients.put(patientId, patient);
            System.out.println("Patient added successfully: " + patient);

        } catch (InvalidMedicalDataException e) {
            System.err.println("Invalid medical data: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error occurred: " + e.getMessage());
        }
    }

    // Find patient with custom exception
    public Patient findPatient(String patientId) throws PatientNotFoundException,
            InvalidPatientIdException {
        if (patientId == null || patientId.trim().isEmpty()) {
            throw new InvalidPatientIdException("Patient ID cannot be null or empty");
        }

        Patient patient = patients.get(patientId);
        if (patient == null) {
            throw new PatientNotFoundException("Patient not found with ID: " + patientId);
        }

        return patient;
    }

    // Save patient data to file
    public void savePatientData() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(dataFilePath))) {
            writer.println("Patient Data Export");
            writer.println("==================");

            for (Patient patient : patients.values()) {
                writer.println(patient.toString());
            }

            System.out.println("Patient data saved to: " + dataFilePath);

        } catch (IOException e) {
            System.err.println("Error saving patient data: " + e.getMessage());
        }
    }

    // Load patient data from file
    public void loadPatientData() {
        try (BufferedReader reader = new BufferedReader(new FileReader(dataFilePath))) {
            String line;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;
                try {
                    if (line.startsWith("Patient[")) {
                        parsePatientLine(line);
                    }
                } catch (Exception e) {
                    System.err.println("Error parsing line " + lineNumber + ": " + e.getMessage());
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("Patient data file not found. Starting with empty database.");
        } catch (IOException e) {
            System.err.println("Error reading patient data: " + e.getMessage());
        }
    }

    private void parsePatientLine(String line) throws InvalidMedicalDataException {
        // Simple parsing logic (in real application, use proper parsing)
        // This is just for demonstration
        System.out.println("Parsing patient data: " + line);
    }

    // Calculate average temperature with exception handling
    public double calculateAverageTemperature() throws ArithmeticException {
        if (patients.isEmpty()) {
            throw new ArithmeticException("Cannot calculate average: No patients in database");
        }

        double sum = 0;
        for (Patient patient : patients.values()) {
            sum += patient.getTemperature();
        }

        return sum / patients.size();
    }

    // Get patients by blood type
    public List<Patient> getPatientsByBloodType(String bloodType) {
        List<Patient> result = new ArrayList<>();

        try {
            for (Patient patient : patients.values()) {
                if (patient.getBloodType().equalsIgnoreCase(bloodType)) {
                    result.add(patient);
                }
            }
        } catch (NullPointerException e) {
            System.err.println("Error processing patients: " + e.getMessage());
        }

        return result;
    }

    public void displayAllPatients() {
        if (patients.isEmpty()) {
            System.out.println("No patients in the database.");
            return;
        }

        System.out.println("All Patients:");
        System.out.println("=============");
        for (Patient patient : patients.values()) {
            System.out.println(patient);
        }
    }
}

// Main class demonstrating exception handling
public class MedicalSystemDemo {
    public static void main(String[] args) {
        HospitalManagementSystem hospital = new HospitalManagementSystem("patient_data.txt");
        Scanner scanner = new Scanner(System.in);

        // Load existing data
        hospital.loadPatientData();

        while (true) {
            try {
                System.out.println("\n=== Hospital Management System ===");
                System.out.println("1. Add Patient");
                System.out.println("2. Find Patient");
                System.out.println("3. Display All Patients");
                System.out.println("4. Calculate Average Temperature");
                System.out.println("5. Find Patients by Blood Type");
                System.out.println("6. Save Data");
                System.out.println("7. Exit");
                System.out.print("Choose option: ");

                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        addPatientInteractive(hospital, scanner);
                        break;
                    case 2:
                        findPatientInteractive(hospital, scanner);
                        break;
                    case 3:
                        hospital.displayAllPatients();
                        break;
                    case 4:
                        calculateAverageTemp(hospital);
                        break;
                    case 5:
                        findPatientsByBloodType(hospital, scanner);
                        break;
                    case 6:
                        hospital.savePatientData();
                        break;
                    case 7:
                        System.out.println("Goodbye!");
                        return;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }

            } catch (NumberFormatException e) {
                System.err.println("Please enter a valid number.");
            } catch (Exception e) {
                System.err.println("An unexpected error occurred: " + e.getMessage());
            }
        }
    }

    private static void addPatientInteractive(HospitalManagementSystem hospital, Scanner scanner) {
        try {
            System.out.print("Enter Patient ID: ");
            String id = scanner.nextLine();

            System.out.print("Enter Patient Name: ");
            String name = scanner.nextLine();

            System.out.print("Enter Age: ");
            int age = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter Temperature (°F): ");
            double temperature = Double.parseDouble(scanner.nextLine());

            System.out.print("Enter Blood Type (A+, A-, B+, B-, AB+, AB-, O+, O-): ");
            String bloodType = scanner.nextLine();

            hospital.addPatient(id, name, age, temperature, bloodType);

        } catch (NumberFormatException e) {
            System.err.println("Invalid number format. Please enter valid numbers for age and temperature.");
        }
    }

    private static void findPatientInteractive(HospitalManagementSystem hospital, Scanner scanner) {
        try {
            System.out.print("Enter Patient ID to search: ");
            String searchId = scanner.nextLine();

            Patient patient = hospital.findPatient(searchId);
            System.out.println("Patient found: " + patient);

        } catch (PatientNotFoundException e) {
            System.err.println("Search failed: " + e.getMessage());
        } catch (InvalidPatientIdException e) {
            System.err.println("Invalid input: " + e.getMessage());
        }
    }

    private static void calculateAverageTemp(HospitalManagementSystem hospital) {
        try {
            double avgTemp = hospital.calculateAverageTemperature();
            System.out.printf("Average patient temperature: %.2f°F%n", avgTemp);
        } catch (ArithmeticException e) {
            System.err.println("Calculation error: " + e.getMessage());
        }
    }

    private static void findPatientsByBloodType(HospitalManagementSystem hospital, Scanner scanner) {
        System.out.print("Enter Blood Type: ");
        String bloodType = scanner.nextLine();

        List<Patient> patients = hospital.getPatientsByBloodType(bloodType);

        if (patients.isEmpty()) {
            System.out.println("No patients found with blood type: " + bloodType);
        } else {
            System.out.println("Patients with blood type " + bloodType + ":");
            for (Patient patient : patients) {
                System.out.println(patient);
            }
        }
    }
}
