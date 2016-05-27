package ir.Epy.MyStock.controllers;

import ir.Epy.MyStock.Constants;
import ir.Epy.MyStock.DAOs.ConfigDAO;
import ir.Epy.MyStock.DAOs.RoleDAO;
import ir.Epy.MyStock.models.Role;

import javax.management.relation.RoleNotFoundException;
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

// @TODO : validating existense of users....

@WebServlet("/admin/update_limit")
public class UpdateLimit extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<String> errors = new ArrayList<String>();

        String new_limit = request.getParameter("new_limit");
        if(new_limit == null || new_limit.equals(""))
            errors.add(Constants.NotProvided(Constants.limit));
        else if(!new_limit.matches("\\d+"))
            errors.add(Constants.ShouldBeNumeric(Constants.limit));

        if(errors.size() == 0) {
            try {
                ConfigDAO.I().set_limit(Integer.parseInt(new_limit));
                HttpSession session = request.getSession(false);
                session.setAttribute("success_message", Constants.UpdatedMessage(Constants.limit));
                response.sendRedirect("/admin/manage_limit.jsp");
                return;
            } catch (SQLException e) {
                errors.add(Constants.SQLExceptionMessage);
                e.printStackTrace();
            }
        }

        request.setAttribute("errors", errors);
        request.getRequestDispatcher("/admin/manage_limit.jsp").forward(request, response);
        return;
    }
}
