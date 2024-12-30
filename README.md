# Banking Application

**Description:**

This Java application provides a user-friendly interface for managing bank accounts. It allows users to register, login, deposit, withdraw, transfer money, change their personal information, and more. The application connects to a MySQL database to store and retrieve user data.

**Prerequisites:**

- Java Development Kit (JDK) installed
- MySQL database with a table named "your-initial-database-table-name" having the following columns:

  - full_name (varchar(50))
  - password (int)
  - DateOfBirth (varchar(10))
  - money (decimal(12,2))
  - currency_type (char)
  - id (int)

**Database Setup:**

1. Create a MySQL database and a table with the specified columns.
2. Replace the placeholders "Your-initial-database-username," "Your-initial-database-password," and "Your-initial-database-URL" in the `DBConnectionHelper` class with your actual database credentials.
3. Replace all the "your-database-..." with the table name that you have in your database

**Installation:**

1. Clone this repository or download the ZIP file.
2. Compile the Java files using a Java compiler (e.g., `javac`).

**Usage:**

1. Run the `Main` class to start the application.
2. Click the "Register" button to create a new account or "Login" to access an existing one.
3. Once logged in, you can use the available features to manage your account.

**Features:**

- **Registration:** Users can create new accounts by providing their name, surname, date of birth, and password.
- **Login:** Users can log in to their accounts using their username and password.
- **Account Management:**
  - Deposit money
  - Withdraw money
  - Transfer money to other accounts
- **Settings:**
  - Change name
  - Change password
  - Change currency type
- **Password Recovery:** Users can reset their password if forgotten.

**Known Issues:**

- Password storage is not secure. Consider using a stronger hashing algorithm like bcrypt or scrypt.
- Input validation should be implemented to prevent invalid data entry.

**Future Enhancements:**

- Implement transaction history.
- Add support for multiple currencies.
- Integrate with online payment gateways.
- Improve security measures.

**Contributing:**

Contributions are welcome! Please feel free to fork the repository, make changes, and submit a pull request.
