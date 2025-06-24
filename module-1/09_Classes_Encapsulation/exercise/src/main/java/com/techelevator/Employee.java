package com.techelevator;

public class Employee {
    private int employeeId;
    private String firstName;
    private String lastName;

    private String department;
    private double annualSalary;

    //getters and setters
    public int getEmployeeId(){
        return employeeId;
    }
    public String getFirstName(){
        return firstName;
    }
    public String getLastName(){
        return lastName;
    }
    public String getDepartment(){
        return department;
    }
    public void setDepartment(String department){
        this.department = department;
    }
    public double getAnnualSalary(){
        return annualSalary;
    }
    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    //constructor
    public Employee(int employeeId, String firstName, String lastName, double annualSalary){
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.annualSalary = annualSalary;
    }

    //derived instance
    public String getFullName(){
        return lastName + ", " + firstName;
    }

    //methods
    public void raiseSalary(double percent){
        annualSalary += annualSalary * (percent/100);
    }
}
