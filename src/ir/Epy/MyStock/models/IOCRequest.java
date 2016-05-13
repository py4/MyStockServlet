package ir.Epy.MyStock.models;

import ir.Epy.MyStock.database.Database;
import ir.Epy.MyStock.Constants;
import ir.Epy.MyStock.exceptions.CustomerNotFoundException;

import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.PriorityQueue;

/**
 * Created by py4_ on 2/24/16.
 */
public class IOCRequest extends ir.Epy.MyStock.models.StockRequest {
    public IOCRequest(String by, Stock stock, int base_price, int quantity, String type, Boolean is_buy) {
        super(by,stock,base_price,quantity,type,is_buy);
    }

    @Override
    public void process(PrintWriter out) throws CustomerNotFoundException, SQLException {

        if(!IOC_satisfiable()) {
            out.write(Constants.OrderDeclinedMessage);
            return;
        }
        while(true) {
            StockRequest opposite_head = get_opposite_queue().poll();

            Customer buyer;
            Customer seller;
            int buy_price = !opposite_head.is_buy ? base_price : opposite_head.base_price;

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

    private Boolean IOC_satisfiable() {
        PriorityQueue<StockRequest> backup = new PriorityQueue<>(get_opposite_queue());
        int backup_quantity = quantity;
        while(true) {
            StockRequest head = backup.poll();
            if(head == null || !price_satisfiable(head))
                return false;
            if(backup_quantity <= head.quantity)
                return true;
            else
                backup_quantity -= head.quantity;
        }
    }
}
