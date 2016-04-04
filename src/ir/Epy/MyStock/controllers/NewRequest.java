package ir.Epy.MyStock.controllers;

import ir.Epy.MyStock.Constants;
import ir.Epy.MyStock.Database;
import ir.Epy.MyStock.exceptions.*;
import ir.Epy.MyStock.models.Customer;
import ir.Epy.MyStock.models.Stock;
import ir.Epy.MyStock.models.StockRequest;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;

/**
 * Created by root on 4/4/16.
 */
@WebServlet("/requests/new")
public class NewRequest extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ArrayList<String> errors = new ArrayList<String>();
        String id = request.getParameter("id");
        String symbol = request.getParameter("instrument");
        Integer price = Integer.parseInt(request.getParameter("price"));
        Integer quantity = Integer.parseInt(request.getParameter("quantity"));
        String type = request.getParameter("type");
        String buy_or_sell = request.getParameter("buy_or_sell");
        Stock stock = null;

        StringWriter org = new StringWriter();
        PrintWriter msg = new PrintWriter(org);
        try {
            Customer customer = Database.get_obj().get_customer(id);
            try {
                stock = Database.get_obj().get_stock(symbol);
                if (buy_or_sell.equals("buy"))
                    buy(customer, stock, price, quantity, type, msg);
                else
                    sell(customer, stock, price, quantity, type, msg);
            } catch (StockNotFoundException e) {
                if(customer.is_admin() && buy_or_sell.equals("sell")) {
                    stock = Database.get_obj().add_stock(symbol);
                    StockRequest req = StockRequest.create_request(id, stock, price, quantity,type,false);
                    customer.increase_share(symbol, 0);
                    if(type.equals("GTC"))
                        stock.add_sell_req(req);
                    req.process(msg);
                } else
                    errors.add(Constants.SymbolNotFoundMessage);
            }
        } catch (CustomerNotFoundException e) {
            errors.add(Constants.CustomerNotFoundMessage);
        } catch (NotEnoughMoneyException e) {
            errors.add(Constants.NotEnoughMoneyMessage);
        } catch (NotEnoughShareException e) {
            errors.add(Constants.NotEnoughShareMessage);
        }

        if(errors.size() > 0) {
            request.setAttribute("errors", errors);
            request.getRequestDispatcher("/requests/new.jsp").forward(request, response);
        } else {
            request.setAttribute("success_message", org.toString());
            request.getRequestDispatcher("/requests/index.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/requests/new.jsp").forward(request, response);
    }

    private void buy(Customer customer, Stock stock, Integer price, Integer quantity, String type, PrintWriter msg) throws NotEnoughMoneyException {
        String symbol = stock.get_symbol();
        StockRequest req = StockRequest.create_request(customer.id, stock, price, quantity, type, true);
        if(type.equals("GTC")) {
            if (!customer.can_buy(quantity, price))
                throw new NotEnoughMoneyException();
            customer.decrease_deposit(price * quantity);
            stock.add_buy_req(req);
        }
        try {
            req.process(msg);
        } catch (CustomerNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void sell(Customer customer, Stock stock, Integer price, Integer quantity, String type, PrintWriter msg) throws NotEnoughShareException {
        String symbol = stock.get_symbol();
        if (!customer.can_sell(symbol, quantity))
            throw new NotEnoughShareException();

        StockRequest req = StockRequest.create_request(customer.id, stock, price, quantity,type,false);
        if(type.equals("GTC")) {
            customer.decrease_share(symbol, quantity);
            stock.add_sell_req(req);
        }
        try {
            req.process(msg);
        } catch (CustomerNotFoundException e) {
            e.printStackTrace();
        }
    }
}
