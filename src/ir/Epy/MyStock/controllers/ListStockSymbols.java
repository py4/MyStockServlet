package ir.Epy.MyStock.controllers;

import ir.Epy.MyStock.Constants;
import ir.Epy.MyStock.database.Database;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by esihaj on 4/8/16.
 */
@WebServlet("/stock")
public class ListStockSymbols extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<String> errors = new ArrayList<>();
        Set<String> s = new HashSet<>();

        try {
            s = Database.get_obj().getStockSymbols();
        } catch (SQLException e) {
            errors.add(Constants.DB_ERROR);
        }
        request.setAttribute("errors", errors);
        request.setAttribute("symbol_list", s);
        request.getRequestDispatcher("/stock/index.jsp").forward(request, response);
    }
}
