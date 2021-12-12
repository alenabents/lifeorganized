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

@WebServlet("/controllers.checkSubItems")
public class CheckSubItems extends HttpServlet {

    public CheckSubItems() {
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
        ItemDbUtil dbUtil = new ItemDbUtil();
        dbUtil.setSubCheck(email, idStr, isChkSms);
        String[] ids = idStr.split(" ");

        request.setAttribute("id", ids[1]);
        RequestDispatcher dispatcher = request.getRequestDispatcher(pageName);
        dispatcher.forward(request, response);
    }

}
