package ir.Epy.MyStock.models;

import java.lang.reflect.Array;
import java.util.*;

/**
 * Created by py4_ on 2/17/16.
 */
public class Stock {
    private String symbol;
    PriorityQueue<StockRequest> sell_requests = new PriorityQueue<>(2);//@TODO lazy loading
    PriorityQueue<StockRequest> buy_requests = new PriorityQueue<>(2, Collections.<StockRequest>reverseOrder());//@TODO lazy loading

    public Stock() {
    }

    public Stock(String symbol) {
        this.symbol = symbol;
    }

    public void add_sell_req(StockRequest req) {
        sell_requests.add(req);
    }

    public void add_buy_req(StockRequest req) {
        buy_requests.add(req);
    }

    public void log() {
    }

    public String get_symbol() {
        return symbol;
    }

    public ArrayList<StockRequest> getBuy_requests() {
        return new ArrayList<>(buy_requests);
    }

    public ArrayList<StockRequest> getSell_requests() {
        return new ArrayList<>(sell_requests);
    }

    public ArrayList<HashMap<String,String>> getSellReport() {
        ArrayList<HashMap<String,String>> result = new ArrayList<HashMap<String,String>>();
        for(StockRequest request : sell_requests)
            result.add(request.getReport());
        return result;
    }

    public ArrayList<HashMap<String,String>> getBuyReport() {
        ArrayList<HashMap<String,String>> result = new ArrayList<HashMap<String,String>>();
        for(StockRequest request : buy_requests)
            result.add(request.getReport());
        return result;
    }

}
