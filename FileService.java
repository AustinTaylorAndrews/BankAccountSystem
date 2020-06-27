import java.io.*;
import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class FileService 
{
    private final String FileName = "log.html";
    private String FilePath = "./" + FileName;
    private File File;

    /**
     * FileService constructor: Instantiates file
     */
    public FileService()
    {
        File = new File(FilePath);
    }

    /**
     * Reads all transactions from the file.
     * @return A collection of transactions. If an error occurs, an empty collection is returned.
     */
    public ArrayList<Double> ReadAllTransactions()
    {
        var transactions = new ArrayList<Double>();
        try 
        {
            var document = Jsoup.parse(File, "UTF-8");
            var tableElements = SelectTable(document)
                .select("tr")
                .select("td");
            
            for (Element td : tableElements)
            {
                var transactionText = td.text();
                var transaction = Double.parseDouble(transactionText);
                transactions.add(transaction);
            }
        } catch (Exception exception)
        {
            System.out.println("Unexpected error occured while reading transactions");
        }
        return transactions;
    }

    /**
     * Adds a new transaction to the file. On error, notifies the user that the transaction may have not been saved
     * @param transactionAmount Net monetary value of the new transaction
     */
    public void AddTransaction(String transactionAmount)
    {
        try 
        {
            var document = Jsoup.parse(File, "UTF-8");
            var tableBody = SelectTable(document).select("tbody");
            tableBody.append(String.format("<tr><td>%s</td></tr>", transactionAmount));

            var bufferedWriter = new BufferedWriter(new FileWriter(File));
            bufferedWriter.write(document.toString());
            bufferedWriter.close();
        } catch (Exception exception) 
        {
            System.out.println("Unexpected error occured while saving transaction. Transaction may not have been saved.");
        }
    }

    /**
     * Retrieves the transactions table from the given document
     * @param document Document to query for the transactions table
     * @return The html table element for the table of transactions
     */
    private Element SelectTable(Document document)
    {
        return document.getElementById("transactions");
    }
}