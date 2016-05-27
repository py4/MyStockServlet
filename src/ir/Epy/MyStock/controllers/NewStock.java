package ir.Epy.MyStock.controllers;

import ir.Epy.MyStock.Constants;
import ir.Epy.MyStock.DAOs.CustomerDAO;
import ir.Epy.MyStock.DAOs.GTCDAO;
import ir.Epy.MyStock.DAOs.StockDAO;
import ir.Epy.MyStock.Utils;
import ir.Epy.MyStock.exceptions.CustomerNotFoundException;
import ir.Epy.MyStock.exceptions.StockAlreadyExistsException;
import ir.Epy.MyStock.exceptions.StockNotFoundException;
import ir.Epy.MyStock.models.Customer;
import ir.Epy.MyStock.models.StockRequest;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created customer_id root on 4/4/16.
 */
@WebServlet("/stock/new")
public class NewStock extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ArrayList<String> errors = new ArrayList<String>();

        String symbol = request.getParameter("symbol");
        if(symbol == null || symbol.equals(""))
            errors.add(Constants.NotProvided(Constants.Names.stock));

        String quantity = request.getParameter("quantity");
        if(quantity == null || quantity.equals(""))
            errors.add(Constants.NotProvided(Constants.Names.quantity));

        String base_price = request.getParameter("base_price");
        if(quantity == null || quantity.equals(""))
            errors.add(Constants.NotProvided(Constants.Names.base_price));

        StringWriter org = new StringWriter();
        PrintWriter msg = new PrintWriter(org);

        if(errors.size() == 0) {
            try {
                Customer c = CustomerDAO.I().findByUsername(request.getRemoteUser());
                StockDAO.I().create(symbol, c.id);
                StockRequest req = StockRequest.create_request(c.id, symbol, Integer.parseInt(base_price), Integer.parseInt(quantity), "GTC", false);
                req.status = Constants.AcceptStatus;
                GTCDAO.I().update(req);

                /*if(req.status == Constants.PendingStatus) {
                    Utils.redirect_with_message(request, response, "/customers/home.jsp", Constants.PendingRequestMessage);
                    return;
                }*/
                req.process(msg);

            } catch (SQLException e) {
                errors.add(Constants.SQLExceptionMessage);
                e.printStackTrace();
            } catch (StockAlreadyExistsException e) {
                e.printStackTrace();
                errors.add(Constants.SymbolAlreadyExistsMessage);
            } catch (StockNotFoundException e) {
                e.printStackTrace();
            } catch (CustomerNotFoundException e) {
                e.printStackTrace();
            }
        }


        if (errors.size() > 0)
            Utils.forward_with_error(request, response, "/stock/new.jsp", errors);
        else
            Utils.redirect_with_message(request, response, "/customers/home.jsp", symbol + " " + Constants.SymbolAddedMessage);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/stock/new.jsp").forward(request, response);
    }
}