package ir.Epy.MyStock.models;

import ir.Epy.MyStock.Constants;
import ir.Epy.MyStock.Database;
import ir.Epy.MyStock.exceptions.CustomerNotFoundException;
import java.io.PrintWriter;

/**
 * Created by py4_ on 2/24/16.
 */
public class GTCRequest extends ir.Epy.MyStock.models.StockRequest {
    public GTCRequest(String by, Stock stock, int base_price, int quantity, String type, Boolean is_buy) {
        super(by,stock,base_price,quantity,type,is_buy);
    }

    @Override
    public void process(PrintWriter out) throws CustomerNotFoundException {
        //@// TODO: 2/18/16 bug with order of entrance
        Boolean any_successful_trade = false;
        while(true) {
            StockRequest sell_head = stock.sell_requests.peek();
            StockRequest buy_head = stock.buy_requests.peek();

            if(sell_head == null || buy_head == null || buy_head.base_price < sell_head.base_price)
                break;

            sell_head = stock.sell_requests.poll();
            buy_head = stock.buy_requests.poll();
            any_successful_trade = true;
            Customer buyer = Database.get_obj().get_customer(buy_head.by);
            Customer seller = Database.get_obj().get_customer(sell_head.by);
            int diff = Math.min(sell_head.quantity, buy_head.quantity);
            seller.increase_deposit(buy_head.base_price * diff);
            buyer.increase_share(stock.get_symbol(), diff);
            out.write(seller.id + " sold " + diff + " shares of " + stock.get_symbol() + " @" + buy_head.base_price + " to " + buyer.id+"\n");
            log_transaction(buyer, seller, quantity);

            sell_head.quantity -= diff;
            buy_head.quantity -= diff;


            if(sell_head.quantity > 0)
                stock.sell_requests.add(sell_head);
            if(buy_head.quantity > 0)
                stock.buy_requests.add(buy_head);
        }

        if(!any_successful_trade)
            out.write(Constants.OrderIsQueuedMessage);
    }

    /*private void log_transaction(Customer buyer, Customer seller, Stock stock, int quantity) {
        Database.get_obj().log_stock_transaction(new StockTransactionLog(
                buyer.id, seller.id, stock.get_symbol(), "GTC", quantity, buyer.getDeposit(), seller.getDeposit()
                ));
    }*/
}
