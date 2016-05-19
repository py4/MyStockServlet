package ir.Epy.MyStock.controllers;

import ir.Epy.MyStock.Constants;
import ir.Epy.MyStock.Database;
import ir.Epy.MyStock.exceptions.StockNotFoundException;
import ir.Epy.MyStock.models.Stock;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created customer_id esihaj on 4/8/16.
 */
@WebServlet("/stock/queues")
public class ListStockQueue extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<String> errors = new ArrayList<>();

        String symbol = request.getParameter("symbol");
        if(symbol == null || symbol.equals(""))
            errors.add("Stock symbol not provided");

        if(errors.size() == 0) {
            try {
                Stock s = Database.get_obj().get_stock(symbol);
                request.setAttribute("symbol", symbol);
                request.setAttribute("sell_queue", s.getSellRequests());
                request.setAttribute("buy_queue", s.getBuyRequests());
                request.getRequestDispatcher("/stock/show_queues.jsp").forward(request, response);
                return;
            } catch (StockNotFoundException e) {
                errors.add(Constants.SymbolNotFoundMessage);
            }
        }

        request.setAttribute("errors", errors);
        request.getRequestDispatcher("/stock").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/stock").forward(request, response);
    }
}
