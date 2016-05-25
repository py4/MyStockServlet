package ir.Epy.MyStock.API;

import com.google.gson.Gson;
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
        private Pattern regExLoginPattern = Pattern.compile("/login");


        private Integer id;
        public String response;

        public RestRequest(String method, String pathInfo, JSONObject params) throws ServletException, SQLException {
            // regex parse pathInfo
            Matcher matcher;

            if (method.equals("GET")) { //[GET ALL]
                handle_get_all();
                return;
            }

            matcher = regExNewPattern.matcher(pathInfo); //[POST NEW]
            if (matcher.find()) {
                handle_new(params);
                return;
            }

            matcher = regExEditPattern.matcher(pathInfo); //[POST NEW]
            if (matcher.find()) {
                handle_edit(params);
                return;
            }

            matcher = regExLoginPattern.matcher(pathInfo); //[POST NEW]
            if (matcher.find()) {
                handle_login(params);
                return;
            }

            // Check for ID case first, since the All pattern would also match
            matcher = regExIdPattern.matcher(pathInfo); //[GET ID]
            if (matcher.find()) {
                id = Integer.parseInt(matcher.group(1));
                handle_getId();
                return;
            }



            throw new ServletException("Invalid URI");
        }

        private void handle_new(JSONObject params) throws SQLException {
            JSONObject rsp = new JSONObject();

            try {
                CustomerDAO.I().create((String)params.get("username"),(String)params.get("password"), (String)params.get("name"),(String)params.get("family"));
                rsp.put("status", "200");
            } catch (CustomerAlreadyExistsException e) {
                e.printStackTrace();
                rsp.put("status", "400");
            }
            response = rsp.toString();
        }

        private void handle_get_all() throws SQLException {
            Gson gson = new Gson();
            ArrayList<Customer> customers = CustomerDAO.I().getAll();
            response = gson.toJson(customers);
        }
        private void handle_getId() throws SQLException {
            Gson gson = new Gson();
            try {
                response = gson.toJson(CustomerDAO.I().find(""+id));
            } catch (CustomerNotFoundException e) {
                e.printStackTrace();
            }
        }
        private void handle_edit(JSONObject params) throws SQLException {
            JSONObject rsp = new JSONObject();
            try {
                Customer c = CustomerDAO.I().find((String)params.get("id"));
                if(params.get("password") != null)
                    c.password = (String) params.get("password");
                if(params.get("name") != null)
                    c.name = (String) params.get("name");
                if(params.get("family") != null)
                    c.family = (String) params.get("family");
                if(params.get("role") != null)
                    c.role = (String) params.get("role");

                CustomerDAO.I().update(c);
                rsp.put("status", "200");
            } catch (CustomerNotFoundException e) {
                e.printStackTrace();
                rsp.put("status", "400");
            }
            response = rsp.toString();
        }
        private void handle_login(JSONObject params) throws SQLException {
            JSONObject rsp = new JSONObject();
            String id = CustomerDAO.I().login((String)params.get("username"), (String)params.get("password"));
            System.out.println("login id = " + id);
            if (!id.equals("")) {// couldn't login
                rsp.put("status", "200");
                rsp.put("id", id);
            } else {
                rsp.put("status", "400");
            }
            response = rsp.toString();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setHeader("Access-Control-Allow-Origin", "*");
        try {
            System.out.println("path_info: " + request.getPathInfo());
            System.out.println("srv_path_info: " + request.getServletPath());
            RestRequest rs = new RestRequest("GET", request.getPathInfo(), null);
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
            JSONObject json = (JSONObject)new JSONParser().parse(payload);
            RestRequest rs = new RestRequest("POST", request.getPathInfo(), json);
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
