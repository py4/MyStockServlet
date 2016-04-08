package ir.Epy.MyStock.controllers;

import ir.Epy.MyStock.Constants;
import ir.Epy.MyStock.Database;
import ir.Epy.MyStock.exceptions.CreditRequestNotFoundException;
import ir.Epy.MyStock.exceptions.CustomerNotFoundException;
import ir.Epy.MyStock.models.Bank;
import ir.Epy.MyStock.models.CreditRequest;

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
 * Created by esihaj on 4/8/16.
 */
@WebServlet("/admin/log")
public class GenerateLogs extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<String> errors = new ArrayList<>();
        try {
            StringWriter sw = new StringWriter();
            Database.get_obj().log_transactions_csv(sw);
            response.setContentType("application/csv");
            response.setHeader("Content-Disposition","attachment; filename=\"backup.csv\"");
            response.getWriter().print(sw.toString());
        } catch (Exception e) {
            errors.add(Constants.CSVLogFailedMessage);
            request.setAttribute("errors", errors);
            request.getRequestDispatcher("/admin/index.jsp").forward(request, response);
        }
    }
}
