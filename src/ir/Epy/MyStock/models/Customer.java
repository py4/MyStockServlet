package ir.Epy.MyStock.models;

import ir.Epy.MyStock.exceptions.NotEnoughMoneyException;

import java.util.HashMap;

/**
 * Created by py4_ on 2/16/16.
 */
public class Customer {
    public String id;
    private String name;
    private String family;
    private int deposit;
    private HashMap<String, Integer> shares = new HashMap<>();

    public Customer(String id, String name, String family) {
        this.id = id;
        this.name = name;
        this.family = family;
        this.deposit = 10000;
    }

    public Boolean is_admin() {
        return id.equals("1");
    }

    public Boolean has_enough_deposit(int deposit) {
        return this.deposit >= deposit;
    }

    public void set_deposit(int deposit) {
        this.deposit = deposit;
    }

    public void increase_deposit(int diff) {
        this.deposit += diff;
    }

    public void decrease_deposit(int diff) {
        this.deposit -= diff;
    }

    public void withdraw(int val) throws NotEnoughMoneyException {
        if(!has_enough_deposit(val))
            throw new NotEnoughMoneyException();
        this.deposit -= val;
    }

    private int get_share_count(String symbol) {
        Integer count = shares.get(symbol);
        if(count == null)
            return 0;
        return count;
    }

    public Boolean can_sell(String symbol, int requested_count) {
        if(is_admin())
            return true;
        return get_share_count(symbol) >= requested_count;
    }

    public Boolean can_buy(int count, int base_price) {
        return deposit >= count * base_price;
    }

    public void increase_share(String symbol, int count) {
        shares.put(symbol, get_share_count(symbol) + count);
    }

    public void decrease_share(String symbol, int count) {
        shares.put(symbol, get_share_count(symbol) - count);
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
