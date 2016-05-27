package ir.Epy.MyStock.controllers;

import ir.Epy.MyStock.DAOs.CustomerDAO;
import ir.Epy.MyStock.DBConnection;
import ir.Epy.MyStock.models.Customer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;

/**
 * Created by py4_ on 5/26/16.
 */
public class FrontController  extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handleRequest(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handleRequest(request, response);
    }

    private void handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String className = request.getServletPath().substring(1, request.getServletPath().indexOf(".action"));
        try {
            HttpSession session = request.getSession();
            if (session != null && session.getAttribute("customer") == null) {
                Customer customer = CustomerDAO.I().findByUsername(request.getRemoteUser());
                if (customer != null) {
                    session.setAttribute("customer", customer);
                }
            }

            Class ctrlClass = Class.forName("ir.Epy.MyStock.controllers." + className);
            Method m = ctrlClass.getMethod("execute", HttpServletRequest.class, HttpServletResponse.class);
            String forward = (String)m.invoke(ctrlClass.newInstance(), request, response);
            request.getRequestDispatcher(forward).forward(request, response);
            //response.sendRedirect(forward);
        } catch (InvocationTargetException ex) {
            ex.printStackTrace();
            response.setContentType("text/html");
            if (ex.getTargetException() instanceof SQLException)
                response.getOutputStream().println("Error in accessing database!<p>Contact system administrator");
            else
                response.getOutputStream().println("Internal system error!<p>Contact system administrator");
        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("text/html");
            response.getOutputStream().println("Internal system error!<p>Contact system administrator");
        }
    }
}
