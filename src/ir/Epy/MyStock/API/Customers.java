package ir.Epy.MyStock.API;

import com.google.gson.Gson;
import ir.Epy.MyStock.DAOs.CustomerDAO;
import ir.Epy.MyStock.Database;
import ir.Epy.MyStock.exceptions.CustomerAlreadyExistsException;
import ir.Epy.MyStock.models.Customer;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created customer_id py4_ on 5/6/16.
 */
@WebServlet("/api/customers")
public class Customers  extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setHeader("Access-Control-Allow-Origin", "*");
        try {
            ArrayList<Customer> customers = CustomerDAO.I().getAll();
            Gson gson = new Gson();
//            JSONObject jobj = new JSONObject();
//            String j = "{'users':" +gson.toJson(customers) + ", 'errors':[]}";
//            jobj.put("users",gson.toJson(customers));
//            jobj.put("errors", "[]");
//            System.out.println("json: ");
//            System.out.println(j);
//            System.out.println(gson.toJson(customers));
            response.getWriter().write(gson.toJson(customers));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String payload = request.getParameterNames().nextElement();
        try {
            JSONObject json = (JSONObject)new JSONParser().parse(payload);
            CustomerDAO.I().create((String)json.get("id"),(String)json.get("name"),(String)json.get("family"), 1000);
            //Database.get_obj().add_customer();
            response.setContentType("application/json");
            response.setHeader("Access-Control-Allow-Origin", "*");
            JSONObject obj = new JSONObject();
            obj.put("status", "200");
            response.getWriter().print(obj);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (CustomerAlreadyExistsException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
