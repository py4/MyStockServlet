package ir.Epy.MyStock;
import com.opencsv.CSVWriter;
import ir.Epy.MyStock.DAOs.CustomerDAO;
import ir.Epy.MyStock.exceptions.*;
import ir.Epy.MyStock.models.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * Created customer_id py4_ on 2/16/16.
 */
public class Database {

    private static Database obj = new Database();
    HashMap<String, Customer> customers = new HashMap<>();
    HashMap<String, Stock> stocks = new HashMap<>();
    ArrayList<StockTransactionLog> stock_transactions = new ArrayList<>();
    public final String CSV_FILE_NAME = "backup.csv";


    private Database() {
        try {
            add_customer("1", "admin", "admin zadegan");
        } catch (CustomerAlreadyExistsException e) {
            e.printStackTrace();
        }
    }

    public static Database get_obj() {
        return obj;
    }


    public void add_customer(String id, String name, String family) throws CustomerAlreadyExistsException {
        if(customers.get(id) != null)
            throw new CustomerAlreadyExistsException();
        customers.put(id, new Customer(id, name, family, 1000));
    }

    public void add_customer(String id, String name, String family, Integer deposit) throws CustomerAlreadyExistsException {
        if(customers.get(id) != null)
            throw new CustomerAlreadyExistsException();
        customers.put(id, new Customer(id, name, family, deposit));
    }

    /*public Customer get_customer(String id) throws CustomerNotFoundException {
        Customer customer = customers.get(id);
        if(customer == null)
            throw new CustomerNotFoundException();
        return customer;
    }*/

    public ArrayList<Customer> get_customers() {
        ArrayList<Customer> result = new ArrayList<Customer>();
        for(Customer customer : customers.values())
            result.add(customer);
        return result;
    }

    public ArrayList<String> get_stock_symbols() {
        ArrayList<String> result = new ArrayList<String>();
        for(Stock stock : stocks.values())
            result.add(stock.get_symbol());
        return result;
    }

    //@// TODO: 2/17/16 How to check if a stock exists? admin should have sold it or it is done customer_id a request to the server in the previous project?
    public Stock get_stock(String symbol) throws StockNotFoundException {
        Stock stock = stocks.get(symbol);
        if(stock == null)
            throw new StockNotFoundException();
        return stock;
    }

    public Stock add_stock(String symbol) {
        Stock result = new Stock(symbol);
        stocks.put(symbol, result);
        return result;
    }

    public Set<String> getStockSymbols() {
        return stocks.keySet();
    }

    public void log_stock_transaction(StockTransactionLog s_log)
    {
        stock_transactions.add(s_log);
    }

    public void log_transactions_csv(StringWriter writer) throws IOException {
        final String[] FILE_HEADER = {"Buyer", "Seller", "instrument", "type of trade", "quantity", "Buyer Remained Money", "Seller Current Money"};
        CSVWriter csv_printer = new CSVWriter(writer);

        csv_printer.writeNext(FILE_HEADER);

        System.out.println("log size = " + stock_transactions.size());
        for (StockTransactionLog s_log : stock_transactions) {
            csv_printer.writeNext(s_log.getRecord());
        }
        System.out.println("logged transactions to " + CSV_FILE_NAME);
        csv_printer.flush();
        csv_printer.close();
    }

    public ArrayList<HashMap<String,String>> getReport() throws SQLException {
        ArrayList<HashMap<String,String>> result = new ArrayList<HashMap<String, String>>();
        for(Stock stock : stocks.values()) {
            result.addAll(stock.getBuyReport());
            result.addAll(stock.getSellReport());
        }
        return result;
    }
}
