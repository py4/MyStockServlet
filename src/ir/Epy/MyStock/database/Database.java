package ir.Epy.MyStock.database;
import com.opencsv.CSVWriter;
import ir.Epy.MyStock.Constants;
import ir.Epy.MyStock.exceptions.*;
import ir.Epy.MyStock.models.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by py4_ on 2/16/16.
 */
public class Database {

    private static Database obj;
//    HashMap<String, Customer> customers = new HashMap<>();
//    HashMap<String, Stock> stocks = new HashMap<>();
    ArrayList<StockTransactionLog> stock_transactions = new ArrayList<>();
    private Bank bank = new Bank();

    public final String CSV_FILE_NAME = "backup.csv";
    public static final String CONN_STR = "jdbc:hsqldb:hsql://localhost";

    static {
        try {
            System.err.println("HSQL initialization");
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
            obj = new Database();
        } catch (ClassNotFoundException ex) {
            System.err.println("Unable to load HSQLDB JDBC driver");
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Unable to Connect to DB ");
        }
    }



    private Database() throws  SQLException{
        //init_admin();
    }

    public static Database get_obj() {
        return obj;
    }


    public void add_customer(String id, String name, String family) throws SQLException, CustomerExistsException {
        try{
          get_customer(id);
          throw new CustomerExistsException();
        } catch (CustomerNotFoundException e) {
            System.out.println("[add customer] : '"+ id +"', '"+ name +"', '"+family+"'");
            Connection con = DriverManager.getConnection(CONN_STR);
            Statement st = con.createStatement();
//            System.out.println("query : "+ "INSERT INTO customer (c_id, name, family) VALUES ('"+ id +"', '"+ name +"', '"+family+"')");
            st.executeQuery("INSERT INTO customer (c_id, name, family) VALUES ('"+ id +"', '"+ name +"', '"+family+"')");
            con.close();
        }
    }

//    private void init_admin () throws SQLException{
//
//        Connection con = DriverManager.getConnection(CONN_STR);
//        Statement st = con.createStatement();
//        st.executeQuery("INSERT INTO customer (c_id, name, family, is_admin) VALUES ('1', 'admin', 'admin zadeh', 'true')");
//        con.close();
//    }

    public Customer get_customer(String id) throws SQLException, CustomerNotFoundException {
        Connection con = DriverManager.getConnection(CONN_STR);
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM customer c WHERE c.c_id='"+id+"'");
        if (rs.next()) {
            return new Customer(rs.getString("c_id"), rs.getString("name"), rs.getString("family"), rs.getInt("deposit"), shares);
        } else throw new CustomerNotFoundException();
    }

    public ArrayList<Customer> get_customers() throws SQLException{
        ArrayList<Customer> result = new ArrayList<Customer>();
        Connection con = DriverManager.getConnection(CONN_STR);
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM customer");
        while (rs.next()) {
            result.add(new Customer(rs.getString("c_id"), rs.getString("name"), rs.getString("family"), rs.getInt("deposit")));
        }
        return result;
    }

    public ArrayList<String> get_stock_symbols() throws SQLException {
        ArrayList<String> result = new ArrayList<String>();
        Connection con = DriverManager.getConnection(CONN_STR);
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM stocks");
        while (rs.next()) {
            result.add(rs.getString("symbol"));
        }
        return result;
    }

    //@// TODO: 2/17/16 How to check if a stock exists? admin should have sold it or it is done by a request to the server in the previous project?
    public Stock get_stock(String symbol) throws StockNotFoundException, SQLException {
        Connection con = DriverManager.getConnection(CONN_STR);
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM stocks s WHERE s.symbol='"+ symbol +"'");
        if (rs.next()) {
            return new Stock(rs.getString("symbol"));
        } else throw new StockNotFoundException();
    }

    public Stock add_stock(String symbol) throws SQLException, StockExistsException, StockNotFoundException {
        try{
            get_stock(symbol);
            throw new StockExistsException();
        } catch (StockNotFoundException e) {
            System.out.println("[add stock symbol] : '"+ symbol +"'");
            Connection con = DriverManager.getConnection(CONN_STR);
            Statement st = con.createStatement();
            st.executeQuery("INSERT INTO stocks ( symbol ) VALUES ('"+ symbol +"')");
            con.close();
            return get_stock(symbol);
        }
    }

    public Bank getBank() {
        return bank;
    }

    public Set<String> getStockSymbols() throws SQLException {
        return new HashSet(get_stock_symbols());
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
        } catch (SQLException e) {
            errors.add(Constants.DB_ERROR);
        } catch (StockExistsException e) {
            errors.add(Constants.SymbolExistsMessage);
        }catch (StockNotFoundException e) {
            errors.add(Constants.SymbolNotFoundMessage);
        }
        return errors;
    }

    private void buy(Customer customer, Stock stock, Integer price, Integer quantity, String type, PrintWriter msg) throws NotEnoughMoneyException, SQLException {
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

    private void sell(Customer customer, Stock stock, Integer price, Integer quantity, String type, PrintWriter msg) throws NotEnoughShareException, SQLException {
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
        //@TODO complex query
        ArrayList<HashMap<String,String>> result = new ArrayList<HashMap<String, String>>();

//        for(Stock stock : stocks.values()) {
//            result.addAll(stock.getBuyReport());
//            result.addAll(stock.getSellReport());
//        }
        return result;
    }
}
