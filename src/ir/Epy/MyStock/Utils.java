package ir.Epy.MyStock;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by py4_ on 5/27/16.
 */
public class Utils {
    public static void redirect_with_message(HttpServletRequest request, HttpServletResponse response, String to, String msg) throws IOException {
        HttpSession session = request.getSession(false);
        session.setAttribute("success_message", msg);
        response.sendRedirect(to);
    }

    public static void forward_with_error(HttpServletRequest request, HttpServletResponse response, String to, ArrayList<String> errors) throws ServletException, IOException {
        request.setAttribute("errors", errors);
        request.getRequestDispatcher(to).forward(request, response);
    }
}
