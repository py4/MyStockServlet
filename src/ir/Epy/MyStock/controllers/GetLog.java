package ir.Epy.MyStock.controllers;

import com.opencsv.CSVWriter;
import ir.Epy.MyStock.Constants;
import ir.Epy.MyStock.DAOs.ConfigDAO;
import ir.Epy.MyStock.DAOs.TransactionLogDAO;
import ir.Epy.MyStock.Utils;
import ir.Epy.MyStock.models.StockTransactionLog;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Array;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by py4_ on 5/27/16.
 */

// @TODO : validating existense of users....

@WebServlet("/admin/get_log")
public class GetLog extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<String> errors = new ArrayList<>();
        final String[] FILE_HEADER = {"Buyer", "Seller", "instrument", "type of trade", "quantity", "Buyer Remained Money", "Seller Current Money"};
        StringWriter sw = new StringWriter();
        CSVWriter csv_printer = new CSVWriter(sw);
        csv_printer.writeNext(FILE_HEADER);
        try {
            for (StockTransactionLog s_log : TransactionLogDAO.I().getAll())
                csv_printer.writeNext(s_log.getRecord());
            csv_printer.flush();
            csv_printer.close();
            response.setContentType("application/csv");
            response.setHeader("Content-Disposition","attachment; filename=\"logs.csv\"");
            response.getWriter().print(sw.toString());
            return;
        } catch (SQLException e) {
            e.printStackTrace();
            errors.add(Constants.SQLExceptionMessage);
        }
        Utils.forward_with_error(request, response, "/customers/home.jsp", errors);
        return;
    }
}
