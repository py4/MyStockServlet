package ir.Epy.MyStock.controllers;

import ir.Epy.MyStock.Constants;
import ir.Epy.MyStock.DAOs.CustomerDAO;
import ir.Epy.MyStock.DAOs.RoleDAO;
import ir.Epy.MyStock.exceptions.CustomerAlreadyExistsException;
import ir.Epy.MyStock.exceptions.CustomerNotFoundException;
import ir.Epy.MyStock.models.Customer;
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

@WebServlet("/admin/update_role")
public class UpdateRoles extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<String> errors = new ArrayList<String>();

        String username = request.getParameter("username");
        if(username == null || username.equals(""))
            errors.add(Constants.NotProvided("username"));

        String new_role_name = request.getParameter("role");
        if(new_role_name == null || new_role_name.equals(""))
            errors.add(Constants.NotProvided("role"));

        if(!Role.valid_role(new_role_name))
            errors.add(Constants.NotValidRoleMessage);

        if(errors.size() == 0) {
            try {
                Role role = RoleDAO.I().find(username);
                if(role == null)
                    throw new RoleNotFoundException();
                role.role_name = new_role_name;
                RoleDAO.I().update(role);
                HttpSession session = request.getSession(false);
                session.setAttribute("success_message", Constants.RoleUpdatedMessage);
                response.sendRedirect("/admin/manage_roles.jsp");
                return;
            } catch (SQLException e) {
                errors.add(Constants.SQLExceptionMessage);
            } catch (RoleNotFoundException e) {
                errors.add(Constants.RoleNotFoundMessage);
            }
        }

        request.setAttribute("errors", errors);
        request.getRequestDispatcher("/admin/manage_roles.jsp").forward(request, response);
        return;
    }
}
