package ir.Epy.MyStock.models;

import ir.Epy.MyStock.DAOs.CustomerDAO;
import ir.Epy.MyStock.DAOs.GTCDAO;
import ir.Epy.MyStock.Database;
import ir.Epy.MyStock.Constants;
import ir.Epy.MyStock.exceptions.CustomerNotFoundException;
import ir.Epy.MyStock.exceptions.StockNotFoundException;

import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.PriorityQueue;

import static ir.Epy.MyStock.Constants.IOC_ID;

/**
 * Created customer_id py4_ on 2/24/16.
 */
public class IOCRequest extends ir.Epy.MyStock.models.StockRequest {
    public IOCRequest(String customer_id, String stock_symbol, int base_price, int quantity, String type, Boolean is_buy) {
        super(IOC_ID, customer_id, stock_symbol,base_price,quantity,type,is_buy);
    }

    @Override
    public void process(PrintWriter out) throws CustomerNotFoundException, SQLException, StockNotFoundException {

        if(!IOC_satisfiable()) {
            out.write(Constants.OrderDeclinedMessage);
            return;
        }

        Stock stock = get_stock();

        PriorityQueue<StockRequest> sell_requests = stock.getSellRequests();
        PriorityQueue<StockRequest> buy_requests = stock.getBuyRequests();

        while(true) {
            StockRequest opposite_head = get_opposite_queue().poll();
            GTCDAO.I().delete(opposite_head.id);

            Customer buyer;
            Customer seller;
            int buy_price = !opposite_head.is_buy ? base_price : opposite_head.base_price;

            int diff = Math.min(quantity, opposite_head.quantity);

            if(!opposite_head.is_buy) {
                buyer = CustomerDAO.I().find(customer_id);
                seller = CustomerDAO.I().find(opposite_head.customer_id);
                buyer.decrease_deposit(buy_price * diff);
            } else {
                buyer = CustomerDAO.I().find(opposite_head.customer_id);
                seller = CustomerDAO.I().find(customer_id);
                seller.decrease_share(stock.get_symbol(), diff);
            }

            buyer.increase_share(stock.get_symbol(), diff);
            seller.increase_deposit(buy_price * diff);

            out.write(seller.id + " sold " + diff + " shares of " + stock.get_symbol() + " @" + buy_price + " to " + buyer.id+"\n");
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

    private Boolean IOC_satisfiable() throws SQLException {
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
