package ir.Epy.MyStock.API;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ir.Epy.MyStock.Database;
import ir.Epy.MyStock.exceptions.CustomerExistsException;
import ir.Epy.MyStock.models.Customer;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by py4_ on 5/6/16.
 */
@WebServlet("/api/customers")
public class Customers  extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setHeader("Access-Control-Allow-Origin", "*");
        ArrayList<Customer> customers = Database.get_obj().get_customers();
        Gson gson = new Gson();
        response.getWriter().write(gson.toJson(customers));
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String payload = request.getParameterNames().nextElement();
        try {
            JSONObject json = (JSONObject)new JSONParser().parse(payload);
            Database.get_obj().add_customer((String)json.get("id"),(String)json.get("name"),(String)json.get("family"));
            response.setContentType("application/json");
            response.setHeader("Access-Control-Allow-Origin", "*");
            JSONObject obj = new JSONObject();
            obj.put("status", "200");
            response.getWriter().print(obj);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (CustomerExistsException e) {
            e.printStackTrace();
        }


    }
}
