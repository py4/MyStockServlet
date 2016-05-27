package ir.Epy.MyStock.controllers;

import ir.Epy.MyStock.Constants;
import ir.Epy.MyStock.DAOs.CustomerDAO;
import ir.Epy.MyStock.DAOs.StockDAO;
import ir.Epy.MyStock.exceptions.*;
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
@WebServlet("/requests/new")
public class NewRequest extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ArrayList<String> errors = new ArrayList<String>();
        String username = request.getRemoteUser();
        String symbol = request.getParameter("instrument");
        Integer price = Integer.parseInt(request.getParameter("price"));
        Integer quantity = Integer.parseInt(request.getParameter("quantity"));
        String type = request.getParameter("type");
        String buy_or_sell = request.getParameter("buy_or_sell");
        StringWriter org = new StringWriter();
        PrintWriter msg = new PrintWriter(org);


        try {
            Customer customer = CustomerDAO.I().findByUsername(username);
            if (buy_or_sell.equals("buy")) {
                StockRequest req = StockRequest.create_request(customer.id, symbol, price, quantity, type, true);
                if(!customer.can_buy(quantity, price))
                    errors.add(Constants.NotEnoughMoneyMessage);
                else {
                    System.out.println("we can buy it");
                    customer.decrease_deposit(price * quantity);
                    req.process(msg);
                }
            }
            else {
                if(!customer.can_sell(symbol, quantity))
                    errors.add(Constants.NotEnoughShareMessage);
                StockRequest req = StockRequest.create_request(customer.id, symbol, price, quantity, type, false);
                if(type.equals("GTC"))
                    customer.decrease_share(symbol, quantity);
                req.process(msg);
            }
        } catch (StockNotFoundException e) {
            errors.add(Constants.SymbolNotFoundMessage);
        } catch (CustomerNotFoundException e) {
            errors.add(Constants.CustomerNotFoundMessage);
        } catch (SQLException e) {
            e.printStackTrace();
        }


        if(errors.size() > 0) {
            request.setAttribute("errors", errors);
            request.getRequestDispatcher("/requests/new.jsp").forward(request, response);
        } else {
            HttpSession session = request.getSession(false);
            session.setAttribute("success_message", org.toString());
            response.sendRedirect("/requests/index.jsp");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ArrayList<String> errors = new ArrayList<>();

        try {
            request.setAttribute("symbol_list", StockDAO.I().get_all(Constants.AcceptStatus));
        } catch (SQLException e) {
            e.printStackTrace();
            errors.add(Constants.SQLExceptionMessage);
        }
        if (errors.size() > 0)
            request.setAttribute("errors", errors);
        request.getRequestDispatcher("/requests/new.jsp").forward(request, response);
    }


}
