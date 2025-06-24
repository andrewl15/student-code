package com.techelevator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.lang.reflect.Method;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.MethodName.class)
public class Step2_AccountableTest {

    private final String PACKAGE_NAME = "com.techelevator";
    private final String BANK_ACCOUNT_CLASS_NAME = "BankAccount";
    private final String CHECKING_ACCOUNT_CLASS_NAME = "CheckingAccount";
    private final String SAVINGS_ACCOUNT_CLASS_NAME = "SavingsAccount";
    private final String ACCOUNTABLE_INTERFACE_NAME = "Accountable";
    private final String METHOD_GET_BALANCE = "getBalance";

    private static Class<?> bankAccountClass;
    private static Class<?> checkingAccountClass;
    private static Class<?> savingsAccountClass;
    private static Class<?> accountableInterfaceClass;

    @BeforeEach
    public void initialize() {
        try {
            bankAccountClass = Class.forName(PACKAGE_NAME + "." + BANK_ACCOUNT_CLASS_NAME);
            checkingAccountClass = Class.forName(PACKAGE_NAME + "." + CHECKING_ACCOUNT_CLASS_NAME);
            savingsAccountClass = Class.forName(PACKAGE_NAME + "." + SAVINGS_ACCOUNT_CLASS_NAME);
        } catch (ClassNotFoundException e) {
            fail(e.getMessage() + " class could not be found.");
        }
        try {
            accountableInterfaceClass = Class.forName(PACKAGE_NAME + "." + ACCOUNTABLE_INTERFACE_NAME);
        } catch (ClassNotFoundException e) {
            fail(e.getMessage() + " interface could not be found.");
        }
    }

    @Test
    public void test01_InterfaceWellFormed() {
        String wellFormedCheck = interfaceWellFormedCheck();
        if (!wellFormedCheck.isEmpty()) {
            fail(wellFormedCheck);
        }
    }

    @Test
    public void test02_HappyPathTests() {
        String wellFormedCheck = interfaceWellFormedCheck();
        if (!wellFormedCheck.isEmpty()) {
            fail(ACCOUNTABLE_INTERFACE_NAME + " is not well formed. The test01_InterfaceWellFormed tests must pass first.\r\n\t" + wellFormedCheck);
        }

        // Assert BankAccount implements Accountable interface
        boolean bankAccountImplementsInterface = Arrays.stream(bankAccountClass.getInterfaces())
                .anyMatch(interfaceType -> interfaceType.getSimpleName().equals(ACCOUNTABLE_INTERFACE_NAME));
        assertTrue(bankAccountImplementsInterface, BANK_ACCOUNT_CLASS_NAME + " must implement " + ACCOUNTABLE_INTERFACE_NAME + " interface.");

        // Assert CheckingAccount and SavingsAccount don't directly implement Accountable
        boolean checkingAccountImplementsInterface = Arrays.stream(checkingAccountClass.getInterfaces())
                .anyMatch(interfaceType -> interfaceType.getSimpleName().equals(ACCOUNTABLE_INTERFACE_NAME));
        assertFalse(checkingAccountImplementsInterface, CHECKING_ACCOUNT_CLASS_NAME + " must not directly implement " + ACCOUNTABLE_INTERFACE_NAME + " interface, it inherits the interface from the parent class.");

        boolean savingsAccountImplementsInterface = Arrays.stream(savingsAccountClass.getInterfaces())
                .anyMatch(interfaceType -> interfaceType.getSimpleName().equals(ACCOUNTABLE_INTERFACE_NAME));
        assertFalse(savingsAccountImplementsInterface, SAVINGS_ACCOUNT_CLASS_NAME + " must not directly implement " + ACCOUNTABLE_INTERFACE_NAME + " interface, it inherits the interface from the parent class.");

        // Assert CheckingAccount and SavingsAccount extend BankAccount, which implements Accountable
        Class<?> checkingAccountSuperclass = checkingAccountClass.getSuperclass();
        assertEquals(BANK_ACCOUNT_CLASS_NAME, checkingAccountSuperclass.getSimpleName(), CHECKING_ACCOUNT_CLASS_NAME + " must extend " + BANK_ACCOUNT_CLASS_NAME + " which implements " + ACCOUNTABLE_INTERFACE_NAME + ".");

        Class<?> savingsAccountSuperclass = savingsAccountClass.getSuperclass();
        assertEquals(BANK_ACCOUNT_CLASS_NAME, savingsAccountSuperclass.getSimpleName(), SAVINGS_ACCOUNT_CLASS_NAME + " must extend " + BANK_ACCOUNT_CLASS_NAME + " which implements " + ACCOUNTABLE_INTERFACE_NAME + ".");

        Method bankAccountGetBalance = SafeReflection.getMethod(bankAccountClass, METHOD_GET_BALANCE);
        Method checkingAccountGetBalance = SafeReflection.getMethod(checkingAccountClass, METHOD_GET_BALANCE);
        Method savingsAccountGetBalance = SafeReflection.getMethod(savingsAccountClass, METHOD_GET_BALANCE);

        // Assert getBalance() method is overridden in CheckingAccount and SavingsAccount
        assertNotNull(bankAccountGetBalance,
                METHOD_GET_BALANCE + " not found in " + BANK_ACCOUNT_CLASS_NAME +
                ". Have you declared it yet? Make sure the method name is '" + METHOD_GET_BALANCE +
                "', that it is public, and that it accepts zero parameters.");

        assertNotNull(checkingAccountGetBalance,
                METHOD_GET_BALANCE + " not found in " + CHECKING_ACCOUNT_CLASS_NAME + ". It should inherit the method from the parent class.");
        assertEquals(BANK_ACCOUNT_CLASS_NAME, checkingAccountGetBalance.getDeclaringClass().getSimpleName(),
                METHOD_GET_BALANCE + " must not be declared in " + CHECKING_ACCOUNT_CLASS_NAME + ". It should inherit the method from the parent class.");

        assertNotNull(savingsAccountGetBalance,
                METHOD_GET_BALANCE + " not found in " + SAVINGS_ACCOUNT_CLASS_NAME + ". It should inherit the method from the parent class.");
        assertEquals(BANK_ACCOUNT_CLASS_NAME, savingsAccountGetBalance.getDeclaringClass().getSimpleName(),
                METHOD_GET_BALANCE + " must not be declared in " + SAVINGS_ACCOUNT_CLASS_NAME + ". It should inherit the method from the parent class.");
    }

    private String interfaceWellFormedCheck() {

        // Assert class exists
        if (accountableInterfaceClass == null) {
            return ACCOUNTABLE_INTERFACE_NAME + " interface not found. Have you declared it yet? Make sure the name is '" + ACCOUNTABLE_INTERFACE_NAME + "' and the package is '" + PACKAGE_NAME + "'.";
        }

        // Assert is an interface
        if (!accountableInterfaceClass.isInterface()) {
            return ACCOUNTABLE_INTERFACE_NAME + " must be an interface. Make sure you declared it an 'interface' and not a 'class'.";
        }

        // Assert methods exist, are of correct type and access
        String methodCheck = ReflectionTestHelper.checkMethod(accountableInterfaceClass, METHOD_GET_BALANCE, int.class, true, new Class[] { });
        if (!methodCheck.isEmpty()) {
            return methodCheck;
        }

        return "";
    }
}
