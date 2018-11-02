# Bank System

x8academy: Core Java course, Final project

## Functionalities

### signUp

Registers new user in the system

```
* username: <username>
* password: <password>
* repeat password: <password>
* email: <email>
* status: <user_status>
```

### signIn

Logs a registered user in the system

```
* username: <username>
* password: <password>
```

### deleteMyProfile

Deletes current user's profile

### show

Displays current user's personal details

```
* username: <username>
```

### writeMessage

Allows the current user  to write another user a message

```
* username: <username>
* message: <message>
```

### seeAllNewMessages

Displays to the current user all messages from other users

### logout

Logs user out of the system

### createBankAccount

Allows the current user to create a new bank account

```
* type: <account_type>
* IBAN: <iban>
* money: <initial_money>
```

### listAccounts

Displays all bank accounts, belonging to the current user

### transferMoney

Allows the current user to transfer money from one of his accounts to another account

```
* IBAN: <iban_from>
* IBAN: <iban_to>
* money: <money>
```

### cashIn

Allows a registered and unregistered user to transfer money into an account

```
* money: <money>
* IBAN: <iban>
```

### withdraw

Allows the current user to take money from one of his accounts

```
* IBAN: <iban>
* money: <money>
```

### set

Allows an admin or a clerk to change an account's interest rate

```
* account: <iban>
* interest: <interest_rate>
```

### calculateBestSavings

Displays the money the current user can save and put into a savings account based on the incomes and outcomes in the previous period (if there's one, otherwise the current) and the money now in his accounts

### movePeriod

Allows an admin or a clerk to move allow accounts in the system to the next period

## Specifics

### User status

* CLIENT
* CLERK
* ADMIN

### Password

Passwords are required to have minimum 8 characters, of which at least 1 lowercase letter, 1 uppercase letter and 1 digit

### IBAN

IBANs are required to have the following format: BG%%****%%%%%%%%%%%%%%, where % - digit (16), * - capital letter (4)

### Account type

* checkingAccount
* savingsAccount
* certificateOfDeposit
* moneyMarketAccount
* traditionalIRA
* rothIRA

## Files

* users.txt - collects users persional information in the system
* passwords.txt - collects users passwords (for user testing purposes)
* accounts.txt - collects all users accounts information in the system 