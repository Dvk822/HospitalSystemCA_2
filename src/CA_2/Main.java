/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CA_2;

import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
 
public class Main {
    static Scanner scanner = new Scanner(System.in);
    static List<Employee> employeeList = new ArrayList<>();

    static String[] departments = {"Emergency", "Pediatrics", "Surgery", "Cardiology", "HR"};
    static String[] managerTypes = {"Head Manager", "Assistant Manager", "Team Lead"};
    //adding file name 

    public static void main(String[] args) {
        MenuOption choice;
        do {
            displayMenu();
            choice = getUserChoice();
            switch (choice) {
                case ADD_EMPLOYEE:
                    addEmployee();
                    break;
                case GENERATE_RANDOM_EMPLOYEE:
                    generateRandomEmployee();
                    break;
                case SORT_EMPLOYEES:
                    sortEmployees(); // will add later
                    // Sort employee list by name using merge sort
                    break;
                case SEARCH_EMPLOYEE:
                    searchEmployee(); // will add later
                    break;
                case LOAD_FROM_FILE:
                loadEmployeesFromFile();
                    break;
                    case VIEW_ALL_EMPLOYEES:
                    viewAllEmployees();
                    break;
                case EXIT:
                    System.out.println("Exiting program. Goodbye!");
                    break;
                    
            }
        } while (choice != MenuOption.EXIT);
    }

    private static void displayMenu() {
        System.out.println("\n--- Hospital Staff Management Menu ---");
        System.out.println("1. Add Employee (Name, Role, Department)");
        System.out.println("2. Generate Random Employees");
        System.out.println("3. S3. Sort Employee List");
        System.out.println("4. Search Employee Name");
        System.out.println("5. Exit");
        


    }

    private static MenuOption getUserChoice() {
        int input = Integer.parseInt(scanner.nextLine());
        return MenuOption.values()[input - 1]; // Just works if menu order matches enum order
    }

    private static void addEmployee() {
        System.out.print("Enter employee name: ");
        String name = scanner.nextLine();

        Manager manager = chooseManager();
        Department department = chooseDepartment();

        Employee employee = new Employee(name, manager, department);
        employeeList.add(employee);
        System.out.println("✅ Employee added: " + employee);
    }

    private static Manager chooseManager() {
        System.out.println("Choose Manager Type:");
        for (int i = 0; i < managerTypes.length; i++) {
            System.out.println((i + 1) + ". " + managerTypes[i]);
        }
        int choice = Integer.parseInt(scanner.nextLine());
        return new Manager(managerTypes[choice - 1]);
    }

    private static Department chooseDepartment() {
        System.out.println("Choose Department:");
        for (int i = 0; i < departments.length; i++) {
            System.out.println((i + 1) + ". " + departments[i]);
        }
        int choice = Integer.parseInt(scanner.nextLine());
        return new Department(departments[choice - 1]);
    }

    private static void generateRandomEmployee() {
        String name = "Emp" + (employeeList.size() + 1);
        Random rand = new Random();
        Manager manager = new Manager(managerTypes[rand.nextInt(managerTypes.length)]);
        Department department = new Department(departments[rand.nextInt(departments.length)]);
        Employee employee = new Employee(name, manager, department);
        employeeList.add(employee);
        System.out.println("✅ Random employee generated: " + employee);
    }
    private static void loadEmployeesFromFile() {
        System.out.print("Enter the filename (e.g., Applicants_Form.txt): ");
        String filename = scanner.nextLine();

        try {
            Scanner fileScanner = new Scanner(new File(filename));
            int count = 0;
            Random rand = new Random();

            while (fileScanner.hasNextLine()) {
                String name = fileScanner.nextLine().trim();
                if (!name.isEmpty()) {
                    Manager manager = new Manager(managerTypes[rand.nextInt(managerTypes.length)]);
                    Department department = new Department(departments[rand.nextInt(departments.length)]);
                    Employee employee = new Employee(name, manager, department);
                    employeeList.add(employee);
                    count++;
                }
            }
            fileScanner.close();
            System.out.println("✅ " + count + " employees loaded from file.");
        } catch (FileNotFoundException e) {
            System.out.println("❌ File not found. Please check the name and try again.");
        }
    }

    private static void sortEmployees() {
    if (employeeList.isEmpty()) {
        System.out.println("⚠️ No employees to sort.");
        return;
    }

    recursiveInsertionSort(employeeList, employeeList.size());

    System.out.println("✅ Employees sorted alphabetically (by name). Showing first 20:");
    for (int i = 0; i < Math.min(20, employeeList.size()); i++) {
        System.out.println((i + 1) + ". " + employeeList.get(i));
    }
}
    private static void recursiveInsertionSort(List<Employee> list, int n) {
    if (n <= 1) return;

    recursiveInsertionSort(list, n - 1);

    Employee last = list.get(n - 1);
    int j = n - 2;

    while (j >= 0 && list.get(j).getName().compareToIgnoreCase(last.getName()) > 0) {
        list.set(j + 1, list.get(j));
        j--;
    }
    list.set(j + 1, last);
}

   private static void searchEmployee() {
    if (employeeList.isEmpty()) {
        System.out.println("⚠️ No employees to search.");
        return;
    }

    // Sort list first to use Binary Search
    recursiveInsertionSort(employeeList, employeeList.size());

    System.out.print("Enter the employee name to search: ");
    String target = scanner.nextLine();

    int index = binarySearchByName(employeeList, target, 0, employeeList.size() - 1);

    if (index != -1) {
        Employee found = employeeList.get(index);
        System.out.println("✅ Employee found!");
        System.out.println("Name: " + found.getName());
        System.out.println("Manager Type: " + found.getManager());
        System.out.println("Department: " + found.getDepartment());
    } else {
        System.out.println("❌ Employee not found.");
    }
}
   private static int binarySearchByName(List<Employee> list, String target, int low, int high) {
    if (low > high) return -1;

    int mid = (low + high) / 2;
    String midName = list.get(mid).getName();

    int comparison = midName.compareToIgnoreCase(target);

    if (comparison == 0) {
        return mid;
    } else if (comparison < 0) {
        return binarySearchByName(list, target, mid + 1, high);
    } else {
        return binarySearchByName(list, target, low, mid - 1);
    }
}
  private static void viewAllEmployees() {
    if (employeeList.isEmpty()) {
        System.out.println("No employees found.");
        return;
    }

    for (int i = 0; i < employeeList.size(); i++) {
        System.out.println((i + 1) + ". " + employeeList.get(i));
    }
}
}
