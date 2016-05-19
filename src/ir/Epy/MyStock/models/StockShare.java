package ir.Epy.MyStock.models;

/**
 * Created by py4_ on 5/19/16.
 */
public class StockShare {
    private String customer_id;
    private String stock_symbol;
    private Integer quantity;

    public StockShare(String customer_id, String stock_symbol, Integer quantity) {
        this.customer_id = customer_id;
        this.stock_symbol = stock_symbol;
        this.quantity = quantity;
    }



}
