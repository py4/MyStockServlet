package ir.Epy.MyStock.models;

import ir.Epy.MyStock.DAOs.GTCDAO;
import ir.Epy.MyStock.DAOs.StockDAO;
import ir.Epy.MyStock.exceptions.CustomerNotFoundException;
import ir.Epy.MyStock.exceptions.StockNotFoundException;

import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * Created customer_id py4_ on 2/17/16.
 */

public abstract class StockRequest implements Comparable<StockRequest> {
    public int id;
    public String customer_id;
    public String type; //@// TODO: 2/18/16 change it to enum
    public String stock_symbol;
    public int base_price;
    public int quantity;
    public boolean is_buy;
    public int status; /* 0: pending, 1: accepted, 2: reject */



    public StockRequest() {
    }

    public StockRequest(Integer id, String customer_id, String stock_symbol, int base_price, int quantity, String type, Boolean is_buy) {
        this.id = id;
        this.customer_id = customer_id;
        this.stock_symbol = stock_symbol;
        this.base_price = base_price;
        this.quantity = quantity;
        this.type = type;
        this.is_buy = is_buy;
    }
    public StockRequest(Integer id, String customer_id, String stock_symbol, int base_price, int quantity, String type, Boolean is_buy, int status) {
        this.id = id;
        this.customer_id = customer_id;
        this.stock_symbol = stock_symbol;
        this.base_price = base_price;
        this.quantity = quantity;
        this.type = type;
        this.is_buy = is_buy;
        this.status = status;
    }

    public Stock get_stock() throws StockNotFoundException, SQLException {
        return StockDAO.I().find(stock_symbol);
    }

    public static StockRequest create_request(String customer_id, String stock_symbol, int base_price, int quantity, String type, Boolean is_buy) throws SQLException {
        if(type.equals("GTC"))
            return GTCDAO.I().create(customer_id, stock_symbol, base_price, quantity, type, is_buy);
        else if(type.equals("IOC"))
            return new IOCRequest(customer_id, stock_symbol, base_price, quantity, type, is_buy);
        else if(type.equals("MPO"))
            return new MPORequest(customer_id, stock_symbol, base_price, quantity, type, is_buy);
        return null;
    }

    protected PriorityQueue<StockRequest> get_opposite_queue() throws SQLException {
        try {
            if(is_buy)
                return StockDAO.I().find(stock_symbol).getSellRequests();
            else return StockDAO.I().find(stock_symbol).getBuyRequests();
        } catch (StockNotFoundException e) {
            e.printStackTrace();
        }
        return null;
        //            result.addAll(GTCDAO.I().getSellRequests(stock_symbol));
        //            result.addAll(GTCDAO.I().getBuyRequests(stock_symbol));
    }

    protected Boolean price_satisfiable(StockRequest req) {
        if(is_buy && !req.is_buy)
            return base_price >= req.base_price;
        else if(!is_buy && req.is_buy)
            return base_price <= req.base_price;
        return false;
    }

    @Override
    public int compareTo(StockRequest stockRequest) {
        return base_price - stockRequest.base_price;
    }


    public abstract void process(PrintWriter out) throws CustomerNotFoundException, SQLException, StockNotFoundException;

    protected void log_transaction(Customer buyer, Customer seller, int quantity) {
            //@TODO log transaction
//        Database.get_obj().log_stock_transaction(new StockTransactionLog(
//                buyer.id, seller.id, stock_symbol, type , quantity, buyer.getDeposit(), seller.getDeposit()
//        ));
    }

    public int getId() {return  id;}
    public int getQuantity() {
        return quantity;
    }

    public int getBase_price() {
        return base_price;
    }

    public String getType() {
        return type;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public HashMap<String,String> getReport() {
        return new HashMap<String,String>();
    }
}
