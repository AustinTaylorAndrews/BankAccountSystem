public class Account 
{
    private UserInputService UserInputService;
    private FileService FileService;

    /**
     * Account constructor: Instantiates required services
     */
    public Account()
    {
        UserInputService = new UserInputService();
        FileService = new FileService();
    }

    /**
     * Deposit to the account. Prompts user for a deposit amount and adds a record of the transaction.
     */
    public void Deposit()
    {
        var depositAmount = UserInputService.PromptUserForInput(PromptEnum.Deposit);
        FileService.AddTransaction(depositAmount);
    }

    /**
     * Withdraw from the account. Prompts user for a withdrawal amount and adds a record of the transaction.
     */
    public void Withdraw()
    {
        var withdrawAmount = UserInputService.PromptUserForInput(PromptEnum.Withdraw);
        FileService.AddTransaction("-" + withdrawAmount);
    }

    /**
     * Determine the balance of the account. Displays the current account balance to the user.
     */
    public void Balance()
    {
        var balance = ReadBalanceFromFile();
        var message = "The current balance is: ";
        var balanceSign = balance < 0 ? "-" : "";
        message += String.format("%s$%.2f", balanceSign, Math.abs(balance));
        System.out.println(message);
    }

    /**
     * Retrieves a list of all transactions for the account and aggregates them into an account balance.
     * @return The sum of all account transactions
     */
    private double ReadBalanceFromFile()
    {
        var transactions = FileService.ReadAllTransactions();
        var balance = transactions
            .stream()
            .mapToDouble(d -> d)
            .sum();
        return balance;
    }
}