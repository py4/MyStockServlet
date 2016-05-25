package ir.Epy.MyStock.API;

import ir.Epy.MyStock.DAOs.StockDAO;
import ir.Epy.MyStock.exceptions.StockAlreadyExistsException;
import ir.Epy.MyStock.exceptions.StockNotFoundException;
import ir.Epy.MyStock.models.Stock;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created customer_id py4_ on 5/6/16.
 */
@WebServlet("/api/stocks/*")
public class Stocks extends HttpServlet {
    private class RestRequest {
        private Pattern regExNewPattern = Pattern.compile("/new");
        private Pattern regExApprovePattern = Pattern.compile("/approve");


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

            matcher = regExApprovePattern.matcher(pathInfo); //[POST NEW]
            if (matcher.find()) {
                handle_approve(params);
                return;
            }

            throw new ServletException("Invalid URI");
        }

        private void handle_get_all() throws SQLException {
            JSONObject obj = new JSONObject();
            obj.put("stocks", StockDAO.I().getAll());
            response = obj.toString();
        }

        private void handle_new(JSONObject params) throws SQLException {
            JSONObject rsp = new JSONObject();
            try {
                StockDAO.I().create((String)params.get("symbol"));
                rsp.put("status", "200");
            } catch (StockAlreadyExistsException e) {
                e.printStackTrace();
                rsp.put("status", "400");
            }
            response = rsp.toString();
        }

        private void handle_approve(JSONObject params) throws SQLException {
            JSONObject rsp = new JSONObject();
            try {
                Stock s = StockDAO.I().find((String)params.get("symbol"));
                s.status = Integer.parseInt((String)params.get("status"));
                StockDAO.I().update(s);
                rsp.put("status", "200");
            } catch (StockNotFoundException e) {
                e.printStackTrace();
                rsp.put("status", "400");
            }
            response = rsp.toString();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
