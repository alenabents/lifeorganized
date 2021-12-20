package controlers;


import Model.Item;
import Model.ItemDbUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = "/controllers.editItems")
public class EditItems extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String id = request.getParameter("id");
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("userEmail");

        if (id == null) {
            request.getRequestDispatcher("editPage.jsp").forward(request, response);
        } else {
            Item task = null;
            task = ItemDbUtil.getItemWithId(id, email);
            if (task != null) {
                String label = task.getLabel();
                String date = task.getDate();
                String time = task.getTime();
                int check = task.getCheck();
                request.setAttribute("id", id);
                request.setAttribute("itemLabel", label);
                request.setAttribute("itemDate", date);
                request.setAttribute("itemTime", time);
                request.setAttribute("itemCheck", check);

            }
            request.getRequestDispatcher("editPage.jsp").forward(request, response);
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
            ItemDbUtil.updateTodo(new Item(label, date, time, 0), id, email);
        }

        response.sendRedirect("homePage.jsp");
    }
}
