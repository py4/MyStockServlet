package ir.Epy.MyStock.API;

import ir.Epy.MyStock.database.Database;
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
import java.util.ArrayList;

/**
 * Created by py4_ on 5/6/16.
 */
@WebServlet("/api/requests")
public class Requests extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String payload = request.getParameterNames().nextElement();

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
            errors = Database.get_obj().addRequest(id, symbol, price, quantity, type, buy_or_sell, msg);
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
        JSONObject obj = new JSONObject();
        obj.put("requests", Database.get_obj().getReport());
        response.getWriter().print(obj);



//            Gson gson = new Gson();
//            response.getWriter().write(gson.toJson(Database.get_obj().get_stock("rena")));

        //}
    }

}
