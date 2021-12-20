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

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("userEmail");
        String idStr = request.getParameter("id");
        String pageName = request.getParameter("pageName");
        String[] chkSms = request.getParameterValues("chkSms");
        int isChkSms = 0;
        if (chkSms != null && chkSms.length > 0) {
            isChkSms = 1;
        }
        ItemDbUtil.setCheck(email, idStr, isChkSms);
        request.setAttribute("id", idStr);

        RequestDispatcher dispatcher = request.getRequestDispatcher(pageName);
        dispatcher.forward(request, response);
    }

}
