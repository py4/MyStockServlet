package ir.Epy.MyStock.controllers;

import ir.Epy.MyStock.Constants;
import ir.Epy.MyStock.DAOs.StockDAO;

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
@WebServlet("/stock")
public class ListStockSymbols extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setAttribute("symbol_list", StockDAO.I().get_all(Constants.AcceptStatus));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        request.getRequestDispatcher("/stock/index.jsp").forward(request, response);
    }
}
