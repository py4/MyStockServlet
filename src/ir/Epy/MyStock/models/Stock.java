package ir.Epy.MyStock.models;

import ir.Epy.MyStock.Constants;
import ir.Epy.MyStock.DAOs.GTCDAO;

import java.sql.SQLException;
import java.util.*;

/**
 * Created customer_id py4_ on 2/17/16.
 */
public class Stock {
    public String symbol;
    public int status;/* 0: pending, 1: accepted, 2: reject */
    public Stock() {}

    public Stock(String symbol) {
        this.symbol = symbol;
        this.status = 0;
    }

    public Stock(String symbol, int status) {
        this.symbol = symbol;
        this.status = status;
    }

    public String get_symbol() {
        return symbol;
    }

    public PriorityQueue<StockRequest> getBuyRequests() throws SQLException {
        PriorityQueue<StockRequest> result = new PriorityQueue<>(2, Collections.<StockRequest>reverseOrder());
        result.addAll(GTCDAO.I().getBuyRequests(symbol));
        return result;
    }

    public PriorityQueue<StockRequest> getSellRequests() throws SQLException {
        PriorityQueue<StockRequest> result = new PriorityQueue<>();
        result.addAll(GTCDAO.I().getSellRequests(symbol));
        return result;
    }

    public ArrayList<HashMap<String,String>> getSellReport() throws SQLException {
        ArrayList<HashMap<String,String>> result = new ArrayList<HashMap<String,String>>();
        for(StockRequest request : getSellRequests())
            result.add(request.getReport());
        return result;
    }

    public ArrayList<HashMap<String,String>> getBuyReport() throws SQLException {
        ArrayList<HashMap<String,String>> result = new ArrayList<HashMap<String,String>>();
        for(StockRequest request : getBuyRequests())
            result.add(request.getReport());
        return result;
    }

}
