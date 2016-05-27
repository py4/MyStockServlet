package ir.Epy.MyStock.controllers;

import ir.Epy.MyStock.Constants;
import ir.Epy.MyStock.DAOs.StockDAO;
import ir.Epy.MyStock.exceptions.StockAlreadyExistsException;
import ir.Epy.MyStock.exceptions.StockNotFoundException;
import ir.Epy.MyStock.models.Stock;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created customer_id esihaj on 4/8/16.
 */
@WebServlet("/stock/approve")
public class ListUnapprovedStockSymbols extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ArrayList<String> errors = new ArrayList<String>();
        String symbol = request.getParameter("symbol");
        String status = request.getParameter("status");
        if(symbol == null || symbol.equals(""))
            errors.add("Stock symbol not provided");
        if(status == null || status.equals(""))
            errors.add("Status not provided");

        if(errors.size() == 0) {
            try {
                Stock s = StockDAO.I().find(symbol);
                s.status = Integer.parseInt(status);
                StockDAO.I().update(s);
            } catch (SQLException e) {
                errors.add(Constants.SQLExceptionMessage);
                e.printStackTrace();
            } catch (StockNotFoundException e) {
                e.printStackTrace();
                errors.add(Constants.SymbolNotFoundMessage);
            }
        }

        if (errors.size() > 0) {
            request.setAttribute("errors", errors);
            request.getRequestDispatcher("/stock/approve.jsp").forward(request, response);
        } else {
            HttpSession session = request.getSession(false);
            session.setAttribute("success_message", symbol + " " + Constants.SymbolUpdatedMessage);
            response.sendRedirect("/stock");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setAttribute("symbol_list", StockDAO.I().get_all(Constants.PendingStatus));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        request.getRequestDispatcher("/stock/approve.jsp").forward(request, response);
    }
}
