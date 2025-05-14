/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CA_2;

/**
 *
 * @author acer
 */
public class Employee {
    private String name;
    private Manager manager;
    private Department department;

    public Employee(String name, Manager manager, Department department) {
        this.name = name;
        this.manager = manager;
        this.department = department;
    }

    public String getName() {
        return name;
    }

    public Manager getManager() {
        return manager;
    }

    public Department getDepartment() {
        return department;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "Employee Name: " + name +
               " | Manager Type: " + manager +
               " | Department: " + department;
    }
}
