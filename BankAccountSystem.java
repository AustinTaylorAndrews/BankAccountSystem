class BankAccountSystem 
{
    /** 
     * Main
     * 
     * @author Austin Andrews; Austin.Taylor.Andrews@gmail.com
     * @param args Ignored
     */
    public static void main(String[] args)
    {
        Startup();
    }

    /**
     * Primary app cycle. Retrieves user input. 
     * Depending on input, either performs a deposit, withdrawal, or balance query.
     * 
     * @return None. Expects that lower level service performs shutdown.
     */
    private static void Startup()
    {
        var userInputService = new UserInputService();
        var account = new Account();
        while (true)
        {
            var command = userInputService.PromptUserForInput(PromptEnum.Menu);
            switch (command)
            {
                case "deposit":
                    account.Deposit();
                    break;
                case "withdraw":
                    account.Withdraw();
                    break;
                case "balance":
                    account.Balance();
                    break;
            }
        }
    }
}