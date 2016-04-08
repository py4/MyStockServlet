package ir.Epy.MyStock.models;

import ir.Epy.MyStock.Constants;
import ir.Epy.MyStock.Database;
import ir.Epy.MyStock.exceptions.CustomerNotFoundException;

import java.io.PrintWriter;
import java.util.PriorityQueue;

/**
 * Created by py4_ on 2/24/16.
 */
public class MPORequest extends ir.Epy.MyStock.models.StockRequest {

    public MPORequest(String by, Stock stock, int base_price, int quantity, String type, Boolean is_buy) {
        super(by,stock,base_price,quantity,type,is_buy);
    }

    @Override
    public void process(PrintWriter out) throws CustomerNotFoundException {
        if(!MPO_satisfiable()) {
            out.write(Constants.OrderDeclinedMessage);
            return;
        }

        while(true) {
            StockRequest opposite_head = get_opposite_queue().poll();

            Customer buyer;
            Customer seller;

            int buy_price = opposite_head.base_price;
            int diff = Math.min(quantity, opposite_head.quantity);

            if(!opposite_head.is_buy) {
                buyer = Database.get_obj().get_customer(by);
                seller = Database.get_obj().get_customer(opposite_head.by);
                buyer.decrease_deposit(buy_price * diff);
            } else {
                buyer = Database.get_obj().get_customer(opposite_head.by);
                seller = Database.get_obj().get_customer(by);
                seller.decrease_share(stock.get_symbol(), diff);
            }

            buyer.increase_share(stock.get_symbol(), diff);
            seller.increase_deposit(buy_price * diff);

            out.write(seller.id + " sold " + diff + " shares of " + stock.get_symbol() + " @" + buy_price + " to " + buyer.id+"\n");
            log_transaction(buyer,seller, diff);

            quantity -= diff;
            opposite_head.quantity -= diff;
            if(opposite_head.quantity != 0)
                (opposite_head.is_buy ? stock.buy_requests : stock.sell_requests).add(opposite_head);
            if(quantity == 0)
                return;
        }
    }

    private boolean MPO_satisfiable() throws CustomerNotFoundException {
        PriorityQueue<StockRequest> backup = new PriorityQueue<>(get_opposite_queue());
        int backup_quantity = quantity;
        int price_sum = 0;
        while(true) {
            StockRequest head = backup.poll();
            if(head == null)
                return false;
            price_sum += head.base_price * head.quantity;
            if(backup_quantity <= head.quantity) {
                if(!is_buy)
                    return true;
                else
                    return Database.get_obj().get_customer(by).has_enough_deposit(price_sum);
            }
            backup_quantity -= head.quantity;
        }
    }
}
