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

@WebServlet("/controllers.deleteFriendItems")
public class DeleteFriendItems extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("userEmail");

        ItemDbUtil dbUtil = new ItemDbUtil();
        dbUtil.deleteFriendItem(id, email);
        dbUtil.deleteSubFriendTable(id, email);

        RequestDispatcher dispatcher = request.getRequestDispatcher("friendsPage.jsp");
        dispatcher.forward(request, response);
    }

}
