package ir.Epy.MyStock.controllers;

/**
 * Created customer_id esihaj on 4/8/16.
 */

import ir.Epy.MyStock.Constants;
import ir.Epy.MyStock.DAOs.CreditRequestDAO;
import ir.Epy.MyStock.exceptions.CreditRequestNotFoundException;
import ir.Epy.MyStock.exceptions.CustomerNotFoundException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import static ir.Epy.MyStock.Constants.AcceptStatus;
import static ir.Epy.MyStock.Constants.RejectStatus;

@WebServlet("/credit/credit_check")
public class AdminCreditCheck extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<String> errors = new ArrayList<>();

        String req_id = request.getParameter("req_id");
        if(req_id == null || req_id.equals(""))
            errors.add("Request id not provided");
        String action = request.getParameter("action");
        if(action == null || action.equals(""))
            errors.add("Action not provided");

        int action_status = 0;
        if(action.equals("accept"))
            action_status = AcceptStatus;
        else if (action.equals("deny"))
            action_status = RejectStatus;
        else errors.add("Action not defined");
        if(errors.size() == 0) {
            try {
                CreditRequestDAO.I().find(req_id).process_request(action_status);
                request.setAttribute("success_message", Constants.CreditRequestProcessedMessage + ": " + req_id +" [" + action + "]");
                request.getRequestDispatcher("/credit/requests").forward(request, response);
                return;
            } catch (CreditRequestNotFoundException e) {
                errors.add(Constants.CreditRequestNotFoundMessage);
            } catch (SQLException e) {
                errors.add(Constants.SQLExceptionMessage);
            } catch (CustomerNotFoundException e) {
                e.printStackTrace();
            }
        }

        request.setAttribute("errors", errors);
        request.getRequestDispatcher("/credit/requests").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/credit/requests").forward(request, response);
    }
}
