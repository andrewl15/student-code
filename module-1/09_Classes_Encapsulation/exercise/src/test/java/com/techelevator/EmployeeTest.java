package com.techelevator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.MethodName.class)
public class EmployeeTest {

    @BeforeAll
    public static void EmployeeVerifyClassDefinition() {
        Class klass = Employee.class;

        // Verify not abstract
        assertFalse(Modifier.isAbstract(klass.getModifiers()), "Employee class must not be abstract. Remove the 'abstract' modifier on Employee.");

        // Verify constructor(s)
        Constructor<Employee> constructor = SafeReflection.getConstructor(klass, Integer.TYPE, String.class,
                String.class, Double.TYPE);
        assertNotNull(constructor, "Employee class does not have the required constructor(int, String, String, double)");

        // Verify attributes
        EncapsulationTestHelpers.verifyClassField(Employee.class, "employeeId", Integer.TYPE, true, false);
        EncapsulationTestHelpers.verifyClassField(Employee.class, "firstName", String.class, true, false);
        EncapsulationTestHelpers.verifyClassField(Employee.class, "lastName", String.class, true, true);
        EncapsulationTestHelpers.verifyDerivedField(Employee.class, "fullName", String.class);
        EncapsulationTestHelpers.verifyClassField(Employee.class, "department", String.class, true, true);
        EncapsulationTestHelpers.verifyClassField(Employee.class, "annualSalary", Double.TYPE, true, false);
    }

    @Test
    public void employeeConstructor() {
        Class klass = Employee.class;

        Constructor<Employee> constructor = SafeReflection.getConstructor(klass, Integer.TYPE, String.class,
                String.class, Double.TYPE);
        assertNotNull(constructor, "You do not have the required constructor(int, String, String, double)");
        try {
            Employee employee = constructor.newInstance(1, "Jane", "Smith", 100000.00);
            Method getEmployeeId = SafeReflection.getMethod(klass, "getEmployeeId");
            Method getFirstName = SafeReflection.getMethod(klass, "getFirstName");
            Method getLastName = SafeReflection.getMethod(klass, "getLastName");
            Method getAnnualSalary = SafeReflection.getMethod(klass, "getAnnualSalary");

            assertEquals(1, (int) getEmployeeId.invoke(employee),
                    "Passed 1 into constructor and expected EmployeeId property to hold 1.");
            assertEquals("Jane", (String) getFirstName.invoke(employee),
                    "Passed Jane into constructor and expected FirstName property to hold Jane.");
            assertEquals("Smith", (String) getLastName.invoke(employee),
                    "Passed Smith into constructor and expected LastName property to hold Smith.");
            assertEquals(100000.00, (double) getAnnualSalary.invoke(employee),
                    "Passed 100000.00 into constructor and expected AnnualSalary property to hold 100000.00.");
        } catch (Exception e) {
            fail("An unknown error occurred with Employee class: " + e.getMessage());
        }
    }

    @Test
    public void employeeFullNameTest() {
        Class klass = Employee.class;

        Constructor<Employee> constructor = SafeReflection.getConstructor(klass, Integer.TYPE, String.class,
                String.class, Double.TYPE);
        assertNotNull(constructor, "You do not have the required constructor(int, String, String, double)");
        try {
            Employee employee = constructor.newInstance(1, "Jane", "Smith", 100000.00);
            Method getFullName = SafeReflection.getMethod(klass, "getFullName");

            assertEquals("Smith, Jane", (String) getFullName.invoke(employee),
                    "Expected getFullName to return derived value of 'LastName, FirstName'");
        } catch (Exception e) {
            fail("An unknown error occurred with Employee class: " + e.getMessage());
        }
    }

    @Test
    public void employeeRaiseSalaryTest() {
        Class klass = Employee.class;

        Constructor<Employee> constructor = SafeReflection.getConstructor(klass, Integer.TYPE, String.class,
                String.class, Double.TYPE);
        assertNotNull(constructor, "You do not have the required constructor(int, String, String, double)");
        try {
            Employee employee = constructor.newInstance(1, "Jane", "Smith", 100000.00);
            Method getAnnualSalary = SafeReflection.getMethod(klass, "getAnnualSalary");
            Method raiseSalary = SafeReflection.getMethod(klass, "raiseSalary", Double.TYPE);

            assertNotNull(raiseSalary, "raiseSalary method is missing");

            raiseSalary.invoke(employee, new Object[] { 5.5 });
            assertEquals(105500.00, (double) getAnnualSalary.invoke(employee),
                    "Raised salary by 5.5%. Expected to go from 100000.00 to 105500.00");
        } catch (Exception e) {
            fail("An unknown error occurred with Employee class: " + e.getMessage());
        }
    }
}
