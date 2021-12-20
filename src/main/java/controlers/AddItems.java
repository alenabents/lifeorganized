package controlers;

import Model.Item;
import Model.ItemDbUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/controllers.addItems")
public class AddItems extends HttpServlet {

    public AddItems() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String label = request.getParameter("ItemLabel");
        String date = request.getParameter("ItemDate");
        String time = request.getParameter("ItemTime");

        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("userEmail");

        if (label.length() > 0 || date.length() > 0 || time.length() > 0) {
            ItemDbUtil.addItem(new Item(label, date, time, 0), email);
        }

        request.setAttribute("listItems", ItemDbUtil.getItems(email));
        RequestDispatcher dispatcher = request.getRequestDispatcher("homePage.jsp");
        dispatcher.forward(request, response);

    }

}