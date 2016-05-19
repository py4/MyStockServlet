package ir.Epy.MyStock.controllers;

import ir.Epy.MyStock.Constants;
import ir.Epy.MyStock.Database;
import ir.Epy.MyStock.exceptions.CustomerNotFoundException;
import ir.Epy.MyStock.exceptions.InvalidCreditValueRequest;
import ir.Epy.MyStock.models.Bank;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created customer_id esihaj on 4/8/16.
 */
@WebServlet("/credit/new")
public class NewCredit extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<String> errors = new ArrayList<>();

        String id = request.getParameter("customer_id");
        if(id == null || id.equals(""))
            errors.add("User id not provided");
        String credit = request.getParameter("credit_value");
        if(credit == null || credit.equals(""))
            errors.add("Credit not provided");
        String trans_type = request.getParameter("transaction_type");//is_deposit
        if(trans_type == null || trans_type.equals(""))
            errors.add("Transaction type not provided");
        if(!trans_type.equals("deposit") && !trans_type.equals("withdraw"))
            errors.add("Undefined transaction type");
        boolean is_deposit = trans_type.equals("deposit");

        if(errors.size() == 0) {
            try {
                Bank b  = Database.get_obj().getBank();
                b.add_request(id,Integer.parseInt(credit),is_deposit);
                request.setAttribute("success_message", Constants.CreditRequestAddedMessage);
                request.setAttribute("credit", Database.get_obj().get_customer(id).getDeposit());
                request.setAttribute("id", id);
                request.getRequestDispatcher("/credit/index.jsp").forward(request, response);
                return;
            } catch (CustomerNotFoundException e) {
                errors.add(Constants.CustomerNotFoundMessage);
            } catch (InvalidCreditValueRequest e) {
                errors.add(Constants.InvalidCreditValueMessage);
            } catch (NumberFormatException e) {
                errors.add(Constants.InvalidCreditValueMessage);
            }
        }

        request.setAttribute("errors", errors);
        request.getRequestDispatcher("/credit/new.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/credit/new.jsp").forward(request, response);
    }
}
