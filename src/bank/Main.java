package bank;

import java.util.LinkedHashMap;
import java.util.Map;

import accounts.types.AccountType;
import accounts.types.AccountTypeFactory;
import accounts.types.CertificateOfDeposit;
import accounts.types.CheckingAccount;
import accounts.types.MoneyMarketAccount;
import accounts.types.RothIRA;
import accounts.types.SavingsAccount;
import accounts.types.TraditionalIRA;
import actions.Action;
import actions.ActionFactory;
import actions.account_actions.CalculateBestSavingsAction;
import actions.account_actions.CashInMoneyAction;
import actions.account_actions.CreateAccountAction;
import actions.account_actions.DisplayAccountsAction;
import actions.account_actions.MovePeriodAction;
import actions.account_actions.SetAccountInterestRateAction;
import actions.account_actions.TransferMoneyAction;
import actions.account_actions.WithdrawMoneyAction;
import actions.user_actions.SendMessageAction;
import actions.user_actions.ReadMessagesAction;
import actions.user_actions.GetUserAction;
import actions.user_actions.LoginAction;
import actions.user_actions.LogoutAction;
import actions.user_actions.RegisterAction;
import actions.user_actions.DeleteUserAction;
import users.Profile;

public class Main {

    public static void main(String[] args) {
        Profile userProfile = null;
        Map<String, Action> actionsList = new LinkedHashMap<>();
        actionsList.put("signUp", new RegisterAction(userProfile));
        actionsList.put("signIn", new LoginAction(userProfile));
        actionsList.put("deleteMyProfile", new DeleteUserAction(userProfile));
        actionsList.put("show", new GetUserAction(userProfile));
        actionsList.put("writeMessage", new SendMessageAction(userProfile));
        actionsList.put("seeAllNewMessages", new ReadMessagesAction(userProfile));
        actionsList.put("logout", new LogoutAction(userProfile));

        actionsList.put("createBankAccount", new CreateAccountAction(userProfile));
        actionsList.put("listAccounts", new DisplayAccountsAction(userProfile));
        actionsList.put("transferMoney", new TransferMoneyAction(userProfile));
        actionsList.put("cashIn", new CashInMoneyAction(userProfile));
        actionsList.put("withdraw", new WithdrawMoneyAction(userProfile));

        actionsList.put("set", new SetAccountInterestRateAction(userProfile));
        actionsList.put("calculateBestSavings", new CalculateBestSavingsAction(userProfile));
        actionsList.put("movePeriod", new MovePeriodAction(userProfile));

        InputScanner scanner = new InputScanner();
        ActionFactory actionFactory = new ActionFactory(actionsList, userProfile);

        Map<String, AccountType> accountTypesList = new LinkedHashMap<>();
        accountTypesList.put("checkingAccount", new CheckingAccount());
        accountTypesList.put("savingsAccount", new SavingsAccount());
        accountTypesList.put("certificateOfDeposit", new CertificateOfDeposit());
        accountTypesList.put("moneyMarketAccount", new MoneyMarketAccount());
        accountTypesList.put("traditionalIRA", new TraditionalIRA());
        accountTypesList.put("rothIRA", new RothIRA());

        AccountTypeFactory accountTypeFactory = new AccountTypeFactory(accountTypesList);

        String usersDatabase = "users.txt";
        String accountsDatabase = "accounts.txt";
        BankSystem bankSystem = new BankSystem(actionFactory, accountTypeFactory, usersDatabase, accountsDatabase);

        System.out.println("Welcome to Lili's bank system! (To leave, write: exit)");
        bankSystem.resolveAction(scanner);
        scanner.close();
        System.out.println("Goodbye! See you soon!");
    }

}
