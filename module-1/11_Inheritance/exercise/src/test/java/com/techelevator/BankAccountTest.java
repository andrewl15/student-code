package com.techelevator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.lang.reflect.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.MethodName.class)
public class BankAccountTest {

    private final String PACKAGE_NAME = "com.techelevator";
    private final String BANK_ACCOUNT_CLASS_NAME = "BankAccount";
    private static Class<?> bankAccountClass;

    @BeforeEach
    public void Initialize() {
        try {
            bankAccountClass = Class.forName(PACKAGE_NAME + "." + BANK_ACCOUNT_CLASS_NAME);
        } catch (ClassNotFoundException e) {
            fail(e.getMessage() + " class could not be found. Have you declared it yet? Make sure the class name is '" + BANK_ACCOUNT_CLASS_NAME + "' and the package name is '" + PACKAGE_NAME + "'.");
        }
    }

    @Test
    public void test01_ClassWellFormed() {
        String wellFormedCheck = classWellFormedCheck();
        if (!wellFormedCheck.isEmpty()) {
            fail(wellFormedCheck);
        }
    }

    @Test
    public void test02_HappyPath_Tests() throws InvocationTargetException, IllegalAccessException, InstantiationException, NoSuchMethodException {
        String wellFormedCheck = classWellFormedCheck();
        if (!wellFormedCheck.isEmpty()) {
            fail(BANK_ACCOUNT_CLASS_NAME + " is not well formed. The test01_ClassWellFormed tests must pass first.\r\n\t" + wellFormedCheck);
        }

        // Assert constructors set fields
        String testAccountHolderName = "Tester Testerson";
        String testAccountNumber = "CHK:1234";
        int testBalance = 100;

        // Two arg constructor
        Constructor<?> twoArgConstructor = SafeReflection.getConstructor(bankAccountClass, String.class, String.class);
        Object twoArgInstance = twoArgConstructor.newInstance(testAccountHolderName, testAccountNumber);

        Method twoArgGetAccountHolderName = twoArgInstance.getClass().getMethod("getAccountHolderName");
        assertEquals(testAccountHolderName, twoArgGetAccountHolderName.invoke(twoArgInstance),
                BANK_ACCOUNT_CLASS_NAME + " constructor " + BANK_ACCOUNT_CLASS_NAME + "(String, String) does not correctly set the field accountHolderName.");
        Method twoArgGetAccountNumber = twoArgInstance.getClass().getMethod("getAccountNumber");
        assertEquals(testAccountNumber, twoArgGetAccountNumber.invoke(twoArgInstance),
                BANK_ACCOUNT_CLASS_NAME + " constructor " + BANK_ACCOUNT_CLASS_NAME + "(String, String) does not correctly set the field accountNumber.");
        Method twoArgGetBalance = twoArgInstance.getClass().getMethod("getBalance");
        assertEquals(0, twoArgGetBalance.invoke(twoArgInstance),
                BANK_ACCOUNT_CLASS_NAME + " constructor " + BANK_ACCOUNT_CLASS_NAME + "(String, String) does not default balance to 0.");

        // Three arg constructor
        Constructor<?> threeArgConstructor = SafeReflection.getConstructor(bankAccountClass, String.class, String.class, int.class);
        Object threeArgInstance = threeArgConstructor.newInstance(testAccountHolderName, testAccountNumber, testBalance);

        Method threeArgGetAccountHolderName = threeArgInstance.getClass().getMethod("getAccountHolderName");
        assertEquals(testAccountHolderName, threeArgGetAccountHolderName.invoke(threeArgInstance),
                BANK_ACCOUNT_CLASS_NAME + " constructor " + BANK_ACCOUNT_CLASS_NAME + "(String, String, int) does not correctly set the field accountHolderName.");
        Method threeArgGetAccountNumber = threeArgInstance.getClass().getMethod("getAccountNumber");
        assertEquals(testAccountNumber, threeArgGetAccountNumber.invoke(threeArgInstance),
                BANK_ACCOUNT_CLASS_NAME + " constructor " + BANK_ACCOUNT_CLASS_NAME + "(String, String, int) does not correctly set the field accountNumber.");
        Method threeArgGetBalance = threeArgInstance.getClass().getMethod("getBalance");
        assertEquals(testBalance, threeArgGetBalance.invoke(threeArgInstance),
                BANK_ACCOUNT_CLASS_NAME + " constructor " + BANK_ACCOUNT_CLASS_NAME + "(String, String, int) does not correctly set the field balance.");

        // Assert deposit increases balance
        Method deposit = threeArgInstance.getClass().getMethod("deposit", int.class);
        int depositParamValue = 23;
        int depositExpectedReturnValue = testBalance + depositParamValue;
        Object depositActualReturnValue = deposit.invoke(threeArgInstance, depositParamValue);
        assertEquals(depositExpectedReturnValue, depositActualReturnValue,
                BANK_ACCOUNT_CLASS_NAME + " deposit method fails to increase balance by correct amount. Starting balance: " + testBalance + ", deposit: " + depositParamValue + ", new balance should be " + depositExpectedReturnValue + ".");

        // Assert withdraw decreases balance
        Method withdraw = threeArgInstance.getClass().getMethod("withdraw", int.class);
        int withdrawParamValue = 22;
        int withdrawExpectedReturnValue = depositExpectedReturnValue - withdrawParamValue;
        Object withdrawActualReturnValue = withdraw.invoke(threeArgInstance, withdrawParamValue);
        assertEquals(withdrawExpectedReturnValue, withdrawActualReturnValue,
                BANK_ACCOUNT_CLASS_NAME + " withdraw method fails to decrease balance by correct amount. Starting balance: " + depositExpectedReturnValue + ", withdraw: " + withdrawParamValue + ", new balance should be " + withdrawExpectedReturnValue + ".");
    }

    @Test
    public void test03_EdgeCase_Tests() throws IllegalAccessException, InvocationTargetException, InstantiationException {
        String wellFormedCheck = classWellFormedCheck();
        if (!wellFormedCheck.isEmpty()) {
            fail(BANK_ACCOUNT_CLASS_NAME + " is not well formed. The test01_ClassWellFormed tests must pass first.\r\n\t" + wellFormedCheck);
        }

        Constructor<?> twoArgConstructor = SafeReflection.getConstructor(bankAccountClass, String.class, String.class);
        Constructor<?> threeArgConstructor = SafeReflection.getConstructor(bankAccountClass, String.class, String.class, int.class);
        Method deposit = SafeReflection.getMethod(bankAccountClass, "deposit", int.class);
        Method withdraw = SafeReflection.getMethod(bankAccountClass, "withdraw", int.class);
        Method getBalance = SafeReflection.getMethod(bankAccountClass, "getBalance");

        // Assert two argument constructor defaults balance to 0
        Object bankAccount = twoArgConstructor.newInstance("", "");
        Object balance = getBalance.invoke(bankAccount);
        assertEquals(0, balance, BANK_ACCOUNT_CLASS_NAME + " two argument constructor BankAccount(String, String) does not default balance to 0.");

        // Assert withdraw method can handle 0 balance
        bankAccount = threeArgConstructor.newInstance("", "", 1);
        withdraw.invoke(bankAccount, 1);
        balance = getBalance.invoke(bankAccount);
        assertEquals(0, balance, BANK_ACCOUNT_CLASS_NAME + " withdraw method fails to decrease balance by correct amount. Starting balance: 1, withdraw: 1, new balance should be 0.");

        // Assert deposit method can handle 0 balance
        bankAccount = threeArgConstructor.newInstance("", "", 0);
        deposit.invoke(bankAccount, 1);
        balance = getBalance.invoke(bankAccount);
        assertEquals(1, balance, BANK_ACCOUNT_CLASS_NAME + " deposit method fails to increase balance by correct amount. Starting balance: 0, deposit: 1, new balance should be 1.");

        // Assert can't deposit a negative amount
        bankAccount = threeArgConstructor.newInstance("", "", 100);
        deposit.invoke(bankAccount, -10);
        balance = getBalance.invoke(bankAccount);
        assertEquals(100, balance, BANK_ACCOUNT_CLASS_NAME + " deposit method fails to prevent negative deposit amount. Starting balance: 100, deposit: -10, balance should remain at 100.");

        // Assert can't withdraw a negative amount
        bankAccount = threeArgConstructor.newInstance("", "", 100);
        withdraw.invoke(bankAccount, -10);
        balance = getBalance.invoke(bankAccount);
        assertEquals(100, balance, BANK_ACCOUNT_CLASS_NAME + " withdraw method fails to prevent negative withdraw amount. Starting balance: 100, withdraw: -10, balance should remain at 100.");
    }

    private String classWellFormedCheck() {
        // Assert class exists
        if (bankAccountClass == null) {
            return BANK_ACCOUNT_CLASS_NAME + " class not found. Have you declared it yet? Make sure the class name is '" + BANK_ACCOUNT_CLASS_NAME + "' and the package name is '" + PACKAGE_NAME + "'.";
        }

        if (Modifier.isAbstract(bankAccountClass.getModifiers())) {
            return BANK_ACCOUNT_CLASS_NAME + " class must not be abstract. Remove the 'abstract' modifier on " + BANK_ACCOUNT_CLASS_NAME + ".";
        }

        // Assert constructors exist
        try {
            Constructor<?> twoArgConstructor = bankAccountClass.getConstructor(String.class, String.class);
        } catch (NoSuchMethodException e) {
            return BANK_ACCOUNT_CLASS_NAME + " does not have the required two argument constructor "
                    + BANK_ACCOUNT_CLASS_NAME + "(String, String). Make sure access for the constructor is public.";
        }
        try {
            Constructor<?> threeArgConstructor = bankAccountClass.getConstructor(String.class, String.class, int.class);
        } catch (NoSuchMethodException e) {
            return BANK_ACCOUNT_CLASS_NAME + " does not have the required three argument constructor "
                    + BANK_ACCOUNT_CLASS_NAME + "(String, String, int). Make sure access for the constructor is public.";
        }

        // Assert fields exist, are of correct type and access
        String fieldCheck = ReflectionTestHelper.checkField(bankAccountClass, "accountHolderName", String.class, ReflectionTestHelper.AccessModifier.Private);
        if (!fieldCheck.isEmpty()) {
            return fieldCheck;
        }
        fieldCheck = ReflectionTestHelper.checkField(bankAccountClass, "accountNumber", String.class, ReflectionTestHelper.AccessModifier.Private);
        if (!fieldCheck.isEmpty()) {
            return fieldCheck;
        }
        fieldCheck = ReflectionTestHelper.checkField(bankAccountClass, "balance", int.class, ReflectionTestHelper.AccessModifier.Private);
        if (!fieldCheck.isEmpty()) {
            return fieldCheck;
        }

        // Assert getters and setters for fields
        String methodCheck = ReflectionTestHelper.checkMethod(bankAccountClass, "getAccountHolderName", String.class, true, new Class[]{});
        if (!methodCheck.isEmpty()) {
            return methodCheck;
        }
        methodCheck = ReflectionTestHelper.checkMethod(bankAccountClass, "getAccountNumber", String.class, true, new Class[]{});
        if (!methodCheck.isEmpty()) {
            return methodCheck;
        }
        methodCheck = ReflectionTestHelper.checkMethod(bankAccountClass, "getBalance", int.class, true, new Class[]{});
        if (!methodCheck.isEmpty()) {
            return methodCheck;
        }

        Method accountHolderNameSetter = SafeReflection.getMethod(bankAccountClass, "setAccountHolderName", String.class);
        assertNull(accountHolderNameSetter, "BankAccount must not have a setter setAccountHolderName(String)");

        Method accountNumberSetter = SafeReflection.getMethod(bankAccountClass, "setAccountNumber", String.class);
        assertNull(accountNumberSetter, "BankAccount must not have a setter setAccountNumber(String)");

        Method balanceSetter = SafeReflection.getMethod(bankAccountClass, "setBalance", int.class);
        assertNull(balanceSetter, "BankAccount must not have a setter setBalance(int)");

        methodCheck = ReflectionTestHelper.checkMethod(bankAccountClass, "deposit", int.class, true, new Class<?>[]{int.class});
        if (!methodCheck.isEmpty()) {
            return methodCheck;
        }

        methodCheck = ReflectionTestHelper.checkMethod(bankAccountClass, "withdraw", int.class, true, new Class<?>[]{int.class});
        if (!methodCheck.isEmpty()) {
            return methodCheck;
        }

        return "";
    }
}
