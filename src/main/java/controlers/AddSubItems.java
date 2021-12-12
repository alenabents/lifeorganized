package controlers;
import Model.ItemDbUtil;
import Model.Item;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = "/controllers.addSubItems")
public class AddSubItems extends HttpServlet {
    ItemDbUtil db = new ItemDbUtil();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String id = request.getParameter("id");
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("userEmail");


        if (id == null) {
            request.getRequestDispatcher("homePage.jsp").forward(request, response);
        } else {
            request.setAttribute("id", id);

            request.getRequestDispatcher("addSubPage.jsp").forward(request, response);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String id = request.getParameter("id");
        String label = request.getParameter("ItemLabel");
        String date = request.getParameter("ItemDate");
        String time = request.getParameter("ItemTime");
        HttpSession session = request.getSession();

        String email = (String) session.getAttribute("userEmail");

        if (!label.trim().equals("")) {
            db.addSubItem(id, email, new Item(label, date, time, 0));
        }

        response.sendRedirect("homePage.jsp");

    }


}