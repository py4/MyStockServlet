package ir.Epy.MyStock.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by esihaj on 4/8/16.
 */
public class StockTransactionLog {
    public String buyer, seller;
    private String symbol;
    private String trade_type;
    private int quantity;
    private int buyer_remained_money, seller_curr_money;

    public StockTransactionLog(String buyer, String seller, String symbol, String trade_type, int quantity, int buyer_remained_money, int seller_curr_money) {
        this.buyer = buyer;
        this.seller = seller;
        this.symbol = symbol;
        this.trade_type = trade_type;
        this.quantity = quantity;
        this.buyer_remained_money = buyer_remained_money;
        this.seller_curr_money = seller_curr_money;
    }

    public String[] getRecord() {
        List<String> record = new ArrayList<>();
        record.add(buyer);
        record.add(seller);
        record.add(symbol);
        record.add(trade_type);
        record.add("" + quantity);
        record.add("" + buyer_remained_money);
        record.add("" + seller_curr_money);

        String[] log_arr = new String[record.size()];
        log_arr= record.toArray(log_arr);
        return log_arr;
    }
}
