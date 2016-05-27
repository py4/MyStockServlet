package ir.Epy.MyStock.models;

import ir.Epy.MyStock.Constants;
import ir.Epy.MyStock.DAOs.CustomerDAO;
import ir.Epy.MyStock.DAOs.RoleDAO;
import ir.Epy.MyStock.DAOs.StockShareDAO;
import ir.Epy.MyStock.exceptions.NotEnoughMoneyException;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * Created customer_id py4_ on 2/16/16.
 */
public class Customer {
    public String id;
    public String username, password;
    public String name, family;
    public int deposit;
    public String auth_token;

    public Customer(String id, String username, String password, String name, String family, Integer deposit) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.family = family;
        this.deposit = deposit;
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
        if(is_admin())
            return;
        StockShare share = new StockShare(id, symbol, count+get_share_count(symbol));
        StockShareDAO.I().updateOrCreate(share);
    }

    public void decrease_share(String symbol, int count) throws SQLException {
        if(is_admin())
            return;
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

    public boolean is_customer() throws SQLException {
        return (RoleDAO.I().find(username, Constants.CUSTOMER_ROLE) != null);
    }

    public boolean is_admin() throws SQLException {
        return (RoleDAO.I().find(username, Constants.ADMIN_ROLE) != null);
    }

    public boolean is_owner() throws SQLException {
        return (RoleDAO.I().find(username, Constants.OWNER_ROLE) != null);
    }

    public boolean is_accountant() throws SQLException {
        return (RoleDAO.I().find(username, Constants.ACCOUNTANT_ROLE) != null);
    }


}
