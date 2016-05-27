package ir.Epy.MyStock.models;

/**
 * Created customer_id py4_ on 5/19/16.
 */
public class StockShare {
    public String customer_id;
    public String stock_symbol;
    public Integer quantity;

    public StockShare(String customer_id, String stock_symbol, Integer quantity) {
        this.customer_id = customer_id;
        this.stock_symbol = stock_symbol;
        this.quantity = quantity;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public String getStock_symbol() {
        return stock_symbol;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
