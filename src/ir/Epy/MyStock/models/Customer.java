package ir.Epy.MyStock.models;

import ir.Epy.MyStock.DAOs.CustomerDAO;
import ir.Epy.MyStock.DAOs.StockShareDAO;
import ir.Epy.MyStock.exceptions.NotEnoughMoneyException;

import java.sql.SQLException;
import java.util.HashMap;

/**
 * Created customer_id py4_ on 2/16/16.
 */
public class Customer {
    public String id;
    public String name;
    public String family;
    public int deposit;

    public Customer(String id, String name, String family, Integer deposit) {
        this.id = id;
        this.name = name;
        this.family = family;
        this.deposit = deposit;
    }

    public Boolean is_admin() {
        return id.equals("1");
    }

    public Boolean has_enough_deposit(int deposit) {
        return this.deposit >= deposit;
    }

    public void increase_deposit(int diff) throws SQLException {
        this.deposit += diff;
        CustomerDAO.I().update(this);
    }

    public void decrease_deposit(int diff) throws SQLException {
        this.deposit -= diff;
        CustomerDAO.I().update(this);
    }

    public void withdraw(int val) throws NotEnoughMoneyException, SQLException {
        if(!has_enough_deposit(val))
            throw new NotEnoughMoneyException();
        this.deposit -= val;
        CustomerDAO.I().update(this);
    }

    private int get_share_count(String symbol) throws SQLException {
        StockShare share = StockShareDAO.I().find(id, symbol);
        if(share == null)
            return 0;
        return share.quantity;
    }

    public Boolean can_sell(String symbol, int requested_count) throws SQLException {
        if(is_admin())
            return true;
        return get_share_count(symbol) >= requested_count;
    }

    public Boolean can_buy(int count, int base_price) {
        return deposit >= count * base_price;
    }

    public void increase_share(String symbol, int count) throws SQLException {
        StockShare share = new StockShare(id, symbol, count+get_share_count(symbol));
        StockShareDAO.I().updateOrCreate(share);
    }

    public void decrease_share(String symbol, int count) throws SQLException {
        increase_share(symbol, -count);
    }

    public int getDeposit() {
        return deposit;
    }
    public void log() {
        System.out.println("id:  "+id);
        System.out.println("name:  "+name);
        System.out.println("family:  "+family);
        System.out.println("deposit:  "+deposit);
    }
}
