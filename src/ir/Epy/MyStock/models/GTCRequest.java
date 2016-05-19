package ir.Epy.MyStock.models;

import ir.Epy.MyStock.Constants;
import ir.Epy.MyStock.DAOs.CustomerDAO;
import ir.Epy.MyStock.DAOs.GTCDAO;
import ir.Epy.MyStock.Database;
import ir.Epy.MyStock.exceptions.CustomerNotFoundException;
import ir.Epy.MyStock.exceptions.StockNotFoundException;

import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * Created customer_id py4_ on 2/24/16.
 */
public class GTCRequest extends ir.Epy.MyStock.models.StockRequest {

    public GTCRequest(Integer id, String customer_id, String stock_symbol, int base_price, int quantity, String type, Boolean is_buy) {
        super(id, customer_id,stock_symbol,base_price,quantity,type,is_buy);
    }

    @Override
    public void process(PrintWriter out) throws CustomerNotFoundException, SQLException, StockNotFoundException {
        Boolean any_successful_trade = false;
        Stock stock = get_stock();

        PriorityQueue<StockRequest> sell_requests = stock.getSellRequests();
        PriorityQueue<StockRequest> buy_requests = stock.getBuyRequests();

        while(true) {
            StockRequest sell_head = sell_requests.peek();
            StockRequest buy_head = buy_requests.peek();

            if(sell_head == null || buy_head == null || buy_head.base_price < sell_head.base_price)
                break;

            sell_head = sell_requests.poll();
            buy_head = buy_requests.poll();
            GTCDAO.I().delete(sell_head.id);
            GTCDAO.I().delete(buy_head.id);

            any_successful_trade = true;
            Customer buyer = CustomerDAO.I().find(buy_head.customer_id);
            Customer seller = CustomerDAO.I().find(sell_head.customer_id);
            int diff = Math.min(sell_head.quantity, buy_head.quantity);
            seller.increase_deposit(buy_head.base_price * diff);
            buyer.increase_share(stock.get_symbol(), diff);
            out.write(seller.id + " sold " + diff + " shares of " + stock.get_symbol() + " @" + buy_head.base_price + " to " + buyer.id+"\n");
            log_transaction(buyer, seller, quantity);

            sell_head.quantity -= diff;
            buy_head.quantity -= diff;


            if(sell_head.quantity > 0) {
                sell_requests.add(sell_head);
                GTCDAO.I().updateOrCreate(sell_head);
            }
            if(buy_head.quantity > 0) {
                buy_requests.add(buy_head);
                GTCDAO.I().updateOrCreate(buy_head);
            }
        }

        if(!any_successful_trade)
            out.write(Constants.OrderIsQueuedMessage);
    }

    public HashMap<String,String> getReport() {
        HashMap<String, String> result = new HashMap<String, String>();
        result.put("symbol", stock_symbol);
        result.put("customer_id", customer_id);
        result.put("basePrice", Integer.toString(base_price));
        result.put("quantity", Integer.toString(quantity));
        result.put("is_buy", (is_buy ? "true" : "false"));
        return result;
    }

    /*private void log_transaction(Customer buyer, Customer seller, Stock stock, int quantity) {
        Database.get_obj().log_stock_transaction(new StockTransactionLog(
                buyer.id, seller.id, stock.get_symbol(), "GTC", quantity, buyer.getDeposit(), seller.getDeposit()
                ));
    }*/
}
