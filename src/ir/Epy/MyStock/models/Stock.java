package ir.Epy.MyStock.models;

import java.util.Collections;
import java.util.PriorityQueue;

/**
 * Created by py4_ on 2/17/16.
 */
public class Stock {
    private String symbol;
    PriorityQueue<StockRequest> sell_requests = new PriorityQueue<>(2);
    PriorityQueue<StockRequest> buy_requests = new PriorityQueue<>(2, Collections.<StockRequest>reverseOrder());

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


}
