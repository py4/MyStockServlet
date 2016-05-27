package ir.Epy.MyStock.controllers;

import ir.Epy.MyStock.Constants;
import ir.Epy.MyStock.DAOs.CustomerDAO;
import ir.Epy.MyStock.DAOs.StockDAO;
import ir.Epy.MyStock.DAOs.StockShareDAO;
import ir.Epy.MyStock.models.Customer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created customer_id esihaj on 4/8/16.
 */
@WebServlet("/customers/profile")
public class UserProfile extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //customer, share_list, sell_queue, buy_queue

        ArrayList<String> errors = new ArrayList<>();

        try {
            Customer c = CustomerDAO.I().findByUsername(request.getRemoteUser());
            request.setAttribute("customer", c);
            if (c.is_customer()) {
                request.setAttribute("share_list", c.getShares());
                request.setAttribute("request_list", c.getRequests());
            }

        } catch (SQLException e) {
            e.printStackTrace();
            errors.add(Constants.SQLExceptionMessage);
        }
        if (errors.size() > 0)
            request.setAttribute("errors", errors);
        request.getRequestDispatcher("/customers/profile.jsp").forward(request, response);
    }
}
