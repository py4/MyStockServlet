package ir.Epy.MyStock.API;

import ir.Epy.MyStock.database.Database;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by py4_ on 5/6/16.
 */
@WebServlet("/api/stocks")
public class Stocks extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setHeader("Access-Control-Allow-Origin", "*");
        JSONObject obj = new JSONObject();
        try {
            obj.put("stocks", Database.get_obj().get_stock_symbols());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        response.getWriter().print(obj);
    }
}
