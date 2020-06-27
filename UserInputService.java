import java.io.*;
import java.util.ArrayList;

public class UserInputService 
{
    private ArrayList<String> ValidMenuOptions;
    private BufferedReader Console;

    /**
     * UserInputService constructor: Instantiates a list of valid commands & the console reader.
     */
    public UserInputService()
    {
        ValidMenuOptions = new ArrayList<String>();
        ValidMenuOptions.add("deposit");
        ValidMenuOptions.add("withdraw");
        ValidMenuOptions.add("balance");
        Console = new BufferedReader(new InputStreamReader(System.in));
    }

    /**
     * Prompts the user to input a command, checks validity of the command, and exits the program if directed to do so by the user.
     * 
     * NOTE: Will terminate the app if directed to exit by the user.
     * 
     * @param prompt The prompt that the user is to be given.
     * @return the user's response to the given prompt.
     */
    public String PromptUserForInput(PromptEnum prompt)
    {
        var waitingForValidInput = true;
        String input = null;
        while (waitingForValidInput)
        {
            PrintInputPrompt(prompt);
            input = ReadLine();
            
            if (input.equals("exit"))
            {
                Exit(0);
            }
            
            if (IsValidInput(prompt, input))
            {
                waitingForValidInput = false;
            }
            else 
            {
                System.out.println(String.format("Invalid Input '%s'", input));
            }
        }
        return input;
    }

    /**
     * Prints an input prompt based on the given prompt type.
     * @param prompt The type of prompt to be printed.
     */
    private void PrintInputPrompt(PromptEnum prompt)
    {
        switch (prompt)
        {
            case Menu:
                System.out.println("\nPlease enter in a command (Deposit, Withdraw, Balance, Exit): ");
                break;
            case Deposit:
                System.out.println("Please enter an amount to deposit: ");
                break;
            case Withdraw:
                System.out.println("Please enter an amount to withdraw: ");
                break;
            default:
                System.out.println("\nUnknown prompt type.");
                Exit(-1);
        }
    }

    /**
     * Reads a single line of input from the user, trims the input, and changes it to lower case.
     * @return Line of user input after being trimmed and formatted to lower case.
     */
    private String ReadLine()
    {
        String input = null;
        do 
        {
            try 
            {
                input = Console.readLine();
            }
            catch (Exception exception)
            { 
                System.out.println("Unexpected error occured while reading user input");
            }
        } while (input == null || input.trim() == "");

        return input.trim().toLowerCase();
    }

    /**
     * Determines whether the user's input meets the criteria of validity
     * 
     * @param prompt The prompt to validate input for
     * @param input The user input to validate
     * @return For the Menu prompt, returns true if input is a valid menu item. For other prompts, returns true if it is a valid monetary amount. Otherwise returns false.
     */
    private boolean IsValidInput(PromptEnum prompt, String input)
    {
        var isValid = false;
        if (prompt == PromptEnum.Menu)
        {
            isValid = ValidMenuOptions.contains(input);
        }
        else
        {
            isValid = IsMonetaryAmountValid(input);
        }
        return isValid;
    }

    /**
     * Determines if the input string is a valid monetary amount.
     * @param input
     * @return If the input is a positive number with 2 or fewer decimals returns true. Otherwise, false
     */
    private boolean IsMonetaryAmountValid(String input)
    {
        var isNumeric = true;
        try 
        {
            Double.parseDouble(input);
        } catch (NumberFormatException exception) {
            isNumeric = false;
        }

        var isPositiveNumber = input.indexOf('-') == -1;
        var isInteger = input.indexOf('.') == -1;
        var isLessThanTwoDecimals = 2 >= (input.length() - input.indexOf('.')) - 1;
        return isNumeric && isPositiveNumber && (isInteger || isLessThanTwoDecimals);
    }

    /**
     * Closes the current buffere reader.
     * Terminates the program.
     * @param exitCode The exit status
     */
    private void Exit(int exitCode)
    {
        try
        {
            Console.close();
        } catch (IOException exception)
        {
            System.out.println("Error occured when closing input buffer.");
        }
        System.out.println("Program shutting down. Thank you");
        System.exit(exitCode);
    }
}