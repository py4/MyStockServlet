package ir.Epy.MyStock.API;

import com.google.gson.Gson;
import ir.Epy.MyStock.Constants;
import ir.Epy.MyStock.DAOs.CustomerDAO;
import ir.Epy.MyStock.exceptions.CustomerAlreadyExistsException;
import ir.Epy.MyStock.exceptions.CustomerNotFoundException;
import ir.Epy.MyStock.models.Customer;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created customer_id py4_ on 5/6/16.
 */
@WebServlet("/api/users/*")
public class Customers  extends HttpServlet {

    private class RestRequest {
        // Accommodate two requests, one for all resources, another for a specific resource
        private Pattern regExIdPattern = Pattern.compile("/([0-9]*)");
        private Pattern regExNewPattern = Pattern.compile("/new");
        private Pattern regExEditPattern = Pattern.compile("/edit");


        private Integer id;
        public String response;

        public RestRequest(String pathInfo, JSONObject params) throws ServletException, SQLException {
            // regex parse pathInfo
            Matcher matcher;
            Gson gson = new Gson();

            if (pathInfo == null) { //[GET ALL]
                ArrayList<Customer> customers = CustomerDAO.I().getAll();
                response = gson.toJson(customers);
                return;
            }

            matcher = regExNewPattern.matcher(pathInfo); //[POST NEW]
            if (matcher.find()) {
                JSONObject obj = new JSONObject();

                try {
                    CustomerDAO.I().create((String)params.get("username"),(String)params.get("password"), (String)params.get("name"),(String)params.get("family"));
                    obj.put("status", "200");
                } catch (CustomerAlreadyExistsException e) {
                    e.printStackTrace();
                    obj.put("status", "400");
                }
                response = obj.toString();
                return;
            }

            // Check for ID case first, since the All pattern would also match
            matcher = regExIdPattern.matcher(pathInfo); //[GET ID]
            if (matcher.find()) {
                id = Integer.parseInt(matcher.group(1));
                try {
                    response = gson.toJson(CustomerDAO.I().find(""+id));
                } catch (CustomerNotFoundException e) {
                    e.printStackTrace();
                }
                return;
            }



            throw new ServletException("Invalid URI");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setHeader("Access-Control-Allow-Origin", "*");
        try {
            System.out.println("path_info: " + request.getPathInfo());
            System.out.println("srv_path_info: " + request.getServletPath());
            RestRequest rs = new RestRequest(request.getPathInfo(), null);
            response.getWriter().write(rs.response);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String payload = request.getParameterNames().nextElement();
        try {
            System.out.println("payload "+ payload);
            JSONObject json = (JSONObject)new JSONParser().parse(payload);
            RestRequest rs = new RestRequest(request.getPathInfo(), json);
            response.setContentType("application/json");
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.getWriter().print(rs.response);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        }


    }
}
