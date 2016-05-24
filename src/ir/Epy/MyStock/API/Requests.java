package ir.Epy.MyStock.API;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ir.Epy.MyStock.Constants;
import ir.Epy.MyStock.DAOs.CustomerDAO;
import ir.Epy.MyStock.DAOs.GTCDAO;
import ir.Epy.MyStock.DAOs.StockDAO;
import ir.Epy.MyStock.Database;
import ir.Epy.MyStock.exceptions.*;
import ir.Epy.MyStock.models.Customer;
import ir.Epy.MyStock.models.Stock;
import ir.Epy.MyStock.models.StockRequest;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.Data;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created customer_id py4_ on 5/6/16.
 */
@WebServlet("/api/requests")
public class Requests extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String payload = request.getParameterNames().nextElement();
        System.out.println("New Buy/Sell Request");
        try {
            JSONObject json = (JSONObject)new JSONParser().parse(payload);
            ArrayList<String> errors = new ArrayList<String>();
            String id = (String)json.get("id");
            String symbol = (String)json.get("instrument");
            Integer price = Integer.parseInt((String)json.get("price"));
            Integer quantity = Integer.parseInt((String)json.get("quantity"));
            String type = (String)json.get("type");
            String buy_or_sell = (String)json.get("buy_or_sell");
            StringWriter org = new StringWriter();
            PrintWriter msg = new PrintWriter(org);

            try {
                Customer customer = CustomerDAO.I().find(id);
                if (buy_or_sell.equals("buy")) {
                    StockRequest req = StockRequest.create_request(id, symbol, price, quantity, type, true);
                    if(!customer.can_buy(quantity, price))
                        errors.add(Constants.NotEnoughMoneyMessage);
                    else {
                        customer.decrease_deposit(price * quantity);
                        req.process(msg);
                    }
                }
                else {
                    if(!customer.can_sell(symbol, quantity))
                        errors.add(Constants.NotEnoughShareMessage);
                    StockRequest req = StockRequest.create_request(id, symbol, price, quantity, type, false);
                    if(type.equals("GTC"))
                        customer.decrease_share(symbol, quantity);
                    req.process(msg);
                }
            } catch (StockNotFoundException e) {
                errors.add(Constants.SymbolNotFoundMessage);
            } catch (CustomerNotFoundException e) {
                errors.add(Constants.CustomerNotFoundMessage);
            } catch (SQLException e) {
                e.printStackTrace();
            }


            response.setContentType("application/json");
            response.setHeader("Access-Control-Allow-Origin", "*");
            JSONObject obj = new JSONObject();
            if(errors.size() > 0)
                obj.put("errors", errors);
            else
                obj.put("msg", org.toString());
            //obj.put("status", "200");

            response.getWriter().print(obj);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setHeader("Access-Control-Allow-Origin", "*");
        //JSONObject obj = new JSONObject();
        try {
            Gson gson = new Gson();
            //obj.put("requests", gson.toJson(GTCDAO.I().getAll()));
            //System.out.println("{'requests':"+gson.toJson(GTCDAO.I().getAll())+"}");
            response.getWriter().print("{\"requests\":"+gson.toJson(GTCDAO.I().getAll())+"}");
        } catch (SQLException e) {
            e.printStackTrace();
        }




//            Gson gson = new Gson();
//            response.getWriter().write(gson.toJson(Database.get_obj().get_stock("rena")));

        //}
    }

}
