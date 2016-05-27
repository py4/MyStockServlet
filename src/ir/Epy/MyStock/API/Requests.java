package ir.Epy.MyStock.API;

import com.google.gson.Gson;
import ir.Epy.MyStock.Constants;
import ir.Epy.MyStock.DAOs.CustomerDAO;
import ir.Epy.MyStock.DAOs.GTCDAO;
import ir.Epy.MyStock.DAOs.StockDAO;
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
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created customer_id py4_ on 5/6/16.
 */
@WebServlet("/api/requests/*")
public class Requests extends HttpServlet {

    private class RestRequest {
        private Pattern regExNewPattern = Pattern.compile("/new");
        private Pattern regExApprovePattern = Pattern.compile("/approve");
        private Pattern regExLimitPattern = Pattern.compile("/limit"); //[GET and POST]



        public String response;

        public RestRequest(String method, String pathInfo, JSONObject params) throws ServletException, SQLException {
            // regex parse pathInfo
            Matcher matcher;

            if (method.equals("GET")) { //[GET]
                matcher = regExLimitPattern.matcher(pathInfo); //[GET Limit List]
                if (matcher.find()) {
                    //handle_get_limited();
                } else handle_get_all(); // [GET All]
                return;
            }

            matcher = regExNewPattern.matcher(pathInfo); //[POST NEW]
            if (matcher.find()) {
                handle_new(params);
                return;
            }

            matcher = regExApprovePattern.matcher(pathInfo); //[POST Approve]
            if (matcher.find()) {
                handle_approve(params);
                return;
            }

            throw new ServletException("Invalid URI");
        }

        private void handle_get_all() throws SQLException {
            JSONObject obj = new JSONObject();
            obj.put("requests", GTCDAO.I().getAll());
            response = obj.toString();
        }

        private void handle_new(JSONObject params) throws SQLException {
            JSONObject rsp = new JSONObject();
            ArrayList<String> errors = new ArrayList<String>();
            String id = (String)params.get("id");
            String symbol = (String)params.get("instrument");
            Integer price = Integer.parseInt((String)params.get("price"));
            Integer quantity = Integer.parseInt((String)params.get("quantity"));
            String type = (String)params.get("type");
            String buy_or_sell = (String)params.get("buy_or_sell");
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
            }

            if(errors.size() > 0) {
                rsp.put("errors", errors);
                rsp.put("status", "400");
            }
            else {
                rsp.put("msg", org.toString());
                rsp.put("status", "200");
            }
            response = rsp.toString();
        }

        private void handle_approve(JSONObject params) throws SQLException {
            JSONObject rsp = new JSONObject();
            StockRequest sr = GTCDAO.I().find(Integer.parseInt((String) params.get("id")));
            if(sr != null) {
                sr.status = Integer.parseInt((String)params.get("status"));
                GTCDAO.I().update(sr);
                rsp.put("status", "200");
            } else {
                rsp.put("status", "400");
            }
            response = rsp.toString();
        }
    }




    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String payload = request.getParameterNames().nextElement();
        System.out.println("New Buy/Sell Request");
        try {
            JSONObject json = (JSONObject)new JSONParser().parse(payload);



            response.setContentType("application/json");
            response.setHeader("Access-Control-Allow-Origin", "*");
            JSONObject obj = new JSONObject();

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
            //obj.put("requests", gson.toJson(GTCDAO.I().get_all()));
            //System.out.println("{'requests':"+gson.toJson(GTCDAO.I().get_all())+"}");
            response.getWriter().print("{\"requests\":"+gson.toJson(GTCDAO.I().getAll())+"}");
        } catch (SQLException e) {
            e.printStackTrace();
        }




//            Gson gson = new Gson();
//            response.getWriter().write(gson.toJson(Database.get_obj().get_stock("rena")));

        //}
    }

}
