package ir.Epy.MyStock.controllers;

import ir.Epy.MyStock.Constants;
import ir.Epy.MyStock.DAOs.CustomerDAO;
import ir.Epy.MyStock.DAOs.StockDAO;
import ir.Epy.MyStock.exceptions.CustomerNotFoundException;
import ir.Epy.MyStock.exceptions.StockAlreadyExistsException;
import ir.Epy.MyStock.exceptions.StockNotFoundException;
import ir.Epy.MyStock.models.Customer;
import ir.Epy.MyStock.models.StockRequest;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created customer_id root on 4/4/16.
 */
@WebServlet("/stock/new")
public class NewStock extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ArrayList<String> errors = new ArrayList<String>();
        String symbol = request.getParameter("symbol");
        if(symbol == null || symbol.equals(""))
            errors.add("Stock symbol not provided");
        if(errors.size() == 0) {
            try {
                StockDAO.I().create(symbol);
            } catch (SQLException e) {
                errors.add(Constants.SQLExceptionMessage);
                e.printStackTrace();
            } catch (StockAlreadyExistsException e) {
                e.printStackTrace();
                errors.add(Constants.SymbolAlreadyExistsMessage);
            }
        }

        if (errors.size() > 0) {
            request.setAttribute("errors", errors);
            request.getRequestDispatcher("/stock/new.jsp").forward(request, response);
        } else {
            HttpSession session = request.getSession(false);
            session.setAttribute("success_message", symbol + " " + Constants.SymbolAddedMessage);
            response.sendRedirect("/customers/home.jsp");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/stock/new.jsp").forward(request, response);
    }
}