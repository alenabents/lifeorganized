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

@WebServlet("/controllers.deleteItems")
public class DeleteItems extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("userEmail");

        ItemDbUtil dbUtil = new ItemDbUtil();
        dbUtil.deleteItem(id,email);
        dbUtil.deleteSubTable(id, email);
        RequestDispatcher dispatcher = request.getRequestDispatcher("homePage.jsp");

        // send list to home.jsp
        dispatcher.forward(request, response);
    }

}