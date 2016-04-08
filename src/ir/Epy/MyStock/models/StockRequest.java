package ir.Epy.MyStock.models;

import ir.Epy.MyStock.Constants;
import ir.Epy.MyStock.Database;
import ir.Epy.MyStock.exceptions.CustomerNotFoundException;
import ir.Epy.MyStock.exceptions.HTTPException;

import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.PriorityQueue;

/**
 * Created by py4_ on 2/17/16.
 */

public abstract class StockRequest implements Comparable<StockRequest>{
    public String by;
    public String type; //@// TODO: 2/18/16 change it to enum
    public Stock stock;
    public int base_price;
    public int quantity;
    public boolean is_buy;

    public StockRequest() {
    }

    public StockRequest(String by, Stock stock, int base_price, int quantity, String type, Boolean is_buy) {
        this.by = by;
        this.stock = stock;
        this.base_price = base_price;
        this.quantity = quantity;
        this.type = type;
        this.is_buy = is_buy;
    }

    public static StockRequest create_request(String by, Stock stock, int base_price, int quantity, String type, Boolean is_buy) {
        /*try {
            System.out.println("looking for:  "+"ir.Epy.MyStock.models."+type+"Request");
            Class<?> klass = Class.forName("ir.Epy.MyStock.models."+type+"Request");
            Constructor<?> cons = klass.getConstructor(String.class,Stock.class,int.class,int.class,String.class,Boolean.class);
            return (StockRequest) cons.newInstance(by, stock, base_price, quantity, type, is_buy);
        } catch (ClassNotFoundException e) {
            throw new HTTPException(200, Constants.InvalidTypeMessage);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;*/
        if(type.equals("GTC"))
            return new GTCRequest(by, stock, base_price, quantity, type, is_buy);
        else if(type.equals("IOC"))
            return new IOCRequest(by, stock, base_price, quantity, type, is_buy);
        else if(type.equals("MPO"))
            return new MPORequest(by, stock, base_price, quantity, type, is_buy);
        return null;
    }
    protected PriorityQueue<StockRequest> get_opposite_queue() {
        if(is_buy)
            return stock.sell_requests;
        return stock.buy_requests;
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


    public abstract void process(PrintWriter out) throws CustomerNotFoundException;

    protected void log_transaction(Customer buyer, Customer seller, int quantity) {
        Database.get_obj().log_stock_transaction(new StockTransactionLog(
                buyer.id, seller.id, stock.get_symbol(), type , quantity, buyer.getDeposit(), seller.getDeposit()
        ));
    }

    public int getQuantity() {
        return quantity;
    }

    public int getBase_price() {
        return base_price;
    }

    public String getType() {
        return type;
    }

    public String getBy() {
        return by;
    }
}
