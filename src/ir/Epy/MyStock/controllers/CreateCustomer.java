package ir.Epy.MyStock.controllers;

import ir.Epy.MyStock.Constants;
import ir.Epy.MyStock.DAOs.CustomerDAO;
import ir.Epy.MyStock.exceptions.CustomerAlreadyExistsException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by py4_ on 5/27/16.
 */
@WebServlet("/customers/create")
public class CreateCustomer extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<String> errors = new ArrayList<String>();

        String name = request.getParameter("name");
        if(name == null || name.equals(""))
            errors.add("User name not provided");
        String family = request.getParameter("family");
        if(family == null || family.equals(""))
            errors.add("User family not provided");
        String username = request.getParameter("username");
        if(username == null || username.equals(""))
            errors.add("Username not provided");
        String password = request.getParameter("password");
        if(password == null || password.equals(""))
            errors.add("Password not provided");

        if(errors.size() == 0) {
            try {
                CustomerDAO.I().create(username, password, name, family);
                HttpSession session = request.getSession(false);
                session.setAttribute("success_message", Constants.CustomerAddedMessage);
                response.sendRedirect("/customers/home.jsp");
                return;
            } catch (CustomerAlreadyExistsException e) {
                errors.add(Constants.CustomerExistsMessage);
            } catch (SQLException e) {
                errors.add(Constants.SQLExceptionMessage);
            }
        }

        request.setAttribute("errors", errors);
        request.getRequestDispatcher("/customers/new.jsp").forward(request, response);
        return;
    }
}
