package ir.Epy.MyStock.models;

import ir.Epy.MyStock.database.Database;
import ir.Epy.MyStock.exceptions.StockNotFoundException;

import java.io.PrintWriter;
import java.sql.SQLException;

/**
 * Created by py4_ on 2/18/16.
 */
public class RequestProcessor {
    private StockRequest req;
    private PrintWriter out;
    private Stock stock;

    public RequestProcessor(StockRequest req, PrintWriter out) {
        this.req = req;
        this.out = out;
        try {
            this.stock = Database.get_obj().get_stock(req.stock.get_symbol());
        } catch (StockNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    void process_IOC() {
    }

    void process_MPO() {
    }
}
