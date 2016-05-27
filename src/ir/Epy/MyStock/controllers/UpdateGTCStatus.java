package ir.Epy.MyStock.controllers;

import ir.Epy.MyStock.Constants;
import ir.Epy.MyStock.DAOs.ConfigDAO;
import ir.Epy.MyStock.DAOs.CustomerDAO;
import ir.Epy.MyStock.DAOs.GTCDAO;
import ir.Epy.MyStock.DAOs.StockDAO;
import ir.Epy.MyStock.Utils;
import ir.Epy.MyStock.exceptions.CustomerNotFoundException;
import ir.Epy.MyStock.exceptions.StockNotFoundException;
import ir.Epy.MyStock.models.Customer;
import ir.Epy.MyStock.models.GTCRequest;
import ir.Epy.MyStock.models.StockRequest;

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

@WebServlet("/accountant/update_gtc_status")
public class UpdateGTCStatus extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String loc = "/accountant/manage_limited_requests.jsp";
        ArrayList<String> errors = new ArrayList<String>();

        String action = request.getParameter("action");
        String req_id = request.getParameter("req_id");

        if(req_id == null || req_id.equals(""))
            errors.add(Constants.NotProvided("req_id"));

        if(errors.size() == 0) {
            try {

                GTCRequest req = GTCDAO.I().find(Integer.parseInt(req_id));
                if(action.equals("accept")) {
                    req.status = Constants.AcceptStatus;
                    GTCDAO.I().update(req);
                    Utils.redirect_with_message(request, response, "/accountant/manage_limited_requests.jsp", Constants.RequestAcceptedMessage);
                    return;
                }
                else {
                    req.status = Constants.RejectStatus;
                    GTCDAO.I().update(req);
                    Customer c = CustomerDAO.I().find(req.customer_id);
                    if(!req.customer_id.equals(StockDAO.I().find(req.stock_symbol).owner_id)) {
                        if (req.is_buy) {
                            c.increase_deposit(req.base_price * req.quantity);
                            Utils.redirect_with_message(request, response, loc, Constants.RefundMessage(c.username, req.base_price * req.quantity));
                            return;
                        } else {
                            c.increase_share(req.stock_symbol, req.quantity);
                            Utils.redirect_with_message(request, response, loc, Constants.ReshareMessage(c.username, req.stock_symbol, req.quantity));
                            return;
                        }
                    }
                    Utils.redirect_with_message(request, response, "/accountant/manage_limited_requests.jsp", Constants.RequestRejectedMessage);
                }
                return;
            } catch (SQLException e) {
                errors.add(Constants.SQLExceptionMessage);
                e.printStackTrace();
            } catch (StockNotFoundException e) {
                e.printStackTrace();
            } catch (CustomerNotFoundException e) {
                e.printStackTrace();
            }
        }

        Utils.forward_with_error(request, response, loc, errors);
    }
}
