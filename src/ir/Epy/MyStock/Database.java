package ir.Epy.MyStock;
;
import ir.Epy.MyStock.exceptions.CustomerExistsException;
import ir.Epy.MyStock.exceptions.CustomerNotFoundException;
import ir.Epy.MyStock.exceptions.StockNotFoundException;
import ir.Epy.MyStock.models.Customer;
import ir.Epy.MyStock.models.Stock;

import java.util.HashMap;

/**
 * Created by py4_ on 2/16/16.
 */
public class Database {

    private static Database obj = new Database();
    HashMap<String, Customer> customers = new HashMap<>();
    HashMap<String, Stock> stocks = new HashMap<>();

    private Database() {
        try {
            add_customer("1", "admin", "admin zadegan");
        } catch (CustomerExistsException e) {
            e.printStackTrace();
        }
    }

    public static Database get_obj() {
        return obj;
    }


    public void add_customer(String id, String name, String family) throws CustomerExistsException {
        if(customers.get(id) != null)
            throw new CustomerExistsException();
        customers.put(id, new Customer(id, name, family));
    }

    public Customer get_customer(String id) throws CustomerNotFoundException {
        Customer customer = customers.get(id);
        if(customer == null)
            throw new CustomerNotFoundException();
        return customer;
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


}
