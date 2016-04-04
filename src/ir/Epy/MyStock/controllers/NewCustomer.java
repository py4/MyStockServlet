package ir.Epy.MyStock.controllers;

import ir.Epy.MyStock.Constants;
import ir.Epy.MyStock.Database;
import ir.Epy.MyStock.exceptions.CustomerExistsException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by root on 4/4/16.
 */
@WebServlet("/customers/new")
public class NewCustomer extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<String> errors = new ArrayList<String>();

        String id = request.getParameter("id");
        if(id == null || id.equals(""))
            errors.add("User id not provided");
        String name = request.getParameter("name");
        if(name == null || name.equals(""))
            errors.add("User name not provided");
        String family = request.getParameter("family");
        if(family == null || family.equals(""))
            errors.add("User family not provided");

        if(errors.size() == 0) {
            try {
                Database.get_obj().add_customer(id,name,family);
                request.setAttribute("success_message", Constants.CustomerAddedMessage);
                request.getRequestDispatcher("/customers/index.jsp").forward(request, response);
                return;
            } catch (CustomerExistsException e) {
                errors.add(Constants.CustomerExistsMessage);
            }
        }

        request.setAttribute("errors", errors);
        request.getRequestDispatcher("/customers/new.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/customers/new.jsp").forward(request, response);
    }
}
