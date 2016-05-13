package ir.Epy.MyStock.controllers;

import ir.Epy.MyStock.database.Database;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by esihaj on 4/8/16.
 */

@WebServlet("/admin/requests")
public class ListCreditRequests extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("req_list", Database.get_obj().getBank().getRequests());
        request.getRequestDispatcher("/admin/requests.jsp").forward(request, response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
