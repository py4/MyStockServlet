package ir.Epy.MyStock.models;

import ir.Epy.MyStock.Constants;
import ir.Epy.MyStock.DAOs.CustomerDAO;
import ir.Epy.MyStock.DAOs.GTCDAO;
import ir.Epy.MyStock.exceptions.CustomerNotFoundException;

import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.PriorityQueue;

import static ir.Epy.MyStock.Constants.MPO_ID;

/**
 * Created customer_id py4_ on 2/24/16.
 */
public class MPORequest extends ir.Epy.MyStock.models.StockRequest {

    public MPORequest(String customer_id, String stock_symbol, int base_price, int quantity, String type, Boolean is_buy) {
        super(MPO_ID, customer_id, stock_symbol,base_price,quantity,type,is_buy, Constants.AcceptStatus);
    }

    @Override
    public void process(PrintWriter out) throws CustomerNotFoundException, SQLException {
        if(!MPO_satisfiable()) {
            System.out.println(Constants.OrderDeclinedMessage);
            out.write(Constants.OrderDeclinedMessage);
            return;
        }

        while(true) {
            StockRequest opposite_head = get_opposite_queue().poll();
            GTCDAO.I().delete(opposite_head.id);
            System.out.println("buy head " + " id " + opposite_head.customer_id + " price " + opposite_head.base_price + " count " + opposite_head.quantity);

            Customer buyer;
            Customer seller;

            int buy_price = opposite_head.base_price;
            int diff = Math.min(quantity, opposite_head.quantity);

            if(!opposite_head.is_buy) {
                buyer = CustomerDAO.I().find(customer_id);
                seller = CustomerDAO.I().find(opposite_head.customer_id);
                buyer.decrease_deposit(buy_price * diff);
            } else {
                buyer = CustomerDAO.I().find(opposite_head.customer_id);
                seller = CustomerDAO.I().find(customer_id);
                seller.decrease_share(stock_symbol, diff);
            }

            buyer.increase_share(stock_symbol, diff);
            seller.increase_deposit(buy_price * diff);

            out.write(seller.id + " sold " + diff + " shares of " + stock_symbol + " @" + buy_price + " to " + buyer.id+"\n");
            log_transaction(buyer,seller, diff);

            quantity -= diff;
            opposite_head.quantity -= diff;
            if(opposite_head.quantity != 0) {
                //(opposite_head.is_buy ? stock.buy_requests : stock.sell_requests).add(opposite_head);
                GTCDAO.I().updateOrCreate(opposite_head);
            }
            if(quantity == 0)
                return;
        }
    }

    private boolean MPO_satisfiable() throws CustomerNotFoundException, SQLException {
        PriorityQueue<StockRequest> backup = new PriorityQueue<>(get_opposite_queue());
        int backup_quantity = quantity;
        int price_sum = 0;
        while(true) {
            StockRequest head = backup.poll();
            if(head == null) {
            System.out.println("no opp head found!");
                return false;
            }

            System.out.println("buy head " + " id " + head.customer_id + " price " + head.base_price + " count " + head.quantity);
            price_sum += head.base_price * Math.min(head.quantity, backup_quantity);
            if(backup_quantity <= head.quantity) {
                if(!is_buy)
                    return true;
                else
                    return CustomerDAO.I().find(customer_id).has_enough_deposit(price_sum);
            }
            backup_quantity -= head.quantity;
        }
    }
}
