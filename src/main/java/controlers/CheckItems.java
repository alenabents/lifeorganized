package controlers;

import Model.ItemDbUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebServlet("/controllers.checkItems")
public class CheckItems extends HttpServlet {

    public CheckItems() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        Boolean check = request.getParameter("check") != null;
        String [] checked = request.getParameterValues("check");

        HttpSession session = request.getSession();

        String email = (String) session.getAttribute("userEmail");

        ItemDbUtil dbUtil = new ItemDbUtil();

        dbUtil.checkItem(email, Integer.parseInt(checked[0]), check);

        // making an html attribute
        request.setAttribute("check", check);

        // making a request dispatcher
        RequestDispatcher dispatcher = request.getRequestDispatcher("homePage.jsp");

        // send list to home.jsp
        dispatcher.forward(request, response);

    }
}
