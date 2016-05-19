package ir.Epy.MyStock;
import com.opencsv.CSVWriter;
import ir.Epy.MyStock.exceptions.*;
import ir.Epy.MyStock.models.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by py4_ on 2/16/16.
 */
public class Database {

    private static Database obj = new Database();
    HashMap<String, Customer> customers = new HashMap<>();
    HashMap<String, Stock> stocks = new HashMap<>();
    ArrayList<StockTransactionLog> stock_transactions = new ArrayList<>();
    public final String CSV_FILE_NAME = "backup.csv";

    private Bank bank = new Bank();

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

    public Customer get_customer(String id) throws CustomerNotFoundException {
        Customer customer = customers.get(id);
        if(customer == null)
            throw new CustomerNotFoundException();
        return customer;
    }

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

    //@// TODO: 2/17/16 How to check if a stock exists? admin should have sold it or it is done by a request to the server in the previous project?
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

    public Bank getBank() {
        return bank;
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

    public ArrayList<String> addRequest(String id, String symbol, int price, int quantity, String type, String buy_or_sell, PrintWriter msg) {
        ArrayList<String> errors = new ArrayList<String>();
        Stock stock = null;
        try {
            Customer customer = Database.get_obj().get_customer(id);
            try {
                stock = Database.get_obj().get_stock(symbol);
                if (buy_or_sell.equals("buy"))
                    buy(customer, stock, price, quantity, type, msg);
                else
                    sell(customer, stock, price, quantity, type, msg);
            } catch (StockNotFoundException e) {
                if(customer.is_admin() && buy_or_sell.equals("sell")) {
                    stock = Database.get_obj().add_stock(symbol);
                    StockRequest req = StockRequest.create_request(id, stock, price, quantity,type,false);
                    customer.increase_share(symbol, 0);
                    if(type.equals("GTC"))
                        stock.add_sell_req(req);
                    req.process(msg);
                } else
                    errors.add(Constants.SymbolNotFoundMessage);
            }
        } catch (CustomerNotFoundException e) {
            errors.add(Constants.CustomerNotFoundMessage);
        } catch (NotEnoughMoneyException e) {
            errors.add(Constants.NotEnoughMoneyMessage);
        } catch (NotEnoughShareException e) {
            errors.add(Constants.NotEnoughShareMessage);
        }
        return errors;
    }

    private void buy(Customer customer, Stock stock, Integer price, Integer quantity, String type, PrintWriter msg) throws NotEnoughMoneyException {
        String symbol = stock.get_symbol();
        StockRequest req = StockRequest.create_request(customer.id, stock, price, quantity, type, true);
        if(type.equals("GTC")) {
            if (!customer.can_buy(quantity, price))
                throw new NotEnoughMoneyException();
            customer.decrease_deposit(price * quantity);
            stock.add_buy_req(req);
        }
        try {
            req.process(msg);
        } catch (CustomerNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void sell(Customer customer, Stock stock, Integer price, Integer quantity, String type, PrintWriter msg) throws NotEnoughShareException {
        String symbol = stock.get_symbol();
        if (!customer.can_sell(symbol, quantity))
            throw new NotEnoughShareException();

        StockRequest req = StockRequest.create_request(customer.id, stock, price, quantity,type,false);
        if(type.equals("GTC")) {
            customer.decrease_share(symbol, quantity);
            stock.add_sell_req(req);
        }
        try {
            req.process(msg);
        } catch (CustomerNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<HashMap<String,String>> getReport() {
        ArrayList<HashMap<String,String>> result = new ArrayList<HashMap<String, String>>();
        for(Stock stock : stocks.values()) {
            result.addAll(stock.getBuyReport());
            result.addAll(stock.getSellReport());
        }
        return result;
    }
}
