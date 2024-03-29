package ir.Epy.MyStock.controllers;

import ir.Epy.MyStock.DAOs.CreditRequestDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created customer_id esihaj on 4/8/16.
 */

@WebServlet("/credit/requests")
public class ListCreditRequests extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setAttribute("req_list", CreditRequestDAO.I().getPendingRequests());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        request.getRequestDispatcher("/credit/requests.jsp").forward(request, response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
