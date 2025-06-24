package com.techelevator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@TestMethodOrder(MethodOrderer.MethodName.class)
public class Step1_TransferFundsTest {

    private final String PACKAGE_NAME = "com.techelevator";
    private final String BANK_ACCOUNT_CLASS_NAME = "BankAccount";
    private final String CHECKING_ACCOUNT_CLASS_NAME = "CheckingAccount";
    private final String SAVINGS_ACCOUNT_CLASS_NAME = "SavingsAccount";
    private final String METHOD_NAME = "transferFunds";

    private static Class<?> bankAccountClass;
    private static Class<?> checkingAccountClass;
    private static Class<?> savingsAccountClass;

    @BeforeEach
    public void initialize() {
        try {
            bankAccountClass = Class.forName(PACKAGE_NAME + "." + BANK_ACCOUNT_CLASS_NAME);
            checkingAccountClass = Class.forName(PACKAGE_NAME + "." + CHECKING_ACCOUNT_CLASS_NAME);
            savingsAccountClass = Class.forName(PACKAGE_NAME + "." + SAVINGS_ACCOUNT_CLASS_NAME);
        } catch (ClassNotFoundException e) {
            fail(e.getMessage() + " class could not be found.");
        }
    }

    @Test
    public void test01_MethodWellFormed() {
        String wellFormedCheck = methodWellFormedCheck();
        if (!wellFormedCheck.isEmpty()) {
            fail(wellFormedCheck);
        }
    }

    @Test
    public void test02_HappyPathTests() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String wellFormedCheck = methodWellFormedCheck();
        if (!wellFormedCheck.isEmpty()) {
            fail(METHOD_NAME + " is not well formed. The test01_MethodWellFormed tests must pass first.\r\n\t" + wellFormedCheck);
        }

        BankAccount account1, account2;
        Method transferFunds = bankAccountClass.getMethod(METHOD_NAME, BankAccount.class, int.class);
        int balanceAccount1, balanceAccount2, balanceExpected1, balanceExpected2, transferAmount;
        Object returnValue;

        // FROM BANK
        // bank to bank
        balanceAccount1 = 100;
        balanceAccount2 = 20;
        transferAmount = 30;
        balanceExpected1 = balanceAccount1 - transferAmount;
        balanceExpected2 = balanceAccount2 + transferAmount;
        account1 = new BankAccount("BNK:1234", balanceAccount1);
        account2 = new BankAccount("BNK:5678", balanceAccount2);

        returnValue = transferFunds.invoke(account1, account2, transferAmount);
        assertEquals(balanceExpected1, returnValue,
                "Bank to bank " + METHOD_NAME + " did not return the correct value. Account 1 balance: " + balanceAccount1 + ", Account 2 balance: " + balanceAccount2 + ", Transfer: " + transferAmount + ", Return value expected: " + balanceExpected1 + ".");
        assertEquals(balanceExpected1, account1.getBalance(),
                "Bank to bank " + METHOD_NAME + " did not correctly set balance for the 'from' account. Account 1 balance: " + balanceAccount1 + ", Account 2 balance: " + balanceAccount2 + ", Transfer: " + transferAmount + ", Account 1 balance expected: " + balanceExpected1 + ".");
        assertEquals(balanceExpected2, account2.getBalance(),
                "Bank to bank " + METHOD_NAME + " did not correctly set balance for the 'to' account. Account 1 balance: " + balanceAccount1 + ", Account 2 balance: " + balanceAccount2 + ", Transfer: " + transferAmount + ", Account 2 balance expected: " + balanceExpected2 + ".");

        // bank to checking
        balanceAccount1 = 100;
        balanceAccount2 = 20;
        transferAmount = 35;
        balanceExpected1 = balanceAccount1 - transferAmount;
        balanceExpected2 = balanceAccount2 + transferAmount;
        account1 = new BankAccount("BNK:1234", balanceAccount1);
        account2 = new CheckingAccount("CHK:1234", balanceAccount2);

        returnValue = transferFunds.invoke(account1, account2, transferAmount);
        assertEquals(balanceExpected1, returnValue,
                "Bank to checking " + METHOD_NAME + " did not return the correct value. Account 1 balance: " + balanceAccount1 + ", Account 2 balance: " + balanceAccount2 + ", Transfer: " + transferAmount + ", Return value expected: " + balanceExpected1 + ".");
        assertEquals(balanceExpected1, account1.getBalance(),
                "Bank to checking " + METHOD_NAME + " did not correctly set balance for the 'from' account. Account 1 balance: " + balanceAccount1 + ", Account 2 balance: " + balanceAccount2 + ", Transfer: " + transferAmount + ", Account 1 balance expected: " + balanceExpected1 + ".");
        assertEquals(balanceExpected2, account2.getBalance(),
                "Bank to checking " + METHOD_NAME + " did not correctly set balance for the 'to' account. Account 1 balance: " + balanceAccount1 + ", Account 2 balance: " + balanceAccount2 + ", Transfer: " + transferAmount + ", Account 2 balance expected: " + balanceExpected2 + ".");

        // bank to savings
        balanceAccount1 = 100;
        balanceAccount2 = 25;
        transferAmount = 40;
        balanceExpected1 = balanceAccount1 - transferAmount;
        balanceExpected2 = balanceAccount2 + transferAmount;
        account1 = new BankAccount("BNK:1234", balanceAccount1);
        account2 = new SavingsAccount("SAV:1234", balanceAccount2);

        returnValue = transferFunds.invoke(account1, account2, transferAmount);
        assertEquals(balanceExpected1, returnValue,
                "Bank to savings " + METHOD_NAME + " did not return the correct value. Account 1 balance: " + balanceAccount1 + ", Account 2 balance: " + balanceAccount2 + ", Transfer: " + transferAmount + ", Return value expected: " + balanceExpected1 + ".");
        assertEquals(balanceExpected1, account1.getBalance(),
                "Bank to savings " + METHOD_NAME + " did not correctly set balance for the 'from' account. Account 1 balance: " + balanceAccount1 + ", Account 2 balance: " + balanceAccount2 + ", Transfer: " + transferAmount + ", Account 1 balance expected: " + balanceExpected1 + ".");
        assertEquals(balanceExpected2, account2.getBalance(),
                "Bank to savings " + METHOD_NAME + " did not correctly set balance for the 'to' account. Account 1 balance: " + balanceAccount1 + ", Account 2 balance: " + balanceAccount2 + ", Transfer: " + transferAmount + ", Account 2 balance expected: " + balanceExpected2 + ".");

        
        /// FROM CHECKING
        // checking to bank
        balanceAccount1 = 100;
        balanceAccount2 = 20;
        transferAmount = 30;
        balanceExpected1 = balanceAccount1 - transferAmount;
        balanceExpected2 = balanceAccount2 + transferAmount;
        account1 = new CheckingAccount("CHK:1234", balanceAccount1);
        account2 = new BankAccount("BNK:1234", balanceAccount2);

        returnValue = transferFunds.invoke(account1, account2, transferAmount);
        assertEquals(balanceExpected1, returnValue,
                "Checking to bank " + METHOD_NAME + " did not return the correct value. Account 1 balance: " + balanceAccount1 + ", Account 2 balance: " + balanceAccount2 + ", Transfer: " + transferAmount + ", Return value expected: " + balanceExpected1 + ".");
        assertEquals(balanceExpected1, account1.getBalance(),
                "Checking to bank " + METHOD_NAME + " did not correctly set balance for the 'from' account. Account 1 balance: " + balanceAccount1 + ", Account 2 balance: " + balanceAccount2 + ", Transfer: " + transferAmount + ", Account 1 balance expected: " + balanceExpected1 + ".");
        assertEquals(balanceExpected2, account2.getBalance(),
                "Checking to bank " + METHOD_NAME + " did not correctly set balance for the 'to' account. Account 1 balance: " + balanceAccount1 + ", Account 2 balance: " + balanceAccount2 + ", Transfer: " + transferAmount + ", Account 2 balance expected: " + balanceExpected2 + ".");

        // checking to checking
        balanceAccount1 = 80;
        balanceAccount2 = 30;
        transferAmount = 40;
        balanceExpected1 = balanceAccount1 - transferAmount;
        balanceExpected2 = balanceAccount2 + transferAmount;
        account1 = new CheckingAccount("CHK:1234", balanceAccount1);
        account2 = new CheckingAccount("CHK:5678", balanceAccount2);

        returnValue = transferFunds.invoke(account1, account2, transferAmount);
        assertEquals(balanceExpected1, returnValue,
                "Checking to checking " + METHOD_NAME + " did not return the correct value. Account 1 balance: " + balanceAccount1 + ", Account 2 balance: " + balanceAccount2 + ", Transfer: " + transferAmount + ", Return value expected: " + balanceExpected1 + ".");
        assertEquals(balanceExpected1, account1.getBalance(),
                "Checking to checking " + METHOD_NAME + " did not correctly set balance for the 'from' account. Account 1 balance: " + balanceAccount1 + ", Account 2 balance: " + balanceAccount2 + ", Transfer: " + transferAmount + ", Account 1 balance expected: " + balanceExpected1 + ".");
        assertEquals(balanceExpected2, account2.getBalance(),
                "Checking to checking " + METHOD_NAME + " did not correctly set balance for the 'to' account. Account 1 balance: " + balanceAccount1 + ", Account 2 balance: " + balanceAccount2 + ", Transfer: " + transferAmount + ", Account 2 balance expected: " + balanceExpected2 + ".");

        // checking to savings
        balanceAccount1 = 80;
        balanceAccount2 = 60;
        transferAmount = 40;
        balanceExpected1 = balanceAccount1 - transferAmount;
        balanceExpected2 = balanceAccount2 + transferAmount;
        account1 = new CheckingAccount("CHK:1234", balanceAccount1);
        account2 = new SavingsAccount("SAV:1234", balanceAccount2);

        returnValue = transferFunds.invoke(account1, account2, transferAmount);
        assertEquals(balanceExpected1, returnValue,
                "Checking to savings " + METHOD_NAME + " did not return the correct value. Account 1 balance: " + balanceAccount1 + ", Account 2 balance: " + balanceAccount2 + ", Transfer: " + transferAmount + ", Return value expected: " + balanceExpected1 + ".");
        assertEquals(balanceExpected1, account1.getBalance(),
                "Checking to savings " + METHOD_NAME + " did not correctly set balance for the 'from' account. Account 1 balance: " + balanceAccount1 + ", Account 2 balance: " + balanceAccount2 + ", Transfer: " + transferAmount + ", Account 1 balance expected: " + balanceExpected1 + ".");
        assertEquals(balanceExpected2, account2.getBalance(),
                "Checking to savings " + METHOD_NAME + " did not correctly set balance for the 'to' account. Account 1 balance: " + balanceAccount1 + ", Account 2 balance: " + balanceAccount2 + ", Transfer: " + transferAmount + ", Account 2 balance expected: " + balanceExpected2 + ".");


        /// FROM SAVINGS
        // savings to bank
        balanceAccount1 = 465;
        balanceAccount2 = 20;
        transferAmount = 30;
        balanceExpected1 = balanceAccount1 - transferAmount;
        balanceExpected2 = balanceAccount2 + transferAmount;
        account1 = new SavingsAccount("SAV:1234", balanceAccount1);
        account2 = new BankAccount("BNK:1234", balanceAccount2);

        returnValue = transferFunds.invoke(account1, account2, transferAmount);
        assertEquals(balanceExpected1, returnValue,
                "Savings to bank " + METHOD_NAME + " did not return the correct value. Account 1 balance: " + balanceAccount1 + ", Account 2 balance: " + balanceAccount2 + ", Transfer: " + transferAmount + ", Return value expected: " + balanceExpected1 + ".");
        assertEquals(balanceExpected1, account1.getBalance(),
                "Savings to bank " + METHOD_NAME + " did not correctly set balance for the 'from' account. Account 1 balance: " + balanceAccount1 + ", Account 2 balance: " + balanceAccount2 + ", Transfer: " + transferAmount + ", Account 1 balance expected: " + balanceExpected1 + ".");
        assertEquals(balanceExpected2, account2.getBalance(),
                "Savings to bank " + METHOD_NAME + " did not correctly set balance for the 'to' account. Account 1 balance: " + balanceAccount1 + ", Account 2 balance: " + balanceAccount2 + ", Transfer: " + transferAmount + ", Account 2 balance expected: " + balanceExpected2 + ".");

        // savings to checking
        balanceAccount1 = 255;
        balanceAccount2 = 10;
        transferAmount = 30;
        balanceExpected1 = balanceAccount1 - transferAmount;
        balanceExpected2 = balanceAccount2 + transferAmount;
        account1 = new SavingsAccount("SAV:1234", balanceAccount1);
        account2 = new CheckingAccount("CHK:1234", balanceAccount2);

        returnValue = transferFunds.invoke(account1, account2, transferAmount);
        assertEquals(balanceExpected1, returnValue,
                "Savings to checking " + METHOD_NAME + " did not return the correct value. Account 1 balance: " + balanceAccount1 + ", Account 2 balance: " + balanceAccount2 + ", Transfer: " + transferAmount + ", Return value expected: " + balanceExpected1 + ".");
        assertEquals(balanceExpected1, account1.getBalance(),
                "Savings to checking " + METHOD_NAME + " did not correctly set balance for the 'from' account. Account 1 balance: " + balanceAccount1 + ", Account 2 balance: " + balanceAccount2 + ", Transfer: " + transferAmount + ", Account 1 balance expected: " + balanceExpected1 + ".");
        assertEquals(balanceExpected2, account2.getBalance(),
                "Savings to checking " + METHOD_NAME + " did not correctly set balance for the 'to' account. Account 1 balance: " + balanceAccount1 + ", Account 2 balance: " + balanceAccount2 + ", Transfer: " + transferAmount + ", Account 2 balance expected: " + balanceExpected2 + ".");

        // savings to savings
        balanceAccount1 = 290;
        balanceAccount2 = 100;
        transferAmount = 60;
        balanceExpected1 = balanceAccount1 - transferAmount;
        balanceExpected2 = balanceAccount2 + transferAmount;
        account1 = new SavingsAccount("SAV:1234", balanceAccount1);
        account2 = new SavingsAccount("SAV:5678", balanceAccount2);

        returnValue = transferFunds.invoke(account1, account2, transferAmount);
        assertEquals(balanceExpected1, returnValue,
                "Savings to savings " + METHOD_NAME + " did not return the correct value. Account 1 balance: " + balanceAccount1 + ", Account 2 balance: " + balanceAccount2 + ", Transfer: " + transferAmount + ", Return value expected: " + balanceExpected1 + ".");
        assertEquals(balanceExpected1, account1.getBalance(),
                "Savings to savings " + METHOD_NAME + " did not correctly set balance for the 'from' account. Account 1 balance: " + balanceAccount1 + ", Account 2 balance: " + balanceAccount2 + ", Transfer: " + transferAmount + ", Account 1 balance expected: " + balanceExpected1 + ".");
        assertEquals(balanceExpected2, account2.getBalance(),
                "Savings to savings " + METHOD_NAME + " did not correctly set balance for the 'to' account. Account 1 balance: " + balanceAccount1 + ", Account 2 balance: " + balanceAccount2 + ", Transfer: " + transferAmount + ", Account 2 balance expected: " + balanceExpected2 + ".");
    }

    @Test
    public void test03_EdgeCaseTests() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String wellFormedCheck = methodWellFormedCheck();
        if (!wellFormedCheck.isEmpty()) {
            fail(METHOD_NAME + " is not well formed. The test01_MethodWellFormed tests must pass first.\r\n\t" + wellFormedCheck);
        }

        BankAccount account1, account2;
        Method transferFunds = bankAccountClass.getMethod(METHOD_NAME, BankAccount.class, int.class);
        int balanceAccount1, balanceAccount2, balanceExpected1, balanceExpected2, transferAmount;
        Object returnValue;

        // negative transfer values
        balanceAccount1 = 100;
        balanceAccount2 = 20;
        transferAmount = -30;
        balanceExpected1 = balanceAccount1; // no change
        balanceExpected2 = balanceAccount2; // no change
        account1 = new BankAccount("BNK:1234", balanceAccount1);
        account2 = new BankAccount("BNK:5678", balanceAccount2);

        returnValue = transferFunds.invoke(account1, account2, transferAmount);
        assertEquals(balanceExpected1, returnValue,
                METHOD_NAME + " with a negative transfer amount returned a value different than the Account 1 balance meaning the transfer went through. Account 1 balance: " + balanceAccount1 + ", Account 2 balance: " + balanceAccount2 + ", Transfer: " + transferAmount + ", Return value expected: " + balanceExpected1 + ".");
        assertEquals(balanceExpected1, account1.getBalance(),
                METHOD_NAME + " with a negative transfer amount should not change the balance for the 'from' account. Account 1 balance: " + balanceAccount1 + ", Account 2 balance: " + balanceAccount2 + ", Transfer: " + transferAmount + ", Account 1 balance expected: " + balanceExpected1 + ".");
        assertEquals(balanceExpected2, account2.getBalance(),
                METHOD_NAME + " with a negative transfer amount should not change the balance for the 'to' account. Account 1 balance: " + balanceAccount1 + ", Account 2 balance: " + balanceAccount2 + ", Transfer: " + transferAmount + ", Account 2 balance expected: " + balanceExpected2 + ".");

        // transfer exceeds balance
        balanceAccount1 = 150;
        balanceAccount2 = 100;
        transferAmount = 999999;
        balanceExpected1 = balanceAccount1; // no change
        balanceExpected2 = balanceAccount2; // no change
        account1 = new BankAccount("BNK:1234", balanceAccount1);
        account2 = new BankAccount("BNK:5678", balanceAccount2);

        returnValue = transferFunds.invoke(account1, account2, transferAmount);
        assertEquals(balanceExpected1, returnValue,
                METHOD_NAME + " should not allow a transfer if amount exceeds balance. Account 1 balance: " + balanceAccount1 + ", Account 2 balance: " + balanceAccount2 + ", Transfer: " + transferAmount + ", Return value expected: " + balanceExpected1 + ".");
        assertEquals(balanceExpected1, account1.getBalance(),
                METHOD_NAME + " should not change the balance for the 'from' account if amount exceeds balance. Account 1 balance: " + balanceAccount1 + ", Account 2 balance: " + balanceAccount2 + ", Transfer: " + transferAmount + ", Account 1 balance expected: " + balanceExpected1 + ".");
        assertEquals(balanceExpected2, account2.getBalance(),
                METHOD_NAME + " should not change the balance for the 'to' account if amount exceeds balance. Account 1 balance: " + balanceAccount1 + ", Account 2 balance: " + balanceAccount2 + ", Transfer: " + transferAmount + ", Account 2 balance expected: " + balanceExpected2 + ".");
    }

    private String methodWellFormedCheck() {
        Method bankAccountTransferFunds = SafeReflection.getMethod(bankAccountClass, METHOD_NAME, bankAccountClass, int.class);
        Method checkingAccountTransferFunds = SafeReflection.getMethod(checkingAccountClass, METHOD_NAME, bankAccountClass, int.class);
        Method savingsAccountTransferFunds = SafeReflection.getMethod(savingsAccountClass, METHOD_NAME, bankAccountClass, int.class);

        // Assert method exists
        if (bankAccountTransferFunds == null) {
            return METHOD_NAME + " not found in " + BANK_ACCOUNT_CLASS_NAME +
                    ". Have you declared it yet? Make sure the method name is '" + METHOD_NAME +
                    "', that it is public, and that it accepts a " + BANK_ACCOUNT_CLASS_NAME + " and an int.";
        }
        if (checkingAccountTransferFunds == null) {
            return METHOD_NAME + " not found in " + CHECKING_ACCOUNT_CLASS_NAME +
                    ". Have you declared it yet? Make sure the method name is '" + METHOD_NAME +
                    "', that it is public, and that it accepts a " + BANK_ACCOUNT_CLASS_NAME + " and an int.";
        }
        if (savingsAccountTransferFunds == null) {
            return METHOD_NAME + " not found in " + SAVINGS_ACCOUNT_CLASS_NAME +
                    ". Have you declared it yet? Make sure the method name is '" + METHOD_NAME +
                    "', that it is public, and that it accepts a " + BANK_ACCOUNT_CLASS_NAME + " and an int.";
        }

        // Assert methods are of correct type and access
        String methodCheck = ReflectionTestHelper.checkMethod(bankAccountClass, METHOD_NAME, int.class, true, new Class[]{bankAccountClass, int.class});
        if (!methodCheck.isEmpty()) {
            return methodCheck;
        }

        methodCheck = ReflectionTestHelper.checkMethod(checkingAccountClass, METHOD_NAME, int.class, true, new Class[]{bankAccountClass, int.class});
        if (!methodCheck.isEmpty()) {
            return methodCheck;
        }

        methodCheck = ReflectionTestHelper.checkMethod(savingsAccountClass, METHOD_NAME, int.class, true, new Class[]{bankAccountClass, int.class});
        if (!methodCheck.isEmpty()) {
            return methodCheck;
        }

        // Assert method in correct class and inherited
        if (!checkingAccountTransferFunds.getDeclaringClass().getSimpleName().equals(BANK_ACCOUNT_CLASS_NAME)) {
            return CHECKING_ACCOUNT_CLASS_NAME + " must not declare " + METHOD_NAME +
                    " method, it should inherit from the " + BANK_ACCOUNT_CLASS_NAME + " class.";
        }
        if (!savingsAccountTransferFunds.getDeclaringClass().getSimpleName().equals(BANK_ACCOUNT_CLASS_NAME)) {
            return SAVINGS_ACCOUNT_CLASS_NAME + " must not declare " + METHOD_NAME +
                    " method, it should inherit from the " + BANK_ACCOUNT_CLASS_NAME + " class.";
        }

        return "";
    }
}
