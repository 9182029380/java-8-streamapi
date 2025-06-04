package org.example.TCS;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.function.Function;
import java.math.BigDecimal;
import org.apache.poi.ss.usermodel.*;


// Employee class with all required attributes
class Employee {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String gender;
    private String jobTitle;
    private String university;
    private String empId;
    private double salary;

    // Constructor
    public Employee(int id, String firstName, String lastName, String email,
                    String gender, String jobTitle, String university, String empId, double salary) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
        this.jobTitle = jobTitle;
        this.university = university;
        this.empId = empId;
        this.salary = salary;
    }

    // Getters
    public int getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getGender() { return gender; }
    public String getJobTitle() { return jobTitle; }
    public String getUniversity() { return university; }
    public String getEmpId() { return empId; }
    public double getSalary() { return salary; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setEmail(String email) { this.email = email; }
    public void setGender(String gender) { this.gender = gender; }
    public void setJobTitle(String jobTitle) { this.jobTitle = jobTitle; }
    public void setUniversity(String university) { this.university = university; }
    public void setEmpId(String empId) { this.empId = empId; }
    public void setSalary(double salary) { this.salary = salary; }

    // Full name getter
    public String getFullName() {
        return firstName + " " + lastName;
    }

    @Override
    public String toString() {
        return String.format("Employee{id=%d, name='%s %s', email='%s', gender='%s', jobTitle='%s', university='%s', empId='%s', salary=%.2f}",
                id, firstName, lastName, email, gender, jobTitle, university, empId, salary);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Employee employee = (Employee) obj;
        return Objects.equals(empId, employee.empId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(empId);
    }
}

public class EmployeeStreamOperations {

    // Method to read employees from Excel file
    public static List<Employee> readEmployeesFromExcel(String filePath) {
        List<Employee> employees = new ArrayList<>();



        return employees;
    }

    // Method to create sample data for demonstration
    public static List<Employee> createSampleEmployees() {
        return Arrays.asList(
                new Employee(1, "John", "Doe", "john.doe@company.com", "Male", "Software Engineer", "MIT", "EMP001", 75000),
                new Employee(2, "Jane", "Smith", "jane.smith@company.com", "Female", "Product Manager", "Stanford", "EMP002", 85000),
                new Employee(3, "Mike", "Johnson", "mike.johnson@company.com", "Male", "Data Analyst", "Harvard", "EMP003", 65000),
                new Employee(4, "Sarah", "Williams", "sarah.williams@company.com", "Female", "Software Engineer", "MIT", "EMP004", 78000),
                new Employee(5, "David", "Brown", "david.brown@company.com", "Male", "Manager", "Stanford", "EMP005", 95000),
                new Employee(6, "Lisa", "Davis", "lisa.davis@company.com", "Female", "Designer", "RISD", "EMP006", 60000),
                new Employee(7, "Robert", "Wilson", "robert.wilson@company.com", "Male", "Software Engineer", "CalTech", "EMP007", 72000),
                new Employee(8, "Emma", "Taylor", "emma.taylor@company.com", "Female", "Data Scientist", "MIT", "EMP008", 82000),
                new Employee(9, "James", "Anderson", "james.anderson@company.com", "Male", "Product Manager", "Harvard", "EMP009", 88000),
                new Employee(10, "olivia", "Thomas", "olivia.thomas@company.com", "Female", "Software Engineer", "Stanford", "EMP010", 76000)
        );
    }

    public static void main(String[] args) {
        // For actual implementation, use this line to read from Excel:
        // List<Employee> employees = readEmployeesFromExcel("resources/employees.xlsx");

        // Using sample data for demonstration
        List<Employee> employees = createSampleEmployees();

        System.out.println("=== Java 8 Stream API Operations on Employee Data ===\n");

        // 1. FILTERING OPERATIONS
        System.out.println("1. FILTERING OPERATIONS:");

        // Filter employees by gender
        List<Employee> maleEmployees = employees.stream()
                .filter(emp -> "Male".equals(emp.getGender()))
                .collect(Collectors.toList());
        System.out.println("Male Employees: " + maleEmployees.size());

        // Filter employees with salary > 80000
        List<Employee> highSalaryEmployees = employees.stream()
                .filter(emp -> emp.getSalary() > 80000)
                .collect(Collectors.toList());
        System.out.println("High Salary Employees (>80k): " + highSalaryEmployees.size());

        // Filter Software Engineers
        List<Employee> softwareEngineers = employees.stream()
                .filter(emp -> "Software Engineer".equals(emp.getJobTitle()))
                .collect(Collectors.toList());
        System.out.println("Software Engineers: " + softwareEngineers.size());

        // 2. MAPPING OPERATIONS
        System.out.println("\n2. MAPPING OPERATIONS:");

        // Get all employee names
        List<String> employeeNames = employees.stream()
                .map(Employee::getFullName)
                .collect(Collectors.toList());
        System.out.println("Employee Names: " + employeeNames);

        // Get all email domains
        List<String> emailDomains = employees.stream()
                .map(emp -> emp.getEmail().
                        substring(emp.getEmail().indexOf("@") + 1))
                .distinct()
                .collect(Collectors.toList());
        System.out.println("Email Domains: " + emailDomains);

        // Convert names to uppercase
        List<String> upperCaseNames = employees.stream()
                .map(emp -> emp.getFullName().toUpperCase())
                .collect(Collectors.toList());
        System.out.println("Uppercase Names: " + upperCaseNames.subList(0, 3) + "...");

        // 3. SORTING OPERATIONS
        System.out.println("\n3. SORTING OPERATIONS:");

        // Sort by salary (ascending)
        List<Employee> sortedBySalary = employees.stream()
                .sorted(Comparator.comparing(Employee::getSalary))
                .collect(Collectors.toList());
        System.out.println("Lowest Salary: " + sortedBySalary.get(0).getFullName() +
                " - $" + sortedBySalary.get(0).getSalary());

        // Sort by name (descending)
        List<Employee> sortedByNameDesc = employees.stream()
                .sorted(Comparator.comparing(Employee::getFirstName).reversed())
                .collect(Collectors.toList());
        System.out.println("Names in Descending Order: " +
                sortedByNameDesc.stream().limit(3).
                        map(Employee::getFirstName).collect(Collectors.toList()));

        // 4. GROUPING OPERATIONS
        System.out.println("\n4. GROUPING OPERATIONS:");

        // Group by gender
        Map<String, List<Employee>> groupedByGender = employees.stream()
                .collect(Collectors.groupingBy(Employee::getGender));
        System.out.println("Grouped by Gender:");
        groupedByGender.forEach((gender, empList) ->
                System.out.println("  " + gender + ": " + empList.size() + " employees"));

        // Group by job title
        Map<String, List<Employee>> groupedByJobTitle = employees.stream()
                .collect(Collectors.groupingBy(Employee::getJobTitle));
        System.out.println("Grouped by Job Title:");
        groupedByJobTitle.forEach((jobTitle, empList) ->
                System.out.println("  " + jobTitle + ": " + empList.size() + " employees"));

        // Group by university
        Map<String, List<Employee>> groupedByUniversity = employees.stream()
                .collect(Collectors.groupingBy(Employee::getUniversity));
        System.out.println("Grouped by University:");
        groupedByUniversity.forEach((university, empList) ->
                System.out.println("  " + university + ": " + empList.size() + " employees"));

        // 5. AGGREGATION OPERATIONS
        System.out.println("\n5. AGGREGATION OPERATIONS:");

        // Total salary
        double totalSalary = employees.stream()
                .mapToDouble(Employee::getSalary)
                .sum();
        System.out.println("Total Salary: $" + totalSalary);

        // Average salary
        OptionalDouble averageSalary = employees.stream()
                .mapToDouble(Employee::getSalary)
                .average();
        System.out.println("Average Salary: $" + (averageSalary.isPresent()
                ? averageSalary.getAsDouble() : 0));

        // Maximum salary
        OptionalDouble maxSalary = employees.stream()
                .mapToDouble(Employee::getSalary)
                .max();
        System.out.println("Maximum Salary: $" + (maxSalary.isPresent() ? maxSalary.getAsDouble() : 0));

        // Minimum salary
        OptionalDouble minSalary = employees.stream()
                .mapToDouble(Employee::getSalary)
                .min();
        System.out.println("Minimum Salary: $" + (minSalary.isPresent() ? minSalary.getAsDouble() : 0));

        // Count of employees
        long employeeCount = employees.stream().count();
        System.out.println("Total Employees: " + employeeCount);

        // 6. STATISTICAL OPERATIONS
        System.out.println("\n6. STATISTICAL OPERATIONS:");

        DoubleSummaryStatistics salaryStats = employees.stream()
                .mapToDouble(Employee::getSalary)
                .summaryStatistics();
        System.out.println("Salary Statistics:");
        System.out.println("  Count: " + salaryStats.getCount());
        System.out.println("  Sum: $" + salaryStats.getSum());
        System.out.println("  Average: $" + salaryStats.getAverage());
        System.out.println("  Min: $" + salaryStats.getMin());
        System.out.println("  Max: $" + salaryStats.getMax());

        // 7. PARTITIONING OPERATIONS
        System.out.println("\n7. PARTITIONING OPERATIONS:");

        // Partition by salary > 75000
        Map<Boolean, List<Employee>> partitionedBySalary = employees.stream()
                .collect(Collectors.partitioningBy(emp -> emp.getSalary() > 75000));
        System.out.println("High Salary (>75k): " + partitionedBySalary.get(true).size());
        System.out.println("Low Salary (<=75k): " + partitionedBySalary.get(false).size());

        // 8. FINDING OPERATIONS
        System.out.println("\n8. FINDING OPERATIONS:");

        // Find first employee with specific criteria
        Optional<Employee> firstHighSalaryEmployee = employees.stream()
                .filter(emp -> emp.getSalary() > 80000)
                .findFirst();
        System.out.println("First High Salary Employee: " +
                (firstHighSalaryEmployee.isPresent() ? firstHighSalaryEmployee.get().getFullName() : "None"));

        // Find any employee from MIT
        Optional<Employee> mitEmployee = employees.stream()
                .filter(emp -> "MIT".equals(emp.getUniversity()))
                .findAny();
        System.out.println("Any MIT Employee: " +
                (mitEmployee.isPresent() ? mitEmployee.get().getFullName() : "None"));

        // 9. MATCHING OPERATIONS
        System.out.println("\n9. MATCHING OPERATIONS:");

        // Check if all employees have salary > 50000
        boolean allHighSalary = employees.stream()
                .allMatch(emp -> emp.getSalary() > 50000);
        System.out.println("All employees have salary > 50k: " + allHighSalary);

        // Check if any employee is from Harvard
        boolean anyHarvardEmployee = employees.stream()
                .anyMatch(emp -> "Harvard".equals(emp.getUniversity()));
        System.out.println("Any Harvard employee: " + anyHarvardEmployee);

        // Check if no employee has salary > 100000
        boolean noVeryHighSalary = employees.stream()
                .noneMatch(emp -> emp.getSalary() > 100000);
        System.out.println("No employee has salary > 100k: " + noVeryHighSalary);

        // 10. COMPLEX OPERATIONS
        System.out.println("\n10. COMPLEX OPERATIONS:");

        // Average salary by gender
        Map<String, Double> avgSalaryByGender = employees.stream()
                .collect(Collectors.groupingBy(Employee::getGender,
                        Collectors.averagingDouble(Employee::getSalary)));
        System.out.println("Average Salary by Gender:");
        avgSalaryByGender.forEach((gender, avgSal) ->
                System.out.println("  " + gender + ": $" + String.format("%.2f", avgSal)));

        // Count by job title
        Map<String, Long> countByJobTitle = employees.stream()
                .collect(Collectors.groupingBy(Employee::getJobTitle, Collectors.counting()));
        System.out.println("Count by Job Title:");
        countByJobTitle.forEach((jobTitle, count) ->
                System.out.println("  " + jobTitle + ": " + count));

        // Top 3 highest paid employees
        List<Employee> top3HighestPaid = employees.stream()
                .sorted(Comparator.comparing(Employee::getSalary).reversed())
                .limit(3)
                .collect(Collectors.toList());
        System.out.println("Top 3 Highest Paid:");
        top3HighestPaid.forEach(emp ->
                System.out.println("  " + emp.getFullName() + " - $" + emp.getSalary()));

        // 11. DISTINCT OPERATIONS
        System.out.println("\n11. DISTINCT OPERATIONS:");

        // Get distinct universities
        List<String> distinctUniversities = employees.stream()
                .map(Employee::getUniversity)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
        System.out.println("Distinct Universities: " + distinctUniversities);

        // Get distinct job titles
        List<String> distinctJobTitles = employees.stream()
                .map(Employee::getJobTitle)
                .distinct()
                .collect(Collectors.toList());
        System.out.println("Distinct Job Titles: " + distinctJobTitles);

        // 12. PARALLEL STREAM OPERATIONS
        System.out.println("\n12. PARALLEL STREAM OPERATIONS:");

        // Calculate total salary using parallel stream
        double totalSalaryParallel = employees.parallelStream()
                .mapToDouble(Employee::getSalary)
                .sum();
        System.out.println("Total Salary (Parallel): $" + totalSalaryParallel);

        // Count employees by gender using parallel stream
        Map<String, Long> genderCountParallel = employees.parallelStream()
                .collect(Collectors.groupingBy(Employee::getGender, Collectors.counting()));
        System.out.println("Gender Count (Parallel): " + genderCountParallel);
    }
}

// Additional utility class for advanced operations
class EmployeeAnalytics {

    // Method to get salary range analysis
    public static void salaryRangeAnalysis(List<Employee> employees) {
        Map<String, Long> salaryRanges = employees.stream()
                .collect(Collectors.groupingBy(emp -> {
                    double salary = emp.getSalary();
                    if (salary < 60000) return "Below 60k";
                    else if (salary < 80000) return "60k-80k";
                    else if (salary < 100000) return "80k-100k";
                    else return "Above 100k";
                }, Collectors.counting()));

        System.out.println("Salary Range Analysis:");
        salaryRanges.forEach((range, count) ->
                System.out.println("  " + range + ": " + count + " employees"));
    }

    // Method to find employees with specific criteria
    public static List<Employee> findEmployees(List<Employee> employees,
                                               String jobTitle,
                                               String gender,
                                               double minSalary) {
        return employees.stream()
                .filter(emp -> jobTitle == null || jobTitle.equals(emp.getJobTitle()))
                .filter(emp -> gender == null || gender.equals(emp.getGender()))
                .filter(emp -> emp.getSalary() >= minSalary)
                .collect(Collectors.toList());
    }
}