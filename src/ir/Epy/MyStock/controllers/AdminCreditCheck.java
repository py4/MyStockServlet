package ir.Epy.MyStock.controllers;

/**
 * Created customer_id esihaj on 4/8/16.
 */

import ir.Epy.MyStock.Constants;
import ir.Epy.MyStock.Database;
import ir.Epy.MyStock.exceptions.CreditRequestNotFoundException;
import ir.Epy.MyStock.exceptions.CustomerNotFoundException;
import ir.Epy.MyStock.exceptions.InvalidCreditValueRequest;
import ir.Epy.MyStock.models.Bank;
import ir.Epy.MyStock.models.CreditRequest;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/admin/credit_check")
public class AdminCreditCheck extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<String> errors = new ArrayList<>();

        String req_id = request.getParameter("req_id");
        if(req_id == null || req_id.equals(""))
            errors.add("Request id not provided");
        String action = request.getParameter("action");
        if(action == null || action.equals(""))
            errors.add("Action not provided");
        CreditRequest.TransactionStatus action_status = CreditRequest.TransactionStatus.PENDING;
        if(action.equals("accept"))
            action_status = CreditRequest.TransactionStatus.ACCEPTED;
        else if (action.equals("deny"))
            action_status = CreditRequest.TransactionStatus.REJECTED;
        else errors.add("Action not defined");

        if(errors.size() == 0) {
            try {
                Bank b  = Database.get_obj().getBank();
                b.process_request(req_id,action_status);
                request.setAttribute("success_message", Constants.CreditRequestProcessedMessage + ": " + req_id +" [" + action + "]");
                request.getRequestDispatcher("/admin/requests").forward(request, response);
                return;
            } catch (CustomerNotFoundException e) {
                errors.add(Constants.CustomerNotFoundMessage);
            } catch (CreditRequestNotFoundException e) {
                errors.add(Constants.CreditRequestNotFoundMessage);
            }
        }

        request.setAttribute("errors", errors);
        request.getRequestDispatcher("/admin/requests").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/admin/requests").forward(request, response);
    }
}
